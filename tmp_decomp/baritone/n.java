/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.dimension.BuiltinDimensionTypes
 *  net.minecraft.world.level.dimension.DimensionType
 */
package baritone;

import baritone.a;
import baritone.api.cache.ICachedRegion;
import baritone.api.utils.BlockUtils;
import baritone.fy;
import baritone.m;
import baritone.q;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class n
implements ICachedRegion {
    final m[][] a;
    private final int a;
    private final int b;
    private final DimensionType a;
    private boolean a = new m[32][32];

    n(int n2, int n3, DimensionType dimensionType) {
        this.a = n2;
        this.b = n3;
        this.a = false;
        this.a = dimensionType;
    }

    @Override
    public final BlockState getBlock(int n2, int n3, int n4) {
        n3 -= this.a.minY();
        m m2 = this.a[n2 >> 4][n4 >> 4];
        if (m2 != null) {
            int n5;
            DimensionType dimensionType = this.a;
            int n6 = n4 & 0xF;
            n4 = n3;
            n3 = n2 & 0xF;
            Object object = m2;
            int n7 = n5 = m.a(n3, n4, n6);
            Object object2 = object;
            object2 = fy.a(((m)object2).a.get(n7), ((m)object2).a.get(n7 + 1));
            if (((m)object).a[n3 = n6 << 4 | n3] == n4 && object2 != fy.c) {
                return ((m)object).a[n3];
            }
            if (((m)object).a != null && (object = (String)((m)object).a.get(n5)) != null) {
                return BlockUtils.stringToBlockRequired((String)object).defaultBlockState();
            }
            if (object2 == fy.d) {
                if (n4 == dimensionType.logicalHeight() - 1 && dimensionType.hasCeiling()) {
                    return Blocks.BEDROCK.defaultBlockState();
                }
                if (n4 < -59 && dimensionType.natural()) {
                    return Blocks.OBSIDIAN.defaultBlockState();
                }
            }
            DimensionType dimensionType2 = dimensionType;
            switch (q.a[((Enum)object2).ordinal()]) {
                case 1: {
                    return Blocks.AIR.defaultBlockState();
                }
                case 2: {
                    return Blocks.WATER.defaultBlockState();
                }
                case 3: {
                    return Blocks.LAVA.defaultBlockState();
                }
                case 4: {
                    if (dimensionType2.natural()) {
                        return Blocks.STONE.defaultBlockState();
                    }
                    if (dimensionType2.ultraWarm()) {
                        return Blocks.NETHERRACK.defaultBlockState();
                    }
                    if (!dimensionType2.effectsLocation().equals((Object)BuiltinDimensionTypes.END_EFFECTS)) break;
                    return Blocks.END_STONE.defaultBlockState();
                }
            }
            return null;
        }
        return null;
    }

    @Override
    public final boolean isCached(int n2, int n3) {
        return this.a[n2 >> 4][n3 >> 4] != null;
    }

    public final synchronized void a(int n2, int n3, m m2) {
        this.a[n2][n3] = m2;
        this.a = true;
    }

    public final synchronized void a(String object) {
        if (!this.a) {
            return;
        }
        this.a();
        try {
            object = Paths.get((String)object, new String[0]);
            if (!Files.exists((Path)object, new LinkOption[0])) {
                Files.createDirectories((Path)object, new FileAttribute[0]);
            }
            System.out.println("Saving region " + this.a + "," + this.b + " to disk " + String.valueOf(object));
            object = n.a((Path)object, this.a, this.b);
            if (!Files.exists((Path)object, new LinkOption[0])) {
                Files.createFile((Path)object, new FileAttribute[0]);
            }
            object = new FileOutputStream(object.toFile());
            try (GZIPOutputStream gZIPOutputStream = new GZIPOutputStream((OutputStream)object, 16384);
                 DataOutputStream dataOutputStream = new DataOutputStream(gZIPOutputStream);){
                Object object2;
                int n2;
                int n3;
                dataOutputStream.writeInt(456022911);
                for (n3 = 0; n3 < 32; ++n3) {
                    for (n2 = 0; n2 < 32; ++n2) {
                        m m2 = this.a[n3][n2];
                        if (m2 == null) {
                            dataOutputStream.write(0);
                            continue;
                        }
                        dataOutputStream.write(1);
                        object2 = m2.a.toByteArray();
                        dataOutputStream.write((byte[])object2);
                        dataOutputStream.write(new byte[m2.a - ((byte[])object2).length]);
                    }
                }
                for (n3 = 0; n3 < 32; ++n3) {
                    for (n2 = 0; n2 < 32; ++n2) {
                        if (this.a[n3][n2] == null) continue;
                        for (int i2 = 0; i2 < 256; ++i2) {
                            dataOutputStream.writeUTF(BlockUtils.blockToString(this.a[n3][n2].a[i2].getBlock()));
                        }
                    }
                }
                for (n3 = 0; n3 < 32; ++n3) {
                    for (n2 = 0; n2 < 32; ++n2) {
                        if (this.a[n3][n2] == null) continue;
                        Object object3 = this.a[n3][n2].a;
                        dataOutputStream.writeShort(object3.entrySet().size());
                        object2 = object3.entrySet().iterator();
                        while (object2.hasNext()) {
                            object3 = (Map.Entry)object2.next();
                            dataOutputStream.writeUTF((String)object3.getKey());
                            dataOutputStream.writeShort(((List)object3.getValue()).size());
                            for (BlockPos blockPos : (List)object3.getValue()) {
                                dataOutputStream.writeByte((byte)(blockPos.getZ() << 4 | blockPos.getX()));
                                dataOutputStream.writeInt(blockPos.getY() - this.a.minY());
                            }
                        }
                    }
                }
                for (n3 = 0; n3 < 32; ++n3) {
                    for (n2 = 0; n2 < 32; ++n2) {
                        if (this.a[n3][n2] == null) continue;
                        dataOutputStream.writeLong(this.a[n3][n2].a);
                    }
                }
            }
            finally {
                ((FileOutputStream)object).close();
            }
            this.a = false;
            System.out.println("Saved region successfully");
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public final synchronized void b(String object) {
        try {
            Path path;
            object = Paths.get((String)object, new String[0]);
            if (!Files.exists((Path)object, new LinkOption[0])) {
                Files.createDirectories((Path)object, new FileAttribute[0]);
            }
            if (!Files.exists(path = n.a((Path)object, this.a, this.b), new LinkOption[0])) {
                return;
            }
            System.out.println("Loading region " + this.a + "," + this.b + " from disk " + String.valueOf(object));
            long l2 = System.nanoTime() / 1000000L;
            try (FileInputStream fileInputStream = new FileInputStream(path.toFile());
                 GZIPInputStream gZIPInputStream = new GZIPInputStream((InputStream)fileInputStream, 32768);){
                object = new DataInputStream(gZIPInputStream);
                try {
                    int n2;
                    int n3;
                    int n4;
                    int n5;
                    int n6 = ((DataInputStream)object).readInt();
                    if (n6 != 456022911) {
                        throw new IOException("Bad magic value " + n6);
                    }
                    boolean[][] blArray = new boolean[32][32];
                    BitSet[][] bitSetArray = new BitSet[32][32];
                    Map[][] mapArray = new Map[32][32];
                    BlockState[][][] blockStateArray = new BlockState[32][32][];
                    long[][] lArray = new long[32][32];
                    for (n5 = 0; n5 < 32; ++n5) {
                        block22: for (n4 = 0; n4 < 32; ++n4) {
                            switch (((FilterInputStream)object).read()) {
                                case 1: {
                                    byte[] byArray = new byte[m.b(m.a(this.a.height()))];
                                    ((DataInputStream)object).readFully(byArray);
                                    bitSetArray[n5][n4] = BitSet.valueOf(byArray);
                                    mapArray[n5][n4] = new HashMap();
                                    blockStateArray[n5][n4] = new BlockState[256];
                                    blArray[n5][n4] = true;
                                    continue block22;
                                }
                                case 0: {
                                    continue block22;
                                }
                                default: {
                                    throw new IOException("Malformed stream");
                                }
                            }
                        }
                    }
                    for (n5 = 0; n5 < 32; ++n5) {
                        for (n4 = 0; n4 < 32; ++n4) {
                            if (!blArray[n5][n4]) continue;
                            for (n3 = 0; n3 < 256; ++n3) {
                                blockStateArray[n5][n4][n3] = BlockUtils.stringToBlockRequired(((DataInputStream)object).readUTF()).defaultBlockState();
                            }
                        }
                    }
                    for (n5 = 0; n5 < 32; ++n5) {
                        for (n4 = 0; n4 < 32; ++n4) {
                            if (!blArray[n5][n4]) continue;
                            n3 = ((DataInputStream)object).readShort() & 0xFFFF;
                            for (int i2 = 0; i2 < n3; ++i2) {
                                String string = ((DataInputStream)object).readUTF();
                                BlockUtils.stringToBlockRequired(string);
                                ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
                                mapArray[n5][n4].put(string, arrayList);
                                n2 = ((DataInputStream)object).readShort() & 0xFFFF;
                                if (n2 == 0) {
                                    n2 = 65536;
                                }
                                for (int i3 = 0; i3 < n2; ++i3) {
                                    int n7 = ((DataInputStream)object).readByte();
                                    int n8 = n7 & 0xF;
                                    n7 = n7 >>> 4 & 0xF;
                                    int n9 = ((DataInputStream)object).readInt();
                                    arrayList.add(new BlockPos(n8, n9 + this.a.minY(), n7));
                                }
                            }
                        }
                    }
                    for (n5 = 0; n5 < 32; ++n5) {
                        for (n4 = 0; n4 < 32; ++n4) {
                            if (!blArray[n5][n4]) continue;
                            lArray[n5][n4] = ((DataInputStream)object).readLong();
                        }
                    }
                    for (n5 = 0; n5 < 32; ++n5) {
                        for (n4 = 0; n4 < 32; ++n4) {
                            if (!blArray[n5][n4]) continue;
                            n3 = this.a;
                            int n10 = this.b;
                            n2 = n5 + (n3 << 5);
                            int n11 = n4 + (n10 << 5);
                            this.a[n5][n4] = new m(n2, n11, this.a.height(), bitSetArray[n5][n4], blockStateArray[n5][n4], mapArray[n5][n4], lArray[n5][n4]);
                        }
                    }
                }
                finally {
                    ((FilterInputStream)object).close();
                }
            }
            this.a();
            this.a = false;
            long l3 = System.nanoTime() / 1000000L;
            System.out.println("Loaded region successfully in " + (l3 - l2) + "ms");
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public final synchronized void a() {
        long l2 = (Long)baritone.a.a().cachedChunksExpirySeconds.value;
        if (l2 < 0L) {
            return;
        }
        long l3 = System.currentTimeMillis();
        long l4 = l3 - l2 * 1000L;
        for (int i2 = 0; i2 < 32; ++i2) {
            for (int i3 = 0; i3 < 32; ++i3) {
                if (this.a[i2][i3] == null || this.a[i2][i3].a >= l4) continue;
                System.out.println("Removing chunk " + (i2 + 32 * this.a) + "," + (i3 + 32 * this.b) + " because it was cached " + (l3 - this.a[i2][i3].a) / 1000L + " seconds ago, and max age is " + l2);
                this.a[i2][i3] = null;
            }
        }
    }

    public final synchronized m a() {
        m m2 = null;
        for (int i2 = 0; i2 < 32; ++i2) {
            for (int i3 = 0; i3 < 32; ++i3) {
                if (this.a[i2][i3] == null || m2 != null && this.a[i2][i3].a <= m2.a) continue;
                m2 = this.a[i2][i3];
            }
        }
        return m2;
    }

    @Override
    public final int getX() {
        return this.a;
    }

    @Override
    public final int getZ() {
        return this.b;
    }

    private static Path a(Path path, int n2, int n3) {
        return Paths.get(path.toString(), "r." + n2 + "." + n3 + ".bcr");
    }
}

