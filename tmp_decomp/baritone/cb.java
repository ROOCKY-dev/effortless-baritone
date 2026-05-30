/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.item.FallingBlockEntity
 *  net.minecraft.world.phys.AABB
 */
package baritone;

import baritone.a;
import baritone.api.IBaritone;
import baritone.api.pathing.movement.IMovement;
import baritone.api.pathing.movement.MovementStatus;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.VecUtils;
import baritone.api.utils.input.Input;
import baritone.ca;
import baritone.cc;
import baritone.cd;
import baritone.fb;
import baritone.i;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.phys.AABB;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class cb
implements IMovement,
cc {
    public static final Direction[] a = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.DOWN};
    public final IBaritone a;
    public final IPlayerContext a;
    private cd a;
    public final BetterBlockPos a;
    public final BetterBlockPos b;
    public final BetterBlockPos[] a;
    public final BetterBlockPos c;
    public Double a;
    public List<BlockPos> a;
    private List<BlockPos> c;
    public List<BlockPos> b;
    private Set<BetterBlockPos> a;
    public Boolean a;

    public cb(IBaritone iBaritone, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2, BetterBlockPos[] betterBlockPosArray, BetterBlockPos betterBlockPos3) {
        MovementStatus movementStatus = MovementStatus.PREPPING;
        cd cd2 = new cd();
        new cd().a = movementStatus;
        this.a = cd2;
        this.a = null;
        this.c = null;
        this.b = null;
        this.a = null;
        this.a = iBaritone;
        this.a = iBaritone.getPlayerContext();
        this.a = betterBlockPos;
        this.b = betterBlockPos2;
        this.a = betterBlockPosArray;
        this.c = betterBlockPos3;
    }

    public cb(IBaritone iBaritone, BetterBlockPos betterBlockPos, BetterBlockPos betterBlockPos2, BetterBlockPos[] betterBlockPosArray) {
        this(iBaritone, betterBlockPos, betterBlockPos2, betterBlockPosArray, null);
    }

    @Override
    public double getCost() {
        return this.a;
    }

    public abstract double a(ca var1);

    protected abstract Set<BetterBlockPos> a();

    public final Set<BetterBlockPos> b() {
        if (this.a == null) {
            this.a = this.a();
            Objects.requireNonNull(this.a);
        }
        return this.a;
    }

    protected final boolean a() {
        return this.b().contains((Object)this.a.playerFeet()) || this.b().contains((Object)((i)this.a.getPathingBehavior()).a());
    }

    @Override
    public MovementStatus update() {
        this.a.player().getAbilities().flying = false;
        this.a = this.a(this.a);
        if (cc.e(this.a, this.a.playerFeet()) && this.a.player().position().y < (double)this.b.y + 0.6) {
            this.a.a(Input.JUMP, true);
        }
        if (this.a.player().isInWall()) {
            this.a.getSelectedBlock().ifPresent(blockPos -> cc.a(this.a, fb.a(this.a, blockPos)));
            this.a.a(Input.CLICK_LEFT, true);
        }
        Optional.ofNullable(this.a.a.a).ifPresent(rotation -> this.a.getLookBehavior().updateTarget((Rotation)rotation, this.a.a.a));
        this.a.getInputOverrideHandler().clearAllKeys();
        this.a.a.forEach((input, bl2) -> this.a.getInputOverrideHandler().setInputForceState((Input)((Object)input), (boolean)bl2));
        this.a.a.clear();
        if (this.a.a.isComplete()) {
            this.a.getInputOverrideHandler().clearAllKeys();
        }
        return this.a.a;
    }

    public boolean a(cd cd2) {
        if (cd2.a == MovementStatus.WAITING) {
            return true;
        }
        Object object = this.a;
        int n2 = this.a.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            BetterBlockPos betterBlockPos = object[i2];
            if (!this.a.world().getEntitiesOfClass(FallingBlockEntity.class, new AABB(0.0, 0.0, 0.0, 1.0, 1.1, 1.0).move((BlockPos)betterBlockPos)).isEmpty() && ((Boolean)baritone.a.a().pauseMiningForFallingBlocks.value).booleanValue()) {
                return false;
            }
            if (cc.a(this.a, betterBlockPos)) continue;
            cc.a(this.a, fb.a(this.a, (BlockPos)betterBlockPos));
            object = RotationUtils.reachable(this.a, (BlockPos)betterBlockPos, this.a.playerController().getBlockReachDistance());
            if (((Optional)object).isPresent()) {
                object = (Rotation)((Optional)object).get();
                cd2.a(new cd.a((Rotation)object, true));
                if (this.a.isLookingAt(betterBlockPos) || this.a.playerRotations().isReallyCloseTo((Rotation)object)) {
                    cd2.a(Input.CLICK_LEFT, true);
                }
                return false;
            }
            cd2.a(new cd.a(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(betterBlockPos), this.a.playerRotations()), true));
            cd2.a(Input.CLICK_LEFT, true);
            return false;
        }
        return true;
    }

    @Override
    public boolean safeToCancel() {
        cb cb2 = this;
        return cb2.b(cb2.a);
    }

    protected boolean b(cd cd2) {
        return true;
    }

    @Override
    public BetterBlockPos getSrc() {
        return this.a;
    }

    @Override
    public BetterBlockPos getDest() {
        return this.b;
    }

    @Override
    public void reset() {
        MovementStatus movementStatus = MovementStatus.PREPPING;
        cd cd2 = new cd();
        new cd().a = movementStatus;
        this.a = cd2;
    }

    public cd a(cd cd2) {
        if (!this.a(cd2)) {
            MovementStatus movementStatus;
            v0.a = movementStatus = MovementStatus.PREPPING;
            return cd2;
        }
        if (cd2.a == MovementStatus.PREPPING) {
            cd2.a = MovementStatus.WAITING;
        }
        if (cd2.a == MovementStatus.WAITING) {
            cd2.a = MovementStatus.RUNNING;
        }
        return cd2;
    }

    @Override
    public BlockPos getDirection() {
        return this.getDest().subtract((Vec3i)this.getSrc());
    }

    @Override
    public boolean calculatedWhileLoaded() {
        return this.a;
    }

    @Override
    public void resetBlockCache() {
        this.a = null;
        this.c = null;
        this.b = null;
    }

    public List<BlockPos> a(fb fb2) {
        if (this.a != null) {
            return this.a;
        }
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        BetterBlockPos[] betterBlockPosArray = this.a;
        int n2 = this.a.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            BetterBlockPos betterBlockPos = betterBlockPosArray[i2];
            if (cc.a(fb2, betterBlockPos.x, betterBlockPos.y, betterBlockPos.z)) continue;
            arrayList.add(betterBlockPos);
        }
        this.a = arrayList;
        return arrayList;
    }

    public final List<BlockPos> b(fb fb2) {
        if (this.c != null) {
            return this.c;
        }
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        if (this.c != null && !cc.b(fb2, this.c.x, this.c.y, this.c.z)) {
            arrayList.add(this.c);
        }
        this.c = arrayList;
        return arrayList;
    }

    public List<BlockPos> c(fb fb2) {
        if (this.b == null) {
            this.b = new ArrayList();
        }
        return this.b;
    }
}

