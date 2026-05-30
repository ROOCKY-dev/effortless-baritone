/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import java.util.Map;

@FunctionalInterface
private static interface ParsingMode.MapPutAction {
    public Object put(Map<String, Object> var1, String var2, Object var3);
}
