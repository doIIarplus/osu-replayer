import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;

public class LEB128 {
    private LEB128() {
    }

    public static int unsignedLeb128Size(int value) {
        int remaining = value >> 7;
        int count = 0;
        while (remaining != 0) {
            remaining >>= 7;
            count++;
        }
        return count + 1;
    }

    public static int signedLeb128Size(int value) {
        int remaining = value >> 7;
        int count = 0;
        boolean hasMore = true;
        int end = ((value & Integer.MIN_VALUE) == 0) ? 0 : -1;
        while (hasMore) {
            hasMore = (remaining != end)
                    || ((remaining & 1) != ((value >> 6) & 1));
            value = remaining;
            remaining >>= 7;
            count++;
        }
        return count;
    }

    public static int readSignedLeb128(LittleEndianDataInputStream in) throws Exception {
        int result = 0;
        int cur;
        int count = 0;
        int signBits = -1;
        do {
            cur = in.readByte() & 0xff;
            result |= (cur & 0x7f) << (count * 7);
            signBits <<= 7;
            count++;
        } while (((cur & 0x80) == 0x80) && count < 5);
        if ((cur & 0x80) == 0x80) {
            throw new Exception("invalid LEB128 sequence");
        }
        // Sign extend if appropriate
        if (((signBits >> 1) & result) != 0 ) {
            result |= signBits;
        }
        return result;
    }

    public static int readUnsignedLeb128(LittleEndianDataInputStream in) throws Exception {
        int result = 0;
        int cur;
        int count = 0;
        do {
            cur = in.readByte() & 0xff;
            result |= (cur & 0x7f) << (count * 7);
            count++;
        } while (((cur & 0x80) == 0x80) && count < 5);
        if ((cur & 0x80) == 0x80) {
            throw new Exception("invalid LEB128 sequence");
        }
        return result;
    }

    public static void writeUnsignedLeb128(LittleEndianDataOutputStream out, int value) {
        try {
            int remaining = value >>> 7;
            while (remaining != 0) {
                out.writeByte((byte) ((value & 0x7f) | 0x80));
                value = remaining;
                remaining >>>= 7;
            }
            out.writeByte((byte) (value & 0x7f));
        } catch (Exception e)
        {

        }
    }

    public static void writeSignedLeb128(LittleEndianDataOutputStream out, int value) {
        try {
            int remaining = value >> 7;
            boolean hasMore = true;
            int end = ((value & Integer.MIN_VALUE) == 0) ? 0 : -1;
            while (hasMore) {
                hasMore = (remaining != end)
                        || ((remaining & 1) != ((value >> 6) & 1));
                out.writeByte((byte) ((value & 0x7f) | (hasMore ? 0x80 : 0)));
                value = remaining;
                remaining >>= 7;
            }
        } catch (Exception e)
        {

        }
    }
}