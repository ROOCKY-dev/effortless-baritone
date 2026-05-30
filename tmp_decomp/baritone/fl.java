/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.Input
 */
package baritone;

import baritone.api.utils.input.Input;
import baritone.ff;

public class fl
extends net.minecraft.client.player.Input {
    private final ff a;

    fl(ff ff2) {
        this.a = ff2;
    }

    public void tick(boolean bl2, float f2) {
        this.leftImpulse = 0.0f;
        this.forwardImpulse = 0.0f;
        this.jumping = this.a.isInputForcedDown(Input.JUMP);
        this.up = this.a.isInputForcedDown(Input.MOVE_FORWARD);
        if (this.up) {
            this.forwardImpulse += 1.0f;
        }
        if (this.down = this.a.isInputForcedDown(Input.MOVE_BACK)) {
            this.forwardImpulse -= 1.0f;
        }
        if (this.left = this.a.isInputForcedDown(Input.MOVE_LEFT)) {
            this.leftImpulse += 1.0f;
        }
        if (this.right = this.a.isInputForcedDown(Input.MOVE_RIGHT)) {
            this.leftImpulse -= 1.0f;
        }
        if (this.shiftKeyDown = this.a.isInputForcedDown(Input.SNEAK)) {
            this.leftImpulse = (float)((double)this.leftImpulse * 0.3);
            this.forwardImpulse = (float)((double)this.forwardImpulse * 0.3);
        }
    }
}

