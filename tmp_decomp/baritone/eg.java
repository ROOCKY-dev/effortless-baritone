/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 */
package baritone;

import baritone.a;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalNear;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.process.IFollowProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.BetterBlockPos;
import baritone.ey;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class eg
extends ey
implements IFollowProcess {
    private Predicate<Entity> a;
    private List<Entity> a;
    private boolean a;

    public eg(a a2) {
        super(a2);
    }

    @Override
    public final PathingCommand onTick(boolean bl2, boolean bl3) {
        this.a();
        GoalComposite goalComposite = new GoalComposite((Goal[])this.a.stream().map(this::a).toArray(Goal[]::new));
        return new PathingCommand(goalComposite, PathingCommandType.REVALIDATE_GOAL_AND_PATH);
    }

    private Goal a(Entity object) {
        if ((Double)baritone.a.a().followOffsetDistance.value == 0.0 || this.a) {
            object = object.blockPosition();
        } else {
            GoalXZ goalXZ = GoalXZ.fromDirection(object.position(), ((Float)baritone.a.a().followOffsetDirection.value).floatValue(), (Double)baritone.a.a().followOffsetDistance.value);
            object = new BetterBlockPos((double)goalXZ.getX(), object.position().y, (double)goalXZ.getZ());
        }
        if (this.a) {
            return new GoalBlock((BlockPos)object);
        }
        return new GoalNear((BlockPos)object, (Integer)baritone.a.a().followRadius.value);
    }

    private boolean a(Entity entity) {
        if (entity == null) {
            return false;
        }
        if (!entity.isAlive()) {
            return false;
        }
        if (entity.equals((Object)((ey)this).a.player())) {
            return false;
        }
        int n2 = (Integer)baritone.a.a().followTargetMaxDistance.value;
        if (n2 != 0) {
            int n3 = n2;
            if (entity.distanceToSqr((Entity)((ey)this).a.player()) > (double)(n3 * n3)) {
                return false;
            }
        }
        return ((ey)this).a.entitiesStream().anyMatch(arg_0 -> ((Entity)entity).equals(arg_0));
    }

    private void a() {
        this.a = ((ey)this).a.entitiesStream().filter(this::a).filter(this.a).distinct().collect(Collectors.toList());
    }

    @Override
    public final boolean isActive() {
        if (this.a == null) {
            return false;
        }
        this.a();
        return !this.a.isEmpty();
    }

    @Override
    public final void onLostControl() {
        this.a = null;
        this.a = null;
    }

    @Override
    public final String displayName0() {
        return "Following " + String.valueOf(this.a);
    }

    @Override
    public final void follow(Predicate<Entity> predicate) {
        this.a = predicate;
        this.a = false;
    }

    @Override
    public final void pickup(Predicate<ItemStack> predicate) {
        this.a = entity -> entity instanceof ItemEntity && predicate.test(((ItemEntity)entity).getItem());
        this.a = true;
    }

    @Override
    public final List<Entity> following() {
        return this.a;
    }

    @Override
    public final Predicate<Entity> currentFilter() {
        return this.a;
    }
}

