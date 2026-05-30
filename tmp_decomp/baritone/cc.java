/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Holder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.block.AbstractSkullBlock
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.AmethystClusterBlock
 *  net.minecraft.world.level.block.AzaleaBlock
 *  net.minecraft.world.level.block.BambooStalkBlock
 *  net.minecraft.world.level.block.BaseFireBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CarpetBlock
 *  net.minecraft.world.level.block.CauldronBlock
 *  net.minecraft.world.level.block.DoorBlock
 *  net.minecraft.world.level.block.EndPortalBlock
 *  net.minecraft.world.level.block.FallingBlock
 *  net.minecraft.world.level.block.FenceGateBlock
 *  net.minecraft.world.level.block.FrostedIceBlock
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.InfestedBlock
 *  net.minecraft.world.level.block.LeavesBlock
 *  net.minecraft.world.level.block.LiquidBlock
 *  net.minecraft.world.level.block.PointedDripstoneBlock
 *  net.minecraft.world.level.block.ScaffoldingBlock
 *  net.minecraft.world.level.block.ShulkerBoxBlock
 *  net.minecraft.world.level.block.SkullBlock
 *  net.minecraft.world.level.block.SlabBlock
 *  net.minecraft.world.level.block.SnowLayerBlock
 *  net.minecraft.world.level.block.StainedGlassBlock
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.TrapDoorBlock
 *  net.minecraft.world.level.block.WaterlilyBlock
 *  net.minecraft.world.level.block.piston.MovingPistonBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Half
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.SlabType
 *  net.minecraft.world.level.block.state.properties.StairsShape
 *  net.minecraft.world.level.material.FlowingFluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.WaterFluid
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package baritone;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.movement.ActionCosts;
import baritone.api.pathing.movement.MovementStatus;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.RayTraceUtils;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.VecUtils;
import baritone.api.utils.input.Input;
import baritone.ca;
import baritone.cb;
import baritone.cd;
import baritone.dn;
import baritone.do;
import baritone.fb;
import baritone.fm;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.AzaleaBlock;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EndPortalBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface cc
extends ActionCosts,
Helper {
    public static boolean a(fb fb2, int n2, int n3, int n4, BlockState blockState) {
        if (!fb2.a.b(n2, n4)) {
            return true;
        }
        return ((List)baritone.a.a().blocksToDisallowBreaking.value).contains(blockState = blockState.getBlock()) || blockState == Blocks.ICE || blockState instanceof InfestedBlock || cc.a(fb2, n2, n3 + 1, n4, true) || cc.a(fb2, n2 + 1, n3, n4, false) || cc.a(fb2, n2 - 1, n3, n4, false) || cc.a(fb2, n2, n3, n4 + 1, false) || cc.a(fb2, n2, n3, n4 - 1, false);
    }

    public static boolean a(fb fb2, int n2, int n3, int n4, boolean bl2) {
        BlockState blockState = fb2.a(n2, n3, n4);
        Block block = blockState.getBlock();
        if (!bl2 && block instanceof FallingBlock && ((Boolean)baritone.a.a().avoidUpdatingFallingBlocks.value).booleanValue() && FallingBlock.isFree((BlockState)fb2.a(n2, n3 - 1, n4))) {
            return true;
        }
        if (block instanceof LiquidBlock) {
            if (bl2 || ((Boolean)baritone.a.a().strictLiquidCheck.value).booleanValue()) {
                return true;
            }
            if ((Integer)blockState.getValue((Property)LiquidBlock.LEVEL) == 0) {
                return true;
            }
            return !(fb2.a(n2, n3 - 1, n4).getBlock() instanceof LiquidBlock);
        }
        return !blockState.getFluidState().isEmpty();
    }

    public static boolean a(IPlayerContext iPlayerContext, BetterBlockPos betterBlockPos) {
        return cc.a(new fb(iPlayerContext), betterBlockPos.x, betterBlockPos.y, betterBlockPos.z);
    }

    public static boolean a(fb fb2, int n2, int n3, int n4) {
        return cc.b(fb2, n2, n3, n4, fb2.a(n2, n3, n4));
    }

    public static boolean a(ca ca2, int n2, int n3, int n4, BlockState blockState) {
        return ca2.a.a(ca2.a, n2, n3, n4, blockState);
    }

    public static boolean a(ca ca2, int n2, int n3, int n4) {
        return ca2.a.a(ca2.a, n2, n3, n4, ca2.a(n2, n3, n4));
    }

    public static boolean b(fb fb2, int n2, int n3, int n4, BlockState blockState) {
        int n5 = cc.a(blockState);
        if (n5 == do.a) {
            return true;
        }
        if (n5 == do.c) {
            return false;
        }
        return cc.c(fb2, n2, n3, n4, blockState);
    }

    public static int a(BlockState blockState) {
        Block block = blockState.getBlock();
        if (block instanceof AirBlock) {
            return do.a;
        }
        if (block instanceof BaseFireBlock || block == Blocks.TRIPWIRE || block == Blocks.COBWEB || block == Blocks.END_PORTAL || block == Blocks.COCOA || block instanceof AbstractSkullBlock || block == Blocks.BUBBLE_COLUMN || block instanceof ShulkerBoxBlock || block instanceof SlabBlock || block instanceof TrapDoorBlock || block == Blocks.HONEY_BLOCK || block == Blocks.END_ROD || block == Blocks.SWEET_BERRY_BUSH || block == Blocks.POINTED_DRIPSTONE || block instanceof AmethystClusterBlock || block instanceof AzaleaBlock) {
            return do.c;
        }
        if (block == Blocks.BIG_DRIPLEAF) {
            return do.c;
        }
        if (block == Blocks.POWDER_SNOW) {
            return do.c;
        }
        if (((List)baritone.a.a().blocksToAvoid.value).contains(block)) {
            return do.c;
        }
        if (block instanceof DoorBlock || block instanceof FenceGateBlock) {
            if (block == Blocks.IRON_DOOR) {
                return do.c;
            }
            return do.a;
        }
        if (block instanceof CarpetBlock) {
            return do.b;
        }
        if (block instanceof SnowLayerBlock) {
            return do.b;
        }
        FluidState fluidState = blockState.getFluidState();
        if (!fluidState.isEmpty()) {
            if (fluidState.getType().getAmount(fluidState) != 8) {
                return do.c;
            }
            return do.b;
        }
        if (block instanceof CauldronBlock) {
            return do.c;
        }
        if (blockState.isPathfindable(PathComputationType.LAND)) {
            return do.a;
        }
        return do.c;
    }

    public static boolean c(fb fb2, int n2, int n3, int n4, BlockState blockState) {
        Block block = blockState.getBlock();
        if (block instanceof CarpetBlock) {
            return cc.b(fb2, n2, n3 - 1, n4);
        }
        if (block instanceof SnowLayerBlock) {
            if (!fb2.a(n2, n4)) {
                return true;
            }
            if ((Integer)blockState.getValue((Property)SnowLayerBlock.LAYERS) >= 3) {
                return false;
            }
            return cc.b(fb2, n2, n3 - 1, n4);
        }
        block = blockState.getFluidState();
        if (!block.isEmpty()) {
            if (cc.a(n2, n3, n4, blockState, fb2)) {
                return false;
            }
            if (((Boolean)baritone.a.a().assumeWalkOnWater.value).booleanValue()) {
                return false;
            }
            if (!(fb2 = fb2.a(n2, n3 + 1, n4)).getFluidState().isEmpty() || fb2.getBlock() instanceof WaterlilyBlock) {
                return false;
            }
            return block.getType() instanceof WaterFluid;
        }
        return blockState.isPathfindable(PathComputationType.LAND);
    }

    public static int b(BlockState blockState) {
        Block block = blockState.getBlock();
        if (block instanceof AirBlock) {
            return do.a;
        }
        if (block instanceof BaseFireBlock || block == Blocks.TRIPWIRE || block == Blocks.COBWEB || block == Blocks.VINE || block == Blocks.LADDER || block == Blocks.COCOA || block instanceof AzaleaBlock || block instanceof DoorBlock || block instanceof FenceGateBlock || block instanceof SnowLayerBlock || !blockState.getFluidState().isEmpty() || block instanceof TrapDoorBlock || block instanceof EndPortalBlock || block instanceof SkullBlock || block instanceof ShulkerBoxBlock) {
            return do.c;
        }
        if (blockState.isPathfindable(PathComputationType.LAND)) {
            return do.a;
        }
        return do.c;
    }

    public static boolean b(ca ca2, int n2, int n3, int n4) {
        ca ca3 = ca2;
        return cc.a(ca3, ca3.a(n2, n3, n4));
    }

    public static boolean a(ca object, BlockState blockState) {
        fb cfr_ignored_0 = ((ca)object).a;
        object = ((ca)object).a;
        int n2 = Block.BLOCK_STATE_REGISTRY.getId((Object)blockState);
        int n3 = ((dn)object).a[n2];
        if ((n3 & 1) == 0) {
            n3 = ((dn)object).a(n2, blockState);
        }
        if ((n3 & 0x40) != 0) {
            return cc.a(blockState);
        }
        return (n3 & 0x20) != 0;
    }

    public static boolean a(IPlayerContext iPlayerContext, BlockPos blockPos) {
        int n2 = cc.b((BlockState)(iPlayerContext = iPlayerContext.world().getBlockState(blockPos)));
        if (n2 == do.a) {
            return true;
        }
        if (n2 == do.c) {
            return false;
        }
        return iPlayerContext.isPathfindable(PathComputationType.LAND);
    }

    public static boolean a(BlockState blockState) {
        return blockState.isPathfindable(PathComputationType.LAND);
    }

    public static boolean a(int n2, int n3, BlockState blockState, fb fb2) {
        Block block = blockState.getBlock();
        if (block instanceof AirBlock) {
            return true;
        }
        if (block instanceof SnowLayerBlock) {
            if (!fb2.a(n2, n3)) {
                return true;
            }
            return (Integer)blockState.getValue((Property)SnowLayerBlock.LAYERS) == 1;
        }
        if (block == Blocks.LARGE_FERN || block == Blocks.TALL_GRASS) {
            return true;
        }
        return blockState.canBeReplaced();
    }

    public static boolean a(IPlayerContext iPlayerContext, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2) {
        if (betterBlockPos2.equals((Object)betterBlockPos)) {
            return false;
        }
        if (!((iPlayerContext = fb.a(iPlayerContext, (BlockPos)betterBlockPos)).getBlock() instanceof DoorBlock)) {
            return true;
        }
        return cc.a(betterBlockPos, (BlockState)iPlayerContext, betterBlockPos2, DoorBlock.OPEN);
    }

    public static boolean b(IPlayerContext iPlayerContext, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2) {
        if (betterBlockPos2.equals((Object)betterBlockPos)) {
            return false;
        }
        if (!((iPlayerContext = fb.a(iPlayerContext, (BlockPos)betterBlockPos)).getBlock() instanceof FenceGateBlock)) {
            return true;
        }
        return (Boolean)iPlayerContext.getValue((Property)FenceGateBlock.OPEN);
    }

    public static boolean a(BetterBlockPos betterBlockPos, BlockState blockState, BetterBlockPos betterBlockPos2, BooleanProperty booleanProperty) {
        if (betterBlockPos2.equals((Object)betterBlockPos)) {
            return false;
        }
        Direction.Axis axis = ((Direction)blockState.getValue((Property)HorizontalDirectionalBlock.FACING)).getAxis();
        boolean bl2 = (Boolean)blockState.getValue((Property)booleanProperty);
        if (betterBlockPos2.north().equals((Object)betterBlockPos) || betterBlockPos2.south().equals((Object)betterBlockPos)) {
            betterBlockPos = Direction.Axis.Z;
        } else if (betterBlockPos2.east().equals((Object)betterBlockPos) || betterBlockPos2.west().equals((Object)betterBlockPos)) {
            betterBlockPos = Direction.Axis.X;
        } else {
            return true;
        }
        return axis == betterBlockPos == bl2;
    }

    public static boolean b(BlockState blockState) {
        Block block = blockState.getBlock();
        return !blockState.getFluidState().isEmpty() || block == Blocks.MAGMA_BLOCK || block == Blocks.CACTUS || block == Blocks.SWEET_BERRY_BUSH || block instanceof BaseFireBlock || block == Blocks.END_PORTAL || block == Blocks.COBWEB || block == Blocks.BUBBLE_COLUMN;
    }

    public static boolean d(fb fb2, int n2, int n3, int n4, BlockState blockState) {
        int n5 = cc.c(blockState);
        if (n5 == do.a) {
            return true;
        }
        if (n5 == do.c) {
            return false;
        }
        return cc.e(fb2, n2, n3, n4, blockState);
    }

    public static int c(BlockState blockState) {
        Block block = blockState.getBlock();
        if (cc.h(blockState) && block != Blocks.MAGMA_BLOCK && block != Blocks.BUBBLE_COLUMN && block != Blocks.HONEY_BLOCK) {
            return do.a;
        }
        if (block instanceof AzaleaBlock) {
            return do.a;
        }
        if (block == Blocks.LADDER || block == Blocks.VINE && ((Boolean)baritone.a.a().allowVines.value).booleanValue()) {
            return do.a;
        }
        if (block == Blocks.FARMLAND || block == Blocks.DIRT_PATH || block == Blocks.SOUL_SAND) {
            return do.a;
        }
        if (block == Blocks.ENDER_CHEST || block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST) {
            return do.a;
        }
        if (block == Blocks.GLASS || block instanceof StainedGlassBlock) {
            return do.a;
        }
        if (block instanceof StairBlock) {
            return do.a;
        }
        if (cc.d(blockState)) {
            return do.b;
        }
        if (cc.e(blockState) && ((Boolean)baritone.a.a().assumeWalkOnLava.value).booleanValue()) {
            return do.b;
        }
        if (block instanceof SlabBlock) {
            if (!((Boolean)baritone.a.a().allowWalkOnBottomSlab.value).booleanValue()) {
                if (blockState.getValue((Property)SlabBlock.TYPE) != SlabType.BOTTOM) {
                    return do.a;
                }
                return do.c;
            }
            return do.a;
        }
        return do.c;
    }

    public static boolean e(fb fb2, int n2, int n3, int n4, BlockState blockState) {
        blockState.getBlock();
        if (cc.d(blockState)) {
            BlockState blockState2 = fb2.a(n2, n3 + 1, n4);
            Block block = blockState2.getBlock();
            if (block == Blocks.LILY_PAD || block instanceof CarpetBlock) {
                return true;
            }
            if (cc.a(n2, n3, n4, blockState, fb2) || blockState2.getFluidState().getType() == Fluids.FLOWING_WATER) {
                return cc.d(blockState2) && (Boolean)baritone.a.a().assumeWalkOnWater.value == false;
            }
            return cc.d(blockState2) ^ (Boolean)baritone.a.a().assumeWalkOnWater.value;
        }
        return cc.e(blockState) && !cc.a(n2, n3, n4, blockState, fb2) && (Boolean)baritone.a.a().assumeWalkOnLava.value != false;
    }

    public static boolean b(ca object, int n2, int n3, int n4, BlockState blockState) {
        BlockState blockState2 = blockState;
        int n5 = n4;
        n4 = n3;
        n3 = n2;
        fb fb2 = ((ca)object).a;
        object = ((ca)object).a;
        int n6 = Block.BLOCK_STATE_REGISTRY.getId((Object)blockState2);
        int n7 = ((dn)object).a[n6];
        if ((n7 & 1) == 0) {
            n7 = ((dn)object).a(n6, blockState2);
        }
        if ((n7 & 4) != 0) {
            return cc.e(fb2, n3, n4, n5, blockState2);
        }
        return (n7 & 2) != 0;
    }

    public static boolean c(ca ca2, int n2, int n3, int n4) {
        return cc.b(ca2, n2, n3, n4, ca2.a(n2, n3, n4));
    }

    public static boolean a(IPlayerContext iPlayerContext, BetterBlockPos betterBlockPos, BlockState blockState) {
        return cc.d(new fb(iPlayerContext), betterBlockPos.x, betterBlockPos.y, betterBlockPos.z, blockState);
    }

    public static boolean b(IPlayerContext iPlayerContext, BlockPos blockPos) {
        return cc.b(new fb(iPlayerContext), blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static boolean b(IPlayerContext iPlayerContext, BetterBlockPos betterBlockPos) {
        return cc.b(new fb(iPlayerContext), betterBlockPos.x, betterBlockPos.y, betterBlockPos.z);
    }

    public static boolean b(fb fb2, int n2, int n3, int n4) {
        return cc.d(fb2, n2, n3, n4, fb2.a(n2, n3, n4));
    }

    public static boolean b(ca ca2, BlockState blockState) {
        return ca2.a != 0 && blockState == FrostedIceBlock.meltsInto() && (Integer)blockState.getValue((Property)LiquidBlock.LEVEL) == 0;
    }

    public static boolean c(IPlayerContext iPlayerContext, BetterBlockPos betterBlockPos) {
        boolean bl2 = false;
        block0: for (Object object : EquipmentSlot.values()) {
            object = iPlayerContext.player().getItemBySlot((EquipmentSlot)object).getEnchantments().keySet().iterator();
            while (object.hasNext()) {
                if (!((Holder)object.next()).is(Enchantments.FROST_WALKER)) continue;
                bl2 = true;
                break block0;
            }
        }
        BlockState blockState = fb.a(iPlayerContext, (BlockPos)betterBlockPos);
        return bl2 && blockState == FrostedIceBlock.meltsInto() && (Integer)blockState.getValue((Property)LiquidBlock.LEVEL) == 0;
    }

    public static boolean c(ca ca2, int n2, int n3, int n4, BlockState blockState) {
        Block block = blockState.getBlock();
        if (block == Blocks.LADDER || block == Blocks.VINE) {
            return false;
        }
        if (!blockState.getFluidState().isEmpty()) {
            if (block instanceof SlabBlock) {
                if (blockState.getValue((Property)SlabBlock.TYPE) != SlabType.BOTTOM) {
                    return true;
                }
            } else if (block instanceof StairBlock) {
                if (blockState.getValue((Property)StairBlock.HALF) == Half.TOP) {
                    return true;
                }
                if ((blockState = (StairsShape)blockState.getValue((Property)StairBlock.SHAPE)) == StairsShape.INNER_LEFT || blockState == StairsShape.INNER_RIGHT) {
                    return true;
                }
            } else if (block instanceof TrapDoorBlock) {
                if (!((Boolean)blockState.getValue((Property)TrapDoorBlock.OPEN)).booleanValue() && blockState.getValue((Property)TrapDoorBlock.HALF) == Half.TOP) {
                    return true;
                }
            } else {
                if (block == Blocks.SCAFFOLDING) {
                    return true;
                }
                if (block instanceof LeavesBlock) {
                    return true;
                }
            }
            if (ca2.j) {
                return false;
            }
            if (ca2.a(n2, n3 + 1, n4) instanceof LiquidBlock) {
                return false;
            }
        }
        return true;
    }

    public static boolean c(fb fb2, int n2, int n3, int n4) {
        return cc.a(fb2, n2, n4, fb2.a(n2, n3, n4));
    }

    public static boolean a(fb fb2, BlockPos blockPos) {
        return cc.c(fb2, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static boolean c(IPlayerContext iPlayerContext, BlockPos blockPos) {
        return cc.a(new fb(iPlayerContext), blockPos);
    }

    public static boolean a(fb fb2, int n2, int n3, BlockState blockState) {
        if (!fb2.a.b(n2, n3)) {
            return false;
        }
        return cc.h(blockState) || blockState.getBlock() == Blocks.GLASS || blockState.getBlock() instanceof StainedGlassBlock;
    }

    public static double a(ca ca2, int n2, int n3, int n4, boolean bl2) {
        return cc.a(ca2, n2, n3, n4, ca2.a(n2, n3, n4), bl2);
    }

    public static double a(ca ca2, int n2, int n3, int n4, BlockState object, boolean bl2) {
        object.getBlock();
        if (!cc.a(ca2, n2, n3, n4, object)) {
            double d2;
            double d3;
            if (!object.getFluidState().isEmpty()) {
                return 1000000.0;
            }
            double d4 = ca2.b(n2, n3, n4, (BlockState)object);
            if (d3 >= 1000000.0) {
                return 1000000.0;
            }
            if (cc.a(ca2.a, n2, n3, n4, object)) {
                return 1000000.0;
            }
            BlockState blockState = object;
            object = ca2.a;
            double d5 = object.a.computeIfAbsent(blockState.getBlock(), (Function<Block, Double>)((Object)object.a));
            if (d2 <= 0.0) {
                return 1000000.0;
            }
            double d6 = (1.0 / d5 + ca2.c) * d4;
            if (bl2 && (object = ca2.a(n2, n3 + 1, n4)).getBlock() instanceof FallingBlock) {
                d6 += cc.a(ca2, n2, n3 + 1, n4, object, true);
            }
            return d6;
        }
        return 0.0;
    }

    public static boolean c(BlockState blockState) {
        return blockState.getBlock() instanceof SlabBlock && blockState.getValue((Property)SlabBlock.TYPE) == SlabType.BOTTOM;
    }

    public static void a(IPlayerContext iPlayerContext, BlockState blockState) {
        cc.a(iPlayerContext, blockState, new fm(iPlayerContext.player()), (Boolean)BaritoneAPI.getSettings().preferSilkTouch.value);
    }

    public static void a(IPlayerContext iPlayerContext, BlockState blockState, fm fm2, boolean bl2) {
        if (((Boolean)baritone.a.a().autoTool.value).booleanValue() && !((Boolean)baritone.a.a().assumeExternalAutoTool.value).booleanValue()) {
            iPlayerContext.player().getInventory().selected = fm2.a(blockState.getBlock(), bl2, false);
        }
    }

    public static void a(IPlayerContext iPlayerContext, cd cd2, BlockPos blockPos) {
        cd2.a(new cd.a(RotationUtils.calcRotationFromVec3d(iPlayerContext.playerHead(), VecUtils.getBlockPosCenter(blockPos), iPlayerContext.playerRotations()).withPitch(iPlayerContext.playerRotations().getPitch()), false)).a(Input.MOVE_FORWARD, true);
    }

    public static boolean d(BlockState blockState) {
        return (blockState = blockState.getFluidState().getType()) == Fluids.WATER || blockState == Fluids.FLOWING_WATER;
    }

    public static boolean d(IPlayerContext iPlayerContext, BetterBlockPos betterBlockPos) {
        return cc.d(fb.a(iPlayerContext, (BlockPos)betterBlockPos));
    }

    public static boolean e(BlockState blockState) {
        return (blockState = blockState.getFluidState().getType()) == Fluids.LAVA || blockState == Fluids.FLOWING_LAVA;
    }

    public static boolean e(IPlayerContext iPlayerContext, BetterBlockPos betterBlockPos) {
        return cc.f(fb.a(iPlayerContext, (BlockPos)betterBlockPos));
    }

    public static boolean f(BlockState blockState) {
        return !blockState.getFluidState().isEmpty();
    }

    public static boolean g(BlockState blockState) {
        return (blockState = blockState.getFluidState()).getType() instanceof FlowingFluid && blockState.getType().getAmount((FluidState)blockState) != 8;
    }

    public static boolean a(int n2, int n3, int n4, BlockState blockState, fb fb2) {
        if (!((blockState = blockState.getFluidState()).getType() instanceof FlowingFluid)) {
            return false;
        }
        if (blockState.getType().getAmount((FluidState)blockState) != 8) {
            return true;
        }
        return cc.g(fb2.a(n2 + 1, n3, n4)) || cc.g(fb2.a(n2 - 1, n3, n4)) || cc.g(fb2.a(n2, n3, n4 + 1)) || cc.g(fb2.a(n2, n3, n4 - 1));
    }

    public static boolean h(BlockState blockState) {
        Block block = blockState.getBlock();
        if (block instanceof BambooStalkBlock || block instanceof MovingPistonBlock || block instanceof ScaffoldingBlock || block instanceof ShulkerBoxBlock || block instanceof PointedDripstoneBlock || block instanceof AmethystClusterBlock) {
            return false;
        }
        try {
            return Block.isShapeFullBlock((VoxelShape)blockState.getCollisionShape(null, null));
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static a a(cd cd2, IBaritone iBaritone, BlockPos blockPos, boolean bl2, boolean bl3) {
        BlockPos blockPos2;
        IPlayerContext iPlayerContext = iBaritone.getPlayerContext();
        Optional<Rotation> optional = RotationUtils.reachable(iPlayerContext, blockPos, bl3);
        boolean bl4 = false;
        if (optional.isPresent()) {
            cd2.a(new cd.a(optional.get(), true));
            bl4 = true;
        }
        for (int i2 = 0; i2 < 5; ++i2) {
            blockPos2 = blockPos.relative(cb.a[i2]);
            if (!cc.c(iPlayerContext, blockPos2)) continue;
            if (!((baritone.a)iBaritone).a.a(false, blockPos.getX(), blockPos.getY(), blockPos.getZ())) {
                Helper.HELPER.logDebug("bb pls get me some blocks. dirt, netherrack, cobble");
                cd2.a = MovementStatus.UNREACHABLE;
                return a.c;
            }
            double d2 = ((double)(blockPos.getX() + blockPos2.getX()) + 1.0) * 0.5;
            double d3 = ((double)(blockPos.getY() + blockPos2.getY()) + 0.5) * 0.5;
            double d4 = ((double)(blockPos.getZ() + blockPos2.getZ()) + 1.0) * 0.5;
            Rotation rotation = RotationUtils.calcRotationFromVec3d(bl3 ? RayTraceUtils.inferSneakingEyePosition((Entity)iPlayerContext.player()) : iPlayerContext.playerHead(), new Vec3(d2, d3, d4), iPlayerContext.playerRotations());
            Rotation rotation2 = iBaritone.getLookBehavior().getAimProcessor().peekRotation(rotation);
            rotation2 = RayTraceUtils.rayTraceTowards((Entity)iPlayerContext.player(), rotation2, iPlayerContext.playerController().getBlockReachDistance(), bl3);
            if (rotation2 == null || rotation2.getType() != HitResult.Type.BLOCK || !((BlockHitResult)rotation2).getBlockPos().equals((Object)blockPos2) || !((BlockHitResult)rotation2).getBlockPos().relative(((BlockHitResult)rotation2).getDirection()).equals((Object)blockPos)) continue;
            cd2.a(new cd.a(rotation, true));
            bl4 = true;
            if (!bl2) break;
        }
        if (iPlayerContext.getSelectedBlock().isPresent()) {
            BlockPos blockPos3 = iPlayerContext.getSelectedBlock().get();
            blockPos2 = ((BlockHitResult)iPlayerContext.objectMouseOver()).getDirection();
            if (blockPos3.equals((Object)blockPos) || cc.c(iPlayerContext, blockPos3) && blockPos3.relative((Direction)blockPos2).equals((Object)blockPos)) {
                if (bl3) {
                    cd2.a(Input.SNEAK, true);
                }
                ((baritone.a)iBaritone).a.a(true, blockPos.getX(), blockPos.getY(), blockPos.getZ());
                return a.a;
            }
        }
        if (bl4) {
            if (bl3) {
                cd2.a(Input.SNEAK, true);
            }
            ((baritone.a)iBaritone).a.a(true, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            return a.b;
        }
        return a.c;
    }

    public static boolean a(Block block) {
        return block instanceof AirBlock || block == Blocks.LAVA || block == Blocks.WATER;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class a
    extends Enum<a> {
        public static final /* enum */ a a = new a();
        public static final /* enum */ a b = new a();
        public static final /* enum */ a c = new a();
        private static final /* synthetic */ a[] a;

        public static a[] values() {
            return (a[])a.clone();
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        static {
            a = new a[]{a, b, c};
        }
    }
}

