package net.vexelon.appicons.playstore;

import net.vexelon.appicons.utils.StringUtils;
import net.vexelon.appicons.wireframe.IconParser;
import net.vexelon.appicons.wireframe.entities.IconURL;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayStoreParser implements IconParser {

    private static final Logger logger = Logger.getLogger(PlayStoreParser.class.getName());

    private final PlayStoreBuilder.PlayStoreConfig config;

    public PlayStoreParser(PlayStoreBuilder.PlayStoreConfig config) {
        this.config = config;
    }

    @Override
    public List<IconURL> parse(InputStream input) {
        var icons = new ArrayList<IconURL>();

        try (var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            final int BACKBUFFER_SIZE = 1024;
            final char[] ITEM_PROP = "itemprop=\"image\"".toCharArray();

            var backBuffer = new StringBuilder(BACKBUFFER_SIZE);
            var snapshot = new char[4096];
            int charsRead;

            // Using a char buffer instead of .readLine() saves about 10K of reads
            while ((charsRead = reader.read(snapshot)) > 0) {
                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Line={0}", new String(snapshot));
                }

                // Find the first image itemprop, because for what it seems, this is the app logo image.
                int needle = StringUtils.indexOf(snapshot, ITEM_PROP);
                if (needle > 0) {
                    // append the fresh snapshot onto the previously read BACKBUFFER_SIZE chars back buffer
                    backBuffer.append(snapshot);

                    // Search for the first image in the back buffer
                    int beginning = backBuffer.indexOf("<img");
                    if (beginning > 0) {
                        // Search for src attribute independently of img element
                        int srcAttr = backBuffer.indexOf("src=", beginning);
                        // find the last quote character surrounding the image url
                        int end = backBuffer.indexOf("\"", srcAttr + 5);
                        // start from the first quote that contains the image url till the last quote found
                        String found = backBuffer.substring(srcAttr + 5, end).strip();

                        String url;

                        var sizes = found.split("=s");
                        if (sizes.length > 1) {
                            url = sizes[0];
                        } else {
                            url = found;
                        }

                        if (config.getSizes().isEmpty()) {
                            var icon = new IconURL();
                            icon.setUrl(url);
                            icon.setType("PNG");
                            icon.setWidth(512);
                            icon.setHeight(512);
                            icons.add(icon);
                        } else {
                            config.getSizes().forEach(size -> {
                                var icon = new IconURL();
                                icon.setUrl(url + "=s" + size);
                                icon.setType("PNG");
                                icon.setWidth(size);
                                icon.setHeight(size);
                                icons.add(icon);
                            });
                        }

                        // we're done
                        break;
                    }
                }

                // we only wanna "remember" the last BACKBUFFER_SIZE chars
                backBuffer.setLength(0);
                backBuffer.append(snapshot, Math.max(0, charsRead - BACKBUFFER_SIZE), Math.min(charsRead, BACKBUFFER_SIZE));
            }

            return icons;
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing play store lookup result!", e);
        }
    }
}
