package net.vexelon.appicons.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class FileUtils {

    public static void closeQuietly(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            // ignore
        }
    }

    public static byte[] readInputStreamFully(InputStream input) throws IOException {
        var buffer = new byte[4096];
        int read;

        try (var output = new ByteArrayOutputStream()) {
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            return output.toByteArray();
        }
    }

}
