/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.behavior.look;

import baritone.api.behavior.look.IAimProcessor;
import baritone.api.utils.Rotation;

public interface ITickableAimProcessor
extends IAimProcessor {
    public void tick();

    public void advance(int var1);

    public Rotation nextRotation(Rotation var1);
}

