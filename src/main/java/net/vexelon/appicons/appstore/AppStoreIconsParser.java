package net.vexelon.appicons.appstore;

import net.vexelon.appicons.entities.URLIcon;
import net.vexelon.appicons.wireframe.IconParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppStoreIconsParser implements IconParser {

    private static final Logger logger = Logger.getLogger(AppStoreIconsParser.class.getName());

    private final AppStoreIconsBuilder.AppStoreConfig config;

    public AppStoreIconsParser(AppStoreIconsBuilder.AppStoreConfig config) {
        this.config = config;
    }

    @Override
    public List<URLIcon> parse(InputStream input) {
        var icons = new HashMap<Integer, URLIcon>();
        String line = "";
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
                }

                if (resultsCount > 0) {
                    for (var size : config.getSizes()) {
                        if (!icons.containsKey(size)) {
                            String url = StringUtils.substringBetween(line, "\"artworkUrl" + size + "\":", ",");
                            if (!StringUtils.isBlank(url)) {
                                var icon = new URLIcon();
                                icon.setUrl(url.substring(1, url.length() - 1).strip());
                                icon.setType("JPG");
                                icon.setWidth(size);
                                icon.setHeight(size);
                                icons.put(size, icon);
                            }
                        }
                    }
                }

                // Stop parsing, if all images requested were found
                if (icons.size() == config.getSizes().size()) {
                    break;
                }
            }

            return new ArrayList<>(icons.values());
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing app store lookup result! Line=" + line, e);
        }
    }
}
