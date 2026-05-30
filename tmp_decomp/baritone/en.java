/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.floats.FloatArrayList
 *  it.unimi.dsi.fastutil.floats.FloatListIterator
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.FireworkRocketEntity
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.component.Fireworks
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkSource
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

import baritone.api.behavior.look.ITickableAimProcessor;
import baritone.api.event.events.PacketEvent;
import baritone.api.event.events.TickEvent;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.Pair;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.SettingsUtil;
import baritone.cc;
import baritone.dz;
import baritone.em;
import baritone.eo;
import baritone.ep;
import baritone.er;
import baritone.es;
import baritone.ex;
import baritone.fb;
import baritone.fq;
import dev.babbaj.pathfinder.NetherPathfinder;
import dev.babbaj.pathfinder.Octree;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class en
implements Helper {
    public final baritone.a a;
    public final IPlayerContext a;
    public final List<Pair<Vec3, Vec3>> a;
    public final List<Pair<Vec3, Vec3>> b;
    public List<Vec3> c;
    public BetterBlockPos a;
    public List<BetterBlockPos> d;
    public final ep a;
    public final d a;
    final dz a;
    public int a;
    public int b;
    public boolean a;
    public int c;
    public boolean b;
    public final int[] a;
    public fb a;
    public final em a;
    public final BetterBlockPos b;
    final boolean c;
    private final ExecutorService a;
    public Future<f> a;
    public f a;
    public boolean d;
    public long a;
    public int d = 0;
    public final Queue<Runnable> a = new LinkedList();

    public en(baritone.a a2, dz dz2, BlockPos blockPos, boolean bl2) {
        this.a = a2;
        this.a = a2.getPlayerContext();
        this.a = new CopyOnWriteArrayList();
        this.b = new CopyOnWriteArrayList<Pair<Vec3, Vec3>>();
        this.a = new d(this);
        this.a = dz2;
        this.b = new BetterBlockPos(blockPos);
        this.c = bl2;
        this.a = Executors.newSingleThreadExecutor();
        this.a = new int[2];
        this.a = new ep((Long)baritone.a.a().elytraNetherSeed.value);
        this.a = new em(this.a);
    }

    public final void a(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof ClientboundPlayerPositionPacket) {
            this.a.minecraft().execute(() -> {
                this.b = (Integer)baritone.a.a().elytraFireworkSetbackUseDelay.value;
            });
        }
    }

    public final void a() {
        if (!((Boolean)baritone.a.a().elytraAutoJump.value).booleanValue() || this.a.player().isFallFlying()) {
            this.a.a();
        }
    }

    public final void b() {
        if (this.a != null) {
            this.a.cancel(true);
        }
        this.a.shutdown();
        try {
            while (!this.a.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
            }
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        ep ep2 = this.a;
        NetherPathfinder.cancel(ep2.a);
        ep2.a.shutdownNow();
        try {
            while (!ep2.a.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
            }
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        NetherPathfinder.freeContext(ep2.a);
    }

    public final void c() {
        ChunkSource chunkSource = this.a.world().getChunkSource();
        BetterBlockPos betterBlockPos = this.a.playerFeet();
        int n2 = betterBlockPos.getX() >> 4;
        int n3 = betterBlockPos.getZ() >> 4;
        int n4 = n2 - 40;
        int n5 = n3 - 40;
        n2 += 40;
        n3 += 40;
        while (n4 <= n2) {
            for (int i2 = n5; i2 <= n3; ++i2) {
                LevelChunk levelChunk = chunkSource.getChunk(n4, i2, false);
                if (levelChunk == null || levelChunk.isEmpty()) continue;
                this.a.a(levelChunk);
            }
            ++n4;
        }
    }

    public final void a(TickEvent object) {
        if (((TickEvent)object).getType() == TickEvent.Type.IN && this.d) {
            this.a.c();
            object = new g(this, true);
            this.a = this.a.submit(() -> this.b((g)object));
            this.d = false;
        }
    }

    public final f a(g g2) {
        eo eo2 = g2.a;
        int n2 = this.a ? eo2.size() - 1 : g2.a;
        Vec3 vec3 = g2.a;
        f f2 = null;
        for (int i2 = 0; i2 < 3; ++i2) {
            int[] nArray;
            if (g2.a.a()) {
                int[] nArray2 = new int[4];
                nArray2[0] = 20;
                nArray2[1] = 10;
                nArray2[2] = 5;
                nArray = nArray2;
                nArray2[3] = 0;
            } else {
                int[] nArray3 = new int[1];
                nArray = nArray3;
                nArray3[0] = 0;
            }
            int[] nArray4 = nArray;
            int n3 = i2 == 0 ? 2 : 3;
            int n4 = n2;
            for (int i3 = Math.min(n2 + 20, eo2.size() - 1); i3 >= n4; --i3) {
                Object object;
                ArrayList<Pair<Vec3, Integer>> arrayList = new ArrayList<Pair<Vec3, Integer>>();
                Object object2 = nArray4;
                int n5 = nArray4.length;
                for (int i4 = 0; i4 < n5; ++i4) {
                    int n6 = object2[i4];
                    if (i2 == 0 || i3 == n4) {
                        arrayList.add(new Pair<Vec3, Integer>(eo2.a(i3), n6));
                        continue;
                    }
                    if (i2 == 1) {
                        double[] dArray = new double[]{1.0, 0.75, 0.5, 0.25};
                        int cfr_ignored_0 = dArray.length;
                        for (int i5 = 0; i5 < 4; ++i5) {
                            double d2 = dArray[i5];
                            object = d2 == 1.0 ? eo2.a(i3) : eo2.a(i3).scale(d2).add(eo2.a(i3 - 1).scale(1.0 - d2));
                            arrayList.add(new Pair<Vec3, Integer>((Vec3)object, n6));
                        }
                        continue;
                    }
                    object = eo2.a(i3).subtract(eo2.a(i3 - 1));
                    int n7 = ex.a(object.length());
                    object = object.normalize();
                    Vec3 vec32 = eo2.a(i3);
                    for (int i6 = 0; i6 < n7; ++i6) {
                        arrayList.add(new Pair<Vec3, Integer>(vec32, n6));
                        vec32 = vec32.subtract(object);
                    }
                }
                object2 = arrayList.iterator();
                while (object2.hasNext()) {
                    Pair pair = (Pair)object2.next();
                    Integer n8 = (Integer)pair.second();
                    Vec3 vec33 = ((Vec3)pair.first()).add(0.0, (double)n8.intValue(), 0.0);
                    if (this.a) {
                        vec33 = vec33.add(0.5, 0.5, 0.5);
                    }
                    if (n8 != 0 && (i3 + n3 >= eo2.size() || (!(vec3.distanceTo(vec33) < 40.0) ? !this.a(vec33, eo2.a(i3), false) : !this.a(vec33, eo2.a(i3 + n3).add(0.0, (double)n8.intValue(), 0.0), false) || !this.a(vec33, eo2.a(i3 + n3), false)))) continue;
                    double d3 = (Double)baritone.a.a().elytraMinimumAvoidance.value;
                    object = i2 == 2 ? null : Double.valueOf(i2 == 0 ? d3 * 2.0 : d3);
                    if (!this.a(g2, vec33, (Double)object)) continue;
                    float f3 = RotationUtils.calcRotationFromVec3d(vec3, vec33, this.a.playerRotations()).getYaw();
                    Pair<Float, Boolean> pair2 = this.a(g2, vec33, i2);
                    if (pair2 == null) {
                        f2 = new f(g2, new Rotation(f3, this.a.playerRotations().getPitch()), null, false, false);
                        continue;
                    }
                    return new f(g2, new Rotation(f3, pair2.first().floatValue()), vec33, true, pair2.second());
                }
            }
        }
        return f2;
    }

    public final void a(Vec3 vec3, Vec3 vec32, boolean bl2, boolean bl3) {
        block6: {
            block7: {
                double d2;
                double d3;
                block8: {
                    if (this.b > 0) {
                        en en2 = this;
                        en2.logDebug("waiting for elytraFireworkSetbackUseDelay: " + en2.b);
                        return;
                    }
                    if (this.a) {
                        return;
                    }
                    boolean bl4 = (Boolean)baritone.a.a().elytraConserveFireworks.value == false || this.a.player().position().y < vec32.y + 5.0;
                    d3 = new Vec3(this.a.player().getDeltaMovement().x, this.a.player().position().y < vec32.y ? Math.max(0.0, this.a.player().getDeltaMovement().y) : this.a.player().getDeltaMovement().y, this.a.player().getDeltaMovement().z).lengthSqr();
                    d2 = (Double)baritone.a.a().elytraFireworkSpeed.value;
                    if (this.a > 0) break block6;
                    if (bl3) break block7;
                    if (bl2 || !bl4) break block6;
                    if (this.a.player().position().y < vec32.y - 5.0) break block8;
                    Vec3 vec33 = new Vec3(vec32.x + 0.5, this.a.player().position().y, vec32.z + 0.5);
                    if (!(vec3.distanceTo(vec33) > 5.0)) break block6;
                }
                double d4 = d2;
                if (!(d3 < d4 * d4)) break block6;
            }
            if (!this.a.a.a(true, en::b) && !this.a.a.a(true, en::a)) {
                this.logDirect("no fireworks");
                return;
            }
            this.a("attempting to use firework" + (bl3 ? " (forced)" : ""));
            this.a.playerController().processRightClick(this.a.player(), this.a.world(), InteractionHand.MAIN_HAND);
            this.c = 10 * (1 + en.a(this.a.player().getItemInHand(InteractionHand.MAIN_HAND)).orElse(0));
            this.a = 10;
            this.b = true;
        }
    }

    public static boolean a(ItemStack itemStack) {
        if (itemStack.getItem() != Items.FIREWORK_ROCKET) {
            return false;
        }
        return (itemStack = (Fireworks)itemStack.get(DataComponents.FIREWORKS)) != null && itemStack.explosions().isEmpty();
    }

    private static boolean b(ItemStack itemStack) {
        return en.a(itemStack).isPresent();
    }

    private static OptionalInt a(ItemStack itemStack) {
        if ((itemStack = (Fireworks)itemStack.get(DataComponents.FIREWORKS)) != null && itemStack.explosions().isEmpty()) {
            return OptionalInt.of(itemStack.flightDuration());
        }
        return OptionalInt.empty();
    }

    public final Optional<FireworkRocketEntity> a() {
        return this.a.entitiesStream().filter(entity -> entity instanceof FireworkRocketEntity).filter(entity -> Objects.equals(((fq)entity).getBoostedEntity(), this.a.player())).map(entity -> (FireworkRocketEntity)entity).findFirst();
    }

    private boolean a(g object, Vec3 object2, Double d2) {
        Vec3 vec3 = ((g)object).a;
        boolean bl2 = ((g)object).a;
        if (!this.a(vec3, (Vec3)object2, bl2)) {
            return false;
        }
        if (d2 == null) {
            return true;
        }
        object = ((g)object).a.inflate(d2.doubleValue());
        double d3 = object2.x - vec3.x;
        double d4 = object2.y - vec3.y;
        double d5 = object2.z - vec3.z;
        object2 = new double[]{((AABB)object).minX, ((AABB)object).minY, ((AABB)object).minZ, ((AABB)object).minX, ((AABB)object).minY, ((AABB)object).maxZ, ((AABB)object).minX, ((AABB)object).maxY, ((AABB)object).minZ, ((AABB)object).minX, ((AABB)object).maxY, ((AABB)object).maxZ, ((AABB)object).maxX, ((AABB)object).minY, ((AABB)object).minZ, ((AABB)object).maxX, ((AABB)object).minY, ((AABB)object).maxZ, ((AABB)object).maxX, ((AABB)object).maxY, ((AABB)object).minZ, ((AABB)object).maxX, ((AABB)object).maxY, ((AABB)object).maxZ};
        object = new double[]{((AABB)object).minX + d3, ((AABB)object).minY + d4, ((AABB)object).minZ + d5, ((AABB)object).minX + d3, ((AABB)object).minY + d4, ((AABB)object).maxZ + d5, ((AABB)object).minX + d3, ((AABB)object).maxY + d4, ((AABB)object).minZ + d5, ((AABB)object).minX + d3, ((AABB)object).maxY + d4, ((AABB)object).maxZ + d5, ((AABB)object).maxX + d3, ((AABB)object).minY + d4, ((AABB)object).minZ + d5, ((AABB)object).maxX + d3, ((AABB)object).minY + d4, ((AABB)object).maxZ + d5, ((AABB)object).maxX + d3, ((AABB)object).maxY + d4, ((AABB)object).minZ + d5, ((AABB)object).maxX + d3, ((AABB)object).maxY + d4, ((AABB)object).maxZ + d5};
        if (((Boolean)baritone.a.a().elytraRenderHitboxRaytraces.value).booleanValue()) {
            boolean bl3 = true;
            for (int i2 = 0; i2 < 8; ++i2) {
                Vec3 vec32 = new Vec3((double)object2[i2 * 3], (double)object2[i2 * 3 + 1], (double)object2[i2 * 3 + 2]);
                Vec3 vec33 = new Vec3((double)object[i2 * 3], (double)object[i2 * 3 + 1], (double)object[i2 * 3 + 2]);
                if (this.a(vec32, vec33, false)) continue;
                bl3 = false;
            }
            return bl3;
        }
        return this.a.a((double[])object2, (double[])object);
    }

    public final boolean a(Vec3 vec3, Vec3 vec32, boolean bl2) {
        if (!bl2) {
            bl2 = vec3.equals((Object)vec32) || this.a.a(vec3, vec32);
        } else {
            boolean bl3 = bl2 = this.a.world().clip(new ClipContext(vec3, vec32, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)this.a.player())).getType() == HitResult.Type.MISS;
        }
        if (((Boolean)baritone.a.a().elytraRenderRaytraces.value).booleanValue()) {
            (bl2 ? this.a : this.b).add(new Pair<Vec3, Vec3>(vec3, vec32));
        }
        return bl2;
    }

    private static FloatArrayList a(float f2, boolean bl2) {
        float f3;
        float f4 = bl2 ? -90.0f : Math.max(f2 - (float)((Integer)baritone.a.a().elytraPitchRange.value).intValue(), -89.0f);
        float f5 = bl2 ? 90.0f : Math.min(f2 + (float)((Integer)baritone.a.a().elytraPitchRange.value).intValue(), 89.0f);
        FloatArrayList floatArrayList = new FloatArrayList(ex.b(f5 - f4) + 1);
        for (f3 = f2; f3 <= f5; f3 += 1.0f) {
            floatArrayList.add(f3);
        }
        for (f3 = f2 - 1.0f; f3 >= f4; f3 -= 1.0f) {
            floatArrayList.add(f3);
        }
        return floatArrayList;
    }

    private Pair<Float, Boolean> a(g optional, Vec3 object, int n2) {
        boolean bl2 = n2 == 2;
        FloatArrayList floatArrayList = en.a(RotationUtils.calcRotationFromVec3d(((g)((Object)optional)).a, object, this.a.playerRotations()).getPitch(), bl2);
        object = (arg_0, arg_1, arg_2) -> this.a((g)((Object)optional), (Vec3)object, n2, floatArrayList, arg_0, arg_1, arg_2);
        ArrayList<c> arrayList = new ArrayList<c>();
        if (((g)((Object)optional)).a.a()) {
            int n3 = ((g)((Object)optional)).a.a();
            if (n3 == 0) {
                int n4 = Math.max(4, 10 - ((g)((Object)optional)).a.b());
                arrayList.add(new c(n4, 1, 0));
            } else if (n3 <= 5) {
                arrayList.add(new c(n3 + 5, n3, 0));
            } else {
                arrayList.add(new c(n3 + 1, n3, 0));
            }
        }
        int n5 = bl2 ? 3 : (((g)((Object)optional)).a.a() ? Math.max(5, ((g)((Object)optional)).a.a()) : (Integer)baritone.a.a().elytraSimulationTicks.value);
        arrayList.add(new c(n5, ((g)((Object)optional)).a.a() ? n5 : 0, 0));
        Optional<e> optional2 = arrayList.stream().map(arg_0 -> en.b((b)object, arg_0)).filter(Objects::nonNull).findFirst();
        if (optional2.isPresent()) {
            return new Pair<Float, Boolean>(Float.valueOf(optional2.get().a), Boolean.FALSE);
        }
        if (bl2) {
            optional = new ArrayList<c>();
            optional.add(new c(n5, 10, 3));
            optional.add(new c(n5, 10, 2));
            optional.add(new c(n5, 10, 1));
            optional = optional.stream().map(arg_0 -> en.a((b)object, arg_0)).filter(Objects::nonNull).findFirst();
            if (optional.isPresent()) {
                return new Pair<Float, Boolean>(Float.valueOf(optional.get().a), Boolean.TRUE);
            }
        }
        return null;
    }

    final boolean a(int n2, int n3, int n4, boolean bl2) {
        boolean bl3;
        if (bl2) {
            BlockState blockState = this.a.a(n2, n3, n4);
            return blockState.getBlock() instanceof AirBlock || cc.e(blockState);
        }
        int n5 = n4;
        n4 = n3;
        n3 = n2;
        em em2 = this.a;
        if ((n4 | 127 - n4) < 0) {
            bl3 = false;
        } else {
            int n6;
            int n7;
            if (em2.b == 0L | ((n7 = n3 >> 4) ^ em2.a | (n6 = n5 >> 4) ^ em2.b) != 0) {
                em2.a = n7;
                em2.b = n6;
                em2.b = NetherPathfinder.getOrCreateChunk(em2.a, n7, n6);
            }
            bl3 = Octree.getBlock(em2.b, n3 & 0xF, n4 & 0x7F, n5 & 0xF);
        }
        return !bl3;
    }

    public final void a(int n2, int n3, ClickType clickType) {
        this.a.add(() -> this.a(n2, n3, 0, clickType));
    }

    public final void a(String string) {
        if (((Boolean)baritone.a.a().elytraChatSpam.value).booleanValue()) {
            this.logDebug(string);
        }
    }

    private /* synthetic */ void a(int n2, int n3, int n4, ClickType clickType) {
        this.a.playerController().windowClick(n2, n3, n4, clickType, (Player)this.a.player());
    }

    private static /* synthetic */ e a(b b2, c c2) {
        return (e)b2.apply(c2.a, c2.b, c2.c);
    }

    private static /* synthetic */ e b(b b2, c c2) {
        return (e)b2.apply(c2.a, c2.b, c2.c);
    }

    private /* synthetic */ e a(g arrayList, Vec3 object, int n2, FloatArrayList floatArrayList, int n3, int n4, int n5) {
        int n6 = n5;
        n5 = n4;
        n4 = n3;
        FloatListIterator floatListIterator = floatArrayList.iterator();
        int n7 = n2;
        Object vec3 = object;
        object = arrayList;
        arrayList = this;
        Vec3 vec32 = vec3.subtract(((g)object).a);
        Vec3 vec33 = vec32.normalize();
        ArrayDeque<e> arrayDeque = new ArrayDeque<e>();
        while (floatListIterator.hasNext()) {
            ArrayList<Vec3> arrayList3;
            ArrayList<Vec3> arrayList2;
            Object object2;
            float f2;
            block13: {
                f2 = floatListIterator.nextFloat();
                Vec3 vec34 = vec32;
                object2 = object;
                Object object3 = arrayList;
                ITickableAimProcessor iTickableAimProcessor = ((g)object2).a.fork();
                Vec3 vec35 = ((g)object2).b;
                AABB aABB = ((g)object2).a;
                ArrayList<Vec3> arrayList4 = new ArrayList<Vec3>(n4 + 1);
                arrayList4.add(Vec3.ZERO);
                int n8 = n5;
                for (int i2 = 0; i2 < n4; ++i2) {
                    double d2;
                    double d3;
                    double cfr_ignored_0 = aABB.minX;
                    double cfr_ignored_1 = aABB.maxX;
                    double cfr_ignored_2 = aABB.minX;
                    double cfr_ignored_3 = aABB.minZ;
                    double cfr_ignored_4 = aABB.maxZ;
                    double cfr_ignored_5 = aABB.minZ;
                    if (vec34.lengthSqr() < 1.0) break;
                    Rotation rotation = iTickableAimProcessor.nextRotation(RotationUtils.calcRotationFromVec3d(Vec3.ZERO, vec34, ((en)object3).a.playerRotations()).withPitch(f2));
                    Vec3 vec36 = RotationUtils.calcLookDirectionFromRotation(rotation);
                    float f3 = rotation.getPitch();
                    rotation = vec36;
                    double d4 = vec35.x;
                    double d5 = vec35.y;
                    double d6 = vec35.z;
                    float f4 = f3 * ((float)Math.PI / 180);
                    double d7 = Math.sqrt(((Vec3)rotation).x * ((Vec3)rotation).x + ((Vec3)rotation).z * ((Vec3)rotation).z);
                    double d8 = d4;
                    double d9 = d6;
                    double d10 = Math.sqrt(d8 * d8 + d9 * d9);
                    double d11 = rotation.length();
                    f3 = Mth.cos((float)f4);
                    f3 = (float)((double)f3 * (double)f3 * Math.min(1.0, d11 / 0.4));
                    d5 += -0.08 + (double)f3 * 0.06;
                    if (d3 < 0.0 && d7 > 0.0) {
                        d2 = d5 * -0.1 * (double)f3;
                        d5 += d2;
                        d4 += ((Vec3)rotation).x * d2 / d7;
                        d6 += ((Vec3)rotation).z * d2 / d7;
                    }
                    if (f4 < 0.0f) {
                        d2 = d10 * (double)(-Mth.sin((float)f4)) * 0.04;
                        d5 += d2 * 3.2;
                        d4 -= ((Vec3)rotation).x * d2 / d7;
                        d6 -= ((Vec3)rotation).z * d2 / d7;
                    }
                    if (d7 > 0.0) {
                        d4 += (((Vec3)rotation).x / d7 * d10 - d4) * 0.1;
                        d6 += (((Vec3)rotation).z / d7 * d10 - d6) * 0.1;
                    }
                    vec35 = new Vec3(d4 *= (double)0.99f, d5 *= (double)0.98f, d6 *= (double)0.99f);
                    vec34 = vec34.subtract(vec35);
                    rotation = aABB.inflate(vec35.x, vec35.y, vec35.z).inflate(0.01);
                    int n9 = ex.b(((AABB)rotation).maxX);
                    int n10 = ex.a(((AABB)rotation).minY);
                    int n11 = ex.b(((AABB)rotation).maxY);
                    int n12 = ex.a(((AABB)rotation).minZ);
                    int n13 = ex.b(((AABB)rotation).maxZ);
                    for (int i3 = ex.a(((AABB)rotation).minX); i3 < n9; ++i3) {
                        for (int i4 = n10; i4 < n11; ++i4) {
                            for (int i5 = n12; i5 < n13; ++i5) {
                                if (((en)object3).a(i3, i4, i5, ((g)object2).a)) continue;
                                arrayList2 = null;
                                break block13;
                            }
                        }
                    }
                    aABB = aABB.move(vec35);
                    ArrayList<Vec3> arrayList5 = arrayList4;
                    arrayList5.add(((Vec3)arrayList5.get(arrayList4.size() - 1)).add(vec35));
                    if (i2 < n6 || n8-- <= 0) continue;
                    vec35 = vec35.add(vec36.x * 0.1 + (vec36.x * 1.5 - vec35.x) * 0.5, vec36.y * 0.1 + (vec36.y * 1.5 - vec35.y) * 0.5, vec36.z * 0.1 + (vec36.z * 1.5 - vec35.z) * 0.5);
                }
                arrayList2 = arrayList3 = arrayList4;
            }
            if (arrayList2 == null) continue;
            ArrayList<Vec3> arrayList6 = arrayList3;
            object2 = (Vec3)arrayList6.get(arrayList6.size() - 1);
            double d12 = vec33.dot(object2.normalize());
            if (((en)arrayList).a) {
                d12 = -vec32.subtract((Vec3)object2).length();
            }
            if ((object2 = (e)arrayDeque.peek()) != null && !(d12 > ((e)object2).a)) continue;
            arrayDeque.push(new e(f2, d12, arrayList3));
        }
        block5: for (e e2 : arrayDeque) {
            if (n7 < 2) {
                for (int i6 = e2.a.size() - 1; i6 > 0; --i6) {
                    if (!((en)arrayList).a(((g)object).a.add((Vec3)e2.a.get(i6)), (Vec3)vec3, ((g)object).a)) continue block5;
                }
            } else if (!((en)arrayList).a(((g)object).a.add((Vec3)e2.a.get(e2.a.size() - 1)), (Vec3)vec3, ((g)object).a)) continue;
            ((en)arrayList).c = (List<Vec3>)e2.a;
            return e2;
        }
        return null;
    }

    private /* synthetic */ f b(g g2) {
        return this.a(g2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public final class d {
        public eo a;
        public boolean a;
        public boolean b;
        public int a;
        public int b;
        public int c;
        public /* synthetic */ en a;

        public d(en en2) {
            this.a = en2;
            this.a();
        }

        public final CompletableFuture<Void> a() {
            d d2 = this;
            return d2.a(d2.a.a.playerFeet());
        }

        /*
         * Ignored method signature, as it can't be verified against descriptor
         */
        public final CompletableFuture a(BetterBlockPos betterBlockPos) {
            long l2 = System.nanoTime();
            return ((CompletableFuture)this.a(betterBlockPos, this.a.b, UnaryOperator.identity()).thenRun(() -> {
                double d2 = this.a.a(0).distanceTo(this.a.a(this.a.size() - 1));
                if (this.a) {
                    this.a.a(String.format("Computed path (%.1f blocks in %.4f seconds)", d2, (double)(System.nanoTime() - l2) / 1.0E9));
                    return;
                }
                this.a.a(String.format("Computed segment (Next %.1f blocks in %.4f seconds)", d2, (double)(System.nanoTime() - l2) / 1.0E9));
            })).whenComplete((object, throwable) -> {
                this.b = false;
                if (throwable != null) {
                    object = throwable.getCause();
                    if (object instanceof er) {
                        this.a.logDirect("Failed to compute path to destination");
                        return;
                    }
                    this.a.logUnhandledException((Throwable)object);
                }
            });
        }

        private CompletableFuture<Void> a(OptionalInt optionalInt) {
            if (this.b) {
                throw new IllegalStateException("already recalculating");
            }
            this.b = true;
            List list = optionalInt.isPresent() ? this.a.subList(optionalInt.getAsInt() + 1, this.a.size()) : Collections.emptyList();
            boolean bl2 = this.a;
            d d2 = this;
            return d2.a(d2.a.a.playerFeet(), optionalInt.isPresent() ? this.a.a(optionalInt.getAsInt()) : this.a.b, es2 -> {
                boolean bl3 = bl2 || es2.a && !optionalInt.isPresent();
                Stream stream = list.stream();
                list = es2;
                return new es(Stream.concat(((es)object).a, stream), bl3);
            }).whenComplete((object, throwable) -> {
                this.b = false;
                if (throwable != null) {
                    object = throwable.getCause();
                    if (object instanceof er) {
                        this.a.logDirect("Failed to recompute segment");
                        return;
                    }
                    this.a.logUnhandledException((Throwable)object);
                }
            });
        }

        public final void a(int n2) {
            if (this.b) {
                return;
            }
            this.b = true;
            List list = this.a.subList(0, n2 + 1);
            long l2 = System.nanoTime();
            BetterBlockPos betterBlockPos = this.a.a(n2);
            ((CompletableFuture)this.a(betterBlockPos, this.a.b, object2 -> {
                es es2 = object2;
                object2 = list.stream();
                list = es2;
                return new es(Stream.concat(object2, ((es)object).a), ((es)object).a);
            }).thenRun(() -> {
                int n2 = this.a.size() - list.size() - 1;
                double d2 = this.a.a(0).distanceTo(this.a.a(n2));
                if (this.a) {
                    this.a.a(String.format("Computed path (%.1f blocks in %.4f seconds)", d2, (double)(System.nanoTime() - l2) / 1.0E9));
                    return;
                }
                this.a.a(String.format("Computed segment (Next %.1f blocks in %.4f seconds)", d2, (double)(System.nanoTime() - l2) / 1.0E9));
            })).whenComplete((object, throwable) -> {
                this.b = false;
                if (throwable != null) {
                    object = throwable.getCause();
                    if (object instanceof er) {
                        this.a.logDirect("Failed to compute next segment");
                        if (this.a.a.player().distanceToSqr(betterBlockPos.getCenter()) < 256.0) {
                            this.a.a("Player is near the segment start, therefore repeating this calculation is pointless. Marking as complete");
                            this.a = true;
                            return;
                        }
                    } else {
                        this.a.logUnhandledException((Throwable)object);
                    }
                }
            });
        }

        public final void a() {
            this.a = eo.a();
            this.a = true;
            this.b = false;
            this.c = 0;
            this.b = 0;
            this.a = 0;
        }

        private void a(es es2) {
            List<BetterBlockPos> list = es2.a.collect(Collectors.toList());
            Object object = new HashMap();
            for (int i2 = 0; i2 < list.size(); ++i2) {
                BetterBlockPos betterBlockPos = (BetterBlockPos)((Object)list.get(i2));
                if (object.containsKey((Object)betterBlockPos)) {
                    int n2 = (Integer)object.get((Object)betterBlockPos);
                    while (i2 > n2) {
                        list.remove(i2);
                        --i2;
                    }
                    continue;
                }
                object.put(betterBlockPos, i2);
            }
            if (this.a.c) {
                BlockPos blockPos;
                BlockPos blockPos2;
                object = this.a.b;
                if (!list.isEmpty()) {
                    List<BetterBlockPos> list2 = list;
                    blockPos2 = list2.get(list2.size() - 1);
                } else {
                    blockPos2 = blockPos = null;
                }
                if (blockPos2 != null && this.a.a(Vec3.atLowerCornerOf((Vec3i)object), Vec3.atLowerCornerOf((Vec3i)blockPos), false)) {
                    list.add(new BetterBlockPos((BlockPos)object));
                } else {
                    this.a.logDirect("unable to land at " + String.valueOf((Object)this.a.b));
                    this.a.a.a(new BetterBlockPos(this.a.b));
                }
            }
            this.a = new eo(list);
            this.a = es2.a;
            this.c = 0;
            this.b = 0;
            this.a = 0;
        }

        /*
         * Ignored method signature, as it can't be verified against descriptor
         */
        private CompletableFuture a(BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2, UnaryOperator unaryOperator) {
            return ((CompletableFuture)((CompletableFuture)this.a.a.a(betterBlockPos, betterBlockPos2).thenApply(es::a)).thenApply((Function)unaryOperator)).thenAcceptAsync(this::a, arg_0 -> ((Minecraft)this.a.a.minecraft()).execute(arg_0));
        }

        public final void b() {
            int n2;
            if (this.b) {
                return;
            }
            int n3 = this.c;
            for (n2 = this.c; n2 < this.a.size(); ++n2) {
                ChunkPos chunkPos = new ChunkPos((BlockPos)this.a.a(n2));
                if (!NetherPathfinder.hasChunkFromJava(this.a.a.a, chunkPos.x, chunkPos.z)) break;
            }
            if (n3 >= n2) {
                return;
            }
            BetterBlockPos betterBlockPos = this.a.a(n3);
            if (!this.a.a(betterBlockPos.x, betterBlockPos.y, betterBlockPos.z, false)) {
                return;
            }
            if (this.a.a.a != dz.a.f && this.b > 100) {
                this.a(OptionalInt.of(n2 - 1)).thenRun(() -> this.a.a("Recalculating segment, no progress in last 100 ticks"));
                this.b = 0;
                return;
            }
            boolean bl2 = false;
            for (int i2 = n3; i2 < n2 - 1; ++i2) {
                if (this.a.a(this.a.a.playerFeetAsVec(), this.a.a(i2), false) || this.a.a(this.a.a.playerHead(), this.a.a(i2), false)) {
                    bl2 = true;
                }
                if (this.a.a(this.a.a(i2), this.a.a(i2 + 1), false)) continue;
                OptionalInt optionalInt = this.a.a(n2 - 1).distanceSq(this.a.b) < this.a.a.playerFeet().distanceSq(this.a.b) ? OptionalInt.of(n2 - 1) : OptionalInt.empty();
                BetterBlockPos betterBlockPos2 = this.a.a(i2);
                double d2 = this.a.a.playerFeet().distanceTo(this.a.a(optionalInt.orElse(this.a.size() - 1)));
                long l2 = System.nanoTime();
                this.a(optionalInt).thenRun(() -> this.a.a(String.format("Recalculated segment around path blockage near %s %s %s (next %.1f blocks in %.4f seconds)", SettingsUtil.maybeCensor(betterBlockPos.x), SettingsUtil.maybeCensor(betterBlockPos.y), SettingsUtil.maybeCensor(betterBlockPos.z), d2, (double)(System.nanoTime() - l2) / 1.0E9)));
                return;
            }
            if (!bl2 && n3 < n2 - 2 && this.a.a.a != dz.a.c) {
                this.a(OptionalInt.of(n2 - 1)).thenRun(() -> this.a.a("Recalculated segment since no path points were visible"));
            }
        }

        public final void c() {
            int n2;
            if (this.a.isEmpty()) {
                return;
            }
            int n3 = this.c;
            BetterBlockPos betterBlockPos = this.a.a.playerFeet();
            for (n2 = n3; n2 >= Math.max(n3 - 1000, 0); n2 -= 10) {
                if (!(this.a.a(n2).distanceSq(betterBlockPos) < this.a.a(n3).distanceSq(betterBlockPos))) continue;
                n3 = n2;
            }
            for (n2 = n3; n2 < Math.min(n3 + 1000, this.a.size()); n2 += 10) {
                if (!(this.a.a(n2).distanceSq(betterBlockPos) < this.a.a(n3).distanceSq(betterBlockPos))) continue;
                n3 = n2;
            }
            for (n2 = n3; n2 >= Math.max(n3 - 50, 0); --n2) {
                if (!(this.a.a(n2).distanceSq(betterBlockPos) < this.a.a(n3).distanceSq(betterBlockPos))) continue;
                n3 = n2;
            }
            for (n2 = n3; n2 < Math.min(n3 + 50, this.a.size()); ++n2) {
                if (!(this.a.a(n2).distanceSq(betterBlockPos) < this.a.a(n3).distanceSq(betterBlockPos))) continue;
                n3 = n2;
            }
            this.c = n3;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class f {
        public final g a;
        public final Rotation a;
        public final Vec3 a;
        public final boolean a;
        public final boolean b;

        public f(g g2, Rotation rotation, Vec3 vec3, boolean bl2, boolean bl3) {
            this.a = g2;
            this.a = rotation;
            this.a = vec3;
            this.a = bl2;
            this.b = bl3;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public final class g {
        public final eo a;
        public final int a;
        public final Vec3 a;
        public final Vec3 b;
        public final AABB a;
        public final boolean a;
        public final a a;
        public final ITickableAimProcessor a;

        public g(en en2, boolean bl2) {
            Object object;
            this.a = en2.a.a;
            this.a = en2.a.c;
            this.a = en2.a.playerFeetAsVec();
            this.b = en2.a.playerMotion();
            this.a = en2.a.player().getBoundingBox();
            this.a = en2.a.player().isInLava();
            object = bl2 && en2.b ? (Object)(en2.a[1] > (object = en2.a)[0] ? Integer.valueOf(0) : null) : (Object)en2.a().map(fireworkRocketEntity -> fireworkRocketEntity.tickCount).orElse(null);
            this.a = new a((Integer)object, en2.c);
            object = en2.a.a.getAimProcessor().fork();
            if (bl2) {
                object.advance(1);
            }
            this.a = object;
        }

        public final boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || object.getClass() != g.class) {
                return false;
            }
            object = (g)object;
            return this.a == ((g)object).a && this.a == ((g)object).a && Objects.equals(this.a, ((g)object).a) && Objects.equals(this.b, ((g)object).b) && Objects.equals(this.a, ((g)object).a) && this.a == ((g)object).a && Objects.equals(this.a, ((g)object).a);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class a {
        private final Integer a;
        private final int a;
        private final int b;

        public a(Integer n2, int n3) {
            this.a = n2;
            this.a = n3;
            this.b = n3 + 11;
        }

        public final boolean a() {
            return this.a != null;
        }

        public final int a() {
            if (this.a()) {
                return Math.max(0, this.a - this.a);
            }
            return 0;
        }

        public final int b() {
            if (this.a()) {
                return Math.max(0, this.b - this.a);
            }
            return 0;
        }

        public final boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || object.getClass() != a.class) {
                return false;
            }
            object = (a)object;
            if (!this.a() && !((a)object).a()) {
                return true;
            }
            return Objects.equals(this.a, ((a)object).a) && this.a == ((a)object).a && this.b == ((a)object).b;
        }
    }

    @FunctionalInterface
    static interface b<T> {
        public T apply(int var1, int var2, int var3);
    }

    static final class c {
        public final int a;
        public final int b;
        public final int c;

        public c(int n2, int n3, int n4) {
            this.a = n2;
            this.b = n3;
            this.c = n4;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class e {
        public final float a;
        public final double a;
        public final List<Vec3> a;

        public e(float f2, double d2, List<Vec3> list) {
            this.a = f2;
            this.a = d2;
            this.a = (float)list;
        }
    }
}

