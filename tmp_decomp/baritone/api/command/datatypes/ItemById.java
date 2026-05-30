/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 */
package baritone.api.command.datatypes;

import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public enum ItemById implements IDatatypeFor<Item>
{
    INSTANCE;


    @Override
    public final Item get(IDatatypeContext iDatatypeContext) {
        iDatatypeContext = ResourceLocation.parse((String)iDatatypeContext.getConsumer().getString());
        if ((iDatatypeContext = (Item)BuiltInRegistries.ITEM.getOptional((ResourceLocation)iDatatypeContext).orElse(null)) == null) {
            throw new IllegalArgumentException("No item found by that id");
        }
        return iDatatypeContext;
    }

    @Override
    public final Stream<String> tabComplete(IDatatypeContext iDatatypeContext) {
        return new TabCompleteHelper().append(BuiltInRegistries.BLOCK.keySet().stream().map(ResourceLocation::toString)).filterPrefixNamespaced(iDatatypeContext.getConsumer().getString()).sortAlphabetically().stream();
    }
}

