package net.vexelon.appicons;

import java.util.Set;

public class Test {

    public static void main(String[] args) {
        try {
            var downloader = AppIcons.appstore()
//                    .proxy(BuilderConfig.ProxyType.HTTP, "localhost", 1080, "user1", "pass1")
//                    .skipSSLVerify(false)
                    .country("de")
                    .language("de")
                    .timeout(200)
                    .size60(false)
                    .size100(false)
                    .build();
            downloader.getUrls("389801252").forEach(url -> System.out.println(url));
            downloader.getMultiUrls(Set.of("389801252", "310633997")).forEach((k, v) -> v.forEach(url -> System.out.println(url)));

//            var downloader = AppIcons.playstore()
//                    .sizes(180, 200, 60)
//                    .setCountry("bg")
//                    .setLanguage("bg")
//                    .timeout(200)
//                    .build();
//            downloader.getUrls("net.vexelon.currencybg.app").forEach(url -> System.out.println(url));

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
