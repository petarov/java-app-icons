package net.vexelon.appicons;

import java.nio.file.Path;

public class Test {

    public static void main(String[] args) {
        try {
            var path = Path.of("/Users/ppetrov/Developer/playground/gh/java-app-icons/downloads");
            var downloader = AppIcons.appstore()
//                    .proxy(BuilderConfig.ProxyType.HTTP, "localhost", 1080, "user1", "pass1")
//                    .skipSSLVerify(false)
                    .country("de")
                    .language("de")
                    .timeout(200)
                    .size60(true)
                    .size100(true)
                    .build();
//            downloader.getUrls("389801252").forEach(url -> System.out.println(url));
//            downloader.getMultiUrls(Set.of("389801252", "310633997")).forEach((k, v) -> v.forEach(url -> System.out.println(url)));
            downloader.getFiles("389801252", path).forEach(url -> System.out.println(url));

            var downloader2 = AppIcons.playstore()
                    .sizes(180, 200, 60)
                    .country("bg")
                    .language("bg")
                    .timeout(200)
                    .build();
            downloader2.getFiles("net.vexelon.currencybg.app", path).forEach(url -> System.out.println(url));

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
