package net.vexelon.appicons;

import net.vexelon.appicons.utils.StringUtils;
import net.vexelon.appicons.wireframe.AsyncDownloader;
import net.vexelon.appicons.wireframe.DownloadCallback;
import net.vexelon.appicons.wireframe.entities.IconFile;
import net.vexelon.appicons.wireframe.entities.IconURL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncTests {

    private static final int TIMEOUT = 10_000;

    private static ExecutorService executorService;
    private AsyncDownloader appStore;
    private AsyncDownloader playStore;

    @BeforeAll
    static void init() {
        executorService = Executors.newCachedThreadPool();
    }

    @AfterAll
    static void cleanup() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    @BeforeEach
    void initBefore() {
        appStore = AppIcons.appstore().buildAsync(executorService);
        playStore = AppIcons.playstore().buildAsync(executorService);
    }

    @Test
    void test_getUrls() {
        appStore.getUrls("389801252", new DownloadCallback<>() {
            @Override
            public void onError(String appId, Throwable t) {
                Assertions.fail("Failed for: " + appId, t);
            }

            @Override
            public void onSuccess(String appId, List<IconURL> iconURLS) {
                iconURLS.forEach(iconURL -> {
                    Assertions.assertTrue(StringUtils.defaultString(iconURL.getUrl()).startsWith("https://"));
                    Assertions.assertEquals("JPG", iconURL.getType());
                    Assertions.assertTrue(iconURL.getWidth() > 0);
                    Assertions.assertTrue(iconURL.getHeight() > 0);
                });
            }
        });

        playStore.getUrls("com.instagram.android", new DownloadCallback<>() {
            @Override
            public void onError(String appId, Throwable t) {
                Assertions.fail("Failed for: " + appId, t);
            }

            @Override
            public void onSuccess(String appId, List<IconURL> iconURLS) {
                iconURLS.forEach(iconURL -> {
                    Assertions.assertTrue(StringUtils.defaultString(iconURL.getUrl()).startsWith("https://"));
                    Assertions.assertEquals("PNG", iconURL.getType());
                    Assertions.assertTrue(iconURL.getWidth() > 0);
                    Assertions.assertTrue(iconURL.getHeight() > 0);
                });
            }
        });
    }

    @Test
    void test_getFiles() {
        try {
            var path = TestUtils.getJavaAppIcons();

            appStore.getFiles("389801252", path, new DownloadCallback<>() {
                @Override
                public void onError(String appId, Throwable t) {
                    Assertions.fail("Failed for: " + appId, t);
                }

                @Override
                public void onSuccess(String appId, IconFile iconFile) {
                    Assertions.assertEquals(TestUtils.FILENAME_LEN, Path.of(iconFile.getPath()).getFileName().toString().length());
                    Assertions.assertEquals("jpg", iconFile.getExtension());
                    Assertions.assertTrue(iconFile.getWidth() > 0);
                    Assertions.assertTrue(iconFile.getHeight() > 0);
                }
            });

            playStore.getFiles("com.instagram.android", path, new DownloadCallback<>() {
                @Override
                public void onError(String appId, Throwable t) {
                    Assertions.fail("Failed for: " + appId, t);
                }

                @Override
                public void onSuccess(String appId, IconFile iconFile) {
                    Assertions.assertEquals(TestUtils.FILENAME_LEN, Path.of(iconFile.getPath()).getFileName().toString().length());
                    Assertions.assertEquals("png", iconFile.getExtension());
                    Assertions.assertEquals(512, iconFile.getWidth());
                    Assertions.assertEquals(512, iconFile.getHeight());
                }
            });

            Thread.sleep(TIMEOUT);
        } catch (Throwable t) {
            Assertions.fail(t);
        }
    }

    @Test
    void test_getMultiUrls() {
        appStore.getMultiUrls(Set.of("389801252", "310633997"), new DownloadCallback<>() {
            @Override
            public void onError(String appId, Throwable t) {
                Assertions.fail("Failed for: " + appId, t);
            }

            @Override
            public void onSuccess(String appId, List<IconURL> iconURLS) {
                Assertions.assertEquals(3, iconURLS.size());
            }
        });

        playStore.getMultiUrls(Set.of("com.zhiliaoapp.musically", "com.instagram.android"), new DownloadCallback<>() {
            @Override
            public void onError(String appId, Throwable t) {
                Assertions.fail("Failed for: " + appId, t);
            }

            @Override
            public void onSuccess(String appId, List<IconURL> iconURLS) {
                Assertions.assertEquals(1, iconURLS.size());
            }
        });
    }

    @Test
    void test_getMultiFiles() {
        try {
            var path = TestUtils.getJavaAppIcons();

            appStore.getMultiFiles(Set.of("389801252", "310633997"), path, new DownloadCallback<>() {
                @Override public void onError(String appId, Throwable t) {
                    Assertions.fail("Failed for: " + appId, t);
                }

                @Override public void onSuccess(String appId, IconFile iconFile) {
                    Assertions.assertEquals(TestUtils.FILENAME_LEN, Path.of(iconFile.getPath()).getFileName().toString().length());
                    Assertions.assertEquals("jpg", iconFile.getExtension());
                    Assertions.assertTrue(iconFile.getWidth() > 0);
                    Assertions.assertTrue(iconFile.getHeight() > 0);
                }
            });

            playStore.getMultiFiles(Set.of("com.zhiliaoapp.musically", "com.instagram.android"), path, new DownloadCallback<>() {
                @Override public void onError(String appId, Throwable t) {
                    Assertions.fail("Failed for: " + appId, t);
                }

                @Override public void onSuccess(String appId, IconFile iconFile) {
                    Assertions.assertEquals(TestUtils.FILENAME_LEN, Path.of(iconFile.getPath()).getFileName().toString().length());
                    Assertions.assertEquals("png", iconFile.getExtension());
                    Assertions.assertTrue(iconFile.getWidth() > 0);
                    Assertions.assertTrue(iconFile.getHeight() > 0);
                }
            });

            Thread.sleep(TIMEOUT);
        } catch (Throwable t) {
            Assertions.fail(t);
        }
    }

}
