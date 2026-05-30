/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events;

import dev.huskuraft.effortless.api.events.Event;
import dev.huskuraft.effortless.api.events.EventFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventHolder {
    private final Map<Class<?>, Event<?>> events = new HashMap();

    private static Class<?> getReturnType(Class<?> functionalInterface) {
        Method[] methods = functionalInterface.getDeclaredMethods();
        if (methods.length != 1) {
            throw new IllegalArgumentException("Functional interface must have exactly one method.");
        }
        Method method = methods[0];
        return method.getReturnType();
    }

    public <T> Event<T> get(Class<T> clazz) {
        return this.events.computeIfAbsent(clazz, clazz1 -> {
            if (EventHolder.getReturnType(clazz1) == Void.TYPE) {
                return EventFactory.createLoop(clazz1);
            }
            return EventFactory.createEventResult(clazz1);
        });
    }

    public <T> Event<T> get(T ... typeGetter) {
        if (typeGetter.length != 0) {
            throw new IllegalStateException("array must be empty!");
        }
        return this.get(typeGetter.getClass().getComponentType());
    }

    public <T> void clear(Class<T> clazz) {
        this.events.remove(clazz);
    }

    public <T> void clear(T ... typeGetter) {
        if (typeGetter.length != 0) {
            throw new IllegalStateException("array must be empty!");
        }
        this.clear(typeGetter.getClass().getComponentType());
    }
}
