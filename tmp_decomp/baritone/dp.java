/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.EmptyLevelChunk
 */
package baritone;

import baritone.a;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.input.Input;
import baritone.cb;
import baritone.cc;
import baritone.cd;
import baritone.dl;
import baritone.ey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.EmptyLevelChunk;

public final class dp
extends ey {
    private HashMap<BlockPos, BlockState> a = new HashMap();

    public dp(a a2) {
        super(a2);
    }

    @Override
    public final boolean isActive() {
        if (((ey)this).a.player() == null || ((ey)this).a.world() == null) {
            return false;
        }
        if (!((Boolean)baritone.a.a().backfill.value).booleanValue()) {
            return false;
        }
        if (((Boolean)baritone.a.a().allowParkour.value).booleanValue()) {
            this.logDirect("Backfill cannot be used with allowParkour true");
            baritone.a.a().backfill.value = Boolean.FALSE;
            return false;
        }
        for (BlockPos blockPos : new ArrayList<BlockPos>(this.a.keySet())) {
            if (!(((ey)this).a.world().getChunk(blockPos) instanceof EmptyLevelChunk) && ((ey)this).a.world().getBlockState(blockPos).getBlock() == Blocks.AIR) continue;
            this.a.remove(blockPos);
        }
        dp dp2 = this;
        if (((ey)dp2).a.getSelectedBlock().isPresent() && ((ey)dp2).a.a.isPathing()) {
            dp2.a.put(((ey)dp2).a.getSelectedBlock().get(), ((ey)dp2).a.world().getBlockState(((ey)dp2).a.getSelectedBlock().get()));
        }
        ((ey)this).a.a.clearAllKeys();
        return !this.a().isEmpty();
    }

    @Override
    public final PathingCommand onTick(boolean bl2, boolean bl3) {
        if (!bl3) {
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        ((ey)this).a.a.clearAllKeys();
        block5: for (BlockPos blockPos : this.a()) {
            cd cd2 = new cd();
            switch (cc.a(cd2, ((ey)this).a, blockPos, false, false)) {
                case c: {
                    continue block5;
                }
                case a: {
                    ((ey)this).a.a.setInputForceState(Input.CLICK_RIGHT, true);
                    return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
                }
                case b: {
                    ((ey)this).a.a.updateTarget(Optional.ofNullable(cd2.a.a).get(), true);
                    return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
                }
            }
            throw new IllegalStateException();
        }
        return new PathingCommand(null, PathingCommandType.DEFER);
    }

    private List<BlockPos> a() {
        return this.a.keySet().stream().filter(blockPos -> ((ey)this).a.world().getBlockState(blockPos).getBlock() == Blocks.AIR).filter(blockPos -> ((ey)this).a.a.a((BlockPos)blockPos, Blocks.DIRT.defaultBlockState())).filter(object -> {
            BlockPos blockPos = object;
            object = ((ey)this).a.a.a;
            return !(object == null || ((dl)object).b() || ((dl)object).a ? false : Arrays.asList(((cb)((dl)object).getPath().movements().get((int)((dl)object).getPosition())).a).contains(blockPos));
        }).sorted(Comparator.comparingDouble(arg_0 -> ((BetterBlockPos)((ey)this).a.playerFeet()).distSqr(arg_0)).reversed()).collect(Collectors.toList());
    }

    @Override
    public final void onLostControl() {
        if (this.a != null && !this.a.isEmpty()) {
            this.a.clear();
        }
    }

    @Override
    public final String displayName0() {
        return "Backfill";
    }

    @Override
    public final boolean isTemporary() {
        return true;
    }

    @Override
    public final double priority() {
        return 5.0;
    }
}

