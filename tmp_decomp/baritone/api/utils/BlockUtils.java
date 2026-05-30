/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.Block
 */
package baritone.api.utils;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class BlockUtils {
    private static transient Map<String, Block> resourceCache = new HashMap<String, Block>();

    public static String blockToString(Block block) {
        block = BuiltInRegistries.BLOCK.getKey((Object)block);
        String string = block.getPath();
        if (!block.getNamespace().equals("minecraft")) {
            string = block.toString();
        }
        return string;
    }

    public static Block stringToBlockRequired(String string) {
        Block block = BlockUtils.stringToBlockNullable(string);
        if (block == null) {
            throw new IllegalArgumentException(String.format("Invalid block name %s", string));
        }
        return block;
    }

    public static Block stringToBlockNullable(String string) {
        Block block = resourceCache.get(string);
        if (block != null) {
            return block;
        }
        if (resourceCache.containsKey(string)) {
            return null;
        }
        block = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.tryParse((String)(string.contains(":") ? string : "minecraft:" + string))).orElse(null);
        HashMap<String, Block> hashMap = new HashMap<String, Block>(resourceCache);
        hashMap.put(string, block);
        resourceCache = hashMap;
        return block;
    }

    private BlockUtils() {
    }
}

