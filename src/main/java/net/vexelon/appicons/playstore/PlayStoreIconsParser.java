package net.vexelon.appicons.playstore;

import net.vexelon.appicons.entities.URLIcon;
import net.vexelon.appicons.wireframe.IconParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayStoreIconsParser implements IconParser {

    private static final Logger logger = Logger.getLogger(PlayStoreIconsParser.class.getName());

    private final PlayStoreIconsBuilder.PlayStoreConfig config;

    public PlayStoreIconsParser(PlayStoreIconsBuilder.PlayStoreConfig config) {
        this.config = config;
    }

    @Override
    public List<URLIcon> parse(InputStream input) {
        var icons = new ArrayList<URLIcon>();

        try (var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            var buf = CharBuffer.allocate(4096);

            // Using a char buffer instead of .readLine(), saves about 10K of reads
            while (reader.read(buf) > 0) {
                buf.flip();
                String line = buf.toString();

                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Line={0}", line);
                }

                // Find the first image itemprop, because for what it seems, this is the app logo image.
                int needle = line.indexOf("itemprop=\"image\"");
                if (needle > 0) {
                    // Search for the start of the image some 350 chars before the needle. This number is somewhat
                    // arbitrary and may need adjustment!
                    int beginning = line.indexOf("<img src=", needle - 350);
                    if (beginning > 0) {
                        // find the last quote character surrounding the image url
                        int end = line.indexOf("\"", beginning + 10);
                        // start from the first quote that contains the image url till the last quote found
                        line = line.substring(beginning + 10, end).strip();

                        String url;

                        var sizes = line.split("=s");
                        if (sizes.length > 1) {
                            url = sizes[0];
                        } else {
                            url = line;
                        }

                        if (config.getSizes().isEmpty()) {
                            var icon = new URLIcon();
                            icon.setUrl(url);
                            icon.setType("PNG");
                            icon.setWidth(512);
                            icon.setHeight(512);
                            icons.add(icon);
                        } else {
                            config.getSizes().forEach(size -> {
                                var icon = new URLIcon();
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

                buf.clear();
            }

            return icons;
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing play store lookup result!", e);
        }
    }
}
