package org.springframework.boot.peach.message.util;

import java.nio.ByteBuffer;

public class ByteUtil {

    public static int getInteger(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getInt();
    }

    public static long getLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    public static byte[] toBytes(int intValue) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(intValue);
        buffer.flip();
        return buffer.array();
    }

    public static byte[] toBytes(long longValue) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(longValue);
        buffer.flip();
        return buffer.array();
    }

    public static String asHex(byte[] bytes) {
        StringBuffer hexBuffer = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            String hexByte = Integer.toHexString(Byte.toUnsignedInt(bytes[i]));
            if (hexByte.length() == 1)
                hexBuffer.append("0");

            hexBuffer.append(hexByte);
        }
        return hexBuffer.toString();
    }
}
