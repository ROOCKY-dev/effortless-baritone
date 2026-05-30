/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.BlockHitResult
 */
package baritone;

import baritone.api.utils.IPlayerController;
import baritone.fs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public final class ga
implements IPlayerController {
    private final Minecraft a;

    public ga(Minecraft minecraft) {
        this.a = minecraft;
    }

    @Override
    public final void syncHeldItem() {
        ((fs)this.a.gameMode).callSyncCurrentPlayItem();
    }

    @Override
    public final boolean hasBrokenBlock() {
        return !((fs)this.a.gameMode).isHittingBlock();
    }

    @Override
    public final boolean onPlayerDamageBlock(BlockPos blockPos, Direction direction) {
        return this.a.gameMode.continueDestroyBlock(blockPos, direction);
    }

    @Override
    public final void resetBlockRemoving() {
        this.a.gameMode.stopDestroyBlock();
    }

    @Override
    public final void windowClick(int n2, int n3, int n4, ClickType clickType, Player player) {
        this.a.gameMode.handleInventoryMouseClick(n2, n3, n4, clickType, player);
    }

    @Override
    public final GameType getGameType() {
        return this.a.gameMode.getPlayerMode();
    }

    @Override
    public final InteractionResult processRightClickBlock(LocalPlayer localPlayer, Level level, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        return this.a.gameMode.useItemOn(localPlayer, interactionHand, blockHitResult);
    }

    @Override
    public final InteractionResult processRightClick(LocalPlayer localPlayer, Level level, InteractionHand interactionHand) {
        return this.a.gameMode.useItem((Player)localPlayer, interactionHand);
    }

    @Override
    public final boolean clickBlock(BlockPos blockPos, Direction direction) {
        return this.a.gameMode.startDestroyBlock(blockPos, direction);
    }

    @Override
    public final void setHittingBlock(boolean bl2) {
        ((fs)this.a.gameMode).setIsHittingBlock(bl2);
    }
}

