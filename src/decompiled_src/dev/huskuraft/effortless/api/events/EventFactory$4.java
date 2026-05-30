/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events;

import dev.huskuraft.effortless.api.events.EventFactory;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;

static class EventFactory.4
extends EventFactory.AbstractInvocationHandler {
    final /* synthetic */ List val$listeners;

    EventFactory.4(List list) {
        this.val$listeners = list;
    }

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        for (Consumer listener : this.val$listeners) {
            EventFactory.invokeMethod(listener, method, args);
        }
        return null;
    }
}
