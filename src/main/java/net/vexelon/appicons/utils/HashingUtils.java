package net.vexelon.appicons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashingUtils {

    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String getHex(byte[] data) {
        final var result = new char[data.length * 2];
        for (int i = 0; i < data.length; i += 1) {
            result[i * 2] = HEX[(data[i] & 0xF0) >> 4];
            result[i * 2 + 1] = HEX[data[i] & 0x0F];
        }
        return new String(result);
    }

    public static String hash(String text, String algo) {
        try {
            return getHex(MessageDigest.getInstance(algo).digest(
                    text.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String sha1(String text) {
        return hash(text, "SHA-1");
    }

    public static String hash(InputStream input, String algo) {
        try {
            return getHex(MessageDigest.getInstance(algo).digest(input.readAllBytes()));
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String sha1(Path path) {
        try (var input = Files.newInputStream(path, StandardOpenOption.READ)) {
            return hash(input, "SHA-1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
