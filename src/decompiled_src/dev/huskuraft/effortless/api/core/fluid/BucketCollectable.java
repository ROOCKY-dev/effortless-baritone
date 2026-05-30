/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core.fluid;

import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.sound.Sound;
import java.util.Optional;

public interface BucketCollectable {
    public ItemStack pickupBlock(World var1, Player var2, BlockPosition var3, BlockState var4);

    public Optional<Sound> getPickupSound();
}
