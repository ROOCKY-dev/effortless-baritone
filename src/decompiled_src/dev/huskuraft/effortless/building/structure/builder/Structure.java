/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package dev.huskuraft.effortless.building.structure.builder;

import com.google.common.collect.Sets;
import dev.huskuraft.effortless.api.core.BlockInteraction;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.building.Context;
import dev.huskuraft.effortless.building.structure.BuildFeature;
import dev.huskuraft.effortless.building.structure.BuildFeatures;
import dev.huskuraft.effortless.building.structure.BuildMode;
import dev.huskuraft.effortless.building.structure.CircleStart;
import dev.huskuraft.effortless.building.structure.CubeFilling;
import dev.huskuraft.effortless.building.structure.LineDirection;
import dev.huskuraft.effortless.building.structure.PlaneFacing;
import dev.huskuraft.effortless.building.structure.PlaneFilling;
import dev.huskuraft.effortless.building.structure.PlaneLength;
import dev.huskuraft.effortless.building.structure.RaisedEdge;
import dev.huskuraft.effortless.building.structure.builder.standard.Disable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Structure {
    public static final Structure DISABLED = new Disable();

    public int volume(Context var1);

    public int traceSize(Context var1);

    public BlockInteraction trace(Player var1, Context var2);

    public Stream<BlockPosition> collect(Context var1);

    public BuildMode getMode();

    default public Set<BuildFeatures> getSupportedFeatures() {
        return Sets.newHashSet((Object[])this.getMode().getSupportedFeatures());
    }

    @Deprecated
    default public CircleStart circleStart() {
        return null;
    }

    @Deprecated
    default public CubeFilling cubeFilling() {
        return null;
    }

    @Deprecated
    default public PlaneFilling planeFilling() {
        return null;
    }

    @Deprecated
    default public PlaneFacing planeFacing() {
        return null;
    }

    @Deprecated
    default public PlaneLength planeLength() {
        return null;
    }

    @Deprecated
    default public RaisedEdge raisedEdge() {
        return null;
    }

    @Deprecated
    default public LineDirection lineDirection() {
        return null;
    }

    default public Set<BuildFeature> getFeatures() {
        return Stream.of(this.circleStart(), this.cubeFilling(), this.planeFilling(), this.planeFacing(), this.planeLength(), this.raisedEdge(), this.lineDirection()).filter(x$0 -> Objects.nonNull(x$0)).collect(Collectors.toSet());
    }

    default public Structure withFeature(BuildFeature feature) {
        return this;
    }

    default public Structure withFeatures(List<BuildFeature> features) {
        Structure structure = this;
        for (BuildFeature feature : features) {
            structure = structure.withFeature(feature);
        }
        return structure;
    }
}
