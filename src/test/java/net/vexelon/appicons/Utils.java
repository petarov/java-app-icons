package net.vexelon.appicons;

import net.vexelon.appicons.utils.HashingUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Utils {

    public static final int FILENAME_LEN = 40 + 4; // SHA1 + extension

    public static Path getJavaAppIcons() throws IOException {
        return Files.createTempDirectory("java_app_icons");
    }

    public static String sha1Res(String name) {
        return HashingUtils.hash(UtilsTests.class.getResourceAsStream("/" + name), "SHA-1");
    }
}
