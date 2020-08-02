package net.vexelon.appicons;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public static void main(String[] args) {
        try {
//            var downloader = AppIcons.appstore()
//                    .setCountry("de")
//                    .setLanguage("de")
//                    .timeout(200)
//                    .build();
//            downloader.getUrls("389801252").forEach(url -> System.out.println(url));

            var downloader = AppIcons.playstore()
                    .setCountry("bg")
                    .setLanguage("bg")
                    .timeout(200)
                    .build();
            downloader.getUrls("net.vexelon.currencybg.app").forEach(url -> System.out.println(url));

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
