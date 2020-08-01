package net.vexelon.appicons.utils;

public final class UrlUtils {

    public static String appstore(String id, String country, String language) {
        var sb = new StringBuilder();
        sb.append("https://itunes.apple.com/lookup?id=").append(id);

        if (!country.isBlank()) {
            sb.append("&country=").append(country);
        }

        if (!language.isBlank()) {
            sb.append("&lang=").append(language);
        }

        return sb.toString();
    }

}
