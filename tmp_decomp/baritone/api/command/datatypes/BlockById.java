/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.Block
 */
package baritone.api.command.datatypes;

import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public enum BlockById implements IDatatypeFor<Block>
{
    INSTANCE;


    @Override
    public final Block get(IDatatypeContext iDatatypeContext) {
        iDatatypeContext = ResourceLocation.parse((String)iDatatypeContext.getConsumer().getString());
        if ((iDatatypeContext = (Block)BuiltInRegistries.BLOCK.getOptional((ResourceLocation)iDatatypeContext).orElse(null)) == null) {
            throw new IllegalArgumentException("no block found by that id");
        }
        return iDatatypeContext;
    }

    @Override
    public final Stream<String> tabComplete(IDatatypeContext object) {
        object = object.getConsumer().getString();
        return new TabCompleteHelper().append(BuiltInRegistries.BLOCK.keySet().stream().map(Object::toString)).filterPrefixNamespaced((String)object).sortAlphabetically().stream();
    }
}

