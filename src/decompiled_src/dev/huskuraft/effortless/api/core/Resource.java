/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.ResourceMetadata;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import java.io.IOException;

public interface Resource
extends PlatformReference {
    public ResourceMetadata metadata() throws IOException;
}
