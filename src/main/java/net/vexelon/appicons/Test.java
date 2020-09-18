package net.vexelon.appicons;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        var executorService = Executors.newCachedThreadPool();

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
//                    .buildAsync(executorService);
            downloader.getUrls("389801252").forEach(url -> System.out.println(url));
            downloader.getMultiUrls(Set.of("389801252", "310633997")).forEach((k, v) -> v.forEach(url -> System.out.println(url)));
            downloader.getFiles("389801252", path).forEach(url -> System.out.println(url));
            downloader.getMultiFiles(Set.of("389801252", "310633997"), path).forEach((k, v) -> System.out.println("K=" + k + " V=" + v.toString()));
//                downloader.getUrls("389801252", new DownloadCallback<List<IconURL>>() {
//                    @Override public void onError(String appId, Throwable t) {
//                        t.printStackTrace();
//                    }
//
//                    @Override public void onSuccess(String appId, List<IconURL> icons) {
//                        icons.forEach(url -> System.out.println(url));
//                    }
//                });
//            downloader.getMultiUrls(Set.of("389801252", "310633997"), new DownloadCallback<>() {
//                @Override public void onError(String appId, Throwable t) {
//                    System.err.println("Error for " + appId);
//                    t.printStackTrace();
//                }
//
//                @Override public void onSuccess(String appId, List<IconURL> icons) {
//                    icons.forEach(url -> System.out.println(url));
//                }
//            });

//                downloader.getUrls("389801252").forEach(url -> System.out.println(url));

            System.out.println("** AFTER ASYNC");

//            var downloader2 = AppIcons.playstore()
//                    .sizes(200, 60)
//                    .country("bg")
//                    .language("bg")
//                    .timeout(200)
//                    .build());
//            downloader2.getFiles("net.vexelon.currencybg.app", path).forEach(url -> System.out.println(url));

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (executorService != null) {
                executorService.shutdown();
                try {
                    if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executorService.shutdownNow();
                }
            }
        }
    }
}
