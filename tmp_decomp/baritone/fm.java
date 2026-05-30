/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.Holder
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.TieredItem
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentEffectComponents
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.item.enchantment.ItemEnchantments
 *  net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.a;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class fm {
    public final Map<Block, Double> a;
    public final Function<Block, Double> a;
    private final LocalPlayer a = new HashMap<Block, Double>();

    public fm(LocalPlayer object) {
        this.a = object;
        if (((Boolean)baritone.a.a().considerPotionEffects.value).booleanValue()) {
            object = this;
            double d2 = 1.0;
            if (((fm)object).a.hasEffect(MobEffects.DIG_SPEED)) {
                d2 = 1.0 * (1.0 + (double)(((fm)object).a.getEffect(MobEffects.DIG_SPEED).getAmplifier() + 1) * 0.2);
            }
            if (((fm)object).a.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                switch (((fm)object).a.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) {
                    case 0: {
                        d2 *= 0.3;
                        break;
                    }
                    case 1: {
                        d2 *= 0.09;
                        break;
                    }
                    case 2: {
                        d2 *= 0.0027;
                        break;
                    }
                    default: {
                        d2 *= 8.1E-4;
                    }
                }
            }
            object = d3 -> d2 * d3;
            this.a = object.compose(this::a);
            return;
        }
        this.a = this::a;
    }

    private static int a(ItemStack itemStack) {
        if (itemStack.getItem() instanceof TieredItem) {
            return (int)((TieredItem)itemStack.getItem()).getTier().getAttackDamageBonus();
        }
        return -1;
    }

    private static boolean a(ItemStack itemStack) {
        itemStack = itemStack.getEnchantments();
        for (Holder holder : itemStack.keySet()) {
            if (!holder.is(Enchantments.SILK_TOUCH) || itemStack.getLevel(holder) <= 0) continue;
            return true;
        }
        return false;
    }

    public final int a(Block block, boolean bl2, boolean bl3) {
        int n2;
        if (!((Boolean)baritone.a.a().autoTool.value).booleanValue() && bl3) {
            return this.a.getInventory().selected;
        }
        boolean bl4 = false;
        double d2 = Double.NEGATIVE_INFINITY;
        int n3 = Integer.MIN_VALUE;
        boolean bl5 = false;
        block = block.defaultBlockState();
        for (int i2 = 0; i2 < 9; ++i2) {
            int n4;
            ItemStack itemStack = this.a.getInventory().getItem(i2);
            if (!((Boolean)baritone.a.a().useSwordToMine.value).booleanValue() && itemStack.getItem() instanceof SwordItem || ((Boolean)baritone.a.a().itemSaver.value).booleanValue() && itemStack.getDamageValue() + (Integer)baritone.a.a().itemSaverThreshold.value >= itemStack.getMaxDamage() && itemStack.getMaxDamage() > 1) continue;
            double d3 = fm.a(itemStack, (BlockState)block);
            boolean bl6 = fm.a(itemStack);
            if (d3 > d2) {
                d2 = d3;
                n2 = i2;
                n3 = fm.a(itemStack);
                bl5 = bl6;
                continue;
            }
            if (d3 != d2 || ((n4 = fm.a(itemStack)) >= n3 || !bl6 && bl5) && (!bl2 || bl5 || !bl6)) continue;
            d2 = d3;
            n2 = i2;
            n3 = n4;
            bl5 = bl6;
        }
        return n2;
    }

    private double a(Block block) {
        return fm.a(this.a.getInventory().getItem(this.a(block, false, true)), block.defaultBlockState()) * (((List)baritone.a.a().blocksToAvoidBreaking.value).contains(block) ? (Double)baritone.a.a().avoidBreakingMultiplier.value : 1.0);
    }

    public static double a(ItemStack itemStack, BlockState blockState) {
        float f2;
        float f3;
        try {
            f3 = blockState.getDestroySpeed(null, null);
        }
        catch (NullPointerException nullPointerException) {
            return -1.0;
        }
        if (f3 < 0.0f) {
            return -1.0;
        }
        float f4 = itemStack.getDestroySpeed(blockState);
        if (f2 > 1.0f) {
            ItemEnchantments itemEnchantments = itemStack.getEnchantments();
            block2: for (Holder holder : itemEnchantments.keySet()) {
                for (EnchantmentAttributeEffect enchantmentAttributeEffect : ((Enchantment)holder.value()).getEffects(EnchantmentEffectComponents.ATTRIBUTES)) {
                    if (!enchantmentAttributeEffect.attribute().is((ResourceKey)Attributes.MINING_EFFICIENCY.unwrapKey().get())) continue;
                    f4 += enchantmentAttributeEffect.amount().calculate(itemEnchantments.getLevel(holder));
                    break block2;
                }
            }
        }
        f4 /= f3;
        if (!blockState.requiresCorrectToolForDrops() || !itemStack.isEmpty() && itemStack.isCorrectToolForDrops(blockState)) {
            return f4 / 30.0f;
        }
        return f4 / 100.0f;
    }
}

