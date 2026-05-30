/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.ItemById;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class be
extends Command {
    public be(a a2) {
        super(a2, "pickup");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        object = new HashSet();
        while (iArgConsumer.hasAny()) {
            Item item = (Item)iArgConsumer.getDatatypeFor(ItemById.INSTANCE);
            object.add(item);
        }
        if (object.isEmpty()) {
            this.baritone.getFollowProcess().pickup(itemStack -> true);
            this.logDirect("Picking up all items");
            return;
        }
        this.baritone.getFollowProcess().pickup(arg_0 -> be.a((Set)object, arg_0));
        this.logDirect("Picking up these items:");
        object.stream().map(arg_0 -> ((DefaultedRegistry)BuiltInRegistries.ITEM).getKey(arg_0)).map(ResourceLocation::toString).forEach(this::logDirect);
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        while (iArgConsumer.has(2)) {
            if (iArgConsumer.peekDatatypeOrNull(ItemById.INSTANCE) == null) {
                return Stream.empty();
            }
            iArgConsumer.get();
        }
        return iArgConsumer.tabCompleteDatatype(ItemById.INSTANCE);
    }

    @Override
    public final String getShortDesc() {
        return "Pickup items";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Usage:", "> pickup - Pickup anything", "> pickup <item1> <item2> <...> - Pickup certain items");
    }

    private static /* synthetic */ boolean a(Set set, ItemStack itemStack) {
        return set.contains(itemStack.getItem());
    }
}

