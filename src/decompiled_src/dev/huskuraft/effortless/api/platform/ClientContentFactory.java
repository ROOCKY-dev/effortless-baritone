/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.core.ItemStack;
import dev.huskuraft.effortless.api.input.KeyBinding;
import dev.huskuraft.effortless.api.platform.ContentFactory;
import dev.huskuraft.effortless.api.platform.PlatformLoader;
import dev.huskuraft.effortless.api.platform.SearchBy;
import dev.huskuraft.effortless.api.platform.SearchTree;
import dev.huskuraft.effortless.api.text.Text;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface ClientContentFactory
extends ContentFactory {
    public static ClientContentFactory getInstance() {
        return PlatformLoader.getSingleton(new ClientContentFactory[0]);
    }

    public SearchTree<ItemStack> searchItemStack(SearchBy var1);

    @Deprecated
    public <T> SearchTree<T> search(List<T> var1, Function<T, Stream<Text>> var2);

    public KeyBinding newKeyBinding(String var1, String var2, int var3);
}
