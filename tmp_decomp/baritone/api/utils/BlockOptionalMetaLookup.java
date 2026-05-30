/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.utils;

import baritone.api.utils.BlockOptionalMeta;
import baritone.api.utils.accessor.IItemStack;
import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockOptionalMetaLookup {
    private final ImmutableSet<Block> blockSet;
    private final ImmutableSet<BlockState> blockStateSet;
    private final ImmutableSet<Integer> stackHashes;
    private final BlockOptionalMeta[] boms;

    public BlockOptionalMetaLookup(BlockOptionalMeta ... blockOptionalMetaArray) {
        this.boms = blockOptionalMetaArray;
        HashSet<Block> hashSet = new HashSet<Block>();
        HashSet<BlockState> hashSet2 = new HashSet<BlockState>();
        HashSet<Integer> hashSet3 = new HashSet<Integer>();
        for (BlockOptionalMeta blockOptionalMeta : blockOptionalMetaArray) {
            hashSet.add(blockOptionalMeta.getBlock());
            hashSet2.addAll(blockOptionalMeta.getAllBlockStates());
            hashSet3.addAll(blockOptionalMeta.stackHashes());
        }
        this.blockSet = ImmutableSet.copyOf(hashSet);
        this.blockStateSet = ImmutableSet.copyOf(hashSet2);
        this.stackHashes = ImmutableSet.copyOf(hashSet3);
    }

    public BlockOptionalMetaLookup(Block ... blockArray) {
        this((BlockOptionalMeta[])Stream.of(blockArray).map(BlockOptionalMeta::new).toArray(BlockOptionalMeta[]::new));
    }

    public BlockOptionalMetaLookup(List<Block> list) {
        this((BlockOptionalMeta[])list.stream().map(BlockOptionalMeta::new).toArray(BlockOptionalMeta[]::new));
    }

    public BlockOptionalMetaLookup(String ... stringArray) {
        this((BlockOptionalMeta[])Stream.of(stringArray).map(BlockOptionalMeta::new).toArray(BlockOptionalMeta[]::new));
    }

    public boolean has(Block block) {
        return this.blockSet.contains((Object)block);
    }

    public boolean has(BlockState blockState) {
        return this.blockStateSet.contains((Object)blockState);
    }

    public boolean has(ItemStack itemStack) {
        int n2 = ((IItemStack)itemStack).getBaritoneHash() - itemStack.getDamageValue();
        return this.stackHashes.contains((Object)n2);
    }

    public List<BlockOptionalMeta> blocks() {
        return Arrays.asList(this.boms);
    }

    public String toString() {
        return String.format("BlockOptionalMetaLookup{%s}", Arrays.toString(this.boms));
    }
}

