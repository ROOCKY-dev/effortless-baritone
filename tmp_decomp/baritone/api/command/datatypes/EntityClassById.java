/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 */
package baritone.api.command.datatypes;

import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public enum EntityClassById implements IDatatypeFor<EntityType>
{
    INSTANCE;


    @Override
    public final EntityType get(IDatatypeContext iDatatypeContext) {
        iDatatypeContext = ResourceLocation.parse((String)iDatatypeContext.getConsumer().getString());
        if ((iDatatypeContext = (EntityType)BuiltInRegistries.ENTITY_TYPE.getOptional((ResourceLocation)iDatatypeContext).orElse(null)) == null) {
            throw new IllegalArgumentException("no entity found by that id");
        }
        return iDatatypeContext;
    }

    @Override
    public final Stream<String> tabComplete(IDatatypeContext iDatatypeContext) {
        return new TabCompleteHelper().append(BuiltInRegistries.ENTITY_TYPE.stream().map(Object::toString)).filterPrefixNamespaced(iDatatypeContext.getConsumer().getString()).sortAlphabetically().stream();
    }
}

