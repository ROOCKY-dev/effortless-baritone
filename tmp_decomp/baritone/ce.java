/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.utils.BetterBlockPos;
import baritone.ca;
import baritone.cb;
import baritone.cf;
import baritone.cg;
import baritone.ch;
import baritone.ci;
import baritone.cj;
import baritone.ck;
import baritone.cl;
import baritone.cm;
import baritone.cn;
import baritone.co;
import baritone.cp;
import baritone.cq;
import baritone.cr;
import baritone.cs;
import baritone.ct;
import baritone.cu;
import baritone.cv;
import baritone.cw;
import baritone.cx;
import baritone.cy;
import baritone.cz;
import baritone.da;
import baritone.fw;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract sealed class ce
extends Enum<ce>
permits cf, cq, cu, cv, cw, cx, cy, cz, da, cg, ch, ci, cj, ck, cl, cm, cn, co, cp, cr, cs, ct {
    private static /* enum */ cf a = new cf();
    private static /* enum */ cq a = new cq();
    private static /* enum */ cu a = new cu();
    private static /* enum */ cv a = new cv();
    private static /* enum */ cw a = new cw();
    private static /* enum */ cx a = new cx();
    private static /* enum */ cy a = new cy();
    private static /* enum */ cz a = new cz();
    private static /* enum */ da a = new da();
    private static /* enum */ cg a = new cg();
    private static /* enum */ ch a = new ch();
    private static /* enum */ ci a = new ci();
    private static /* enum */ cj a = new cj();
    private static /* enum */ ck a = new ck();
    private static /* enum */ cl a = new cl();
    private static /* enum */ cm a = new cm();
    private static /* enum */ cn a = new cn();
    private static /* enum */ co a = new co();
    private static /* enum */ cp a = new cp();
    private static /* enum */ cr a = new cr();
    private static /* enum */ cs a = new cs();
    private static /* enum */ ct a = new ct();
    public final boolean a;
    public final boolean b;
    public final int a;
    public final int b;
    public final int c;
    private static final /* synthetic */ ce[] a;

    public static ce[] values() {
        return (ce[])a.clone();
    }

    public static ce valueOf(String string) {
        return Enum.valueOf(ce.class, string);
    }

    ce(int n3, int n4, int n5, boolean bl2, boolean bl3) {
        this.a = n3;
        this.b = n4;
        this.c = n5;
        this.a = bl2;
        this.b = bl3;
    }

    ce(int n3, int n4, int n5) {
        this(n3, n4, n5, false, false);
    }

    public abstract cb a(ca var1, BetterBlockPos var2);

    public void a(ca ca2, int n2, int n3, int n4, fw fw2) {
        if (this.a || this.b) {
            throw new UnsupportedOperationException("Movements with dynamic offset must override `apply`");
        }
        fw2.a = n2 + this.a;
        fw2.b = n3 + this.b;
        fw2.c = n4 + this.c;
        fw2.a = this.a(ca2, n2, n3, n4);
    }

    public double a(ca ca2, int n2, int n3, int n4) {
        throw new UnsupportedOperationException("Movements must override `cost` or `apply`");
    }

    static {
        a = new ce[]{a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a};
    }
}

