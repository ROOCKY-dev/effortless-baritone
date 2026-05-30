/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.renderer;

import dev.huskuraft.effortless.api.renderer.ScreenRect;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import javax.annotation.Nullable;

private static class Renderer.ScissorStack {
    private final Deque<ScreenRect> stack = new ArrayDeque<ScreenRect>();

    private Renderer.ScissorStack() {
    }

    public ScreenRect push(ScreenRect pScissor) {
        ScreenRect rect = this.stack.peekLast();
        if (rect != null) {
            ScreenRect rect1 = Objects.requireNonNullElse(pScissor.intersection(rect), ScreenRect.empty());
            this.stack.addLast(rect1);
            return rect1;
        }
        this.stack.addLast(pScissor);
        return pScissor;
    }

    @Nullable
    public ScreenRect pop() {
        if (this.stack.isEmpty()) {
            throw new IllegalStateException("Scissor stack underflow");
        }
        this.stack.removeLast();
        return this.stack.peekLast();
    }
}
