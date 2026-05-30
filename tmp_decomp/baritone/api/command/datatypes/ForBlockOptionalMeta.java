/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.properties.Property
 */
package baritone.api.command.datatypes;

import baritone.api.command.datatypes.BlockById;
import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.utils.BlockOptionalMeta;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

public enum ForBlockOptionalMeta implements IDatatypeFor<BlockOptionalMeta>
{
    INSTANCE;

    private static Pattern PATTERN;

    @Override
    public final BlockOptionalMeta get(IDatatypeContext iDatatypeContext) {
        return new BlockOptionalMeta(iDatatypeContext.getConsumer().getString());
    }

    @Override
    public final Stream<String> tabComplete(IDatatypeContext object) {
        String string2 = object.getConsumer().peekString();
        if (!PATTERN.matcher(string2).matches()) {
            object.getConsumer().getString();
            return Stream.empty();
        }
        if (string2.endsWith("]")) {
            object.getConsumer().getString();
            return Stream.empty();
        }
        if (!string2.contains("[")) {
            return object.getConsumer().tabCompleteDatatype(BlockById.INSTANCE);
        }
        object.getConsumer().getString();
        Block block = ForBlockOptionalMeta.splitLast(string2, '[');
        object = block[0];
        Object object2 = block[1];
        block = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse((String)object)).orElse(null);
        if (block == null) {
            return Stream.empty();
        }
        Object object3 = ForBlockOptionalMeta.splitLast((String)object2, ',');
        object = object3[0];
        object2 = object3[1];
        if (!object2.contains("=")) {
            object3 = Stream.of(((String)object).split(",")).map(string -> string.split("=")[0]).collect(Collectors.toSet());
            object = string2.substring(0, string2.length() - object2.length());
            return new TabCompleteHelper().append(block.getStateDefinition().getProperties().stream().map(Property::getName)).filter(arg_0 -> ForBlockOptionalMeta.lambda$tabComplete$1((Set)object3, arg_0)).filterPrefix((String)object2).sortAlphabetically().map(arg_0 -> ForBlockOptionalMeta.lambda$tabComplete$2((String)object, arg_0)).stream();
        }
        object2 = ForBlockOptionalMeta.splitLast((String)object2, '=');
        object3 = object2[0];
        object = object2[1];
        object2 = string2.substring(0, string2.length() - ((String)object).length());
        string2 = block.getStateDefinition().getProperty((String)object3);
        if (string2 == null) {
            return Stream.empty();
        }
        return new TabCompleteHelper().append(ForBlockOptionalMeta.getValues(string2)).filterPrefix((String)object).sortAlphabetically().map(arg_0 -> ForBlockOptionalMeta.lambda$tabComplete$3((String)object2, arg_0)).stream();
    }

    private static String[] splitLast(String string, char c2) {
        int n2 = string.lastIndexOf(c2);
        c2 = (char)n2;
        if (n2 == -1) {
            return new String[]{"", string};
        }
        return new String[]{string.substring(0, c2), string.substring(c2 + '\u0001')};
    }

    private static <T extends Comparable<T>> Stream<String> getValues(Property<T> property) {
        return property.getPossibleValues().stream().map(arg_0 -> property.getName(arg_0));
    }

    private static /* synthetic */ String lambda$tabComplete$3(String string, String string2) {
        return string + string2;
    }

    private static /* synthetic */ String lambda$tabComplete$2(String string, String string2) {
        return string + string2;
    }

    private static /* synthetic */ boolean lambda$tabComplete$1(Set set, String string) {
        return !set.contains(string);
    }

    static {
        PATTERN = Pattern.compile("(?:[a-z0-9_.-]+:)?(?:[a-z0-9/_.-]+(?:\\[(?:(?:[a-z0-9_.-]+=[a-z0-9_.-]+,)*(?:[a-z0-9_.-]+(?:=(?:[a-z0-9_.-]+(?:\\])?)?)?)?|\\])?)?)?");
    }
}

