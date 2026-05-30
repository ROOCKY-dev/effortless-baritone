/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BitStorage
 *  net.minecraft.world.level.chunk.Palette
 */
package baritone;

import net.minecraft.util.BitStorage;
import net.minecraft.world.level.chunk.Palette;

public interface fr<T> {
    public Palette<T> getPalette();

    public BitStorage getStorage();

    public static interface a<T> {
        public Palette<T> getPalette();

        public BitStorage getStorage();
    }
}

