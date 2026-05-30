/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.monster.EnderMan
 *  net.minecraft.world.entity.monster.Spider
 *  net.minecraft.world.entity.monster.ZombifiedPiglin
 */
package baritone;

import baritone.a;
import baritone.api.utils.IPlayerContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.ZombifiedPiglin;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ft {
    final int a;
    final int b;
    final int c;
    final double a;
    final int d;
    private final int e;

    private ft(BlockPos blockPos, double d2, int n2) {
        this(blockPos.getX(), blockPos.getY(), blockPos.getZ(), d2, n2);
    }

    private ft(int n2, int n3, int n4, double d2, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.a = d2;
        this.d = n5;
        int n6 = n5;
        this.e = n6 * n6;
    }

    public static List<ft> a(IPlayerContext iPlayerContext) {
        if (!((Boolean)baritone.a.a().avoidance.value).booleanValue()) {
            return Collections.emptyList();
        }
        ArrayList<ft> arrayList = new ArrayList<ft>();
        double d2 = (Double)baritone.a.a().mobSpawnerAvoidanceCoefficient.value;
        double d3 = (Double)baritone.a.a().mobAvoidanceCoefficient.value;
        if (d2 != 1.0) {
            iPlayerContext.worldData().getCachedWorld().getLocationsOf("mob_spawner", 1, iPlayerContext.playerFeet().x, iPlayerContext.playerFeet().z, 2).forEach(blockPos -> arrayList.add(new ft((BlockPos)blockPos, d2, (Integer)baritone.a.a().mobSpawnerAvoidanceRadius.value)));
        }
        if (d3 != 1.0) {
            iPlayerContext.entitiesStream().filter(entity -> entity instanceof Mob).filter(entity -> !(entity instanceof Spider) || (double)iPlayerContext.player().getLightLevelDependentMagicValue() < 0.5).filter(entity -> !(entity instanceof ZombifiedPiglin) || ((ZombifiedPiglin)entity).getLastHurtByMob() != null).filter(entity -> !(entity instanceof EnderMan) || ((EnderMan)entity).isCreepy()).forEach(entity -> arrayList.add(new ft(entity.blockPosition(), d3, (Integer)baritone.a.a().mobAvoidanceRadius.value)));
        }
        return arrayList;
    }
}

