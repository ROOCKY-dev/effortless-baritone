/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.core.Item;
import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.core.Items;
import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.core.StatType;
import dev.huskuraft.effortless.api.core.StatTypes;
import dev.huskuraft.effortless.api.core.fluid.Fluid;
import dev.huskuraft.effortless.api.core.fluid.Fluids;
import dev.huskuraft.effortless.api.platform.OperatingSystem;
import dev.huskuraft.effortless.api.platform.PlatformLoader;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.sound.Sound;
import dev.huskuraft.effortless.api.sound.Sounds;
import dev.huskuraft.effortless.api.tag.InputStreamTagReader;
import dev.huskuraft.effortless.api.tag.OutputStreamTagWriter;
import dev.huskuraft.effortless.api.text.Text;
import java.util.Locale;
import java.util.Optional;

public interface ContentFactory {
    public static ContentFactory getInstance() {
        return PlatformLoader.getSingleton(new ContentFactory[0]);
    }

    public ResourceLocation newResourceLocation(String var1, String var2);

    public Optional<Item> newOptionalItem(ResourceLocation var1);

    default public Item newItem(ResourceLocation location) {
        return this.newOptionalItem(location).orElseThrow();
    }

    public ItemStack newItemStack();

    public ItemStack newItemStack(Item var1, int var2);

    public Text newText();

    public Text newText(String var1);

    public Text newTranslatableText(String var1);

    public Text newTranslatableText(String var1, Object ... var2);

    public InputStreamTagReader getInputStreamTagReader();

    public OutputStreamTagWriter getOutputStreamTagWriter();

    public OperatingSystem getOperatingSystem();

    public Sound getSound(Sounds var1);

    default public Optional<Item> getOptionalItem(Items items) {
        return this.newOptionalItem(ResourceLocation.of("minecraft", items.name().toLowerCase(Locale.ROOT)));
    }

    default public Item getItem(Items items) {
        return this.getOptionalItem(items).orElseThrow();
    }

    public Fluid getFluid(Fluids var1);

    public <T extends PlatformReference> StatType<T> getStatType(StatTypes var1);
}
