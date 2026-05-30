/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BitStorage
 *  net.minecraft.world.level.chunk.Palette
 *  net.minecraft.world.level.chunk.PalettedContainer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 */
package baritone.launch.mixins;

import baritone.fr;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import net.minecraft.util.BitStorage;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PalettedContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value={PalettedContainer.class})
public abstract class MixinPalettedContainer<T>
implements fr<T> {
    private static final MethodHandle DATA_GETTER;

    @Override
    public Palette<T> getPalette() {
        return this.data().getPalette();
    }

    @Override
    public BitStorage getStorage() {
        return this.data().getStorage();
    }

    @Unique
    private fr.a<T> data() {
        try {
            return (fr.a)DATA_GETTER.invoke((PalettedContainer)this);
        }
        catch (Throwable throwable) {
            throw MixinPalettedContainer.sneaky(throwable, RuntimeException.class);
        }
    }

    @Unique
    private static <T extends Throwable> T sneaky(Throwable throwable, Class<T> clazz) {
        throw throwable;
    }

    static {
        MethodHandle methodHandle;
        Field field = null;
        for (Field field2 : PalettedContainer.class.getDeclaredFields()) {
            Class<?> clazz = field2.getType();
            if (!fr.a.class.isAssignableFrom(clazz) || (field2.getModifiers() & 0x18) != 0 || field2.isSynthetic()) continue;
            if (field != null) {
                throw new IllegalStateException("PalettedContainer has more than one Data field.");
            }
            field = field2;
        }
        if (field == null) {
            throw new IllegalStateException("PalettedContainer has no Data field.");
        }
        try {
            methodHandle = MethodHandles.lookup().unreflectGetter(field);
        }
        catch (IllegalAccessException methodType) {
            throw new IllegalStateException("PalettedContainer may not access its own field?!", methodType);
        }
        MethodType methodType = MethodType.methodType(fr.a.class, PalettedContainer.class);
        DATA_GETTER = MethodHandles.explicitCastArguments(methodHandle, methodType);
    }
}

