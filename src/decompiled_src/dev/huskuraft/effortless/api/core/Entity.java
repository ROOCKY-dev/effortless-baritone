/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.GameMode;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.math.Vector3d;
import dev.huskuraft.effortless.api.platform.Client;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.platform.Server;
import dev.huskuraft.effortless.api.text.Text;
import java.util.UUID;

public interface Entity
extends PlatformReference {
    public UUID getId();

    public boolean isDeadOrDying();

    public Client getClient();

    public Server getServer();

    public World getWorld();

    public Text getDisplayName();

    public Vector3d getPosition();

    default public Vector3d getEyePosition() {
        return this.getPosition().withY(this.getPosition().y() + (double)this.getEyeHeight());
    }

    default public Vector3d getEyeDirection() {
        return Entity.calculateViewVector(this.getXRot(), this.getYRot());
    }

    public GameMode getGameMode();

    public BlockInteraction raytrace(double var1, float var3, boolean var4);

    public static Vector3d calculateViewVector(float xRot, float yRot) {
        double f = (double)xRot * (Math.PI / 180);
        double f1 = (double)(-yRot) * (Math.PI / 180);
        double f2 = Math.cos(f1);
        double f3 = Math.sin(f1);
        double f4 = Math.cos(f);
        double f5 = Math.sin(f);
        return new Vector3d(f3 * f4, -f5, f2 * f4);
    }

    public float getEyeHeight();

    public void setPosition(Vector3d var1);

    public float getXRot();

    public void setXRot(float var1);

    public float getYRot();

    public void setYRot(float var1);
}
