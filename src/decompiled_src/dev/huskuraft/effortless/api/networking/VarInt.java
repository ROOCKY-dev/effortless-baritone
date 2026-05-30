/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.networking.NetByteBuf;

public class VarInt {
    private static final int MAX_VARINT_SIZE = 5;
    private static final int DATA_BITS_MASK = 127;
    private static final int CONTINUATION_BIT_MASK = 128;
    private static final int DATA_BITS_PER_BYTE = 7;

    public static int getByteSize(int pData) {
        for (int i = 1; i < 5; ++i) {
            if ((pData & -1 << i * 7) != 0) continue;
            return i;
        }
        return 5;
    }

    public static boolean hasContinuationBit(byte pData) {
        return (pData & 0x80) == 128;
    }

    public static int read(NetByteBuf byteBuf) {
        byte b0;
        int i = 0;
        int j = 0;
        do {
            b0 = byteBuf.readByte();
            i |= (b0 & 0x7F) << j++ * 7;
            if (j <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while (VarInt.hasContinuationBit(b0));
        return i;
    }

    public static NetByteBuf write(NetByteBuf byteBuf, int pValue) {
        while ((pValue & 0xFFFFFF80) != 0) {
            byteBuf.writeByte((byte)(pValue & 0x7F | 0x80));
            pValue >>>= 7;
        }
        byteBuf.writeByte(pValue);
        return byteBuf;
    }
}
