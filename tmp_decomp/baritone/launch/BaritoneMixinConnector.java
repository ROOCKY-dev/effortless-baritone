/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.spongepowered.asm.mixin.Mixins
 *  org.spongepowered.asm.mixin.connect.IMixinConnector
 */
package baritone.launch;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class BaritoneMixinConnector
implements IMixinConnector {
    public void connect() {
        Mixins.addConfiguration((String)"mixins.baritone.json");
    }
}

