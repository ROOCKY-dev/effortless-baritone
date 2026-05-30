/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fi.dy.masa.litematica.Litematica
 *  fi.dy.masa.litematica.data.DataManager
 *  fi.dy.masa.litematica.schematic.placement.SchematicPlacement
 *  fi.dy.masa.litematica.schematic.placement.SubRegionPlacement
 *  fi.dy.masa.litematica.world.SchematicWorldHandler
 *  fi.dy.masa.litematica.world.WorldSchematic
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.utils.schematic.litematica;

import baritone.api.schematic.CompositeSchematic;
import baritone.api.schematic.ISchematic;
import baritone.api.schematic.IStaticSchematic;
import baritone.ge;
import fi.dy.masa.litematica.Litematica;
import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;
import fi.dy.masa.litematica.schematic.placement.SubRegionPlacement;
import fi.dy.masa.litematica.world.SchematicWorldHandler;
import fi.dy.masa.litematica.world.WorldSchematic;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

public final class LitematicaHelper {
    public static boolean a() {
        try {
            Class.forName(Litematica.class.getName());
            return true;
        }
        catch (ClassNotFoundException | NoClassDefFoundError throwable) {
            return false;
        }
    }

    public static boolean a(int n2) {
        return n2 >= 0 && n2 < DataManager.getSchematicPlacementManager().getAllSchematicsPlacements().size();
    }

    private static Vec3i a(Vec3i vec3i, Mirror mirror, Rotation rotation) {
        int n2 = vec3i.getX();
        int n3 = vec3i.getZ();
        if (mirror == Mirror.LEFT_RIGHT) {
            n3 = -n3;
        } else if (mirror == Mirror.FRONT_BACK) {
            n2 = -n2;
        }
        switch (rotation) {
            case CLOCKWISE_90: {
                return new Vec3i(-n3, vec3i.getY(), n2);
            }
            case CLOCKWISE_180: {
                return new Vec3i(-n2, vec3i.getY(), -n3);
            }
            case COUNTERCLOCKWISE_90: {
                return new Vec3i(n3, vec3i.getY(), -n2);
            }
        }
        return new Vec3i(n2, vec3i.getY(), n3);
    }

    public static Tuple<IStaticSchematic, Vec3i> a(int n2) {
        Vec3i vec3i;
        SchematicPlacement schematicPlacement = (SchematicPlacement)DataManager.getSchematicPlacementManager().getAllSchematicsPlacements().get(n2);
        int n3 = Integer.MAX_VALUE;
        int n4 = Integer.MAX_VALUE;
        int n5 = Integer.MAX_VALUE;
        HashMap<Vec3i, ge> hashMap = new HashMap<Vec3i, ge>();
        WorldSchematic worldSchematic = SchematicWorldHandler.getSchematicWorld();
        for (BlockState[][][] blockStateArray : schematicPlacement.getEnabledRelativeSubRegionPlacements().entrySet()) {
            SubRegionPlacement subRegionPlacement = (SubRegionPlacement)blockStateArray.getValue();
            vec3i = LitematicaHelper.a((Vec3i)subRegionPlacement.getPos(), schematicPlacement.getMirror(), schematicPlacement.getRotation());
            blockStateArray = LitematicaHelper.a(LitematicaHelper.a((Vec3i)schematicPlacement.getSchematic().getAreaSize((String)blockStateArray.getKey()), schematicPlacement.getMirror(), schematicPlacement.getRotation()), subRegionPlacement.getMirror(), subRegionPlacement.getRotation());
            int n6 = Math.min(blockStateArray.getX() + 1, 0);
            int n7 = Math.min(blockStateArray.getY() + 1, 0);
            int n8 = Math.min(blockStateArray.getZ() + 1, 0);
            n3 = Math.min(n3, vec3i.getX() + n6);
            n4 = Math.min(n4, vec3i.getY() + n7);
            n5 = Math.min(n5, vec3i.getZ() + n8);
            BlockPos blockPos = schematicPlacement.getOrigin().offset(vec3i).offset(n6, n7, n8);
            blockStateArray = new BlockState[Math.abs(blockStateArray.getX())][Math.abs(blockStateArray.getZ())][Math.abs(blockStateArray.getY())];
            for (int i2 = 0; i2 < blockStateArray.length; ++i2) {
                for (int i3 = 0; i3 < blockStateArray[i2].length; ++i3) {
                    for (int i4 = 0; i4 < blockStateArray[i2][i3].length; ++i4) {
                        blockStateArray[i2][i3][i4] = worldSchematic.getBlockState(blockPos.offset(i2, i4, i3));
                    }
                }
            }
            ge ge2 = new ge(blockStateArray);
            hashMap.put(vec3i.offset(n6, n7, n8), ge2);
        }
        LitematicaPlacementSchematic litematicaPlacementSchematic = new LitematicaPlacementSchematic(schematicPlacement.getName());
        for (Map.Entry entry : hashMap.entrySet()) {
            vec3i = ((Vec3i)entry.getKey()).offset(-n3, -n4, -n5);
            litematicaPlacementSchematic.put((ISchematic)entry.getValue(), vec3i.getX(), vec3i.getY(), vec3i.getZ());
        }
        return new Tuple((Object)litematicaPlacementSchematic, (Object)schematicPlacement.getOrigin().offset(n3, n4, n5));
    }

    static class LitematicaPlacementSchematic
    extends CompositeSchematic
    implements IStaticSchematic {
        private final String a;

        public LitematicaPlacementSchematic(String string) {
            super(0, 0, 0);
            this.a = string;
        }

        @Override
        public BlockState getDirect(int n2, int n3, int n4) {
            if (this.inSchematic(n2, n3, n4, null)) {
                return this.desiredState(n2, n3, n4, null, Collections.emptyList());
            }
            return null;
        }

        public String toString() {
            return this.a;
        }
    }
}

