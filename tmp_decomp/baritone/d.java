/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.PickaxeItem
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

import baritone.a;
import baritone.api.event.events.TickEvent;
import baritone.api.utils.Helper;
import baritone.c;
import baritone.dr;
import baritone.e;
import baritone.fm;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class d
extends c
implements Helper {
    private int a;
    private int[] a;

    public d(a a2) {
        super(a2);
    }

    @Override
    public final void onTick(TickEvent tickEvent) {
        if (!((Boolean)baritone.a.a().allowInventory.value).booleanValue()) {
            return;
        }
        if (tickEvent.getType() == TickEvent.Type.OUT) {
            return;
        }
        if (((c)this).a.player().containerMenu != ((c)this).a.player().inventoryMenu) {
            return;
        }
        ++this.a;
        if (this.a() >= 9) {
            d d2 = this;
            d2.a(d2.a(), 8);
        }
        Class<PickaxeItem> clazz = PickaxeItem.class;
        Block block = Blocks.STONE;
        tickEvent = ((c)this).a.player().getInventory().items;
        int n2 = -1;
        double d3 = -1.0;
        for (int i2 = 0; i2 < tickEvent.size(); ++i2) {
            double d4;
            ItemStack itemStack = (ItemStack)tickEvent.get(i2);
            if (itemStack.isEmpty() || ((Boolean)baritone.a.a().itemSaver.value).booleanValue() && itemStack.getDamageValue() + (Integer)baritone.a.a().itemSaverThreshold.value >= itemStack.getMaxDamage() && itemStack.getMaxDamage() > 1 || !clazz.isInstance(itemStack.getItem())) continue;
            double d5 = fm.a(itemStack, block.defaultBlockState());
            if (!(d4 > d3)) continue;
            d3 = d5;
            n2 = i2;
        }
        int n3 = n2;
        if (n3 >= 9) {
            this.a(n3, 0);
        }
        if (this.a != null) {
            d d6 = this;
            d6.logDebug("Remembering to move " + d6.a[0] + " " + this.a[1] + " from a previous tick");
            d d7 = this;
            d7.a(d7.a[0], this.a[1]);
        }
    }

    public final boolean a(int n2, Predicate<Integer> object) {
        int n3;
        Predicate<Integer> predicate = object;
        object = this;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (n3 = 1; n3 < 8; ++n3) {
            if (!((ItemStack)((c)object).a.player().getInventory().items.get(n3)).isEmpty() || predicate.test(n3)) continue;
            arrayList.add(n3);
        }
        if (arrayList.isEmpty()) {
            for (n3 = 1; n3 < 8; ++n3) {
                if (predicate.test(n3)) continue;
                arrayList.add(n3);
            }
        }
        return !((OptionalInt)(object = arrayList.isEmpty() ? OptionalInt.empty() : OptionalInt.of((Integer)arrayList.get(new Random().nextInt(arrayList.size()))))).isPresent() || this.a(n2, ((OptionalInt)object).getAsInt());
    }

    private boolean a(int n2, int n3) {
        this.a = new int[]{n2, n3};
        if (this.a < (Integer)baritone.a.a().ticksBetweenInventoryMoves.value) {
            d d2 = this;
            d2.logDebug("Inventory move requested but delaying " + d2.a + " " + String.valueOf(baritone.a.a().ticksBetweenInventoryMoves.value));
            return false;
        }
        if (((Boolean)baritone.a.a().inventoryMoveOnlyIfStationary.value).booleanValue() && !((c)this).a.a.a()) {
            this.logDebug("Inventory move requested but delaying until stationary");
            return false;
        }
        ((c)this).a.playerController().windowClick(((c)this).a.player().inventoryMenu.containerId, n2 < 9 ? n2 + 36 : n2, n3, ClickType.SWAP, (Player)((c)this).a.player());
        this.a = 0;
        this.a = null;
        return true;
    }

    private int a() {
        NonNullList nonNullList = ((c)this).a.player().getInventory().items;
        for (int i2 = 0; i2 < nonNullList.size(); ++i2) {
            if (!((List)baritone.a.a().acceptableThrowawayItems.value).contains(((ItemStack)nonNullList.get(i2)).getItem())) continue;
            return i2;
        }
        return -1;
    }

    public final boolean a() {
        for (Item item : (List)baritone.a.a().acceptableThrowawayItems.value) {
            if (!this.a(false, (? super ItemStack itemStack) -> item.equals(itemStack.getItem()))) continue;
            return true;
        }
        return false;
    }

    public final boolean a(boolean bl2, int n2, int n3, int n4) {
        BlockState blockState = ((c)this).a.a.a(n2, n3, n4);
        int n5 = n4;
        n4 = n3;
        n3 = n2;
        Object object = ((c)this).a.a;
        if ((!((dr)object).isActive() ? null : (!((dr)object).a.inSchematic(n3 - ((dr)object).a.getX(), n4 - ((dr)object).a.getY(), n5 - ((dr)object).a.getZ(), blockState) ? null : (object = (object = ((dr)object).a.desiredState(n3 - ((dr)object).a.getX(), n4 - ((dr)object).a.getY(), n5 - ((dr)object).a.getZ(), blockState, (List<BlockState>)((Object)((dr)object).a))).getBlock() instanceof AirBlock ? null : object))) != null && this.a(bl2, arg_0 -> this.b((BlockState)object, arg_0))) {
            return true;
        }
        if (object != null && this.a(bl2, arg_0 -> d.a((BlockState)object, arg_0))) {
            return true;
        }
        for (Item item : (List)baritone.a.a().acceptableThrowawayItems.value) {
            if (!this.a(bl2, (? super ItemStack itemStack) -> item.equals(itemStack.getItem()))) continue;
            return true;
        }
        return false;
    }

    public final boolean a(boolean bl2, Predicate<? super ItemStack> predicate) {
        return this.a(bl2, predicate, (Boolean)baritone.a.a().allowInventory.value);
    }

    private boolean a(boolean bl2, Predicate<? super ItemStack> predicate, boolean bl3) {
        ItemStack itemStack;
        int n2;
        LocalPlayer localPlayer = ((c)this).a.player();
        NonNullList nonNullList = localPlayer.getInventory().items;
        for (n2 = 0; n2 < 9; ++n2) {
            itemStack = (ItemStack)nonNullList.get(n2);
            if (!predicate.test((ItemStack)itemStack)) continue;
            if (bl2) {
                localPlayer.getInventory().selected = n2;
            }
            return true;
        }
        if (predicate.test((ItemStack)localPlayer.getInventory().offhand.get(0))) {
            for (n2 = 0; n2 < 9; ++n2) {
                itemStack = (ItemStack)nonNullList.get(n2);
                if (!itemStack.isEmpty() && !(itemStack.getItem() instanceof PickaxeItem)) continue;
                if (bl2) {
                    localPlayer.getInventory().selected = n2;
                }
                return true;
            }
        }
        if (bl3) {
            for (n2 = 9; n2 < 36; ++n2) {
                if (!predicate.test((ItemStack)nonNullList.get(n2))) continue;
                if (bl2) {
                    this.a(n2, 7);
                    localPlayer.getInventory().selected = 7;
                }
                return true;
            }
        }
        return false;
    }

    private static /* synthetic */ boolean a(BlockState blockState, ItemStack itemStack) {
        return itemStack.getItem() instanceof BlockItem && ((BlockItem)itemStack.getItem()).getBlock().equals(blockState.getBlock());
    }

    private /* synthetic */ boolean b(BlockState blockState, ItemStack itemStack) {
        return itemStack.getItem() instanceof BlockItem && blockState.equals(((BlockItem)itemStack.getItem()).getBlock().getStateForPlacement(new BlockPlaceContext((UseOnContext)new e(((c)this).a.world(), (Player)((c)this).a.player(), InteractionHand.MAIN_HAND, itemStack, new BlockHitResult(new Vec3(((c)this).a.player().position().x, ((c)this).a.player().position().y, ((c)this).a.player().position().z), Direction.UP, (BlockPos)((c)this).a.playerFeet(), false)))));
    }
}

