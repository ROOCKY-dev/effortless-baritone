/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

public interface Mod {
    public static Mod create(final String id, final String version, final String description, final String name) {
        return new Mod(){

            @Override
            public String getId() {
                return id;
            }

            @Override
            public String getVersionStr() {
                return version;
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    public String getId();

    public String getVersionStr();

    public String getDescription();

    public String getName();
}
