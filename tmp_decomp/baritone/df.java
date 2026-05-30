/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.LadderBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.WaterFluid
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

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
import baritone.dc;
import baritone.fw;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class df
extends cb {
    private static final ItemStack a = new ItemStack((ItemLike)Items.WATER_BUCKET);
    private static final ItemStack b = new ItemStack((ItemLike)Items.BUCKET);

    public df(IBaritone iBaritone, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2) {
        super(iBaritone, betterBlockPos, betterBlockPos2, df.a(betterBlockPos, betterBlockPos2));
    }

    @Override
    public final double a(ca ca2) {
        fw fw2 = new fw();
        dc.a(ca2, ((cb)this).a.x, ((cb)this).a.y, ((cb)this).a.z, ((cb)this).b.x, ((cb)this).b.z, fw2);
        if (fw2.b != ((cb)this).b.y) {
            return 1000000.0;
        }
        return fw2.a;
    }

    @Override
    public final Set<BetterBlockPos> a() {
        HashSet<BetterBlockPos> hashSet = new HashSet<BetterBlockPos>();
        hashSet.add(((cb)this).a);
        for (int i2 = ((cb)this).a.y - ((cb)this).b.y; i2 >= 0; --i2) {
            hashSet.add(((cb)this).b.above(i2));
        }
        return hashSet;
    }

    @Override
    public final cd a(cd object) {
        Direction direction;
        Rotation rotation;
        Rotation rotation2;
        Object object2;
        block23: {
            fw fw2;
            df df2;
            super.a((cd)object);
            if (((cd)object).a != MovementStatus.RUNNING) {
                return object;
            }
            object2 = ((cb)this).a.playerFeet();
            rotation2 = RotationUtils.calcRotationFromVec3d(((cb)this).a.playerHead(), VecUtils.getBlockPosCenter(((cb)this).b), ((cb)this).a.playerRotations());
            rotation = null;
            BlockState blockState = ((cb)this).a.world().getBlockState((BlockPos)((cb)this).b);
            blockState.getBlock();
            boolean bl2 = blockState.getFluidState().getType() instanceof WaterFluid;
            if (!bl2) {
                df2 = this;
                ca ca2 = new ca(((cb)df2).a);
                fw2 = new fw();
                int cfr_ignored_0 = ((cb)df2).a.x;
                int cfr_ignored_1 = ((cb)df2).a.z;
                if (dc.a(ca2, ((cb)df2).a.y, ((cb)df2).b.x, ((cb)df2).b.z, 0.0, ca2.a(((cb)df2).b.x, ((cb)df2).a.y - 2, ((cb)df2).b.z), fw2) && !object2.equals((Object)((cb)this).b)) {
                    if (!Inventory.isHotbarSlot((int)((cb)this).a.player().getInventory().findSlotMatchingItem(a)) || ((cb)this).a.world().dimension() == Level.NETHER) {
                        Object object3 = object;
                        object = MovementStatus.UNREACHABLE;
                        object2 = object3;
                        ((cd)object3).a = object;
                        return object2;
                    }
                    if (((cb)this).a.player().position().y - (double)((cb)this).b.getY() < ((cb)this).a.playerController().getBlockReachDistance() && !((cb)this).a.player().onGround()) {
                        ((cb)this).a.player().getInventory().selected = ((cb)this).a.player().getInventory().findSlotMatchingItem(a);
                        rotation = new Rotation(rotation2.getYaw(), 90.0f);
                        if (((cb)this).a.isLookingAt(((cb)this).b) || ((cb)this).a.isLookingAt(((cb)this).b.below())) {
                            ((cd)object).a(Input.CLICK_RIGHT, true);
                        }
                    }
                }
            }
            if (rotation != null) {
                ((cd)object).a(new cd.a(rotation, true));
            } else {
                ((cd)object).a(new cd.a(rotation2, false));
            }
            if (object2.equals((Object)((cb)this).b) && (((cb)this).a.player().position().y - (double)object2.getY() < 0.094 || bl2)) {
                if (bl2) {
                    if (Inventory.isHotbarSlot((int)((cb)this).a.player().getInventory().findSlotMatchingItem(b))) {
                        ((cb)this).a.player().getInventory().selected = ((cb)this).a.player().getInventory().findSlotMatchingItem(b);
                        if (((cb)this).a.player().getDeltaMovement().y >= 0.0) {
                            return ((cd)object).a(Input.CLICK_RIGHT, true);
                        }
                        return object;
                    }
                    if (((cb)this).a.player().getDeltaMovement().y >= 0.0) {
                        Object object4 = object;
                        object = MovementStatus.SUCCESS;
                        object2 = object4;
                        ((cd)object4).a = object;
                        return object2;
                    }
                } else {
                    cd cd2 = object;
                    object = MovementStatus.SUCCESS;
                    object2 = cd2;
                    cd2.a = object;
                    return object2;
                }
            }
            object2 = VecUtils.getBlockPosCenter(((cb)this).b);
            if (Math.abs(((cb)this).a.player().position().x + ((cb)this).a.player().getDeltaMovement().x - ((Vec3)object2).x) > 0.1 || Math.abs(((cb)this).a.player().position().z + ((cb)this).a.player().getDeltaMovement().z - ((Vec3)object2).z) > 0.1) {
                if (!((cb)this).a.player().onGround() && Math.abs(((cb)this).a.player().getDeltaMovement().y) > 0.4) {
                    ((cd)object).a(Input.SNEAK, true);
                }
                ((cd)object).a(Input.MOVE_FORWARD, true);
            }
            df2 = this;
            for (int i2 = 0; i2 < 15; ++i2) {
                fw2 = ((cb)df2).a.world().getBlockState((BlockPos)((cb)df2).a.playerFeet().below(i2));
                if (fw2.getBlock() != Blocks.LADDER) continue;
                direction = (Direction)fw2.getValue((Property)LadderBlock.FACING);
                break block23;
            }
            direction = null;
        }
        rotation2 = Optional.ofNullable(direction).map(Direction::getNormal).orElse(null);
        if (rotation2 == null) {
            rotation2 = ((cb)this).a.subtract((Vec3i)((cb)this).b);
        } else if (Math.abs((double)rotation2.getX() * (((Vec3)object2).x - (double)rotation2.getX() / 2.0 - ((cb)this).a.player().position().x)) + Math.abs((double)rotation2.getZ() * (((Vec3)object2).z - (double)rotation2.getZ() / 2.0 - ((cb)this).a.player().position().z)) < 0.6) {
            ((cd)object).a(Input.MOVE_FORWARD, true);
        } else if (!((cb)this).a.player().onGround()) {
            ((cd)object).a(Input.SNEAK, false);
        }
        if (rotation == null) {
            object2 = new Vec3(((Vec3)object2).x + 0.125 * (double)rotation2.getX(), ((Vec3)object2).y, ((Vec3)object2).z + 0.125 * (double)rotation2.getZ());
            ((cd)object).a(new cd.a(RotationUtils.calcRotationFromVec3d(((cb)this).a.playerHead(), (Vec3)object2, ((cb)this).a.playerRotations()), false));
        }
        return object;
    }

    @Override
    public final boolean b(cd cd2) {
        return ((cb)this).a.playerFeet().equals((Object)((cb)this).a) || cd2.a != MovementStatus.RUNNING;
    }

    private static BetterBlockPos[] a(BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPosArray) {
        int n2 = betterBlockPos.getX() - betterBlockPosArray.getX();
        int n3 = betterBlockPos.getZ() - betterBlockPosArray.getZ();
        betterBlockPosArray = new BetterBlockPos[Math.abs(betterBlockPos.getY() - betterBlockPosArray.getY()) + 2];
        for (int i2 = 0; i2 < betterBlockPosArray.length; ++i2) {
            betterBlockPosArray[i2] = new BetterBlockPos(betterBlockPos.getX() - n2, betterBlockPos.getY() + 1 - i2, betterBlockPos.getZ() - n3);
        }
        return betterBlockPosArray;
    }

    @Override
    public final boolean a(cd cd2) {
        if (cd2.a == MovementStatus.WAITING) {
            return true;
        }
        for (int i2 = 0; i2 < 4 && i2 < ((cb)this).a.length; ++i2) {
            if (cc.a(((cb)this).a, ((cb)this).a[i2])) continue;
            return super.a(cd2);
        }
        return true;
    }
}

