package net.vexelon.appicons;

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
            Assertions.assertTrue(iconURL.getWidth() > 0);
            Assertions.assertTrue(iconURL.getHeight() > 0);
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
            var path = TestUtils.getJavaAppIcons();

            appStore.getFiles("389801252", path).forEach(iconFile -> {
                Assertions.assertEquals(TestUtils.FILENAME_LEN, Path.of(iconFile.getPath()).getFileName().toString().length());
                Assertions.assertEquals("jpg", iconFile.getExtension());
                Assertions.assertTrue(iconFile.getWidth() > 0);
                Assertions.assertTrue(iconFile.getHeight() > 0);
            });

            playStore.getFiles("com.instagram.android", path).forEach(iconFile -> {
                Assertions.assertEquals(TestUtils.FILENAME_LEN, Path.of(iconFile.getPath()).getFileName().toString().length());
                Assertions.assertEquals("png", iconFile.getExtension());
                Assertions.assertTrue(iconFile.getWidth() > 0);
                Assertions.assertTrue(iconFile.getHeight() > 0);
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
            var path = TestUtils.getJavaAppIcons();

            var aResults = appStore.getMultiFiles(Set.of("389801252", "310633997"), path);
            var app1 = aResults.get("389801252");
            Assertions.assertNotNull(app1);
            Assertions.assertEquals(3, app1.size());

            var app2 = aResults.get("310633997");
            Assertions.assertNotNull(app2);
            Assertions.assertEquals(3, app2.size());

            var gResults = playStore.getMultiFiles(Set.of("com.zhiliaoapp.musically", "com.instagram.android"), path);
            var app3 = gResults.get("com.zhiliaoapp.musically");
            Assertions.assertNotNull(app3);
            Assertions.assertEquals(1, app3.size());

            var app4 = gResults.get("com.instagram.android");
            Assertions.assertNotNull(app4);
            Assertions.assertEquals(1, app4.size());
        } catch (Throwable t) {
            Assertions.fail(t);
        }
    }
}
