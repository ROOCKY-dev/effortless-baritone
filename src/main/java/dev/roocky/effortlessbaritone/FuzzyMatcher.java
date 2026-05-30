package dev.roocky.effortlessbaritone;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles block equivalence checking.
 * 
 * In a survival environment, breaking blocks can lead to state changes (e.g., Grass -> Dirt)
 * or property changes (e.g., Snowy Grass -> Regular Grass). This class uses Minecraft's
 * Tag system to group "good enough" equivalents, preventing Baritone from getting stuck
 * trying to place blocks that have naturally changed or don't strictly match the schematic's precise properties.
 */
public class FuzzyMatcher {
    
    /**
     * Tags that define groups of blocks that are "good enough" for each other.
     * This handles cases like Grass -> Dirt, Stone -> Cobblestone, etc.
     */
    private static final List<TagKey<Block>> FUZZY_TAGS = List.of(
        BlockTags.DIRT,
        BlockTags.SAND,
        BlockTags.STONE_ORE_REPLACEABLES,
        BlockTags.DEEPSLATE_ORE_REPLACEABLES,
        BlockTags.BASE_STONE_OVERWORLD,
        BlockTags.BASE_STONE_NETHER,
        BlockTags.SNOW,
        BlockTags.LEAVES,
        BlockTags.LOGS,
        BlockTags.PLANKS,
        BlockTags.WOOL,
        BlockTags.TERRACOTTA,
        BlockTags.CONCRETE_POWDER
    );

    public static boolean isFuzzyMatch(BlockState desired, BlockState current) {
        // 1. Exact match (ignore properties like snowy, facing, waterlogged)
        if (desired.getBlock() == current.getBlock()) {
            return true;
        }

        // 2. Air is never a fuzzy match for a block
        if (desired.isAir() || current.isAir()) {
            return false;
        }

        // 3. Check tags for equivalence (robust for modded blocks)
        for (TagKey<Block> tag : FUZZY_TAGS) {
            if (desired.is(tag) && current.is(tag)) {
                return true;
            }
        }

        return false;
    }

    public static List<Block> getSubstitutes(Block block) {
        List<Block> subs = new ArrayList<>();
        for (TagKey<Block> tag : FUZZY_TAGS) {
            if (BuiltInRegistries.BLOCK.wrapAsHolder(block).is(tag)) {
                BuiltInRegistries.BLOCK.getTag(tag).ifPresent(holders -> {
                    holders.forEach(holder -> {
                        Block b = holder.value();
                        if (b != block) {
                            subs.add(b);
                        }
                    });
                });
            }
        }
        return subs;
    }
}
