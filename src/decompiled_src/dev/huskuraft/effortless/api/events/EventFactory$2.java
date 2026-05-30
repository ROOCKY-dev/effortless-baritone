/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events;

import dev.huskuraft.effortless.api.events.EventFactory;
import dev.huskuraft.effortless.api.events.EventResult;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

static class EventFactory.2
extends EventFactory.AbstractInvocationHandler {
    final /* synthetic */ List val$listeners;

    EventFactory.2(List list) {
        this.val$listeners = list;
    }

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        for (Object listener : this.val$listeners) {
            EventResult result = (EventResult)Objects.requireNonNull(EventFactory.invokeMethod(listener, method, args));
            if (!result.interruptsFurtherEvaluation()) continue;
            return result;
        }
        return EventResult.pass();
    }
}
