/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.container;

import dev.huskuraft.effortless.api.gui.container.SimpleEntryList;
import dev.huskuraft.effortless.api.platform.Entrance;
import java.util.function.Consumer;

class SimpleEntryList.1
extends SimpleEntryList.Entry {
    final /* synthetic */ Consumer val$onCreate;

    SimpleEntryList.1(SimpleEntryList this$0, Entrance entrance, Consumer consumer) {
        this.val$onCreate = consumer;
        super(entrance);
    }

    @Override
    public void onCreate() {
        this.val$onCreate.accept(this);
    }
}
