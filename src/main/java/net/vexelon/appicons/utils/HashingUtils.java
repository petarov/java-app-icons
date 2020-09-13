package net.vexelon.appicons.utils;

import java.nio.charset.StandardCharsets;
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

    private static String hash(String text, String algo) {
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

}
