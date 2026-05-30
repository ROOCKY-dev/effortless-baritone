/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import java.util.function.Supplier;

public interface PlatformReference {
    public static <T> T unavailable() {
        return null;
    }

    public Object refs();

    default public <T> T reference() {
        return (T)this.refs();
    }

    default public boolean isAvailable() {
        return this.refs() != null;
    }

    default public <T extends PlatformReference> T ifUnavailable(Supplier<T> supplier) {
        return (T)(this.isAvailable() ? this : (PlatformReference)supplier.get());
    }

    public static class PlatformUnsupportedException
    extends UnsupportedOperationException {
        public PlatformUnsupportedException(String message) {
            super(message);
        }
    }
}
