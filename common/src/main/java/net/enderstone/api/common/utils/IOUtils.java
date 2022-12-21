package net.enderstone.api.common.utils;

public class IOUtils {

    public static short byteToUnsignedShort(byte b) {
        return b;
    }

    public static short readShort(byte[] bytes) {
        return (short)(((bytes[0] & 0xff) << 8) | (bytes[1] & 0xff));
    }

    public static byte[] writeShort(short s) {
        return new byte[] {(byte)(s >> 8 & 0xff), (byte)(s & 0xff)};
    }

    public static short readUnsignedByte(byte b) {
        return (short)(b & 0xff);
    }

    public static int readUnsignedShort(byte[] bytes) {
        return (bytes[0] & 0xff) << 8 | (bytes[1] & 0xff);
    }

    public static byte[] writeUnsignedShort(int s) {
        return new byte[] {(byte)(s >>> 8 & 0xff), (byte)(s & 0xff)};
    }

    public static int readInteger(byte[] bytes) {
        return (bytes[0] & 0xff) << 24
                | (bytes[1] & 0xff) << 16
                | (bytes[2] & 0xff) << 8
                | (bytes[3] & 0xff);
    }

    public static long readUnsignedInteger(byte[] bytes) {
        return ((long) bytes[0] & 0xff) << 24
                | ((long) bytes[1] & 0xff) << 16
                | ((long) bytes[2] & 0xff) << 8
                | bytes[3];
    }

    public static long readLong(byte[] bytes) {
        return ((long) bytes[0] & 0xff) << 56
                | ((long) bytes[1] & 0xff) << 48
                | ((long) bytes[2] & 0xff) << 40
                | ((long) bytes[3] & 0xff) << 32
                | ((long) bytes[4] & 0xff) << 24
                | ((long) bytes[5] & 0xff) << 16
                | ((long) bytes[6] & 0xff) << 8
                | (bytes[7] & 0xff);
    }

    public static boolean readBoolean(byte b) {
        return (b & 0x1) == 0x1;
    }

}
