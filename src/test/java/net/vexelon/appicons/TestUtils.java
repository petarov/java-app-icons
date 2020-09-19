package net.vexelon.appicons;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TestUtils {

    public static final int FILENAME_LEN = 40 + 4; // SHA1 + extension

    public static Path getJavaAppIcons() throws IOException {
        return Files.createTempDirectory("java_app_icons");
    }

}
