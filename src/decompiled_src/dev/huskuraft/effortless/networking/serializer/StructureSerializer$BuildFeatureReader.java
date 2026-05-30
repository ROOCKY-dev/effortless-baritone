/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;
import dev.huskuraft.effortless.building.structure.BuildFeature;
import dev.huskuraft.effortless.building.structure.BuildFeatures;
import dev.huskuraft.effortless.building.structure.CircleStart;
import dev.huskuraft.effortless.building.structure.CubeFilling;
import dev.huskuraft.effortless.building.structure.CubeLength;
import dev.huskuraft.effortless.building.structure.LineDirection;
import dev.huskuraft.effortless.building.structure.PlaneFacing;
import dev.huskuraft.effortless.building.structure.PlaneFilling;
import dev.huskuraft.effortless.building.structure.PlaneLength;
import dev.huskuraft.effortless.building.structure.RaisedEdge;

public static class StructureSerializer.BuildFeatureReader
implements NetByteBufSerializer<BuildFeature> {
    @Override
    public BuildFeature read(NetByteBuf byteBuf) {
        return switch (byteBuf.readEnum(BuildFeatures.class)) {
            default -> throw new MatchException(null, null);
            case BuildFeatures.CIRCLE_START -> byteBuf.readEnum(CircleStart.class);
            case BuildFeatures.CUBE_FILLING -> byteBuf.readEnum(CubeFilling.class);
            case BuildFeatures.CUBE_LENGTH -> byteBuf.readEnum(CubeLength.class);
            case BuildFeatures.PLANE_FACING -> byteBuf.readEnum(PlaneFacing.class);
            case BuildFeatures.PLANE_FILLING -> byteBuf.readEnum(PlaneFilling.class);
            case BuildFeatures.PLANE_LENGTH -> byteBuf.readEnum(PlaneLength.class);
            case BuildFeatures.LINE_DIRECTION -> byteBuf.readEnum(LineDirection.class);
            case BuildFeatures.RAISED_EDGE -> byteBuf.readEnum(RaisedEdge.class);
        };
    }

    @Override
    public void write(NetByteBuf byteBuf, BuildFeature buildFeature) {
        byteBuf.writeEnum(buildFeature.getType());
        switch (buildFeature.getType()) {
            case CIRCLE_START: {
                byteBuf.writeEnum((CircleStart)buildFeature);
                break;
            }
            case CUBE_FILLING: {
                byteBuf.writeEnum((CubeFilling)buildFeature);
                break;
            }
            case CUBE_LENGTH: {
                byteBuf.writeEnum((CubeLength)buildFeature);
                break;
            }
            case PLANE_FACING: {
                byteBuf.writeEnum((PlaneFacing)buildFeature);
                break;
            }
            case PLANE_FILLING: {
                byteBuf.writeEnum((PlaneFilling)buildFeature);
                break;
            }
            case PLANE_LENGTH: {
                byteBuf.writeEnum((PlaneLength)buildFeature);
                break;
            }
            case LINE_DIRECTION: {
                byteBuf.writeEnum((LineDirection)buildFeature);
                break;
            }
            case RAISED_EDGE: {
                byteBuf.writeEnum((RaisedEdge)buildFeature);
            }
        }
    }
}
