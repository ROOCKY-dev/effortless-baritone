/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import javax.annotation.Nullable;

public record PlayerSkin(@Nullable ResourceLocation texture, @Nullable ResourceLocation capeTexture, @Nullable ResourceLocation elytraTexture, Model model) {

    public static enum Model {
        SLIM("slim"),
        WIDE("default");

        private final String id;

        private Model(String id) {
            this.id = id;
        }

        public static Model byName(@Nullable String name) {
            if (name == null) {
                return WIDE;
            }
            if (name.equals("slim")) {
                return SLIM;
            }
            return WIDE;
        }

        public String id() {
            return this.id;
        }
    }
}
