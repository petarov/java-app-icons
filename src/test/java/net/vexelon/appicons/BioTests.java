package net.vexelon.appicons;

import net.vexelon.appicons.utils.HashingUtils;
import net.vexelon.appicons.utils.StringUtils;
import net.vexelon.appicons.wireframe.BioDownloader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Set;

public class BioTests {

    private BioDownloader appStore;
    private BioDownloader playStore;

    @BeforeEach
    void initBefore() {
        appStore = AppIcons.appstore().build();
        playStore = AppIcons.playstore().build();
    }

    @Test
    void test_getUrls() {
        appStore.getUrls("389801252").forEach(iconURL -> {
            Assertions.assertTrue(StringUtils.defaultString(iconURL.getUrl()).startsWith("https://"));
            Assertions.assertEquals("JPG", iconURL.getType());
            Assertions.assertTrue(iconURL.getWidth() > 0);
            Assertions.assertTrue(iconURL.getHeight() > 0);
        });

        playStore.getUrls("com.instagram.android").forEach(iconURL -> {
            Assertions.assertTrue(StringUtils.defaultString(iconURL.getUrl()).startsWith("https://"));
            Assertions.assertEquals("PNG", iconURL.getType());
            Assertions.assertEquals(512, iconURL.getWidth());
            Assertions.assertEquals(512, iconURL.getHeight());
        });
    }

    @Test
    void test_getUrls_CustomSizes() {
        Assertions.assertEquals(1,
                AppIcons.appstore().size60(true).size100(false).size512(false).build().getUrls("389801252").size());
        Assertions.assertEquals(3,
                AppIcons.playstore().sizes(60, 100, 180).build().getUrls("com.instagram.android").size());
    }

    @Test
    void test_getFiles() {
        try {
            var path = Utils.getJavaAppIcons();

            appStore.getFiles("389801252", path).forEach(iconFile -> {
                Assertions.assertEquals(Utils.FILENAME_LEN, Path.of(iconFile.getPath()).getFileName().toString().length());
                Assertions.assertEquals("jpg", iconFile.getExtension());
                Assertions.assertTrue(iconFile.getWidth() > 0);
                Assertions.assertTrue(iconFile.getHeight() > 0);
            });

            playStore.getFiles("com.instagram.android", path).forEach(iconFile -> {
                Assertions.assertEquals("png", iconFile.getExtension());
                Assertions.assertEquals(512, iconFile.getWidth());
                Assertions.assertEquals(512, iconFile.getHeight());
                Assertions.assertEquals("b8f46faeb5e8075c96b2dafa060226597a475e85",
                        HashingUtils.sha1(Path.of(iconFile.getPath())));
            });

            playStore.getFiles("com.whatsapp", path).forEach(iconFile -> {
                Assertions.assertEquals("png", iconFile.getExtension());
                Assertions.assertEquals(512, iconFile.getWidth());
                Assertions.assertEquals(512, iconFile.getHeight());
                Assertions.assertEquals("9e44265bacd9c417671e4f17bb86020aaec67aef",
                        HashingUtils.sha1(Path.of(iconFile.getPath())));
            });

            playStore.getFiles("com.snapchat.android", path).forEach(iconFile -> {
                Assertions.assertEquals("png", iconFile.getExtension());
                Assertions.assertEquals(512, iconFile.getWidth());
                Assertions.assertEquals(512, iconFile.getHeight());
                Assertions.assertEquals("45563c8d6cbc811007b137bfbefbee5a9d30d2ea",
                        HashingUtils.sha1(Path.of(iconFile.getPath())));
            });
        } catch (Throwable t) {
            Assertions.fail(t);
        }
    }

    @Test
    void test_getMultiUrls() {
        var aResults = appStore.getMultiUrls(Set.of("389801252", "310633997"));
        var app1 = aResults.get("389801252");
        Assertions.assertNotNull(app1);
        Assertions.assertEquals(3, app1.size());

        var app2 = aResults.get("310633997");
        Assertions.assertNotNull(app2);
        Assertions.assertEquals(3, app2.size());

        var gResults = playStore.getMultiUrls(Set.of("com.zhiliaoapp.musically", "com.instagram.android"));
        var app3 = gResults.get("com.zhiliaoapp.musically");
        Assertions.assertNotNull(app3);
        Assertions.assertEquals(1, app3.size());

        var app4 = gResults.get("com.instagram.android");
        Assertions.assertNotNull(app4);
        Assertions.assertEquals(1, app4.size());
    }

    @Test
    void test_getMultiFiles() {
        try {
            var path = Utils.getJavaAppIcons();

            var aResults = appStore.getMultiFiles(Set.of("389801252", "310633997"), path);
            var app1 = aResults.get("389801252");
            Assertions.assertNotNull(app1);
            Assertions.assertEquals(3, app1.size());

            var app2 = aResults.get("310633997");
            Assertions.assertNotNull(app2);
            Assertions.assertEquals(3, app2.size());

            var gResults = playStore.getMultiFiles(
                    Set.of("com.zhiliaoapp.musically", "com.instagram.android", "com.whatsapp"), path);
            var app3 = gResults.get("com.zhiliaoapp.musically");
            Assertions.assertNotNull(app3);
            Assertions.assertEquals(1, app3.size());
            Assertions.assertEquals("ed3cdd5e60914796143165300e2b30d65458d7ed",
                    HashingUtils.sha1(Path.of(app3.get(0).getPath())));

            var app4 = gResults.get("com.instagram.android");
            Assertions.assertNotNull(app4);
            Assertions.assertEquals(1, app4.size());
            Assertions.assertEquals("b8f46faeb5e8075c96b2dafa060226597a475e85",
                    HashingUtils.sha1(Path.of(app4.get(0).getPath())));

            var app5 = gResults.get("com.whatsapp");
            Assertions.assertNotNull(app5);
            Assertions.assertEquals(1, app5.size());
            Assertions.assertEquals("9e44265bacd9c417671e4f17bb86020aaec67aef",
                    HashingUtils.sha1(Path.of(app5.get(0).getPath())));
        } catch (Throwable t) {
            Assertions.fail(t);
        }
    }
}
