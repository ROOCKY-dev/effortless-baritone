/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.conversion;

import dev.huskuraft.effortless.api.nightconfig.core.conversion.ObjectBinder;

private static final class ObjectBinder.BoundSearchResult {
    final ObjectBinder.BoundConfig parentConfig;
    final ObjectBinder.FieldInfos fieldInfos;
    final ObjectBinder.BoundConfig subConfig;

    ObjectBinder.BoundSearchResult(ObjectBinder.BoundConfig parentConfig, Object data) {
        this.parentConfig = parentConfig;
        if (data instanceof ObjectBinder.FieldInfos) {
            this.fieldInfos = (ObjectBinder.FieldInfos)data;
            this.subConfig = this.fieldInfos.boundConfig == null ? null : this.fieldInfos.getUpdatedConfig(parentConfig.object);
        } else {
            this.fieldInfos = null;
            this.subConfig = (ObjectBinder.BoundConfig)data;
        }
    }

    boolean hasFieldInfos() {
        return this.fieldInfos != null;
    }

    boolean hasSubConfig() {
        return this.subConfig != null;
    }
}
