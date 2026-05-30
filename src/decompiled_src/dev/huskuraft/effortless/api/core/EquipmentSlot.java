/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.core;

public enum EquipmentSlot {
    MAINHAND(Type.HAND, 0, 0, "mainhand"),
    OFFHAND(Type.HAND, 1, 5, "offhand"),
    FEET(Type.ARMOR, 0, 1, "feet"),
    LEGS(Type.ARMOR, 1, 2, "legs"),
    CHEST(Type.ARMOR, 2, 3, "chest"),
    HEAD(Type.ARMOR, 3, 4, "head");

    private final Type type;
    private final int index;
    private final int filterFlag;
    private final String name;

    private EquipmentSlot(Type type, int index, int filterFlag, String name) {
        this.type = type;
        this.index = index;
        this.filterFlag = filterFlag;
        this.name = name;
    }

    public Type getType() {
        return this.type;
    }

    public int getIndex() {
        return this.index;
    }

    public int getIndex(int param0) {
        return param0 + this.index;
    }

    public int getFilterFlag() {
        return this.filterFlag;
    }

    public String getName() {
        return this.name;
    }

    public static enum Type {
        HAND,
        ARMOR;

    }
}
