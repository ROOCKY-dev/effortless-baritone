/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.tag.RecordTag;

public interface BlockEntity
extends PlatformReference {
    public BlockState getBlockState();

    public BlockPosition getBlockPosition();

    public World getWorld();

    public RecordTag getTag();

    public void setTag(RecordTag var1);

    default public BlockEntity copy() {
        RecordTag tag = this.getTag();
        BlockEntity newBlockEntity = this.getBlockState().getEntity(this.getBlockPosition());
        if (newBlockEntity != null) {
            newBlockEntity.setTag(tag);
        }
        return newBlockEntity;
    }
}
