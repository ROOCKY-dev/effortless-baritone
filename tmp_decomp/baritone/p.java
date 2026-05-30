/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.DoublePlantBlock
 *  net.minecraft.world.level.block.FlowerBlock
 *  net.minecraft.world.level.block.TallGrassBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.LevelChunkSection
 *  net.minecraft.world.level.chunk.PalettedContainer
 */
package baritone;

import baritone.api.utils.BlockUtils;
import baritone.cc;
import baritone.fb;
import baritone.fy;
import baritone.m;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;

public final class p {
    public static m a(LevelChunk levelChunk) {
        int n2;
        int n3;
        LevelChunkSection[] levelChunkSectionArray;
        HashMap<String, List<BlockPos>> hashMap = new HashMap<String, List<BlockPos>>();
        int n4 = levelChunk.getLevel().dimensionType().height();
        BitSet bitSet = new BitSet(m.a(n4));
        try {
            levelChunkSectionArray = levelChunk.getSections();
            for (n3 = 0; n3 < n4 / 16; ++n3) {
                LevelChunkSection levelChunkSection = levelChunkSectionArray[n3];
                if (levelChunkSection == null) continue;
                PalettedContainer palettedContainer = levelChunkSection.getStates();
                n2 = n3 << 4;
                for (int i2 = 0; i2 < 16; ++i2) {
                    int n5 = i2 | n2;
                    for (int i3 = 0; i3 < 16; ++i3) {
                        for (int i4 = 0; i4 < 16; ++i4) {
                            fy fy2;
                            int n6 = m.a(i4, n5, i3);
                            BlockState blockState = (BlockState)palettedContainer.get(i4, i2, i3);
                            int n7 = i3;
                            int n8 = n5;
                            int n9 = i4;
                            LevelChunk levelChunk2 = levelChunk;
                            Object object = blockState;
                            Block block = blockState.getBlock();
                            if (cc.d(object)) {
                                if (cc.g(object)) {
                                    fy2 = fy.c;
                                } else {
                                    int n10 = n8 - levelChunk2.getLevel().dimensionType().minY();
                                    if (n9 != 15 && cc.g(fb.a(levelChunk2, n9 + 1, n10, n7)) || n9 != 0 && cc.g(fb.a(levelChunk2, n9 - 1, n10, n7)) || n7 != 15 && cc.g(fb.a(levelChunk2, n9, n10, n7 + 1)) || n7 != 0 && cc.g(fb.a(levelChunk2, n9, n10, n7 - 1))) {
                                        fy2 = fy.c;
                                    } else if (n9 == 0 || n9 == 15 || n7 == 0 || n7 == 15) {
                                        object = object.getFluidState().getFlow((BlockGetter)levelChunk2.getLevel(), new BlockPos(n9 + (levelChunk2.getPos().x << 4), n8, n7 + (levelChunk2.getPos().z << 4)));
                                        fy2 = object.x != 0.0 || object.z != 0.0 ? fy.b : fy.c;
                                    } else {
                                        fy2 = fy.b;
                                    }
                                }
                            } else {
                                fy2 = cc.b(object) || cc.c(object) ? fy.c : (block instanceof AirBlock || block instanceof TallGrassBlock || block instanceof DoublePlantBlock || block instanceof FlowerBlock ? fy.a : fy.d);
                            }
                            object = fy2.a;
                            bitSet.set(n6, (boolean)object[0]);
                            bitSet.set(n6 + 1, (boolean)object[1]);
                            Object object2 = blockState.getBlock();
                            if (!m.a.contains(object2)) continue;
                            object2 = BlockUtils.blockToString(object2);
                            hashMap.computeIfAbsent((String)object2, string -> new ArrayList()).add(new BlockPos(i4, n5 + levelChunk.getMinBuildHeight(), i3));
                        }
                    }
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        levelChunkSectionArray = new BlockState[256];
        for (n3 = 0; n3 < 16; ++n3) {
            block7: for (int i5 = 0; i5 < 16; ++i5) {
                for (int i6 = n4 - 1; i6 >= 0; --i6) {
                    n2 = m.a(i5, i6, n3);
                    if (!bitSet.get(n2) && !bitSet.get(n2 + 1)) continue;
                    levelChunkSectionArray[n3 << 4 | i5] = fb.a(levelChunk, i5, i6, n3);
                    continue block7;
                }
                levelChunkSectionArray[n3 << 4 | i5] = Blocks.AIR.defaultBlockState();
            }
        }
        return new m(levelChunk.getPos().x, levelChunk.getPos().z, n4, bitSet, (BlockState[])levelChunkSectionArray, hashMap, System.currentTimeMillis());
    }
}

