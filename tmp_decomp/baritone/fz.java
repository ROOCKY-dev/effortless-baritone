/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.HitResult
 */
package baritone;

import baritone.a;
import baritone.api.cache.IWorldData;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.IPlayerController;
import baritone.api.utils.RayTraceUtils;
import baritone.api.utils.Rotation;
import baritone.f;
import baritone.ga;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class fz
implements IPlayerContext {
    private final a a;
    private final Minecraft a;
    private final ga a;

    public fz(a a2, Minecraft minecraft) {
        this.a = a2;
        this.a = minecraft;
        this.a = new ga(minecraft);
    }

    @Override
    public final Minecraft minecraft() {
        return this.a;
    }

    @Override
    public final LocalPlayer player() {
        return this.a.player;
    }

    @Override
    public final IPlayerController playerController() {
        return this.a;
    }

    @Override
    public final Level world() {
        return this.a.level;
    }

    @Override
    public final IWorldData worldData() {
        return this.a.a.a();
    }

    @Override
    public final BetterBlockPos viewerPos() {
        Entity entity = this.a.getCameraEntity();
        if (entity == null) {
            return this.playerFeet();
        }
        return BetterBlockPos.from(entity.blockPosition());
    }

    @Override
    public final Rotation playerRotations() {
        f f2 = this.a.a;
        return ((Boolean)baritone.a.a().freeLook.value != false ? Optional.ofNullable(f2.a) : Optional.empty()).orElseGet(() -> IPlayerContext.super.playerRotations());
    }

    @Override
    public final HitResult objectMouseOver() {
        return RayTraceUtils.rayTraceTowards((Entity)this.player(), this.playerRotations(), this.playerController().getBlockReachDistance());
    }
}

