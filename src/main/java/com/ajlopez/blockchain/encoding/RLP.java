package com.ajlopez.blockchain.encoding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ajlopez on 12/08/2017.
 */
public class RLP {
    private static byte[] empty = new byte[0];
    private static byte[] emptyEncoding = new byte[] { (byte)0x80 };

    private RLP() {

    }

    public static byte[] encode(byte[] bytes) {
        if (bytes == null || bytes.length == 0)
            return emptyEncoding;

        if (bytes.length == 1 && (bytes[0] & 0x80) == 0)
            return bytes;

        if (bytes.length < 56) {
            byte[] encoded = new byte[1 + bytes.length];
            encoded[0] = (byte)(128 + bytes.length);
            System.arraycopy(bytes, 0, encoded, 1, bytes.length);
            return encoded;
        }

        byte[] blength = lengthToBytes(bytes.length);
        byte[] encoded = new byte[1 + blength.length + bytes.length];
        encoded[0] = (byte) (183 + blength.length);
        System.arraycopy(blength, 0, encoded, 1, blength.length);
        System.arraycopy(bytes, 0, encoded, 1 + blength.length, bytes.length);

        return encoded;
    }

    public static byte[] decode(byte[] bytes) {
        int b0 = bytes[0] & 0xff;

        if (bytes.length == 1)
            if (b0 == 128)
                return empty;
            else
                return bytes;

        long length;
        int offset;

        if (b0 > 183) {
            offset = b0 - 183 + 1;
            length = bytesToLength(bytes, 1, offset - 1);
        }
        else {
            length = b0 & 0x7f;
            offset = 1;
        }

        byte[] decoded = new byte[(int)length];

        System.arraycopy(bytes, offset, decoded, 0, (int)length);

        return decoded;
    }

    public static byte[] encodeList(byte[]... elements) {
        int length = 0;
        byte[] encoded;
        int offset;

        for (int k = 0; k < elements.length; k++)
            length += elements[k].length;

        if (length < 56) {
            encoded = new byte[length + 1];
            encoded[0] = (byte)(192 + length);
            offset = 1;
        }
        else {
            byte[] blength = lengthToBytes(length);
            offset = 1 + blength.length;
            encoded = new byte[length + blength.length + 1];
            encoded[0] = (byte)(247 + blength.length);
            System.arraycopy(blength, 0, encoded, 1, blength.length);
        }

        for (int k = 0; k < elements.length; k++) {
            System.arraycopy(elements[k], 0, encoded, offset, elements[k].length);
            offset += elements[k].length;
        }

        return encoded;
    }

    public static byte[][] decodeList(byte[] encoded) {
        int b0 = encoded[0] & 0xff;
        int length;
        int offset = 1;

        if (b0 > 247) {
            offset = b0 - 247 + 1;
            length = bytesToLength(encoded, 1, b0 - 247);
        }
        else
            length = b0 - 192;

        List<byte[]> items = new ArrayList<>();

        int position = offset;

        while (position < offset + length) {
            int l = getTotalLength(encoded, position);

            byte[] item = new byte[l];

            System.arraycopy(encoded, position, item, 0, l);
            items.add(item);
            position += l;
        }

        return items.toArray(new byte[items.size()][]);
    }

    public static int getTotalLength(byte[] bytes, int position) {
        int b0 = bytes[position] & 0xff;

        if (b0 > 247) {
            int nbytes = b0 - 247;
            return 1 + nbytes + bytesToLength(bytes, position + 1, nbytes);
        }

        if (b0 >= 192)
            return b0 - 192 + 1;

        if (b0 > 183) {
            int nbytes = b0 - 183;
            return 1 + nbytes + bytesToLength(bytes, position + 1, nbytes);
        }

        if (b0 <= 128)
            return 1;

        return b0 - 128 + 1;
    }

    public static byte[] lengthToBytes(int length) {
        byte[] bytes = new byte[4];
        int pos = 0;
        int l = length;

        do {
            bytes[4 - ++pos] = (byte)(l & 0xff);
            l >>>= 8;
        } while (pos < 4 && l != 0);

        return Arrays.copyOfRange(bytes, 4 - pos, 4);
    }

    public static int bytesToLength(byte[] bytes) {
        return bytesToLength(bytes, 0, bytes.length);
    }

    public static int bytesToLength(byte[] bytes, int position, int size) {
        int length = 0;

        for (int k = 0; k < size; k++) {
            length <<= 8;
            length += bytes[position + k] & 0xff;
        }

        return length;
    }
}
