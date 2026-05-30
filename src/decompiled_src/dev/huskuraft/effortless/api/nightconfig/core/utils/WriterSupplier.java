/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

import java.io.IOException;
import java.io.Writer;

public interface WriterSupplier {
    public Writer get() throws IOException;
}
