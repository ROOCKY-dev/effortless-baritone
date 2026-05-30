/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.utils;

import baritone.api.utils.BooleanBinaryOperator;

public enum BooleanBinaryOperators implements BooleanBinaryOperator
{
    OR((bl2, bl3) -> bl2 || bl3),
    AND((bl2, bl3) -> bl2 && bl3),
    XOR((bl2, bl3) -> bl2 ^ bl3);

    private final BooleanBinaryOperator op;

    private BooleanBinaryOperators(BooleanBinaryOperator booleanBinaryOperator) {
        this.op = booleanBinaryOperator;
    }

    @Override
    public final boolean applyAsBoolean(boolean bl2, boolean bl3) {
        return this.op.applyAsBoolean(bl2, bl3);
    }
}

