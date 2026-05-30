/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.pathing.goals.GoalTwoBlocks;
import baritone.api.process.IGetToBlockProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.input.Input;
import baritone.ca;
import baritone.cc;
import baritone.ei;
import baritone.ek;
import baritone.ey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class eh
extends ey
implements IGetToBlockProcess {
    private BlockOptionalMeta a;
    private List<BlockPos> a;
    private List<BlockPos> b;
    private BetterBlockPos a;
    private int a = 0;
    private int b = 0;

    public eh(baritone.a a2) {
        super(a2);
    }

    @Override
    public final void getToBlock(BlockOptionalMeta blockOptionalMeta) {
        this.onLostControl();
        this.a = blockOptionalMeta;
        this.a = ((ey)this).a.playerFeet();
        this.b = new ArrayList<BlockPos>();
        this.b = 0;
        this.a(new ArrayList<BlockPos>(), new a(this, false));
    }

    @Override
    public final boolean isActive() {
        return this.a != null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public final synchronized PathingCommand onTick(boolean bl2, boolean bl3) {
        Object object;
        void var2_5;
        if (this.a == null) {
            this.a(new ArrayList<BlockPos>(), new a(this, false));
        }
        if (this.a.isEmpty()) {
            if (((Boolean)baritone.a.a().exploreForBlocks.value).booleanValue() && !bl2) {
                return new PathingCommand(new ei(this.a), PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
            }
            eh eh2 = this;
            eh2.logDirect("No known locations of " + String.valueOf(eh2.a) + ", canceling GetToBlock");
            if (var2_5 != false) {
                this.onLostControl();
            }
            return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
        }
        GoalComposite goalComposite = new GoalComposite((Goal[])this.a.stream().map(this::a).toArray(Goal[]::new));
        if (bl2) {
            if (((Boolean)baritone.a.a().blacklistClosestOnFailure.value).booleanValue()) {
                eh eh3 = this;
                eh3.logDirect("Unable to find any path to " + String.valueOf(eh3.a) + ", blacklisting presumably unreachable closest instances...");
                this.blacklistClosest();
                return this.onTick(false, (boolean)var2_5);
            }
            eh eh4 = this;
            eh4.logDirect("Unable to find any path to " + String.valueOf(eh4.a) + ", canceling GetToBlock");
            if (var2_5 != false) {
                this.onLostControl();
            }
            return new PathingCommand(goalComposite, PathingCommandType.CANCEL_AND_SET_GOAL);
        }
        int n2 = (Integer)baritone.a.a().mineGoalUpdateInterval.value;
        if (n2 != 0 && this.a++ % n2 == 0) {
            ArrayList arrayList = new ArrayList(this.a);
            object = new a(this, true);
            baritone.a.a().execute(() -> this.b(arrayList, (ca)object));
        }
        if (goalComposite.isInGoal(((ey)this).a.playerFeet()) && goalComposite.isInGoal(((ey)this).a.a.a()) && var2_5 != false) {
            boolean bl4;
            block16: {
                if (!eh.a(this.a.getBlock())) {
                    this.onLostControl();
                    return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
                eh eh5 = this;
                Iterator iterator = eh5.a.iterator();
                while (iterator.hasNext()) {
                    object = (BlockPos)iterator.next();
                    if (!((Optional)(object = RotationUtils.reachable(((ey)eh5).a, (BlockPos)object, ((ey)eh5).a.playerController().getBlockReachDistance()))).isPresent()) continue;
                    ((ey)eh5).a.a.updateTarget((Rotation)((Optional)object).get(), true);
                    if (eh5.a.contains(((ey)eh5).a.getSelectedBlock().orElse(null))) {
                        ((ey)eh5).a.a.setInputForceState(Input.CLICK_RIGHT, true);
                        System.out.println(((ey)eh5).a.player().containerMenu);
                        if (!(((ey)eh5).a.player().containerMenu instanceof InventoryMenu)) {
                            bl4 = true;
                            break block16;
                        }
                    }
                    if (eh5.b++ > 20) {
                        eh5.logDirect("Right click timed out");
                        bl4 = true;
                        break block16;
                    } else {
                        bl4 = false;
                    }
                    break block16;
                }
                eh5.logDirect("Arrived but failed to right click open");
                bl4 = true;
            }
            if (bl4) {
                this.onLostControl();
                return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
            }
        }
        return new PathingCommand(goalComposite, PathingCommandType.REVALIDATE_GOAL_AND_PATH);
    }

    @Override
    public final synchronized boolean blacklistClosest() {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        this.a.stream().min(Comparator.comparingDouble(arg_0 -> ((BetterBlockPos)((ey)this).a.playerFeet()).distSqr(arg_0))).ifPresent(arrayList::add);
        block0: while (true) {
            Iterator iterator = this.a.iterator();
            while (iterator.hasNext()) {
                BlockPos blockPos = (BlockPos)iterator.next();
                Iterator iterator2 = arrayList.iterator();
                while (iterator2.hasNext()) {
                    int n2;
                    int n3;
                    BlockPos blockPos2;
                    BlockPos blockPos3 = blockPos2 = (BlockPos)iterator2.next();
                    blockPos2 = blockPos;
                    int n4 = Math.abs(blockPos2.getX() - blockPos3.getX());
                    if (!(n4 + (n3 = Math.abs(blockPos2.getY() - blockPos3.getY())) + (n2 = Math.abs(blockPos2.getZ() - blockPos3.getZ())) == 1)) continue;
                    arrayList.add(blockPos);
                    this.a.remove(blockPos);
                    continue block0;
                }
            }
            break;
        }
        arrayList.size();
        this.logDebug("Blacklisting unreachable locations " + String.valueOf(arrayList));
        this.b.addAll(arrayList);
        return !arrayList.isEmpty();
    }

    @Override
    public final synchronized void onLostControl() {
        this.a = null;
        this.a = null;
        this.a = null;
        this.b = null;
        ((ey)this).a.a.clearAllKeys();
    }

    @Override
    public final String displayName0() {
        if (this.a.isEmpty()) {
            return "Exploring randomly to find " + String.valueOf(this.a) + ", no known locations";
        }
        return "Get To " + String.valueOf(this.a) + ", " + this.a.size() + " known locations";
    }

    private synchronized void a(List<BlockPos> list, ca ca2) {
        list = ek.a(ca2, new BlockOptionalMetaLookup(this.a), 64, list, this.b, Collections.emptyList());
        list.removeIf(this.b::contains);
        this.a = list;
    }

    private Goal a(BlockPos blockPos) {
        Block block = this.a.getBlock();
        if ((Boolean)baritone.a.a().enterPortal.value != false && block == Blocks.NETHER_PORTAL) {
            return new GoalTwoBlocks(blockPos);
        }
        block = this.a.getBlock();
        if (eh.a(block) && (block == Blocks.ENDER_CHEST || block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST) && cc.h(((ey)this).a.a.a(blockPos.above()))) {
            return new GoalBlock(blockPos.above());
        }
        return new GoalGetToBlock(blockPos);
    }

    private static boolean a(Block block) {
        if (!((Boolean)baritone.a.a().rightClickContainerOnArrival.value).booleanValue()) {
            return false;
        }
        return block == Blocks.CRAFTING_TABLE || block == Blocks.FURNACE || block == Blocks.BLAST_FURNACE || block == Blocks.ENDER_CHEST || block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST;
    }

    private /* synthetic */ void b(List list, ca ca2) {
        this.a(list, ca2);
    }

    public final class a
    extends ca {
        public a(eh eh2, boolean bl2) {
            super(((ey)eh2).a, bl2);
        }

        @Override
        public final double b(int n2, int n3, int n4, BlockState blockState) {
            return 1.0;
        }
    }
}

