/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.operation.block.Extras;
import dev.huskuraft.effortless.networking.serializer.Vector3dSerializer;

public static class ContextSerializer.EntityStateSerializer
implements NetByteBufSerializer<Extras> {
    @Override
    public Extras read(NetByteBuf byteBuf) {
        return new Extras(byteBuf.read(new Vector3dSerializer()), byteBuf.readFloat(), byteBuf.readFloat());
    }

    @Override
    public void write(NetByteBuf byteBuf, Extras extras) {
        byteBuf.write(extras.position(), new Vector3dSerializer());
        byteBuf.writeFloat(extras.rotationX());
        byteBuf.writeFloat(extras.rotationY());
    }
}
