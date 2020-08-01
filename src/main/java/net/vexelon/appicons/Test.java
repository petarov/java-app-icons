package net.vexelon.appicons;

public class Test {

    public static void main(String[] args) {
        try {
            var downloader = AppIcons.appstore()
                    .timeout(200)
                    .build();

            downloader.getUrls("389801252").forEach(url -> System.out.println(url));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
