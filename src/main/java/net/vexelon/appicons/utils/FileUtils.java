package net.vexelon.appicons.utils;

public final class FileUtils {

    public static void closeQuietly(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            // ignore
        }
    }

}
