/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.ChunkPos
 */
package baritone;

import baritone.api.cache.ICachedWorld;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.process.IExploreProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.MyChunkPos;
import baritone.eb;
import baritone.ey;
import baritone.o;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ea
extends ey
implements IExploreProcess {
    private BlockPos a;
    private d a;
    private int a;

    public ea(baritone.a a2) {
        super(a2);
    }

    @Override
    public final boolean isActive() {
        return this.a != null;
    }

    @Override
    public final void explore(int n2, int n3) {
        this.a = new BlockPos(n2, 0, n3);
        this.a = 0;
    }

    @Override
    public final void applyJsonFilter(Path path, boolean bl2) {
        this.a = new d(this, path, bl2);
    }

    private c a() {
        c c2 = this.a != null ? new b(this.a, new a(this)) : new a(this);
        return c2;
    }

    @Override
    public final PathingCommand onTick(boolean bl2, boolean bl3) {
        if (bl2) {
            this.logDirect("Failed");
            if (((Boolean)baritone.a.a().notificationOnExploreFinished.value).booleanValue()) {
                this.logNotification("Exploration failed", true);
            }
            this.onLostControl();
            return null;
        }
        Goal[] goalArray = this.a();
        if (!((Boolean)baritone.a.a().disableCompletionCheck.value).booleanValue() && goalArray.a() == 0) {
            this.logDirect("Explored all chunks");
            if (((Boolean)baritone.a.a().notificationOnExploreFinished.value).booleanValue()) {
                this.logNotification("Explored all chunks", false);
            }
            this.onLostControl();
            return null;
        }
        ea ea2 = this;
        if ((goalArray = ea2.a(ea2.a, (c)goalArray)) == null) {
            this.logDebug("awaiting region load from disk");
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        return new PathingCommand(new GoalComposite(goalArray), PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Goal[] a(BlockPos blockPos2, c c2) {
        int n2 = blockPos2.getX() >> 4;
        int n3 = blockPos2.getZ() >> 4;
        int n4 = Math.min(c2.a(), (Integer)baritone.a.a().exploreChunkSetMinimumSize.value);
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        int n5 = (Integer)baritone.a.a().worldExploringChunkOffset.value;
        int n6 = this.a;
        block5: while (true) {
            int n7 = -n6;
            while (true) {
                int n8;
                if (n7 <= n6) {
                    n8 = n6 - Math.abs(n7);
                } else {
                    if (n6 % 10 == 0) {
                        n4 = Math.min(c2.a(), (Integer)baritone.a.a().exploreChunkSetMinimumSize.value);
                    }
                    if (arrayList.size() >= n4) {
                        return (Goal[])arrayList.stream().map(blockPos -> {
                            int n2 = blockPos.getZ();
                            int n3 = blockPos.getX();
                            if ((Integer)baritone.a.a().exploreMaintainY.value == -1) {
                                return new GoalXZ(n3, n2);
                            }
                            return new eb(n3, n2);
                        }).toArray(Goal[]::new);
                    }
                    if (arrayList.isEmpty()) {
                        this.a = n6 + 1;
                    }
                    ++n6;
                    continue block5;
                }
                block7: for (int i2 = 0; i2 < 2; ++i2) {
                    int n9 = ((i2 << 1) - 1) * n8;
                    int n10 = Math.abs(n7) + Math.abs(n9);
                    if (n10 != n6) {
                        throw new IllegalStateException(String.format("Offset %s %s has distance %s, expected %s", n7, n9, n10, n6));
                    }
                    switch (c2.a(n2 + n7, n3 + n9).ordinal()) {
                        case 2: {
                            return null;
                        }
                        case 1: {
                            break;
                        }
                        case 0: {
                            continue block7;
                        }
                    }
                    n10 = (n2 + n7 << 4) + 8;
                    int n11 = (n3 + n9 << 4) + 8;
                    int n12 = n5 << 4;
                    n10 = n7 < 0 ? (n10 -= n12) : (n10 += n12);
                    n11 = n9 < 0 ? (n11 -= n12) : (n11 += n12);
                    arrayList.add(new BlockPos(n10, 0, n11));
                }
                ++n7;
            }
            break;
        }
    }

    @Override
    public final void onLostControl() {
        this.a = null;
    }

    @Override
    public final String displayName0() {
        ea ea2 = this;
        return "Exploring around " + String.valueOf(this.a) + ", distance completed " + this.a + ", currently going to " + String.valueOf(new GoalComposite(ea2.a(ea2.a, this.a())));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class d
    implements c {
        private final boolean a;
        private final LongOpenHashSet a;
        private final MyChunkPos[] a;
        private /* synthetic */ ea a;

        d(ea myChunkPosArray, Path path, boolean bl2) {
            this.a = myChunkPosArray;
            this.a = bl2;
            Gson gson = new GsonBuilder().create();
            this.a = (MyChunkPos[])gson.fromJson((Reader)new InputStreamReader(Files.newInputStream(path, new OpenOption[0])), MyChunkPos[].class);
            myChunkPosArray.logDirect("Loaded " + this.a.length + " positions");
            this.a = new LongOpenHashSet();
            myChunkPosArray = this.a;
            int n2 = this.a.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                MyChunkPos myChunkPos = myChunkPosArray[i2];
                this.a.add(ChunkPos.asLong((int)myChunkPos.x, (int)myChunkPos.z));
            }
        }

        @Override
        public final e a(int n2, int n3) {
            if (this.a.contains(ChunkPos.asLong((int)n2, (int)n3)) ^ this.a) {
                return e.a;
            }
            return e.c;
        }

        @Override
        public final int a() {
            if (!this.a) {
                return Integer.MAX_VALUE;
            }
            int n2 = 0;
            a a2 = new a(this.a);
            MyChunkPos[] myChunkPosArray = this.a;
            int n3 = this.a.length;
            for (int i2 = 0; i2 < n3; ++i2) {
                MyChunkPos myChunkPos = myChunkPosArray[i2];
                if (a2.a(myChunkPos.x, myChunkPos.z) == e.a || ++n2 < (Integer)baritone.a.a().exploreChunkSetMinimumSize.value) continue;
                return n2;
            }
            return n2;
        }
    }

    static interface c {
        public e a(int var1, int var2);

        public int a();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class b
    implements c {
        private final d a;
        private final a a;

        b(d d2, a a2) {
            this.a = d2;
            this.a = a2;
        }

        @Override
        public final e a(int n2, int n3) {
            if (this.a.a(n2, n3) == e.a) {
                return e.a;
            }
            return this.a.a(n2, n3);
        }

        @Override
        public final int a() {
            return Math.min(this.a.a(), this.a.a());
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class a
    implements c {
        private final ICachedWorld a;
        private /* synthetic */ ea a;

        a(ea ea2) {
            this.a = ea2;
            this.a = ((ey)((ea)this.a)).a.a.a().getCachedWorld();
        }

        @Override
        public final e a(int n2, int n3) {
            if (this.a.isCached(n2 <<= 4, n3 <<= 4)) {
                return e.a;
            }
            int n4 = n3;
            if (!(((o)this.a).a(n2 >> 9, n4 >> 9) != null)) {
                baritone.a.a().execute(() -> ((o)this.a).b(n2 >> 9, n3 >> 9));
                return e.c;
            }
            return e.b;
        }

        @Override
        public final int a() {
            return Integer.MAX_VALUE;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class e
    extends Enum<e> {
        public static final /* enum */ e a = new e();
        public static final /* enum */ e b = new e();
        public static final /* enum */ e c = new e();
        private static final /* synthetic */ e[] a;

        public static e[] values() {
            return (e[])a.clone();
        }

        public static e valueOf(String string) {
            return Enum.valueOf(e.class, string);
        }

        static {
            a = new e[]{a, b, c};
        }
    }
}

