/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.behavior.look;

import baritone.api.behavior.look.ITickableAimProcessor;
import baritone.api.utils.Rotation;

public interface IAimProcessor {
    public Rotation peekRotation(Rotation var1);

    public ITickableAimProcessor fork();
}

