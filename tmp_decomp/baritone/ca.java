/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.Holder
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentEffectComponents
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.item.enchantment.ItemEnchantments
 *  net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.a;
import baritone.api.IBaritone;
import baritone.api.utils.BetterBlockPos;
import baritone.dn;
import baritone.fb;
import baritone.fm;
import baritone.fu;
import baritone.t;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ca {
    private static final ItemStack a = new ItemStack((ItemLike)Items.WATER_BUCKET);
    public final boolean a;
    public final IBaritone a;
    public final Level a;
    public final t a;
    public final fb a;
    public final fm a;
    public final boolean b;
    public final boolean c;
    public final boolean d;
    public final double a;
    public final boolean e;
    public final List<Block> a;
    public final boolean f;
    public final boolean g;
    public final boolean h;
    public final boolean i;
    public final boolean j;
    public boolean k;
    public final int a;
    public final boolean l;
    public final boolean m;
    public final boolean n;
    public int b;
    public int c;
    public final int d;
    public final double b;
    public final double c;
    public double d;
    public double e;
    public final double f;
    public final fu a;
    public final dn a = new dn();

    public ca(IBaritone iBaritone) {
        this(iBaritone, false);
    }

    public ca(IBaritone iBaritone, boolean bl2) {
        int n2;
        this.a = bl2;
        this.a = iBaritone;
        EquipmentSlot[] equipmentSlotArray = iBaritone.getPlayerContext().player();
        this.a = iBaritone.getPlayerContext().world();
        this.a = (t)iBaritone.getPlayerContext().worldData();
        this.a = new fb(iBaritone.getPlayerContext(), bl2);
        this.a = new fm((LocalPlayer)equipmentSlotArray);
        this.c = (Boolean)baritone.a.a().allowPlace.value != false && ((a)iBaritone).a.a();
        this.b = (Boolean)baritone.a.a().allowWaterBucketFall.value != false && Inventory.isHotbarSlot((int)equipmentSlotArray.getInventory().findSlotMatchingItem(a)) && this.a.dimension() != Level.NETHER;
        this.d = (Boolean)baritone.a.a().allowSprint.value != false && equipmentSlotArray.getFoodData().getFoodLevel() > 6;
        this.a = (Double)baritone.a.a().blockPlacementPenalty.value;
        this.e = (Boolean)baritone.a.a().allowBreak.value;
        this.a = new ArrayList((Collection)baritone.a.a().allowBreakAnyway.value);
        this.f = (Boolean)baritone.a.a().allowParkour.value;
        this.g = (Boolean)baritone.a.a().allowParkourPlace.value;
        this.h = (Boolean)baritone.a.a().allowJumpAtBuildLimit.value;
        this.i = (Boolean)baritone.a.a().allowParkourAscend.value;
        this.j = (Boolean)baritone.a.a().assumeWalkOnWater.value;
        this.k = false;
        boolean holder = false;
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemEnchantments itemEnchantments = iBaritone.getPlayerContext().player().getItemBySlot(equipmentSlot).getEnchantments();
            for (Object object : itemEnchantments.keySet()) {
                if (!object.is(Enchantments.FROST_WALKER)) continue;
                n2 = itemEnchantments.getLevel((Holder)object);
            }
        }
        this.a = n2;
        this.l = (Boolean)baritone.a.a().allowDiagonalDescend.value;
        this.m = (Boolean)baritone.a.a().allowDiagonalAscend.value;
        this.n = (Boolean)baritone.a.a().allowDownward.value;
        this.b = 3;
        this.c = (Integer)baritone.a.a().maxFallHeightNoWater.value;
        this.d = (Integer)baritone.a.a().maxFallHeightBucket.value;
        float f2 = 1.0f;
        block2: for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemEnchantments itemEnchantments = iBaritone.getPlayerContext().player().getItemBySlot(equipmentSlot).getEnchantments();
            for (Holder holder2 : itemEnchantments.keySet()) {
                for (EnchantmentAttributeEffect enchantmentAttributeEffect : ((Enchantment)holder2.value()).getEffects(EnchantmentEffectComponents.ATTRIBUTES)) {
                    if (!enchantmentAttributeEffect.attribute().is((ResourceKey)Attributes.WATER_MOVEMENT_EFFICIENCY.unwrapKey().get())) continue;
                    f2 = enchantmentAttributeEffect.amount().calculate(itemEnchantments.getLevel(holder2));
                    break block2;
                }
            }
        }
        this.b = 9.09090909090909 * (double)(1.0f - f2) + 4.63284688441047 * (double)f2;
        this.c = (Double)baritone.a.a().blockBreakAdditionalPenalty.value;
        this.d = (Double)baritone.a.a().backtrackCostFavoringCoefficient.value;
        this.e = (Double)baritone.a.a().jumpPenalty.value;
        this.f = (Double)baritone.a.a().walkOnWaterOnePenalty.value;
        this.a = new fu(this.a.getWorldBorder());
    }

    public final BlockState a(int n2, int n3, int n4) {
        return this.a.a(n2, n3, n4);
    }

    public final BlockState a(BetterBlockPos betterBlockPos) {
        return this.a(betterBlockPos.getX(), betterBlockPos.getY(), betterBlockPos.getZ());
    }

    public final Block a(int n2, int n3, int n4) {
        return this.a(n2, n3, n4).getBlock();
    }

    public double a(int n2, int n3, int n4, BlockState blockState) {
        if (!this.c) {
            return 1000000.0;
        }
        if (!this.a.b(n2, n4)) {
            return 1000000.0;
        }
        if (!((Boolean)baritone.a.a().allowPlaceInFluidsSource.value).booleanValue() && blockState.getFluidState().isSource()) {
            return 1000000.0;
        }
        if (!(((Boolean)baritone.a.a().allowPlaceInFluidsFlow.value).booleanValue() || blockState.getFluidState().isEmpty() || blockState.getFluidState().isSource())) {
            return 1000000.0;
        }
        return this.a;
    }

    public double b(int n2, int n3, int n4, BlockState blockState) {
        if (!this.e && !this.a.contains(blockState.getBlock())) {
            return 1000000.0;
        }
        return 1.0;
    }

    public double a() {
        return this.a;
    }
}

