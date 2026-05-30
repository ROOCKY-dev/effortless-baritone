/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.FallingBlock
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalTwoBlocks;
import baritone.api.process.IMineProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.api.utils.BlockUtils;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.input.Input;
import baritone.ca;
import baritone.cc;
import baritone.el;
import baritone.ey;
import baritone.fb;
import baritone.m;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ek
extends ey
implements IMineProcess {
    private BlockOptionalMetaLookup a;
    private List<BlockPos> a;
    private List<BlockPos> b;
    private Map<BlockPos, Long> a;
    private BetterBlockPos a;
    private el a;
    private int a;
    private int b;

    public ek(baritone.a a2) {
        super(a2);
    }

    @Override
    public final boolean isActive() {
        return this.a != null;
    }

    @Override
    public final PathingCommand onTick(boolean bl2, boolean bl3) {
        Object object;
        Object object2;
        Object object32;
        int n2;
        if (this.a > 0 && (n2 = ((ey)this).a.player().getInventory().items.stream().filter(itemStack -> this.a.has((ItemStack)itemStack)).mapToInt(ItemStack::getCount).sum()) >= this.a) {
            this.logDirect("Have " + n2 + " valid items");
            this.cancel();
            return null;
        }
        if (bl2) {
            if (!this.a.isEmpty() && ((Boolean)baritone.a.a().blacklistClosestOnFailure.value).booleanValue()) {
                ek ek2 = this;
                ek2.logDirect("Unable to find any path to " + String.valueOf(ek2.a) + ", blacklisting presumably unreachable closest instance...");
                if (((Boolean)baritone.a.a().notificationOnMineFail.value).booleanValue()) {
                    ek ek3 = this;
                    ek3.logNotification("Unable to find any path to " + String.valueOf(ek3.a) + ", blacklisting presumably unreachable closest instance...", true);
                }
                this.a.stream().min(Comparator.comparingDouble(arg_0 -> ((BetterBlockPos)((ey)this).a.playerFeet()).distSqr(arg_0))).ifPresent(this.b::add);
                this.a.removeIf(this.b::contains);
            } else {
                ek ek4 = this;
                ek4.logDirect("Unable to find any path to " + String.valueOf(ek4.a) + ", canceling mine");
                if (((Boolean)baritone.a.a().notificationOnMineFail.value).booleanValue()) {
                    ek ek5 = this;
                    ek5.logNotification("Unable to find any path to " + String.valueOf(ek5.a) + ", canceling mine", true);
                }
                this.cancel();
                return null;
            }
        }
        ek ek6 = this;
        Object object4 = new HashMap(ek6.a);
        ((ey)ek6).a.getSelectedBlock().ifPresent(blockPos -> {
            if (this.a.contains(blockPos)) {
                object4.put(blockPos, System.currentTimeMillis() + (Long)baritone.a.a().mineDropLoiterDurationMSThanksLouca.value);
            }
        });
        for (Object object32 : ek6.a.keySet()) {
            if ((Long)object4.get(object32) >= System.currentTimeMillis()) continue;
            object4.remove(object32);
        }
        ek6.a = object4;
        int n3 = (Integer)baritone.a.a().mineGoalUpdateInterval.value;
        Object object5 = new ArrayList(this.a);
        if (n3 != 0 && this.b++ % n3 == 0) {
            ca ca2 = new ca(((ey)this).a, true);
            baritone.a.a().execute(() -> this.a((List<BlockPos>)object5, ca2));
        }
        if (((Boolean)baritone.a.a().legitMine.value).booleanValue()) {
            boolean bl4;
            ek ek7 = this;
            object4 = ek7.a();
            ek7.a.addAll(object4);
            Object object6 = ((ey)ek7).a.playerFeet();
            object32 = new fb(((ey)ek7).a);
            object2 = ek7.a();
            if (object2 == null) {
                bl4 = false;
            } else {
                for (int i2 = object6.getX() - 10; i2 <= object6.getX() + 10; ++i2) {
                    for (int i3 = object6.getY() - 10; i3 <= object6.getY() + 10; ++i3) {
                        for (int i4 = object6.getZ() - 10; i4 <= object6.getZ() + 10; ++i4) {
                            if (!((BlockOptionalMetaLookup)object2).has(((fb)object32).a(i2, i3, i4))) continue;
                            BlockPos blockPos3 = new BlockPos(i2, i3, i4);
                            if ((!((Boolean)baritone.a.a().legitMineIncludeDiagonals.value).booleanValue() || !ek7.a.stream().anyMatch(blockPos2 -> blockPos2.distSqr((Vec3i)blockPos3) <= 2.0)) && !RotationUtils.reachable(((ey)ek7).a, blockPos3, 20.0).isPresent()) continue;
                            ek7.a.add(blockPos3);
                        }
                    }
                }
                ek7.a = ek.a(new ca(((ey)ek7).a), (List<BlockPos>)((Object)ek7.a), (BlockOptionalMetaLookup)object2, (Integer)baritone.a.a().mineMaxOreLocationsCount.value, ek7.b, (List<BlockPos>)object4);
                bl4 = true;
            }
            if (!bl4) {
                this.cancel();
                return null;
            }
        }
        Optional<Rotation> optional = object5.stream().filter(blockPos -> blockPos.getX() == ((ey)this).a.playerFeet().getX() && blockPos.getZ() == ((ey)this).a.playerFeet().getZ()).filter(blockPos -> blockPos.getY() >= ((ey)this).a.playerFeet().getY()).filter(blockPos -> !(fb.a(((ey)this).a, blockPos).getBlock() instanceof AirBlock)).min(Comparator.comparingDouble(arg_0 -> ((BetterBlockPos)((ey)this).a.playerFeet().above()).distSqr(arg_0)));
        ((ey)this).a.a.clearAllKeys();
        if (optional.isPresent() && ((ey)this).a.player().onGround()) {
            object5 = (BlockPos)optional.get();
            optional = ((ey)this).a.a.a((BlockPos)object5);
            if (!cc.a(((ey)this).a.a, object5.getX(), object5.getY(), object5.getZ(), (BlockState)optional) && (optional = RotationUtils.reachable(((ey)this).a, (BlockPos)object5)).isPresent() && bl3) {
                ((ey)this).a.a.updateTarget(optional.get(), true);
                cc.a(((ey)this).a, ((ey)this).a.world().getBlockState((BlockPos)object5));
                if (((ey)this).a.isLookingAt((BlockPos)object5) || ((ey)this).a.playerRotations().isReallyCloseTo(optional.get())) {
                    ((ey)this).a.a.setInputForceState(Input.CLICK_LEFT, true);
                }
                return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            }
        }
        if ((object4 = ((ek)((Object)(optional = this))).a()) == null) {
            object = null;
        } else {
            boolean bl5 = (Boolean)baritone.a.a().legitMine.value;
            object32 = ((ek)((Object)optional)).a;
            if (!object32.isEmpty()) {
                object2 = new ca(((ey)((Object)optional)).a);
                object5 = ek.a((ca)object2, new ArrayList<BlockPos>((Collection<BlockPos>)object32), (BlockOptionalMetaLookup)object4, (Integer)baritone.a.a().mineMaxOreLocationsCount.value, ((ek)((Object)optional)).b, ((ek)((Object)optional)).a());
                GoalComposite goalComposite = new GoalComposite((Goal[])object5.stream().map(arg_0 -> optional.a((List)object5, (ca)object2, arg_0)).toArray(Goal[]::new));
                ((ek)((Object)optional)).a = object5;
                object = new PathingCommand(goalComposite, bl5 ? PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH : PathingCommandType.REVALIDATE_GOAL_AND_PATH);
            } else if (!bl5 && !((Boolean)baritone.a.a().exploreForBlocks.value).booleanValue()) {
                object = null;
            } else {
                int n4 = (Integer)baritone.a.a().legitMineYLevel.value;
                if (((ek)((Object)optional)).a == null) {
                    ((ek)((Object)optional)).a = ((ey)((Object)optional)).a.playerFeet();
                }
                if (((ek)((Object)optional)).a == null) {
                    ((ek)((Object)optional)).a = new el((Integer)n4, ((ek)((Object)optional)).a);
                }
                object = object5 = new PathingCommand(((ek)((Object)optional)).a, PathingCommandType.REVALIDATE_GOAL_AND_PATH);
            }
        }
        if (object == null) {
            this.cancel();
            return null;
        }
        return object5;
    }

    @Override
    public final void onLostControl() {
        this.mine(0, (BlockOptionalMetaLookup)null);
    }

    @Override
    public final String displayName0() {
        return "Mine " + String.valueOf(this.a);
    }

    private void a(List<BlockPos> list, ca ca2) {
        BlockOptionalMetaLookup blockOptionalMetaLookup = this.a();
        if (blockOptionalMetaLookup == null) {
            return;
        }
        if (((Boolean)baritone.a.a().legitMine.value).booleanValue()) {
            return;
        }
        List<BlockPos> list2 = this.a();
        list = ek.a(ca2, blockOptionalMetaLookup, (Integer)baritone.a.a().mineMaxOreLocationsCount.value, list, this.b, list2);
        list.addAll(list2);
        if (list.isEmpty() && !((Boolean)baritone.a.a().exploreForBlocks.value).booleanValue()) {
            this.logDirect("No locations for " + String.valueOf(blockOptionalMetaLookup) + " known, cancelling");
            if (((Boolean)baritone.a.a().notificationOnMineFail.value).booleanValue()) {
                this.logNotification("No locations for " + String.valueOf(blockOptionalMetaLookup) + " known, cancelling", true);
            }
            this.cancel();
            return;
        }
        this.a = list;
    }

    private boolean a(BlockPos blockPos, ca ca2, List<BlockPos> blockState) {
        if (blockState.contains(blockPos)) {
            return true;
        }
        blockState = ca2.a.a(blockPos);
        if (((Boolean)baritone.a.a().internalMiningAirException.value).booleanValue() && blockState.getBlock() instanceof AirBlock) {
            return true;
        }
        return this.a.has(blockState) && ek.a(ca2, blockPos);
    }

    private List<BlockPos> a() {
        if (!((Boolean)baritone.a.a().mineScanDroppedItems.value).booleanValue()) {
            return Collections.emptyList();
        }
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        for (Entity entity : ((ClientLevel)((ey)this).a.world()).entitiesForRendering()) {
            ItemEntity itemEntity;
            if (!(entity instanceof ItemEntity) || !this.a.has((itemEntity = (ItemEntity)entity).getItem())) continue;
            arrayList.add(entity.blockPosition());
        }
        arrayList.addAll(this.a.keySet());
        return arrayList;
    }

    public static List<BlockPos> a(ca ca2, BlockOptionalMetaLookup blockOptionalMetaLookup, int n2, List<BlockPos> list, List<BlockPos> list2, List<BlockPos> list3) {
        List<BlockPos> list4 = new ArrayList<BlockPos>();
        ArrayList<Block> arrayList = new ArrayList<Block>();
        Iterator<BlockOptionalMeta> iterator = blockOptionalMetaLookup.blocks().iterator();
        while (iterator.hasNext()) {
            Block block = iterator.next().getBlock();
            if (m.a.contains((Object)block)) {
                BetterBlockPos betterBlockPos = ca2.a.getPlayerContext().playerFeet();
                list4.addAll(ca2.a.getCachedWorld().getLocationsOf(BlockUtils.blockToString(block), (Integer)baritone.a.a().maxCachedWorldScanCount.value, betterBlockPos.x, betterBlockPos.z, 2));
                continue;
            }
            arrayList.add(block);
        }
        list4 = ek.a(ca2, list4, blockOptionalMetaLookup, n2, list2, list3);
        if (!arrayList.isEmpty() || ((Boolean)baritone.a.a().extendCacheOnThreshold.value).booleanValue() && list4.size() < n2) {
            list4.addAll(BaritoneAPI.getProvider().getWorldScanner().scanChunkRadius(ca2.a.getPlayerContext(), blockOptionalMetaLookup, n2, 10, 32));
        }
        list4.addAll(list);
        return ek.a(ca2, list4, blockOptionalMetaLookup, n2, list2, list3);
    }

    private static List<BlockPos> a(ca object, List<BlockPos> list, BlockOptionalMetaLookup blockOptionalMetaLookup, int n2, List<BlockPos> list2, List<BlockPos> list3) {
        list3.removeIf(arg_0 -> ek.a(list, blockOptionalMetaLookup, (ca)object, arg_0));
        object = list.stream().distinct().filter(arg_0 -> ek.a((ca)object, blockOptionalMetaLookup, list3, arg_0)).filter(arg_0 -> ek.d((ca)object, arg_0)).filter(arg_0 -> ek.c((ca)object, arg_0)).filter(arg_0 -> ek.b((ca)object, arg_0)).filter(blockPos -> blockPos.getY() <= (Integer)baritone.a.a().maxYLevelWhileMining.value).filter(blockPos -> !list2.contains(blockPos)).sorted(Comparator.comparingDouble(arg_0 -> ((BlockPos)((ca)object).a.getPlayerContext().player().blockPosition()).distSqr(arg_0))).collect(Collectors.toList());
        if (object.size() > n2) {
            return object.subList(0, n2);
        }
        return object;
    }

    private static boolean a(ca ca2, BlockPos blockPos) {
        BlockState blockState = ca2.a.a(blockPos);
        if (cc.a(ca2, blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockState, true) >= 1000000.0) {
            return false;
        }
        if (cc.a(ca2.a, blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockState)) {
            return false;
        }
        return ca2.a.a(blockPos.above()).getBlock() != Blocks.BEDROCK || ca2.a.a(blockPos.below()).getBlock() != Blocks.BEDROCK;
    }

    @Override
    public final void mineByName(int n2, String ... stringArray) {
        this.mine(n2, new BlockOptionalMetaLookup(stringArray));
    }

    @Override
    public final void mine(int n2, BlockOptionalMetaLookup blockOptionalMetaLookup) {
        this.a = blockOptionalMetaLookup;
        if (this.a() == null) {
            this.a = null;
        }
        this.a = n2;
        this.a = new ArrayList();
        this.b = new ArrayList<BlockPos>();
        this.a = null;
        this.a = null;
        this.a = new HashMap();
        if (blockOptionalMetaLookup != null) {
            this.a(new ArrayList<BlockPos>(), new ca(((ey)this).a));
        }
    }

    private BlockOptionalMetaLookup a() {
        if (this.a == null) {
            return null;
        }
        if (!((Boolean)baritone.a.a().allowBreak.value).booleanValue()) {
            BlockOptionalMetaLookup blockOptionalMetaLookup = new BlockOptionalMetaLookup((BlockOptionalMeta[])this.a.blocks().stream().filter(blockOptionalMeta -> ((List)baritone.a.a().allowBreakAnyway.value).contains(blockOptionalMeta.getBlock())).toArray(BlockOptionalMeta[]::new));
            if (blockOptionalMetaLookup.blocks().isEmpty()) {
                this.logDirect("Unable to mine when allowBreak is false and target block is not in allowBreakAnyway!");
                return null;
            }
            return blockOptionalMetaLookup;
        }
        return this.a;
    }

    private static /* synthetic */ boolean b(ca ca2, BlockPos blockPos) {
        return blockPos.getY() >= (Integer)baritone.a.a().minYLevelWhileMining.value + ca2.a.dimensionType().minY();
    }

    private static /* synthetic */ boolean c(ca ca2, BlockPos blockPos) {
        if (((Boolean)baritone.a.a().allowOnlyExposedOres.value).booleanValue()) {
            int n2 = (Integer)baritone.a.a().allowOnlyExposedOresDistance.value;
            for (int i2 = -n2; i2 <= n2; ++i2) {
                for (int i3 = -n2; i3 <= n2; ++i3) {
                    for (int i4 = -n2; i4 <= n2; ++i4) {
                        if (Math.abs(i2) + Math.abs(i3) + Math.abs(i4) > n2 || !cc.a(ca2.a(blockPos.getX() + i2, blockPos.getY() + i3, blockPos.getZ() + i4))) continue;
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    private static /* synthetic */ boolean d(ca ca2, BlockPos blockPos) {
        return ek.a(ca2, blockPos);
    }

    private static /* synthetic */ boolean a(ca ca2, BlockOptionalMetaLookup blockOptionalMetaLookup, List list, BlockPos blockPos) {
        return !ca2.a.a(blockPos.getX(), blockPos.getZ()) || blockOptionalMetaLookup.has(ca2.a(blockPos.getX(), blockPos.getY(), blockPos.getZ())) || list.contains(blockPos);
    }

    private static /* synthetic */ boolean a(List object, BlockOptionalMetaLookup blockOptionalMetaLookup, ca ca2, BlockPos blockPos) {
        object = object.iterator();
        while (object.hasNext()) {
            BlockPos blockPos2;
            BlockPos blockPos3 = (BlockPos)object.next();
            if (!(blockPos2.distSqr((Vec3i)blockPos) <= 9.0) || !blockOptionalMetaLookup.has(ca2.a(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ())) || !ek.a(ca2, blockPos3)) continue;
            return true;
        }
        return false;
    }

    private /* synthetic */ Goal a(List object, ca ca2, BlockPos object2) {
        boolean bl2;
        BlockPos blockPos = object2;
        ca ca3 = ca2;
        object2 = object;
        ca2 = blockPos;
        object = this;
        boolean bl3 = bl2 = !(((ey)object).a.a.a(ca2.above()).getBlock() instanceof FallingBlock);
        if (!((Boolean)baritone.a.a().forceInternalMining.value).booleanValue()) {
            if (bl2) {
                return new a((BlockPos)ca2);
            }
            return new GoalTwoBlocks((BlockPos)ca2);
        }
        boolean bl4 = ((ek)object).a(ca2.above(), ca3, (List<BlockPos>)object2);
        boolean bl5 = ((ek)object).a(ca2.below(), ca3, (List<BlockPos>)object2);
        boolean bl6 = ((ek)object).a(ca2.below(2), ca3, (List<BlockPos>)object2);
        if (bl4 == bl5) {
            if (bl6 && bl2) {
                return new a((BlockPos)ca2);
            }
            return new GoalTwoBlocks((BlockPos)ca2);
        }
        if (bl4) {
            return new GoalBlock((BlockPos)ca2);
        }
        if (bl6 && bl2) {
            return new GoalTwoBlocks(ca2.below());
        }
        return new GoalBlock(ca2.below());
    }

    static final class a
    extends GoalTwoBlocks {
        public a(BlockPos blockPos) {
            super(blockPos);
        }

        @Override
        public final boolean isInGoal(int n2, int n3, int n4) {
            return n2 == this.x && (n3 == this.y || n3 == this.y - 1 || n3 == this.y - 2) && n4 == this.z;
        }

        @Override
        public final double heuristic(int n2, int n3, int n4) {
            return GoalBlock.calculate(n2 -= this.x, (n3 -= this.y) < -1 ? n3 + 2 : (n3 == -1 ? 0 : n3), n4 -= this.z);
        }

        @Override
        public final boolean equals(Object object) {
            return super.equals(object);
        }

        @Override
        public final int hashCode() {
            return super.hashCode() * 393857768;
        }

        @Override
        public final String toString() {
            return String.format("GoalThreeBlocks{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z));
        }
    }
}

