/*
 * Decompiled with CFR 0.152.
 */
package baritone;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class fy
extends Enum<fy> {
    public static final /* enum */ fy a = new fy(0);
    public static final /* enum */ fy b = new fy(1);
    public static final /* enum */ fy c = new fy(2);
    public static final /* enum */ fy d = new fy(3);
    public final boolean[] a;
    private static final /* synthetic */ fy[] a;

    public static fy[] values() {
        return (fy[])a.clone();
    }

    public static fy valueOf(String string) {
        return Enum.valueOf(fy.class, string);
    }

    private fy(int n3) {
        this.a = new boolean[]{(n3 & 2) != 0, (n3 & 1) != 0};
    }

    public static fy a(boolean bl2, boolean bl3) {
        if (bl2) {
            if (bl3) {
                return d;
            }
            return c;
        }
        if (bl3) {
            return b;
        }
        return a;
    }

    static {
        a = new fy[]{a, b, c, d};
    }
}

