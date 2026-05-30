/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.networking.NetByteBufReader;
import dev.huskuraft.effortless.api.networking.NetByteBufWriter;

public interface NetByteBufSerializer<T>
extends NetByteBufReader<T>,
NetByteBufWriter<T> {
}
