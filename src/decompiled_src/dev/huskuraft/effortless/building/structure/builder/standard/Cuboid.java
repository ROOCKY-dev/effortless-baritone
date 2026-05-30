/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package dev.huskuraft.effortless.building.structure.builder.standard;

import com.google.common.collect.Sets;
import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.building.Context;
import dev.huskuraft.effortless.building.structure.BuildFeature;
import dev.huskuraft.effortless.building.structure.BuildFeatures;
import dev.huskuraft.effortless.building.structure.BuildMode;
import dev.huskuraft.effortless.building.structure.CubeFilling;
import dev.huskuraft.effortless.building.structure.PlaneFacing;
import dev.huskuraft.effortless.building.structure.PlaneLength;
import dev.huskuraft.effortless.building.structure.builder.BlockStructure;
import dev.huskuraft.effortless.building.structure.builder.Structure;
import dev.huskuraft.effortless.building.structure.builder.standard.Line;
import dev.huskuraft.effortless.building.structure.builder.standard.Single;
import dev.huskuraft.effortless.building.structure.builder.standard.Square;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

public record Cuboid(CubeFilling cubeFilling, PlaneFacing planeFacing, PlaneLength planeLength) implements BlockStructure
{
    public Cuboid() {
        this(CubeFilling.FILLED, PlaneFacing.BOTH, PlaneLength.VARIABLE);
    }

    @Override
    public Structure withFeature(BuildFeature feature) {
        return switch (feature.getType()) {
            case BuildFeatures.CUBE_FILLING -> new Cuboid((CubeFilling)feature, this.planeFacing, this.planeLength);
            case BuildFeatures.PLANE_FACING -> new Cuboid(this.cubeFilling, (PlaneFacing)feature, this.planeLength);
            case BuildFeatures.PLANE_LENGTH -> new Cuboid(this.cubeFilling, this.planeFacing, (PlaneLength)feature);
            default -> this;
        };
    }

    public static void addFullCubeBlocks(Set<BlockPosition> set, int x1, int x2, int y1, int y2, int z1, int z2) {
        int l = x1;
        while (x1 < x2 ? l <= x2 : l >= x2) {
            int n = z1;
            while (z1 < z2 ? n <= z2 : n >= z2) {
                int m = y1;
                while (y1 < y2 ? m <= y2 : m >= y2) {
                    set.add(new BlockPosition(l, m, n));
                    m += y1 < y2 ? 1 : -1;
                }
                n += z1 < z2 ? 1 : -1;
            }
            l += x1 < x2 ? 1 : -1;
        }
    }

    public static void addHollowCubeBlocks(Set<BlockPosition> set, int x1, int x2, int y1, int y2, int z1, int z2) {
        Square.addFullSquareBlocksX(set, x1, y1, y2, z1, z2);
        Square.addFullSquareBlocksX(set, x2, y1, y2, z1, z2);
        Square.addFullSquareBlocksZ(set, x1, x2, y1, y2, z1);
        Square.addFullSquareBlocksZ(set, x1, x2, y1, y2, z2);
        Square.addFullSquareBlocksY(set, x1, x2, y1, z1, z2);
        Square.addFullSquareBlocksY(set, x1, x2, y2, z1, z2);
    }

    public static void addSkeletonCubeBlocks(Set<BlockPosition> set, int x1, int x2, int y1, int y2, int z1, int z2) {
        Line.addXLineBlocks(set, x1, x2, y1, z1);
        Line.addXLineBlocks(set, x1, x2, y1, z2);
        Line.addXLineBlocks(set, x1, x2, y2, z1);
        Line.addXLineBlocks(set, x1, x2, y2, z2);
        Line.addYLineBlocks(set, y1, y2, x1, z1);
        Line.addYLineBlocks(set, y1, y2, x1, z2);
        Line.addYLineBlocks(set, y1, y2, x2, z1);
        Line.addYLineBlocks(set, y1, y2, x2, z2);
        Line.addZLineBlocks(set, z1, z2, x1, y1);
        Line.addZLineBlocks(set, z1, z2, x1, y2);
        Line.addZLineBlocks(set, z1, z2, x2, y1);
        Line.addZLineBlocks(set, z1, z2, x2, y2);
    }

    public static Stream<BlockPosition> collectCubePlaneBlocks(Context context, CubeFilling cubeFilling) {
        LinkedHashSet set = Sets.newLinkedHashSet();
        BlockPosition pos1 = context.getPosition(0);
        BlockPosition pos2 = context.getPosition(1);
        int x1 = pos1.x();
        int y1 = pos1.y();
        int z1 = pos1.z();
        int x2 = pos2.x();
        int y2 = pos2.y();
        int z2 = pos2.z();
        switch (cubeFilling) {
            case SKELETON: {
                Square.addHollowSquareBlocks(set, x1, x2, y1, y2, z1, z2);
                break;
            }
            case FILLED: {
                Square.addFullSquareBlocks(set, x1, x2, y1, y2, z1, z2);
                break;
            }
            case HOLLOW: {
                Square.addFullSquareBlocks(set, x1, x2, y1, y2, z1, z2);
            }
        }
        return set.stream();
    }

    public static Stream<BlockPosition> collectCubeBlocks(Context context, CubeFilling cubeFilling) {
        LinkedHashSet set = Sets.newLinkedHashSet();
        BlockPosition pos1 = context.getPosition(0);
        BlockPosition pos3 = context.getPosition(2);
        int x1 = pos1.x();
        int y1 = pos1.y();
        int z1 = pos1.z();
        int x3 = pos3.x();
        int y3 = pos3.y();
        int z3 = pos3.z();
        switch (cubeFilling) {
            case FILLED: {
                Cuboid.addFullCubeBlocks(set, x1, x3, y1, y3, z1, z3);
                break;
            }
            case HOLLOW: {
                Cuboid.addHollowCubeBlocks(set, x1, x3, y1, y3, z1, z3);
                break;
            }
            case SKELETON: {
                Cuboid.addSkeletonCubeBlocks(set, x1, x3, y1, y3, z1, z3);
            }
        }
        return set.stream();
    }

    @Override
    public BlockInteraction trace(Player player, Context context, int index) {
        return switch (index) {
            case 0 -> Single.traceSingle(player, context);
            case 1 -> Square.traceSquare(player, context, this.planeFacing, this.planeLength);
            case 2 -> Line.traceLineOnPlane(player, context, this.planeFacing);
            default -> null;
        };
    }

    @Override
    public Stream<BlockPosition> collect(Context context, int index) {
        return switch (index) {
            case 1 -> Single.collectSingleBlocks(context);
            case 2 -> Cuboid.collectCubePlaneBlocks(context, this.cubeFilling);
            case 3 -> Cuboid.collectCubeBlocks(context, this.cubeFilling);
            default -> Stream.empty();
        };
    }

    @Override
    public int traceSize(Context context) {
        return 3;
    }

    @Override
    public BuildMode getMode() {
        return BuildMode.CUBOID;
    }
}
