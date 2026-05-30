/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.replace.Replace;
import dev.huskuraft.effortless.building.replace.ReplaceStrategy;

public static class ContextSerializer.ReplaceSerializer
implements NetByteBufSerializer<Replace> {
    @Override
    public Replace read(NetByteBuf byteBuf) {
        return new Replace(byteBuf.readEnum(ReplaceStrategy.class), byteBuf.readList(NetByteBuf::readItemStack), byteBuf.readBoolean());
    }

    @Override
    public void write(NetByteBuf byteBuf, Replace replace) {
        byteBuf.writeEnum(replace.replaceStrategy());
        byteBuf.writeList(replace.replaceList(), NetByteBuf::writeItemStack);
        byteBuf.writeBoolean(replace.isQuick());
    }
}
