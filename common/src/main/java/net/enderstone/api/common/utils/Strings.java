package net.enderstone.api.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Strings {

    private final static int BYTE_OFFSET_TYPE = 6;

    private final static int BYTE_OFFSET_VARIATION = 8;

    private static int _gatherInt(byte[] buffer, int offset) {
        return (buffer[offset] << 24) | ((buffer[offset + 1] & 0xFF) << 16)
                | ((buffer[offset + 2] & 0xFF) << 8) | (buffer[offset + 3] & 0xFF);
    }

    protected static long gatherLong(byte[] buffer, int offset) {
        long hi = ((long) _gatherInt(buffer, offset)) << 32;
        //long lo = ((long) _gatherInt(buffer, offset+4)) & MASK_LOW_INT;
        long lo = (((long) _gatherInt(buffer, offset + 4)) << 32) >>> 32;
        return hi | lo;
    }

    private static void _checkUUIDByteArray(byte[] bytes) {
        if (16 > bytes.length) {
            throw new IllegalArgumentException("Invalid offset (" + 0 + ") passed: not enough room in byte array (need 16 bytes)");
        }
    }

    private static UUID uuid(byte[] bytes) {
        _checkUUIDByteArray(bytes);
        long l1 = gatherLong(bytes, 0);
        long l2 = gatherLong(bytes, 8);
        return new UUID(l1, l2);
    }

    public static UUID nameToUUID(String name) {
        String hash = toSHA1(name);
        byte[] bytes = hash.getBytes();

        int b = bytes[BYTE_OFFSET_TYPE] & 0xF;
        b = b | (5 << 4);
        bytes[BYTE_OFFSET_TYPE] = (byte) b;
        b = bytes[BYTE_OFFSET_VARIATION] & 0x3F;
        b = b | 0x80;
        bytes[BYTE_OFFSET_VARIATION] = (byte) b;
        return uuid(bytes);
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String toSHA1(String str) {
        return toSHA1(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String toSHA1(byte[] data) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            return bytesToHex(md.digest(data));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toSHA256(final String str) {
        return toSHA256(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String toSHA256(byte[] data) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            return bytesToHex(md.digest(data));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
