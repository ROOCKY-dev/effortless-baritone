/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CarpetBlock
 *  net.minecraft.world.level.block.DoorBlock
 *  net.minecraft.world.level.block.FenceGateBlock
 *  net.minecraft.world.level.block.LadderBlock
 *  net.minecraft.world.level.block.SlabBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.SlabType
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

import baritone.a;
import baritone.api.IBaritone;
import baritone.api.pathing.movement.MovementStatus;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.VecUtils;
import baritone.api.utils.input.Input;
import baritone.ca;
import baritone.cb;
import baritone.cc;
import baritone.cd;
import baritone.dh;
import baritone.dj;
import baritone.fb;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class di
extends cb {
    private boolean a = true;

    public di(IBaritone iBaritone, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2) {
        super(iBaritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[]{betterBlockPos2.above(), betterBlockPos2}, betterBlockPos2.below());
    }

    @Override
    public final void reset() {
        super.reset();
        this.a = true;
    }

    @Override
    public final double a(ca ca2) {
        return di.a(ca2, ((cb)this).a.x, ((cb)this).a.y, ((cb)this).a.z, this.b.x, this.b.z);
    }

    @Override
    public final Set<BetterBlockPos> a() {
        return ImmutableSet.of((Object)((Object)((cb)this).a), (Object)((Object)this.b));
    }

    public static double a(ca ca2, int n2, int n3, int n4, int n5, int n6) {
        BlockState blockState = ca2.a(n5, n3 + 1, n6);
        BlockState blockState2 = ca2.a(n5, n3, n6);
        BlockState blockState3 = ca2.a(n5, n3 - 1, n6);
        BlockState blockState4 = ca2.a(n2, n3 - 1, n4);
        Block block = blockState4.getBlock();
        boolean bl2 = cc.c(ca2, n2, n3 - 1, n4, blockState4);
        int n7 = bl2 && !ca2.j && cc.b(ca2, blockState3) ? 1 : 0;
        if (n7 != 0 || cc.b(ca2, n5, n3 - 1, n6, blockState3)) {
            double d2;
            double d3 = 4.63284688441047;
            boolean bl3 = false;
            if (cc.d(blockState) || cc.d(blockState2)) {
                d3 = ca2.b;
                bl3 = true;
            } else {
                if (blockState3.getBlock() == Blocks.SOUL_SAND) {
                    d3 = 6.949270326615705;
                } else if (n7 == 0 && blockState3.getBlock() == Blocks.WATER) {
                    d3 = 4.63284688441047 + ca2.f;
                }
                if (block == Blocks.SOUL_SAND) {
                    d3 += 2.316423442205235;
                }
            }
            double d4 = cc.a(ca2, n5, n3, n6, blockState2, false);
            if (d2 >= 1000000.0) {
                return 1000000.0;
            }
            double d5 = cc.a(ca2, n5, n3 + 1, n6, blockState, true);
            if (d4 == 0.0 && d5 == 0.0) {
                if (!bl3 && ca2.d) {
                    d3 *= 0.7692444761225944;
                }
                return d3;
            }
            if (block == Blocks.LADDER || block == Blocks.VINE) {
                d4 *= 5.0;
                d5 *= 5.0;
            }
            return d3 + d4 + d5;
        }
        if (block == Blocks.LADDER || block == Blocks.VINE) {
            return 1000000.0;
        }
        if (cc.a(n5, n6, blockState3, ca2.a)) {
            double d6;
            double d7;
            boolean bl4;
            boolean bl5 = bl4 = cc.d(blockState) || cc.d(blockState2);
            if (cc.d(blockState3) && bl4) {
                return 1000000.0;
            }
            double d8 = ca2.a(n5, n3 - 1, n6, blockState3);
            if (d7 >= 1000000.0) {
                return 1000000.0;
            }
            double d9 = cc.a(ca2, n5, n3, n6, blockState2, false);
            if (d6 >= 1000000.0) {
                return 1000000.0;
            }
            double d10 = cc.a(ca2, n5, n3 + 1, n6, blockState, true);
            double d11 = bl4 ? ca2.b : 4.63284688441047;
            for (int i2 = 0; i2 < 5; ++i2) {
                int n8 = n5 + a[i2].getStepX();
                int n9 = n3 - 1 + a[i2].getStepY();
                n7 = n6 + a[i2].getStepZ();
                if (n8 == n2 && n7 == n4 || !cc.c(ca2.a, n8, n9, n7)) continue;
                return d11 + d8 + d9 + d10;
            }
            if (block == Blocks.SOUL_SAND || block instanceof SlabBlock && blockState4.getValue((Property)SlabBlock.TYPE) != SlabType.DOUBLE) {
                return 1000000.0;
            }
            if (!bl2) {
                return 1000000.0;
            }
            Block block2 = ca2.a(n2, n3, n4);
            if ((block2 == Blocks.LILY_PAD || block2 instanceof CarpetBlock) && !blockState4.getFluidState().isEmpty()) {
                return 1000000.0;
            }
            return d11 * 3.3207692307692307 + d8 + d9 + d10;
        }
        return 1000000.0;
    }

    @Override
    public final cd a(cd object) {
        Optional<Rotation> optional;
        BetterBlockPos betterBlockPos;
        boolean bl2;
        super.a((cd)object);
        Object object2 = fb.a(((cb)this).a, (BlockPos)((cb)this).a[0]);
        Object object3 = fb.a(((cb)this).a, (BlockPos)((cb)this).a[1]);
        if (((cd)object).a != MovementStatus.RUNNING) {
            if (!((Boolean)baritone.a.a().walkWhileBreaking.value).booleanValue()) {
                return object;
            }
            if (((cd)object).a != MovementStatus.PREPPING) {
                return object;
            }
            if (cc.b(object2)) {
                return object;
            }
            if (cc.b(object3)) {
                return object;
            }
            if (Math.max(Math.abs(((cb)this).a.player().position().x - ((double)this.b.getX() + 0.5)), Math.abs(((cb)this).a.player().position().z - ((double)this.b.getZ() + 0.5))) < 0.83) {
                return object;
            }
            if (!Optional.ofNullable(((cd)object).a.a).isPresent()) {
                return object;
            }
            float f2 = RotationUtils.calcRotationFromVec3d(((cb)this).a.playerHead(), VecUtils.calculateBlockCenter(((cb)this).a.world(), this.b), ((cb)this).a.playerRotations()).getYaw();
            float f3 = Optional.ofNullable(((cd)object).a.a).get().getPitch();
            if (cc.h(object2) || object2.getBlock() instanceof AirBlock && (cc.h(object3) || object3.getBlock() instanceof AirBlock)) {
                f3 = 26.0f;
            }
            return ((cd)object).a(new cd.a(new Rotation(f2, f3), true)).a(Input.MOVE_FORWARD, true).a(Input.SPRINT, true);
        }
        ((cd)object).a(Input.SNEAK, false);
        Block block = fb.a(((cb)this).a, (BlockPos)((cb)this).a.below()).getBlock();
        boolean bl3 = bl2 = block == Blocks.LADDER || block == Blocks.VINE;
        if (object2.getBlock() instanceof DoorBlock || object3.getBlock() instanceof DoorBlock) {
            boolean bl4;
            boolean bl5 = object2.getBlock() instanceof DoorBlock && !cc.a(((cb)this).a, ((cb)this).a, this.b) || object3.getBlock() instanceof DoorBlock && !cc.a(((cb)this).a, this.b, ((cb)this).a);
            boolean bl6 = bl4 = !Blocks.IRON_DOOR.equals(object2.getBlock()) && !Blocks.IRON_DOOR.equals(object3.getBlock());
            if (bl5 && bl4) {
                return ((cd)object).a(new cd.a(RotationUtils.calcRotationFromVec3d(((cb)this).a.playerHead(), VecUtils.calculateBlockCenter(((cb)this).a.world(), ((cb)this).a[0]), ((cb)this).a.playerRotations()), true)).a(Input.CLICK_RIGHT, true);
            }
        }
        if ((object2.getBlock() instanceof FenceGateBlock || object3.getBlock() instanceof FenceGateBlock) && (!cc.b(((cb)this).a, ((cb)this).a[0], ((cb)this).a.above()) ? ((cb)this).a[0] : (betterBlockPos = !cc.b(((cb)this).a, ((cb)this).a[1], ((cb)this).a) ? ((cb)this).a[1] : null)) != null && (optional = RotationUtils.reachable(((cb)this).a, betterBlockPos)).isPresent()) {
            return ((cd)object).a(new cd.a(optional.get(), true)).a(Input.CLICK_RIGHT, true);
        }
        boolean bl7 = cc.b(((cb)this).a, this.c) || bl2 || cc.c(((cb)this).a, this.c);
        BetterBlockPos betterBlockPos2 = ((cb)this).a.playerFeet();
        if (betterBlockPos2.getY() != this.b.getY() && !bl2) {
            this.logDebug("Wrong Y coordinate");
            if (betterBlockPos2.getY() < this.b.getY()) {
                System.out.println("In movement traverse");
                return ((cd)object).a(Input.JUMP, true);
            }
            return object;
        }
        if (bl7) {
            if (betterBlockPos2.equals((Object)this.b)) {
                cd cd2 = object;
                object = MovementStatus.SUCCESS;
                cd cd3 = cd2;
                cd2.a = object;
                return cd3;
            }
            if (((Boolean)baritone.a.a().overshootTraverse.value).booleanValue() && (betterBlockPos2.equals(this.b.offset((Vec3i)this.getDirection())) || betterBlockPos2.equals(this.b.offset((Vec3i)this.getDirection()).offset((Vec3i)this.getDirection())))) {
                Object object4 = object;
                object = MovementStatus.SUCCESS;
                Object object5 = object4;
                ((cd)object4).a = object;
                return object5;
            }
            object2 = fb.a(((cb)this).a, (BlockPos)((cb)this).a).getBlock();
            Block block2 = fb.a(((cb)this).a, (BlockPos)((cb)this).a.above()).getBlock();
            if (((cb)this).a.player().position().y > (double)((cb)this).a.y + 0.1 && !((cb)this).a.player().onGround() && (object2 == Blocks.VINE || object2 == Blocks.LADDER || block2 == Blocks.VINE || block2 == Blocks.LADDER)) {
                return object;
            }
            BlockPos blockPos = this.b.subtract((Vec3i)((cb)this).a).offset((Vec3i)this.b);
            object2 = fb.a(((cb)this).a, blockPos);
            BlockState blockState = fb.a(((cb)this).a, blockPos.above());
            if (!(!this.a || cc.e(((cb)this).a, betterBlockPos2) && !((Boolean)baritone.a.a().sprintInWater.value).booleanValue() || cc.b(object2) && !cc.d(object2) || cc.b(blockState))) {
                ((cd)object).a(Input.SPRINT, true);
            }
            BlockState blockState2 = fb.a(((cb)this).a, (BlockPos)this.b.below());
            BetterBlockPos betterBlockPos3 = ((cb)this).a[0];
            if (betterBlockPos2.getY() != this.b.getY() && bl2 && (blockState2.getBlock() == Blocks.VINE || blockState2.getBlock() == Blocks.LADDER) && (betterBlockPos3 = blockState2.getBlock() == Blocks.VINE ? dh.a(new ca(((cb)this).a), this.b.below()) : this.b.relative(((Direction)blockState2.getValue((Property)LadderBlock.FACING)).getOpposite())) == null) {
                this.logDirect("Unable to climb vines. Consider disabling allowVines.");
                Object object6 = object;
                object = MovementStatus.UNREACHABLE;
                Object object7 = object6;
                ((cd)object6).a = object;
                return object7;
            }
            cc.a(((cb)this).a, (cd)object, (BlockPos)betterBlockPos3);
            return object;
        }
        this.a = false;
        object2 = fb.a(((cb)this).a, betterBlockPos2.below()).getBlock();
        if ((object2.equals(Blocks.SOUL_SAND) || object2 instanceof SlabBlock) && Math.max(Math.abs((double)this.b.getX() + 0.5 - ((cb)this).a.player().position().x), Math.abs((double)this.b.getZ() + 0.5 - ((cb)this).a.player().position().z)) < 0.85) {
            cc.a(((cb)this).a, (cd)object, (BlockPos)this.b);
            return ((cd)object).a(Input.MOVE_FORWARD, false).a(Input.MOVE_BACK, true);
        }
        double d2 = Math.max(Math.abs(((cb)this).a.player().position().x - ((double)this.b.getX() + 0.5)), Math.abs(((cb)this).a.player().position().z - ((double)this.b.getZ() + 0.5)));
        object2 = cc.a((cd)object, ((cb)this).a, this.b.below(), false, true);
        if ((object2 == cc.a.a || d2 < 0.6) && !((Boolean)baritone.a.a().assumeSafeWalk.value).booleanValue()) {
            ((cd)object).a(Input.SNEAK, true);
        }
        switch (dj.a[object2.ordinal()]) {
            case 1: {
                if (((cb)this).a.player().isCrouching() || ((Boolean)baritone.a.a().assumeSafeWalk.value).booleanValue()) {
                    ((cd)object).a(Input.CLICK_RIGHT, true);
                }
                return object;
            }
            case 2: {
                if (d2 > 0.83) {
                    float f4 = RotationUtils.calcRotationFromVec3d(((cb)this).a.playerHead(), VecUtils.getBlockPosCenter(this.b), ((cb)this).a.playerRotations()).getYaw();
                    if ((double)Math.abs(((cd)object).a.a.getYaw() - f4) < 0.1) {
                        return ((cd)object).a(Input.MOVE_FORWARD, true);
                    }
                } else if (((cb)this).a.playerRotations().isReallyCloseTo(((cd)object).a.a)) {
                    return ((cd)object).a(Input.CLICK_LEFT, true);
                }
                return object;
            }
        }
        if (betterBlockPos2.equals((Object)this.b)) {
            double d3 = ((double)(this.b.getX() + ((cb)this).a.getX()) + 1.0) * 0.5;
            double d4 = ((double)(this.b.getY() + ((cb)this).a.getY()) - 1.0) * 0.5;
            double d5 = ((double)(this.b.getZ() + ((cb)this).a.getZ()) + 1.0) * 0.5;
            object2 = ((cb)this).a.below();
            object3 = RotationUtils.calcRotationFromVec3d(((cb)this).a.playerHead(), new Vec3(d3, d4, d5), ((cb)this).a.playerRotations());
            float f5 = object3.getPitch();
            if (Math.max(Math.abs(((cb)this).a.player().position().x - d3), Math.abs(((cb)this).a.player().position().z - d5)) < 0.29) {
                float f6 = RotationUtils.calcRotationFromVec3d(VecUtils.getBlockPosCenter(this.b), ((cb)this).a.playerHead(), ((cb)this).a.playerRotations()).getYaw();
                ((cd)object).a(new cd.a(new Rotation(f6, f5), true));
                ((cd)object).a(Input.MOVE_BACK, true);
            } else {
                ((cd)object).a(new cd.a((Rotation)object3, true));
            }
            if (((cb)this).a.isLookingAt((BlockPos)object2)) {
                return ((cd)object).a(Input.CLICK_RIGHT, true);
            }
            if (((cb)this).a.playerRotations().isReallyCloseTo(((cd)object).a.a)) {
                ((cd)object).a(Input.CLICK_LEFT, true);
            }
            return object;
        }
        cc.a(((cb)this).a, (cd)object, (BlockPos)((cb)this).a[0]);
        return object;
    }

    @Override
    public final boolean b(cd cd2) {
        return cd2.a != MovementStatus.RUNNING || cc.b(((cb)this).a, this.b.below());
    }

    @Override
    public final boolean a(cd cd2) {
        Block block;
        if ((((cb)this).a.playerFeet().equals((Object)((cb)this).a) || ((cb)this).a.playerFeet().equals((Object)((cb)this).a.below())) && ((block = fb.a(((cb)this).a, ((cb)this).a.below())) == Blocks.LADDER || block == Blocks.VINE)) {
            cd2.a(Input.SNEAK, true);
        }
        return super.a(cd2);
    }
}

