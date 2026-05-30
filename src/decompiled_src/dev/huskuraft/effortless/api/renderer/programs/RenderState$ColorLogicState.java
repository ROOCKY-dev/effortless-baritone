/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer.programs;

import dev.huskuraft.effortless.api.renderer.RenderStateFactory;
import dev.huskuraft.effortless.api.renderer.programs.RenderState;

public static interface RenderState.ColorLogicState
extends RenderState {
    public static RenderState.ColorLogicState create(String name, Op op) {
        return RenderStateFactory.getInstance().createColorLogicState(name, op);
    }

    public static enum Op {
        NO_LOGIC,
        OR_REVERSE_LOGIC;

    }
}
