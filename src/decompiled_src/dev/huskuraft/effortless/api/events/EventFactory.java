/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.CheckForNull
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.events;

import dev.huskuraft.effortless.api.events.CompoundEventResult;
import dev.huskuraft.effortless.api.events.Event;
import dev.huskuraft.effortless.api.events.EventActor;
import dev.huskuraft.effortless.api.events.EventResult;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

public final class EventFactory {
    private EventFactory() {
    }

    public static <T> Event<T> create(Function<List<T>, T> function) {
        return new EventImpl<T>(function);
    }

    @SafeVarargs
    public static <T> Event<T> createLoop(T ... typeGetter) {
        if (typeGetter.length != 0) {
            throw new IllegalStateException("array must be empty!");
        }
        return EventFactory.createLoop(typeGetter.getClass().getComponentType());
    }

    private static <T, R> R invokeMethod(T listener, Method method, Object[] args) throws Throwable {
        return (R)MethodHandles.lookup().unreflect(method).bindTo(listener).invokeWithArguments(args);
    }

    public static <T> Event<T> createLoop(Class<T> clazz) {
        return EventFactory.create(listeners -> Proxy.newProxyInstance(EventFactory.class.getClassLoader(), new Class[]{clazz}, (InvocationHandler)new AbstractInvocationHandler((List)listeners){
            final /* synthetic */ List val$listeners;
            {
                this.val$listeners = list;
            }

            @Override
            protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
                for (Object listener : this.val$listeners) {
                    EventFactory.invokeMethod(listener, method, args);
                }
                return null;
            }
        }));
    }

    @SafeVarargs
    public static <T> Event<T> createEventResult(T ... typeGetter) {
        if (typeGetter.length != 0) {
            throw new IllegalStateException("array must be empty!");
        }
        return EventFactory.createEventResult(typeGetter.getClass().getComponentType());
    }

    public static <T> Event<T> createEventResult(Class<T> clazz) {
        return EventFactory.create(listeners -> Proxy.newProxyInstance(EventFactory.class.getClassLoader(), new Class[]{clazz}, (InvocationHandler)new AbstractInvocationHandler((List)listeners){
            final /* synthetic */ List val$listeners;
            {
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
        }));
    }

    @SafeVarargs
    public static <T> Event<T> createCompoundEventResult(T ... typeGetter) {
        if (typeGetter.length != 0) {
            throw new IllegalStateException("array must be empty!");
        }
        return EventFactory.createCompoundEventResult(typeGetter.getClass().getComponentType());
    }

    public static <T> Event<T> createCompoundEventResult(Class<T> clazz) {
        return EventFactory.create(listeners -> Proxy.newProxyInstance(EventFactory.class.getClassLoader(), new Class[]{clazz}, (InvocationHandler)new AbstractInvocationHandler((List)listeners){
            final /* synthetic */ List val$listeners;
            {
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
        }));
    }

    @SafeVarargs
    public static <T> Event<Consumer<T>> createConsumerLoop(T ... typeGetter) {
        if (typeGetter.length != 0) {
            throw new IllegalStateException("array must be empty!");
        }
        return EventFactory.createConsumerLoop(typeGetter.getClass().getComponentType());
    }

    public static <T> Event<Consumer<T>> createConsumerLoop(Class<T> clazz) {
        return EventFactory.create(listeners -> (Consumer)Proxy.newProxyInstance(EventFactory.class.getClassLoader(), new Class[]{Consumer.class}, (InvocationHandler)new AbstractInvocationHandler((List)listeners){
            final /* synthetic */ List val$listeners;
            {
                this.val$listeners = list;
            }

            @Override
            protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
                for (Consumer listener : this.val$listeners) {
                    EventFactory.invokeMethod(listener, method, args);
                }
                return null;
            }
        }));
    }

    @SafeVarargs
    public static <T> Event<EventActor<T>> createEventActorLoop(T ... typeGetter) {
        if (typeGetter.length != 0) {
            throw new IllegalStateException("array must be empty!");
        }
        return EventFactory.createEventActorLoop(typeGetter.getClass().getComponentType());
    }

    public static <T> Event<EventActor<T>> createEventActorLoop(Class<T> clazz) {
        return EventFactory.create(listeners -> (EventActor)Proxy.newProxyInstance(EventFactory.class.getClassLoader(), new Class[]{EventActor.class}, (InvocationHandler)new AbstractInvocationHandler((List)listeners){
            final /* synthetic */ List val$listeners;
            {
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
        }));
    }

    private static class EventImpl<T>
    implements Event<T> {
        private final Function<List<T>, T> function;
        private final ArrayList<T> listeners;
        private T invoker = null;

        EventImpl(Function<List<T>, T> function) {
            this.function = function;
            this.listeners = new ArrayList();
        }

        @Override
        public T invoker() {
            if (this.invoker == null) {
                this.update();
            }
            return this.invoker;
        }

        @Override
        public void register(T listener) {
            this.listeners.add(listener);
            this.invoker = null;
        }

        @Override
        public void unregister(T listener) {
            this.listeners.remove(listener);
            this.listeners.trimToSize();
            this.invoker = null;
        }

        @Override
        public boolean isRegistered(T listener) {
            return this.listeners.contains(listener);
        }

        @Override
        public void clear() {
            this.listeners.clear();
            this.listeners.trimToSize();
            this.invoker = null;
        }

        public void update() {
            this.invoker = this.listeners.size() == 1 ? this.listeners.get(0) : this.function.apply(this.listeners);
        }
    }

    private static abstract class AbstractInvocationHandler
    implements InvocationHandler {
        private static final Object[] NO_ARGS = new Object[0];

        private AbstractInvocationHandler() {
        }

        private static boolean isProxyOfSameInterfaces(Object arg, Class<?> proxyClass) {
            return proxyClass.isInstance(arg) || Proxy.isProxyClass(arg.getClass()) && Arrays.equals(arg.getClass().getInterfaces(), proxyClass.getInterfaces());
        }

        @Override
        @CheckForNull
        public final Object invoke(Object proxy, Method method, @CheckForNull @Nullable Object[] args) throws Throwable {
            if (args == null) {
                args = NO_ARGS;
            }
            if (args.length == 0 && method.getName().equals("hashCode")) {
                return this.hashCode();
            }
            if (args.length == 1 && method.getName().equals("equals") && method.getParameterTypes()[0] == Object.class) {
                Object arg = args[0];
                if (arg == null) {
                    return false;
                }
                if (proxy == arg) {
                    return true;
                }
                return AbstractInvocationHandler.isProxyOfSameInterfaces(arg, proxy.getClass()) && this.equals(Proxy.getInvocationHandler(arg));
            }
            if (args.length == 0 && method.getName().equals("toString")) {
                return this.toString();
            }
            return this.handleInvocation(proxy, method, args);
        }

        @CheckForNull
        protected abstract Object handleInvocation(Object var1, Method var2, @Nullable Object[] var3) throws Throwable;

        public boolean equals(@CheckForNull Object obj) {
            return super.equals(obj);
        }

        public int hashCode() {
            return super.hashCode();
        }

        public String toString() {
            return super.toString();
        }
    }
}
