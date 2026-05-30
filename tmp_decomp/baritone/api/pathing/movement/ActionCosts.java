/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.pathing.movement;

public interface ActionCosts {
    public static final double WALK_ONE_BLOCK_COST = 4.63284688441047;
    public static final double WALK_ONE_IN_WATER_COST = 9.09090909090909;
    public static final double WALK_ONE_OVER_SOUL_SAND_COST = 9.26569376882094;
    public static final double LADDER_UP_ONE_COST = 8.51063829787234;
    public static final double LADDER_DOWN_ONE_COST = 6.666666666666667;
    public static final double SNEAK_ONE_BLOCK_COST = 15.384615384615383;
    public static final double SPRINT_ONE_BLOCK_COST = 3.563791874554526;
    public static final double SPRINT_MULTIPLIER = 0.7692444761225944;
    public static final double WALK_OFF_BLOCK_COST = 3.7062775075283763;
    public static final double CENTER_AFTER_FALL_COST = 0.9265693768820937;
    public static final double COST_INF = 1000000.0;
    public static final double[] FALL_N_BLOCKS_COST = ActionCosts.generateFallNBlocksCost();
    public static final double FALL_1_25_BLOCKS_COST = ActionCosts.distanceToTicks(1.25);
    public static final double FALL_0_25_BLOCKS_COST = ActionCosts.distanceToTicks(0.25);
    public static final double JUMP_ONE_BLOCK_COST = FALL_1_25_BLOCKS_COST - FALL_0_25_BLOCKS_COST;

    public static double[] generateFallNBlocksCost() {
        double[] dArray = new double[4097];
        int n2 = 0;
        while (n2 < 4097) {
            int n3 = n2++;
            dArray[n3] = ActionCosts.distanceToTicks(n3);
        }
        return dArray;
    }

    public static double velocity(int n2) {
        return (Math.pow(0.98, n2) - 1.0) * -3.92;
    }

    public static double oldFormula(double d2) {
        return -3.92 * (99.0 - 49.5 * (Math.pow(0.98, d2) + 1.0) - d2);
    }

    public static double distanceToTicks(double d2) {
        if (d2 == 0.0) {
            return 0.0;
        }
        double d3 = d2;
        int n2 = 0;
        double d4;
        while (!(d3 <= (d4 = ActionCosts.velocity(n2)))) {
            d3 -= d4;
            ++n2;
        }
        return (double)n2 + d3 / d4;
    }
}

