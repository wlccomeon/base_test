package com.lc.test.skills.functionApply;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author wlc
 * @desc
 * @date 2026/3/11 星期三
 */
public class HexLUtils {
    public static final Charset DEFAULT_CHARSET;
    public static final String DEFAULT_CHARSET_NAME;
    private static final char[] DIGITS_LOWER;
    private static final char[] DIGITS_UPPER;

    public static String toHex(byte[] input) {
        return encodeHexString(input);
    }

    public static byte[] fromHex(String input) {
        return decodeHex(input);
    }

    public static byte[] decodeHex(final String data) {
        return decodeHex(data.toCharArray());
    }

    public static byte[] decodeHex(final char[] data) {
        int len = data.length;
        if ((len & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        } else {
            byte[] out = new byte[len >> 1];
            int i = 0;

            for(int j = 0; j < len; ++i) {
                int f = toDigit(data[j], j) << 4;
                ++j;
                f |= toDigit(data[j], j);
                ++j;
                out[i] = (byte)(f & 255);
            }

            return out;
        }
    }

    public static char[] encodeHex(final byte[] data) {
        return encodeHex(data, true);
    }

    public static char[] encodeHex(final ByteBuffer data) {
        return encodeHex(data, true);
    }

    public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    public static char[] encodeHex(final ByteBuffer data, final boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(final byte[] data, final char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for(int j = 0; i < l; ++i) {
            out[j++] = toDigits[(240 & data[i]) >>> 4];
            out[j++] = toDigits[15 & data[i]];
        }

        return out;
    }

    protected static char[] encodeHex(final ByteBuffer byteBuffer, final char[] toDigits) {
        return encodeHex(toByteArray(byteBuffer), toDigits);
    }

    public static String encodeHexString(final byte[] data) {
        return new String(encodeHex(data));
    }

    public static String encodeHexString(final byte[] data, final boolean toLowerCase) {
        return new String(encodeHex(data, toLowerCase));
    }

    public static String encodeHexString(final ByteBuffer data) {
        return new String(encodeHex(data));
    }

    public static String encodeHexString(final ByteBuffer data, final boolean toLowerCase) {
        return new String(encodeHex(data, toLowerCase));
    }

    private static byte[] toByteArray(final ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        if (byteBuffer.hasArray()) {
            byte[] byteArray = byteBuffer.array();
            if (remaining == byteArray.length) {
                byteBuffer.position(remaining);
                return byteArray;
            }
        }

        byte[] byteArray = new byte[remaining];
        byteBuffer.get(byteArray);
        return byteArray;
    }

    protected static int toDigit(final char ch, final int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        } else {
            return digit;
        }
    }

    static {
        DEFAULT_CHARSET = StandardCharsets.UTF_8;
        DEFAULT_CHARSET_NAME = StandardCharsets.UTF_8.name();
        DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

}
