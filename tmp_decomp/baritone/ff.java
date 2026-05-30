/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.KeyboardInput
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 */
package baritone;

import baritone.a;
import baritone.api.BaritoneAPI;
import baritone.api.event.events.TickEvent;
import baritone.api.utils.IInputOverrideHandler;
import baritone.api.utils.input.Input;
import baritone.c;
import baritone.ez;
import baritone.fa;
import baritone.fl;
import baritone.fs;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ff
extends c
implements IInputOverrideHandler {
    private final Map<Input, Boolean> a;
    public final ez a;
    private final fa a = new HashMap<Input, Boolean>();

    public ff(a a2) {
        super(a2);
        this.a = new ez(a2.getPlayerContext());
        this.a = new fa(a2.getPlayerContext());
    }

    @Override
    public final boolean isInputForcedDown(Input input) {
        if (input == null) {
            return false;
        }
        return this.a.getOrDefault((Object)input, Boolean.FALSE);
    }

    @Override
    public final void setInputForceState(Input input, boolean bl2) {
        this.a.put(input, bl2);
    }

    @Override
    public final void clearAllKeys() {
        this.a.clear();
    }

    @Override
    public final void onTick(TickEvent object) {
        boolean bl2;
        block20: {
            int n2;
            HitResult hitResult;
            if (((TickEvent)object).getType() == TickEvent.Type.OUT) {
                return;
            }
            if (this.isInputForcedDown(Input.CLICK_LEFT)) {
                this.setInputForceState(Input.CLICK_RIGHT, false);
            }
            int n3 = this.isInputForcedDown(Input.CLICK_LEFT);
            object = this.a;
            if (((ez)object).a > 0) {
                --((ez)object).a;
            } else {
                hitResult = ((ez)object).a.objectMouseOver();
                int n4 = n2 = hitResult != null && hitResult.getType() == HitResult.Type.BLOCK ? 1 : 0;
                if (n3 != 0 && n2 != 0) {
                    ((ez)object).a.playerController().setHittingBlock(((ez)object).a);
                    if (((ez)object).a.playerController().hasBrokenBlock()) {
                        ((ez)object).a.playerController().syncHeldItem();
                        ((ez)object).a.playerController().clickBlock(((BlockHitResult)hitResult).getBlockPos(), ((BlockHitResult)hitResult).getDirection());
                        ((ez)object).a.player().swing(InteractionHand.MAIN_HAND);
                    } else {
                        if (((ez)object).a.playerController().onPlayerDamageBlock(((BlockHitResult)hitResult).getBlockPos(), ((BlockHitResult)hitResult).getDirection())) {
                            ((ez)object).a.player().swing(InteractionHand.MAIN_HAND);
                        }
                        if (((ez)object).a.playerController().hasBrokenBlock()) {
                            ((ez)object).a = (Integer)BaritoneAPI.getSettings().blockBreakSpeed.value - 1;
                            ((fs)((ez)object).a.minecraft().gameMode).setDestroyDelay(0);
                        }
                    }
                    ((ez)object).a = !((ez)object).a.playerController().hasBrokenBlock();
                    ((ez)object).a.playerController().setHittingBlock(false);
                } else {
                    ((ez)object).a = false;
                }
            }
            n3 = this.isInputForcedDown(Input.CLICK_RIGHT);
            object = this.a;
            if (((fa)object).a > 0) {
                --((fa)object).a;
            } else {
                hitResult = ((fa)object).a.objectMouseOver();
                if (n3 != 0 && !((fa)object).a.player().isHandsBusy() && hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
                    ((fa)object).a = (Integer)baritone.a.a().rightClickSpeed.value - 1;
                    for (InteractionHand interactionHand : InteractionHand.values()) {
                        if (((fa)object).a.playerController().processRightClickBlock(((fa)object).a.player(), ((fa)object).a.world(), interactionHand, (BlockHitResult)hitResult) == InteractionResult.SUCCESS) {
                            ((fa)object).a.player().swing(interactionHand);
                            break;
                        }
                        if (!((fa)object).a.player().getItemInHand(interactionHand).isEmpty() && ((fa)object).a.playerController().processRightClick(((fa)object).a.player(), ((fa)object).a.world(), interactionHand) == InteractionResult.SUCCESS) break;
                    }
                }
            }
            object = this;
            Input[] inputArray = new Input[]{Input.MOVE_FORWARD, Input.MOVE_BACK, Input.MOVE_LEFT, Input.MOVE_RIGHT, Input.SNEAK, Input.JUMP};
            int cfr_ignored_0 = inputArray.length;
            for (n2 = 0; n2 < 6; ++n2) {
                Input input = inputArray[n2];
                if (!((ff)object).isInputForcedDown(input)) continue;
                bl2 = true;
                break block20;
            }
            bl2 = ((c)object).a.a.isPathing() || ((c)object).a != BaritoneAPI.getProvider().getPrimaryBaritone();
        }
        if (bl2) {
            if (((c)this).a.player().input.getClass() != fl.class) {
                ((c)this).a.player().input = new fl(this);
                return;
            }
        } else if (((c)this).a.player().input.getClass() == fl.class) {
            ((c)this).a.player().input = new KeyboardInput(((c)this).a.minecraft().options);
        }
    }
}

