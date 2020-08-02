package net.vexelon.appicons.playstore;

import net.vexelon.appicons.appstore.AppStoreIconsParser;
import net.vexelon.appicons.entities.UrlIcon;
import net.vexelon.appicons.wireframe.IconParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

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

    @Override
    public List<UrlIcon> parse(InputStream input) {
        var icons = new ArrayList<UrlIcon>();

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
                        String url = line.substring(beginning + 10, end).strip();

                        var sizes = url.split("=s");
                        if (sizes.length > 1) {
                            url = sizes[0];
                        }

                        var icon = new UrlIcon();
                        icon.setUrl(url);
                        icon.setWidth(512);
                        icon.setHeight(512);
                        icons.add(icon);

                        icon = new UrlIcon();
                        icon.setUrl(url + "=s180");
                        icon.setWidth(180);
                        icon.setHeight(180);
                        icons.add(icon);

                        icon = new UrlIcon();
                        icon.setUrl(url + "=s360");
                        icon.setWidth(360);
                        icon.setHeight(360);
                        icons.add(icon);

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
