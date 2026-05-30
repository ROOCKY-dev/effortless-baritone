/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 */
package dev.huskuraft.effortless.api.core;

import dev.huskuraft.effortless.api.core.PlaneDirection;
import dev.huskuraft.effortless.api.text.Text;

public enum Axis {
    X("x"){

        @Override
        public int choose(int x, int y, int z) {
            return x;
        }

        @Override
        public double choose(double x, double y, double z) {
            return x;
        }
    }
    ,
    Y("y"){

        @Override
        public int choose(int x, int y, int z) {
            return y;
        }

        @Override
        public double choose(double x, double y, double z) {
            return y;
        }
    }
    ,
    Z("z"){

        @Override
        public int choose(int x, int y, int z) {
            return z;
        }

        @Override
        public double choose(double x, double y, double z) {
            return z;
        }
    };

    private final String name;

    private Axis(String name) {
        this.name = name;
    }

    public Text getDisplayName() {
        return Text.translate("effortless.axis.%s".formatted(this.name));
    }

    public Text getPositionName() {
        return Text.translate("effortless.position.%s".formatted(this.name));
    }

    public String getName() {
        return this.name;
    }

    public boolean isVertical() {
        return this == Y;
    }

    public boolean isHorizontal() {
        return this == X || this == Z;
    }

    public String toString() {
        return this.name;
    }

    public PlaneDirection getPlane() {
        return switch (this.ordinal()) {
            default -> throw new MatchException(null, null);
            case 0, 2 -> PlaneDirection.HORIZONTAL;
            case 1 -> PlaneDirection.VERTICAL;
        };
    }

    @Deprecated
    public abstract int choose(int var1, int var2, int var3);

    public abstract double choose(double var1, double var3, double var5);
}
