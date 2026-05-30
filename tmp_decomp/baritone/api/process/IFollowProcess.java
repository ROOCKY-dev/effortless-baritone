/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 */
package baritone.api.process;

import baritone.api.process.IBaritoneProcess;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface IFollowProcess
extends IBaritoneProcess {
    public void follow(Predicate<Entity> var1);

    public void pickup(Predicate<ItemStack> var1);

    public List<Entity> following();

    public Predicate<Entity> currentFilter();

    default public void cancel() {
        this.onLostControl();
    }
}

