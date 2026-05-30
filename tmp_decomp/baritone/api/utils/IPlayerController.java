/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
package baritone.api.utils;

import baritone.api.BaritoneAPI;
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

public interface IPlayerController {
    public void syncHeldItem();

    public boolean hasBrokenBlock();

    public boolean onPlayerDamageBlock(BlockPos var1, Direction var2);

    public void resetBlockRemoving();

    public void windowClick(int var1, int var2, int var3, ClickType var4, Player var5);

    public GameType getGameType();

    public InteractionResult processRightClickBlock(LocalPlayer var1, Level var2, InteractionHand var3, BlockHitResult var4);

    public InteractionResult processRightClick(LocalPlayer var1, Level var2, InteractionHand var3);

    public boolean clickBlock(BlockPos var1, Direction var2);

    public void setHittingBlock(boolean var1);

    default public double getBlockReachDistance() {
        if (this.getGameType().isCreative()) {
            return 5.0;
        }
        return ((Float)BaritoneAPI.getSettings().blockReachDistance.value).floatValue();
    }
}

