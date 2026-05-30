/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.am;
import baritone.an;
import baritone.ao;
import baritone.ap;
import baritone.aq;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class al {
    an a;
    ao a;
    ap a;
    aq a;

    public al(a a2) {
        boolean[] blArray = new boolean[]{false};
        a2.getPathingControlManager().registerProcess(new am(blArray, a2));
        this.a = new an(a2, new String[]{"pause", "p", "paws"}, blArray);
        this.a = new ao(a2, new String[]{"resume", "r", "unpause", "unpaws"}, blArray);
        this.a = new ap(a2, new String[]{"paused"}, blArray);
        this.a = new aq(a2, new String[]{"cancel", "c", "stop"}, blArray);
    }
}

