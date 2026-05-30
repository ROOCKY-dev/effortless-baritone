/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events;

import dev.huskuraft.effortless.api.events.CompoundEventResult;
import dev.huskuraft.effortless.api.events.EventFactory;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

static class EventFactory.3
extends EventFactory.AbstractInvocationHandler {
    final /* synthetic */ List val$listeners;

    EventFactory.3(List list) {
        this.val$listeners = list;
    }

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        for (Object listener : this.val$listeners) {
            CompoundEventResult result = (CompoundEventResult)Objects.requireNonNull(EventFactory.invokeMethod(listener, method, args));
            if (!result.interruptsFurtherEvaluation()) continue;
            return result;
        }
        return CompoundEventResult.pass();
    }
}
