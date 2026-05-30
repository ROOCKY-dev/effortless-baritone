/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.impl;

import dev.huskuraft.effortless.api.events.Event;
import dev.huskuraft.effortless.api.events.impl.EventRegistry;
import dev.huskuraft.effortless.api.events.input.InteractionInput;
import dev.huskuraft.effortless.api.events.input.KeyInput;
import dev.huskuraft.effortless.api.events.input.RegisterKeys;
import dev.huskuraft.effortless.api.events.lifecycle.ClientStart;
import dev.huskuraft.effortless.api.events.lifecycle.ClientTick;
import dev.huskuraft.effortless.api.events.render.RegisterShader;
import dev.huskuraft.effortless.api.events.render.RenderGui;
import dev.huskuraft.effortless.api.events.render.RenderWorld;

public class ClientEventRegistry
extends EventRegistry {
    public Event<RegisterKeys> getRegisterKeysEvent() {
        return this.get(new RegisterKeys[0]);
    }

    public Event<KeyInput> getKeyInputEvent() {
        return this.get(new KeyInput[0]);
    }

    public Event<InteractionInput> getInteractionInputEvent() {
        return this.get(new InteractionInput[0]);
    }

    public Event<ClientStart> getClientStartEvent() {
        return this.get(new ClientStart[0]);
    }

    public Event<ClientTick> getClientTickEvent() {
        return this.get(new ClientTick[0]);
    }

    public Event<RenderGui> getRenderGuiEvent() {
        return this.get(new RenderGui[0]);
    }

    public Event<RenderWorld> getRenderWorldEvent() {
        return this.get(new RenderWorld[0]);
    }

    public Event<RegisterShader> getRegisterShaderEvent() {
        return this.get(new RegisterShader[0]);
    }
}
