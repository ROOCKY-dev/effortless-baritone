/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class m {
    public static final ImmutableSet<Block> a = ImmutableSet.of((Object)Blocks.ENDER_CHEST, (Object)Blocks.FURNACE, (Object)Blocks.CHEST, (Object)Blocks.TRAPPED_CHEST, (Object)Blocks.END_PORTAL, (Object)Blocks.END_PORTAL_FRAME, (Object[])new Block[]{Blocks.SPAWNER, Blocks.BARRIER, Blocks.OBSERVER, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.NETHER_PORTAL, Blocks.HOPPER, Blocks.BEACON, Blocks.BREWING_STAND, Blocks.CREEPER_HEAD, Blocks.CREEPER_WALL_HEAD, Blocks.DRAGON_HEAD, Blocks.DRAGON_WALL_HEAD, Blocks.PLAYER_HEAD, Blocks.PLAYER_WALL_HEAD, Blocks.ZOMBIE_HEAD, Blocks.ZOMBIE_WALL_HEAD, Blocks.SKELETON_SKULL, Blocks.SKELETON_WALL_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL, Blocks.ENCHANTING_TABLE, Blocks.ANVIL, Blocks.WHITE_BED, Blocks.ORANGE_BED, Blocks.MAGENTA_BED, Blocks.LIGHT_BLUE_BED, Blocks.YELLOW_BED, Blocks.LIME_BED, Blocks.PINK_BED, Blocks.GRAY_BED, Blocks.LIGHT_GRAY_BED, Blocks.CYAN_BED, Blocks.PURPLE_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.GREEN_BED, Blocks.RED_BED, Blocks.BLACK_BED, Blocks.DRAGON_EGG, Blocks.JUKEBOX, Blocks.END_GATEWAY, Blocks.COBWEB, Blocks.NETHER_WART, Blocks.LADDER, Blocks.VINE});
    private int d;
    private int e;
    public final int a;
    public final int b;
    public final int c;
    final BitSet a;
    final Int2ObjectOpenHashMap<String> a;
    final BlockState[] a;
    final int[] a;
    final Map<String, List<BlockPos>> a;
    public final long a;

    /*
     * WARNING - void declaration
     */
    m(int n2, int n3, int n4, BitSet bitSet, BlockState[] blockStateArray, Map<String, List<BlockPos>> map, long l2) {
        void var7_12;
        void var6_11;
        void var5_10;
        int n5;
        void n52;
        int blockPos;
        this.e = blockPos << 9;
        this.a = this.e / 8;
        Iterator i2 = n52;
        m m2 = this;
        if (((BitSet)((Object)i2)).size() > m2.e) {
            throw new IllegalArgumentException("BitSet of invalid length provided");
        }
        this.b = n2;
        this.c = n5;
        this.d = blockPos;
        this.a = n52;
        this.a = var5_10;
        this.a = new int[256];
        this.a = var6_11;
        this.a = var7_12;
        if (var6_11.isEmpty()) {
            this.a = null;
        } else {
            this.a = new Int2ObjectOpenHashMap();
            m2 = this;
            for (Map.Entry entry : m2.a.entrySet()) {
                for (BlockPos blockPos2 : (List)entry.getValue()) {
                    m2.a.put(m.a(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ()), (Object)((String)entry.getKey()));
                }
            }
        }
        m2 = this;
        for (int i3 = 0; i3 < 16; ++i3) {
            block3: for (int i4 = 0; i4 < 16; ++i4) {
                n5 = i3 << 4 | i4;
                m2.a[n5] = 0;
                for (blockPos = m2.d; blockPos >= 0; --blockPos) {
                    int n6 = m.a(i4, blockPos, i3);
                    if (!m2.a.get(n6) && !m2.a.get(n6 + 1)) continue;
                    m2.a[n5] = blockPos;
                    continue block3;
                }
            }
        }
    }

    public static int a(int n2) {
        return n2 << 9;
    }

    public static int b(int n2) {
        return n2 / 8;
    }

    public static int a(int n2, int n3, int n4) {
        return n2 << 1 | n4 << 5 | n3 << 9;
    }
}

