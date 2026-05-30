/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.Interaction;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.math.Vector3d;

public record EntityInteraction(Vector3d position, Player entity) implements Interaction
{
    public EntityInteraction(Player entity) {
        this(entity.getPosition(), entity);
    }

    public Player getEntity() {
        return this.entity;
    }

    @Override
    public Interaction.Target getTarget() {
        return Interaction.Target.ENTITY;
    }

    @Override
    public Vector3d getPosition() {
        return this.position;
    }
}
