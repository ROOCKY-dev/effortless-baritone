/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufUtil
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.networking.DecoderException;
import dev.huskuraft.effortless.api.networking.EncoderException;
import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.VarInt;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import java.nio.charset.StandardCharsets;

public class Utf8String {
    public static String read(NetByteBuf byteBuf, int maxLength) {
        int i = ByteBufUtil.utf8MaxBytes((int)maxLength);
        int j = VarInt.read(byteBuf);
        if (j > i) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + j + " > " + i + ")");
        }
        if (j < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        int k = byteBuf.readableBytes();
        if (j > k) {
            throw new DecoderException("Not enough bytes in buffer, expected " + j + ", but got " + k);
        }
        String s = byteBuf.toString(byteBuf.readerIndex(), j, StandardCharsets.UTF_8);
        byteBuf.readerIndex(byteBuf.readerIndex() + j);
        if (s.length() > maxLength) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + s.length() + " > " + maxLength + ")");
        }
        return s;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void write(NetByteBuf byteBuf, CharSequence string, int maxLength) {
        if (string.length() > maxLength) {
            throw new EncoderException("String too big (was " + string.length() + " characters, max " + maxLength + ")");
        }
        int i = ByteBufUtil.utf8MaxBytes((CharSequence)string);
        ByteBuf bytebuf = byteBuf.alloc().buffer(i);
        try {
            int j = ByteBufUtil.writeUtf8((ByteBuf)bytebuf, (CharSequence)string);
            int k = ByteBufUtil.utf8MaxBytes((int)maxLength);
            if (j > k) {
                throw new EncoderException("String too big (was " + j + " bytes encoded, max " + k + ")");
            }
            VarInt.write(byteBuf, j);
            byteBuf.writeBytes(bytebuf);
        }
        finally {
            bytebuf.release();
        }
    }
}
