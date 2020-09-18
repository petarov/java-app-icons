package net.vexelon.appicons.utils;

public final class AppURLUtils {

    /**
     * @param country  Optional 2-letter country code.
     * @param language Optional 2-letter language code.
     * @return A new iTunes API url for the given app {@code id}.
     */
    public static String appstore(String id, String country, String language) {
        var sb = new StringBuilder();
        sb.append("https://itunes.apple.com/lookup?id=").append(id);

        if (StringUtils.isNotBlank(country)) {
            sb.append("&country=").append(country);
        }

        if (StringUtils.isNotBlank(language)) {
            sb.append("&lang=").append(language);
        }

        return sb.toString();
    }

    /**
     * @param country  Optional 2-letter country code.
     * @param language Optional 2-letter language code.
     * @return A new Playstore url for the given app {@code id}.
     */
    public static String playstore(String id, String country, String language) {
        var sb = new StringBuilder();
        sb.append("https://play.google.com/store/apps/details?id=").append(id);

        if (StringUtils.isNotBlank(country)) {
            sb.append("&gl=").append(country);
        }

        if (StringUtils.isNotBlank(language)) {
            sb.append("&hl=").append(language);
        }

        return sb.toString();
    }

}
