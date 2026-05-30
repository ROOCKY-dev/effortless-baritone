/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.networking.NetByteBuf;

public class VarLong {
    private static final int MAX_VARLONG_SIZE = 10;
    private static final int DATA_BITS_MASK = 127;
    private static final int CONTINUATION_BIT_MASK = 128;
    private static final int DATA_BITS_PER_BYTE = 7;

    public static int getByteSize(long pData) {
        for (int i = 1; i < 10; ++i) {
            if ((pData & -1L << i * 7) != 0L) continue;
            return i;
        }
        return 10;
    }

    public static boolean hasContinuationBit(byte pData) {
        return (pData & 0x80) == 128;
    }

    public static long read(NetByteBuf byteBuf) {
        byte b0;
        long i = 0L;
        int j = 0;
        do {
            b0 = byteBuf.readByte();
            i |= (long)(b0 & 0x7F) << j++ * 7;
            if (j <= 10) continue;
            throw new RuntimeException("VarLong too big");
        } while (VarLong.hasContinuationBit(b0));
        return i;
    }

    public static NetByteBuf write(NetByteBuf byteBuf, long pValue) {
        while ((pValue & 0xFFFFFFFFFFFFFF80L) != 0L) {
            byteBuf.writeByte((byte)((int)(pValue & 0x7FL) | 0x80));
            pValue >>>= 7;
        }
        byteBuf.writeByte((byte)pValue);
        return byteBuf;
    }
}
