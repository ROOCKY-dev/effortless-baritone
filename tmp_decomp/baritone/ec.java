/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Plane
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BonemealableBlock
 *  net.minecraft.world.level.block.CocoaBlock
 *  net.minecraft.world.level.block.CropBlock
 *  net.minecraft.world.level.block.NetherWartBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.process.IFarmProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.RayTraceUtils;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.input.Input;
import baritone.cc;
import baritone.dr;
import baritone.ed;
import baritone.ee;
import baritone.ef;
import baritone.ey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ec
extends ey
implements IFarmProcess {
    private boolean a;
    private List<BlockPos> a;
    private int a;
    private int b;
    private BlockPos a;
    private static final List<Item> b;
    private static final List<Item> c;

    public ec(baritone.a a2) {
        super(a2);
    }

    @Override
    public final boolean isActive() {
        return this.a;
    }

    @Override
    public final void farm(int n2, BlockPos blockPos) {
        this.a = blockPos == null ? ((ey)this).a.getPlayerContext().playerFeet() : blockPos;
        this.b = n2;
        this.a = true;
        this.a = null;
    }

    private boolean a(ItemStack itemStack) {
        return b.contains(itemStack.getItem());
    }

    private boolean b(ItemStack itemStack) {
        return !itemStack.isEmpty() && itemStack.getItem().equals(Items.BONE_MEAL);
    }

    private boolean c(ItemStack itemStack) {
        return !itemStack.isEmpty() && itemStack.getItem().equals(Items.NETHER_WART);
    }

    private boolean d(ItemStack itemStack) {
        return !itemStack.isEmpty() && itemStack.getItem().equals(Items.COCOA_BEANS);
    }

    @Override
    public final PathingCommand onTick(boolean bl2, boolean bl3) {
        Object object4;
        Object object22;
        ArrayList<Object> arrayList;
        if ((Integer)baritone.a.a().mineGoalUpdateInterval.value != 0 && this.a++ % (Integer)baritone.a.a().mineGoalUpdateInterval.value == 0) {
            arrayList = new ArrayList();
            for (a object42 : baritone.ec$a.values()) {
                arrayList.add(object42.a);
            }
            if (((Boolean)baritone.a.a().replantCrops.value).booleanValue()) {
                arrayList.add(Blocks.FARMLAND);
                arrayList.add(Blocks.JUNGLE_LOG);
                if (((Boolean)baritone.a.a().replantNetherWart.value).booleanValue()) {
                    arrayList.add(Blocks.SOUL_SAND);
                }
            }
            baritone.a.a().execute(() -> {
                this.a = BaritoneAPI.getProvider().getWorldScanner().scanChunkRadius(((ey)this).a, arrayList, (int)((Integer)baritone.a.a().farmMaxScanSize.value), 10, 10);
            });
        }
        if (this.a == null) {
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        arrayList = new ArrayList<Object>();
        a[] aArray = new ArrayList();
        ArrayList<BlockPos> arrayList2 = new ArrayList<BlockPos>();
        ArrayList<BlockPos> arrayList3 = new ArrayList<BlockPos>();
        ArrayList<BlockPos> arrayList4 = new ArrayList<BlockPos>();
        block1: for (BlockPos d2 : this.a) {
            boolean bl4;
            BlockState blockState;
            block36: {
                if (this.b != 0 && d2.distSqr((Vec3i)this.a) > (double)(this.b * this.b)) continue;
                blockState = ((ey)this).a.world().getBlockState(d2);
                boolean level = ((ey)this).a.world().getBlockState(d2.above()).getBlock() instanceof AirBlock;
                if (blockState.getBlock() == Blocks.FARMLAND) {
                    if (!level) continue;
                    aArray.add(d2);
                    continue;
                }
                if (blockState.getBlock() == Blocks.SOUL_SAND) {
                    if (!level) continue;
                    arrayList3.add(d2);
                    continue;
                }
                if (blockState.getBlock() == Blocks.JUNGLE_LOG) {
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        if (!(((ey)this).a.world().getBlockState(d2.relative(direction)).getBlock() instanceof AirBlock)) continue;
                        arrayList4.add(d2);
                        continue block1;
                    }
                    continue;
                }
                BlockState blockState2 = blockState;
                object22 = d2;
                Level arrayList42 = ((ey)this).a.world();
                object4 = baritone.ec$a.values();
                int n2 = ((a[])object4).length;
                for (int i2 = 0; i2 < n2; ++i2) {
                    a a2 = object4[i2];
                    if (a2.a != blockState2.getBlock()) continue;
                    bl4 = a2.a(arrayList42, (BlockPos)object22, blockState2);
                    break block36;
                }
                bl4 = false;
            }
            if (bl4) {
                arrayList.add(d2);
                continue;
            }
            if (!(blockState.getBlock() instanceof BonemealableBlock) || !(object22 = (BonemealableBlock)blockState.getBlock()).isValidBonemealTarget((LevelReader)((ey)this).a.world(), d2, blockState) || !object22.isBonemealSuccess(((ey)this).a.world(), ((ey)this).a.world().random, d2, blockState)) continue;
            arrayList2.add(d2);
        }
        ((ey)this).a.a.clearAllKeys();
        Object object3 = ((ey)this).a.playerFeet();
        double d2 = ((ey)this).a.playerController().getBlockReachDistance();
        for (Object object22 : arrayList) {
            Optional<Rotation> optional;
            double d3 = d2;
            if (object3.distSqr((Vec3i)object22) > d3 * d3 || !(optional = RotationUtils.reachable(((ey)this).a, (BlockPos)object22)).isPresent() || !bl3) continue;
            ((ey)this).a.a.updateTarget(optional.get(), true);
            cc.a(((ey)this).a, ((ey)this).a.world().getBlockState((BlockPos)object22));
            if (((ey)this).a.isLookingAt((BlockPos)object22)) {
                ((ey)this).a.a.setInputForceState(Input.CLICK_LEFT, true);
            }
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        ArrayList<BlockPos> arrayList5 = new ArrayList<BlockPos>((Collection<BlockPos>)aArray);
        arrayList5.addAll(arrayList3);
        for (BlockPos blockPos : arrayList5) {
            HitResult hitResult;
            double d4 = d2;
            if (object3.distSqr((Vec3i)blockPos) > d4 * d4) continue;
            boolean optional = arrayList3.contains(blockPos);
            object4 = RotationUtils.reachableOffset(((ey)this).a, blockPos, new Vec3((double)blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5), d2, false);
            if (!((Optional)object4).isPresent() || !bl3 || !((ey)this).a.a.a(true, optional ? this::c : this::a) || !((hitResult = RayTraceUtils.rayTraceTowards((Entity)((ey)this).a.player(), (Rotation)((Optional)object4).get(), d2)) instanceof BlockHitResult) || ((BlockHitResult)hitResult).getDirection() != Direction.UP) continue;
            ((ey)this).a.a.updateTarget((Rotation)((Optional)object4).get(), true);
            if (((ey)this).a.isLookingAt(blockPos)) {
                ((ey)this).a.a.setInputForceState(Input.CLICK_RIGHT, true);
            }
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        for (BlockPos blockPos : arrayList4) {
            double d5 = d2;
            if (object3.distSqr((Vec3i)blockPos) > d5 * d5) continue;
            for (Object object4 : Direction.Plane.HORIZONTAL) {
                HitResult hitResult;
                if (!(((ey)this).a.world().getBlockState(blockPos.relative((Direction)object4)).getBlock() instanceof AirBlock)) continue;
                Object object5 = Vec3.atCenterOf((Vec3i)blockPos).add(Vec3.atLowerCornerOf((Vec3i)object4.getNormal()).scale(0.5));
                if (!((Optional)(object5 = RotationUtils.reachableOffset(((ey)this).a, blockPos, (Vec3)object5, d2, false))).isPresent() || !bl3 || !((ey)this).a.a.a(true, this::d) || !((hitResult = RayTraceUtils.rayTraceTowards((Entity)((ey)this).a.player(), (Rotation)((Optional)object5).get(), d2)) instanceof BlockHitResult) || ((BlockHitResult)hitResult).getDirection() != object4) continue;
                ((ey)this).a.a.updateTarget((Rotation)((Optional)object5).get(), true);
                if (((ey)this).a.isLookingAt(blockPos)) {
                    ((ey)this).a.a.setInputForceState(Input.CLICK_RIGHT, true);
                }
                return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            }
        }
        for (BlockPos blockPos : arrayList2) {
            Optional<Rotation> blockPos2;
            double d6 = d2;
            if (object3.distSqr((Vec3i)blockPos) > d6 * d6 || !(blockPos2 = RotationUtils.reachable(((ey)this).a, blockPos)).isPresent() || !bl3 || !((ey)this).a.a.a(true, this::b)) continue;
            ((ey)this).a.a.updateTarget(blockPos2.get(), true);
            if (((ey)this).a.isLookingAt(blockPos)) {
                ((ey)this).a.a.setInputForceState(Input.CLICK_RIGHT, true);
            }
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        if (bl2) {
            this.logDirect("Farm failed");
            if (((Boolean)baritone.a.a().notificationOnFarmFail.value).booleanValue()) {
                this.logNotification("Farm failed", true);
            }
            this.onLostControl();
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        object22 = new ArrayList();
        for (BlockPos blockPos : arrayList) {
            object22.add(new dr.c(blockPos));
        }
        if (((ey)this).a.a.a(false, this::a)) {
            for (BlockPos blockPos : aArray) {
                object22.add(new GoalBlock(blockPos.above()));
            }
        }
        if (((ey)this).a.a.a(false, this::c)) {
            for (BlockPos blockPos : arrayList3) {
                object22.add(new GoalBlock(blockPos.above()));
            }
        }
        if (((ey)this).a.a.a(false, this::d)) {
            for (BlockPos entity : arrayList4) {
                object4 = Direction.Plane.HORIZONTAL.iterator();
                while (object4.hasNext()) {
                    Direction direction = (Direction)object4.next();
                    if (!(((ey)this).a.world().getBlockState(entity.relative(direction)).getBlock() instanceof AirBlock)) continue;
                    object22.add(new GoalGetToBlock(entity.relative(direction)));
                }
            }
        }
        if (((ey)this).a.a.a(false, this::b)) {
            for (BlockPos blockPos : arrayList2) {
                object22.add(new GoalBlock(blockPos));
            }
        }
        for (Entity entity : ((ey)this).a.entities()) {
            if (!(entity instanceof ItemEntity) || !entity.onGround() || !c.contains((object4 = (ItemEntity)entity).getItem().getItem())) continue;
            object22.add(new GoalBlock(new BetterBlockPos(entity.position().x, entity.position().y + 0.1, entity.position().z)));
        }
        if (object22.isEmpty()) {
            this.logDirect("Farm failed");
            if (((Boolean)baritone.a.a().notificationOnFarmFail.value).booleanValue()) {
                this.logNotification("Farm failed", true);
            }
            this.onLostControl();
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        return new PathingCommand(new GoalComposite(object22.toArray(new Goal[0])), PathingCommandType.SET_GOAL_AND_PATH);
    }

    @Override
    public final void onLostControl() {
        this.a = false;
    }

    @Override
    public final String displayName0() {
        return "Farming";
    }

    static {
        b = (int)Arrays.asList(Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.WHEAT_SEEDS, Items.PUMPKIN_SEEDS, Items.POTATO, Items.CARROT);
        c = Arrays.asList(Items.BEETROOT_SEEDS, Items.BEETROOT, Items.MELON_SEEDS, Items.MELON_SLICE, Blocks.MELON.asItem(), Items.WHEAT_SEEDS, Items.WHEAT, Items.PUMPKIN_SEEDS, Blocks.PUMPKIN.asItem(), Items.POTATO, Items.CARROT, Items.NETHER_WART, Items.COCOA_BEANS, Blocks.SUGAR_CANE.asItem(), Blocks.BAMBOO.asItem(), Blocks.CACTUS.asItem());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static sealed class a
    extends Enum<a>
    permits ed, ee, ef {
        private static /* enum */ a a = new a((CropBlock)Blocks.WHEAT);
        private static /* enum */ a b = new a((CropBlock)Blocks.CARROTS);
        private static /* enum */ a c = new a((CropBlock)Blocks.POTATOES);
        private static /* enum */ a d = new a((CropBlock)Blocks.BEETROOTS);
        private static /* enum */ a e = new a(Blocks.PUMPKIN, blockState -> true);
        private static /* enum */ a f = new a(Blocks.MELON, blockState -> true);
        private static /* enum */ a g = new a(Blocks.NETHER_WART, blockState -> (Integer)blockState.getValue((Property)NetherWartBlock.AGE) >= 3);
        private static /* enum */ a h = new a(Blocks.COCOA, blockState -> (Integer)blockState.getValue((Property)CocoaBlock.AGE) >= 2);
        private static /* enum */ ed a = new ed();
        private static /* enum */ ee a = new ee();
        private static /* enum */ ef a = new ef();
        public final Block a;
        private Predicate<BlockState> a;
        private static final /* synthetic */ a[] a;

        public static a[] values() {
            return (a[])a.clone();
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        private a(CropBlock cropBlock) {
            CropBlock cropBlock2 = cropBlock;
            this((Block)cropBlock2, arg_0 -> ((CropBlock)cropBlock2).isMaxAge(arg_0));
        }

        a(Block block, Predicate<BlockState> predicate) {
            this.a = block;
            this.a = predicate;
        }

        public boolean a(Level level, BlockPos blockPos, BlockState blockState) {
            return this.a.test(blockState);
        }

        static {
            a = new a[]{a, b, c, d, e, f, g, h, a, a, a};
        }
    }
}

