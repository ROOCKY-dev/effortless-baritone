/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events;

import dev.huskuraft.effortless.api.events.EventActor;
import dev.huskuraft.effortless.api.events.EventFactory;
import dev.huskuraft.effortless.api.events.EventResult;
import java.lang.reflect.Method;
import java.util.List;

static class EventFactory.5
extends EventFactory.AbstractInvocationHandler {
    final /* synthetic */ List val$listeners;

    EventFactory.5(List list) {
        this.val$listeners = list;
    }

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        for (EventActor listener : this.val$listeners) {
            EventResult result = (EventResult)EventFactory.invokeMethod(listener, method, args);
            if (!result.interruptsFurtherEvaluation()) continue;
            return result;
        }
        return EventResult.pass();
    }
}
