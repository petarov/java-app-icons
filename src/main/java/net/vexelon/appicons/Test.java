package net.vexelon.appicons;

import net.vexelon.appicons.wireframe.DownloadCallback;
import net.vexelon.appicons.wireframe.entities.IconURL;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        try {
            var path = Path.of("/Users/ppetrov/Developer/playground/gh/java-app-icons/downloads");
            try (var downloader = AppIcons.appstore()
                    .proxy(BuilderConfig.ProxyType.HTTP, "localhost", 1080, "user1", "pass1")
//                    .skipSSLVerify(false)
                    .async(true)
                    .country("de")
                    .language("de")
                    .timeout(200)
                    .size60(true)
                    .size100(true)
                    .build()
            ) {
//            downloader.getUrls("389801252").forEach(url -> System.out.println(url));
//            downloader.getMultiUrls(Set.of("389801252", "310633997")).forEach((k, v) -> v.forEach(url -> System.out.println(url)));
//            downloader.getFiles("389801252", path).forEach(url -> System.out.println(url));
//            downloader.getMultiFiles(Set.of("389801252", "310633997"), path).forEach((k, v) -> System.out.println("K=" + k + " V=" + v.toString()));
//                downloader.getUrls("389801252", new DownloadCallback<List<IconURL>>() {
//                    @Override public void onError(String appId, Throwable t) {
//                        t.printStackTrace();
//                    }
//
//                    @Override public void onSuccess(String appId, List<IconURL> icons) {
//                        icons.forEach(url -> System.out.println(url));
//                    }
//                });
                downloader.getMultiUrls(Set.of("389801252", "310633997"), new DownloadCallback<>() {
                    @Override public void onError(String appId, Throwable t) {
                        System.err.println("Error for " + appId);
                        t.printStackTrace();
                    }

                    @Override public void onSuccess(String appId, Map<String, List<IconURL>> stringListMap) {
                        stringListMap.forEach((k, v) -> System.out.println("K=" + k + " V=" + v.toString()));
                    }
                });

                System.out.println("** AFTER ASYNC");
            }


//            try (var downloader2 = AppIcons.playstore()
//                    .sizes(200, 60)
//                    .country("bg")
//                    .language("bg")
//                    .timeout(200)
//                    .build()) {
//                downloader2.getFiles("net.vexelon.currencybg.app", path).forEach(url -> System.out.println(url));
//            }

        } catch (
                Throwable t) {
            t.printStackTrace();
        }
    }
}
