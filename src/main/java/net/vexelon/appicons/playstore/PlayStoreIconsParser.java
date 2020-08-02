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
        String line = "";
        String img = null;

        try {
            int count = 0;
            var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

            line = reader.readLine();
            while (line != null) {
                if (logger.isLoggable(Level.INFO)) {
                    logger.log(Level.INFO, "Line={0}", line);
                }

                count += line.length();

                // Find the fierst image itemprop, because for what it seems, this is the app logo image.
                int needle = line.indexOf("itemprop=\"image\"");
                if (needle > 0) {
                    // Search for the start of the image some 350 chars before the needle. This number is somewhat
                    // arbitrary and may need adjustment!
                    int beginning = line.indexOf("<img src=", needle - 350);
                    if (beginning > 0) {
                        // find the last quote character surrounding the image url
                        int end = line.indexOf("\"", beginning + 10);
                        // start from the first quote that contains the image url till the last quote found
                        img = line.substring(beginning + 10, end);

                        var icon = new UrlIcon();
                        icon.setUrl(img.strip());
                        icon.setWidth(180);
                        icon.setHeight(180);
                        icons.add(icon);

                        // we're done
                        break;
                    }
                }

                line = reader.readLine();
            }

            System.out.println("SIZE=" + count);

            return icons;
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing app store lookup result! Line=" + line, e);
        }
    }
}
