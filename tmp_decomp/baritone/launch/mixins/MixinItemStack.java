/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package baritone.launch.mixins;

import baritone.api.utils.accessor.IItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ItemStack.class})
public abstract class MixinItemStack
implements IItemStack {
    @Shadow
    @Final
    private Item item;
    @Unique
    private int baritoneHash;

    @Shadow
    public abstract int getDamageValue();

    private void recalculateHash() {
        this.baritoneHash = this.item == null ? -1 : this.item.hashCode() + this.getDamageValue();
    }

    @Inject(method={"<init>*"}, at={@At(value="RETURN")})
    private void onInit(CallbackInfo callbackInfo) {
        this.recalculateHash();
    }

    @Inject(method={"setDamageValue"}, at={@At(value="TAIL")})
    private void onItemDamageSet(CallbackInfo callbackInfo) {
        this.recalculateHash();
    }

    @Override
    public int getBaritoneHash() {
        if (this.baritoneHash == 0) {
            this.recalculateHash();
        }
        return this.baritoneHash;
    }
}

