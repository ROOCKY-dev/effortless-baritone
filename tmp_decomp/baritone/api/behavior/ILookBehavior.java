/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.behavior;

import baritone.api.behavior.IBehavior;
import baritone.api.behavior.look.IAimProcessor;
import baritone.api.utils.Rotation;

public interface ILookBehavior
extends IBehavior {
    public void updateTarget(Rotation var1, boolean var2);

    public IAimProcessor getAimProcessor();
}

