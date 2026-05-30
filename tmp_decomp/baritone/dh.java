/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CarpetBlock
 *  net.minecraft.world.level.block.FallingBlock
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
import baritone.fb;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.FallingBlock;
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
public final class dh
extends cb {
    public dh(IBaritone iBaritone, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2) {
        super(iBaritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[]{betterBlockPos.above(2)}, betterBlockPos);
    }

    @Override
    public final double a(ca ca2) {
        return dh.a(ca2, this.a.x, this.a.y, this.a.z);
    }

    @Override
    public final Set<BetterBlockPos> a() {
        return ImmutableSet.of((Object)((Object)this.a), (Object)((Object)this.b));
    }

    public static double a(ca ca2, int n2, int n3, int n4) {
        double d2;
        BlockState blockState = ca2.a(n2, n3, n4);
        Block block = blockState.getBlock();
        boolean bl2 = block == Blocks.LADDER || block == Blocks.VINE;
        BlockState blockState2 = ca2.a(n2, n3 - 1, n4);
        if (!bl2) {
            if (blockState2.getBlock() == Blocks.LADDER || blockState2.getBlock() == Blocks.VINE) {
                return 1000000.0;
            }
            if (blockState2.getBlock() instanceof SlabBlock && blockState2.getValue((Property)SlabBlock.TYPE) == SlabType.BOTTOM) {
                return 1000000.0;
            }
        }
        if (block == Blocks.VINE && !dh.d(ca2, n2, n3, n4)) {
            return 1000000.0;
        }
        BlockState blockState3 = ca2.a(n2, n3 + 2, n4);
        Block block2 = blockState3.getBlock();
        if (block2 instanceof FenceGateBlock) {
            return 1000000.0;
        }
        BlockState blockState4 = null;
        if (cc.d(blockState3) && cc.d(blockState) && cc.d(blockState4 = ca2.a(n2, n3 + 1, n4))) {
            return 8.51063829787234;
        }
        double d3 = 0.0;
        if (!bl2) {
            double d4;
            d3 = ca2.a(n2, n3, n4, blockState);
            if (d4 >= 1000000.0) {
                return 1000000.0;
            }
            if (blockState2.getBlock() instanceof AirBlock) {
                d3 += 0.1;
            }
        }
        if (cc.f(blockState) && !cc.a(ca2.a, n2, n4, blockState2) || cc.f(blockState2) && ca2.j) {
            return 1000000.0;
        }
        if ((block == Blocks.LILY_PAD || block instanceof CarpetBlock) && !blockState2.getFluidState().isEmpty()) {
            return 1000000.0;
        }
        double d5 = cc.a(ca2, n2, n3 + 2, n4, blockState3, true);
        if (d2 >= 1000000.0) {
            return 1000000.0;
        }
        if (d5 != 0.0) {
            if (block2 == Blocks.LADDER || block2 == Blocks.VINE) {
                d5 = 0.0;
            } else if (ca2.a(n2, n3 + 3, n4).getBlock() instanceof FallingBlock) {
                if (blockState4 == null) {
                    blockState4 = ca2.a(n2, n3 + 1, n4);
                }
                if (!(block2 instanceof FallingBlock) || !(blockState4.getBlock() instanceof FallingBlock)) {
                    return 1000000.0;
                }
            }
        }
        if (bl2) {
            return 8.51063829787234 + d5 * 5.0;
        }
        return JUMP_ONE_BLOCK_COST + d3 + ca2.e + d5;
    }

    private static boolean d(ca ca2, int n2, int n3, int n4) {
        return cc.h(ca2.a(n2 + 1, n3, n4)) || cc.h(ca2.a(n2 - 1, n3, n4)) || cc.h(ca2.a(n2, n3, n4 + 1)) || cc.h(ca2.a(n2, n3, n4 - 1));
    }

    public static BetterBlockPos a(ca ca2, BetterBlockPos betterBlockPos) {
        if (cc.h(ca2.a(betterBlockPos.north()))) {
            return betterBlockPos.north();
        }
        if (cc.h(ca2.a(betterBlockPos.south()))) {
            return betterBlockPos.south();
        }
        if (cc.h(ca2.a(betterBlockPos.east()))) {
            return betterBlockPos.east();
        }
        if (cc.h(ca2.a(betterBlockPos.west()))) {
            return betterBlockPos.west();
        }
        return null;
    }

    @Override
    public final cd a(cd object) {
        boolean bl2;
        super.a((cd)object);
        if (((cd)object).a != MovementStatus.RUNNING) {
            return object;
        }
        if (this.a.playerFeet().y < this.a.y) {
            cd cd2 = object;
            object = MovementStatus.UNREACHABLE;
            cd cd3 = cd2;
            cd2.a = object;
            return cd3;
        }
        Object object2 = fb.a(this.a, (BlockPos)this.a);
        if (cc.d(object2) && cc.d(this.a, this.b)) {
            ((cd)object).a(new cd.a(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(this.b), this.a.playerRotations()), false));
            Vec3 vec3 = VecUtils.getBlockPosCenter(this.b);
            if (Math.abs(this.a.player().position().x - vec3.x) > 0.2 || Math.abs(this.a.player().position().z - vec3.z) > 0.2) {
                ((cd)object).a(Input.MOVE_FORWARD, true);
            }
            if (this.a.playerFeet().equals((Object)this.b)) {
                Object object3 = object;
                object = MovementStatus.SUCCESS;
                object2 = object3;
                ((cd)object3).a = object;
                return object2;
            }
            return object;
        }
        boolean bl3 = object2.getBlock() == Blocks.LADDER || object2.getBlock() == Blocks.VINE;
        boolean bl4 = object2.getBlock() == Blocks.VINE;
        Rotation rotation2 = RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(this.c), this.a.playerRotations());
        if (!bl3) {
            ((cd)object).a(new cd.a(this.a.playerRotations().withPitch(rotation2.getPitch()), true));
        }
        boolean bl5 = bl2 = cc.b(this.a, this.a) || bl3;
        if (bl3) {
            BetterBlockPos betterBlockPos = bl4 ? dh.a(new ca(this.a), this.a) : this.a.relative(((Direction)object2.getValue((Property)LadderBlock.FACING)).getOpposite());
            if (betterBlockPos == null) {
                this.logDirect("Unable to climb vines. Consider disabling allowVines.");
                Object object4 = object;
                object = MovementStatus.UNREACHABLE;
                object2 = object4;
                ((cd)object4).a = object;
                return object2;
            }
            if (this.a.playerFeet().equals(betterBlockPos.above()) || this.a.playerFeet().equals((Object)this.b)) {
                Object object5 = object;
                object = MovementStatus.SUCCESS;
                object2 = object5;
                object5.a = object;
                return object2;
            }
            if (cc.c(fb.a(this.a, (BlockPos)this.a.below()))) {
                ((cd)object).a(Input.JUMP, true);
            }
            cc.a(this.a, (cd)object, (BlockPos)betterBlockPos);
            return object;
        }
        if (!((a)this.a).a.a(true, this.a.x, this.a.y, this.a.z)) {
            Object object6 = object;
            object = MovementStatus.UNREACHABLE;
            object2 = object6;
            ((cd)object6).a = object;
            return object2;
        }
        ((cd)object).a(Input.SNEAK, this.a.player().position().y > (double)this.b.getY() || this.a.player().position().y < (double)this.a.getY() + 0.2);
        double d2 = this.a.player().position().x - ((double)this.b.getX() + 0.5);
        double d3 = this.a.player().position().z - ((double)this.b.getZ() + 0.5);
        double d4 = d2;
        double d5 = d3;
        double d6 = Math.sqrt(d4 * d4 + d5 * d5);
        double d7 = Math.sqrt(this.a.player().getDeltaMovement().x * this.a.player().getDeltaMovement().x + this.a.player().getDeltaMovement().z * this.a.player().getDeltaMovement().z);
        if (d6 > 0.17) {
            ((cd)object).a(Input.MOVE_FORWARD, true);
            ((cd)object).a(new cd.a(rotation2, true));
        } else if (d7 < 0.05) {
            ((cd)object).a(Input.JUMP, this.a.player().position().y < (double)this.b.getY());
        }
        if (!bl2) {
            object2 = fb.a(this.a, (BlockPos)this.a);
            if (!(object2.getBlock() instanceof AirBlock) && !object2.canBeReplaced()) {
                RotationUtils.reachable(this.a, (BlockPos)this.a, this.a.playerController().getBlockReachDistance()).map(rotation -> new cd.a((Rotation)rotation, true)).ifPresent(((cd)object)::a);
                ((cd)object).a(Input.JUMP, false);
                ((cd)object).a(Input.CLICK_LEFT, true);
                bl2 = false;
            } else if (this.a.player().isCrouching() && (this.a.isLookingAt(this.a.below()) || this.a.isLookingAt(this.a)) && this.a.player().position().y > (double)this.b.getY() + 0.1) {
                ((cd)object).a(Input.CLICK_RIGHT, true);
            }
        }
        if (this.a.playerFeet().equals((Object)this.b) && bl2) {
            Object object7 = object;
            object = MovementStatus.SUCCESS;
            object2 = object7;
            ((cd)object7).a = object;
            return object2;
        }
        return object;
    }

    @Override
    public final boolean a(cd cd2) {
        Block block;
        if ((this.a.playerFeet().equals((Object)this.a) || this.a.playerFeet().equals((Object)this.a.below())) && ((block = fb.a(this.a, this.a.below())) == Blocks.LADDER || block == Blocks.VINE)) {
            cd2.a(Input.SNEAK, true);
        }
        if (cc.d(this.a, this.b.above())) {
            return true;
        }
        return super.a(cd2);
    }
}

