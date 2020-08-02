package net.vexelon.appicons.appstore;

import net.vexelon.appicons.entities.UrlIcon;
import net.vexelon.appicons.wireframe.IconParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppStoreIconsParser implements IconParser {

    private static final Logger logger = Logger.getLogger(AppStoreIconsParser.class.getName());

    @Override
    public List<UrlIcon> parse(InputStream input) {
        var icons = new ArrayList<UrlIcon>();
        String line = "";
        String art60 = null;
        String art100 = null;
        String art512 = null;
        int resultsCount = 0;

        try (var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            while ((line = reader.readLine()) != null) {
                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Line={0}", line);
                }

                if (resultsCount == 0) {
                    resultsCount = NumberUtils.toInt(StringUtils.defaultString(
                            StringUtils.substringBetween(line, "\"resultCount\":", ",")).strip(),
                            0);
                } else if (resultsCount > 0) {
                    if (art60 == null) {
                        art60 = StringUtils.substringBetween(line, "\"artworkUrl60\":", ",");
                        if (!StringUtils.isBlank(art60)) {
                            var icon = new UrlIcon();
                            icon.setUrl(art60.strip());
                            icon.setWidth(60);
                            icon.setHeight(60);
                            icons.add(icon);
                        }
                    }

                    if (art100 == null) {
                        art100 = StringUtils.substringBetween(line, "\"artworkUrl100\":", ",");
                        if (!StringUtils.isBlank(art100)) {
                            var icon = new UrlIcon();
                            icon.setUrl(art100.strip());
                            icon.setWidth(100);
                            icon.setHeight(100);
                            icons.add(icon);
                        }
                    }

                    if (art512 == null) {
                        art512 = StringUtils.substringBetween(line, "\"artworkUrl512\":", ",");
                        if (!StringUtils.isBlank(art512)) {
                            var icon = new UrlIcon();
                            icon.setUrl(art512.strip());
                            icon.setWidth(512);
                            icon.setHeight(512);
                            icons.add(icon);
                        }
                    }
                }

                // There are no more than 3 icon sizes for an app store app
                if (icons.size() == 3) {
                    break;
                }

                line = reader.readLine();
            }

            return icons;
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing app store lookup result! Line=" + line, e);
        }
    }
}
