/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.ac;
import baritone.ad;
import baritone.ae;
import baritone.af;
import baritone.ag;
import baritone.ah;
import baritone.aj;
import baritone.ak;
import baritone.al;
import baritone.api.command.Command;
import baritone.ar;
import baritone.as;
import baritone.at;
import baritone.au;
import baritone.av;
import baritone.aw;
import baritone.ax;
import baritone.ay;
import baritone.az;
import baritone.ba;
import baritone.bb;
import baritone.bc;
import baritone.bd;
import baritone.be;
import baritone.bf;
import baritone.bg;
import baritone.bh;
import baritone.bi;
import baritone.bj;
import baritone.bk;
import baritone.bm;
import baritone.bn;
import baritone.bo;
import baritone.bp;
import baritone.br;
import baritone.bs;
import baritone.command.defaults.FollowCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ai {
    /*
     * Ignored method signature, as it can't be verified against descriptor
     */
    public static List a(a object) {
        Objects.requireNonNull(object);
        ArrayList<Command> arrayList = new ArrayList<Command>(Arrays.asList(new az((a)object), new bm((a)object), new ah((a)object, Arrays.asList("modified", "mod", "baritone", "modifiedsettings"), "List modified settings", "set modified"), new ah((a)object, "reset", "Reset all settings or just one", "set reset"), new ax((a)object), new ay((a)object), new bd((a)object), new bf((a)object), new aj((a)object), new br((a)object), new bi((a)object), new ae((a)object), new bb((a)object), new ag((a)object), new ac((a)object), new av((a)object), new aw((a)object), new ba((a)object), new bp((a)object), new bh((a)object), new at((a)object), new FollowCommand((a)object), new be((a)object), new as((a)object), new bg((a)object), new bj((a)object), new ar((a)object), new ad((a)object), new au((a)object), new bc((a)object), new af((a)object), new bn((a)object), new bo((a)object), new bs((a)object), new ah((a)object, "sethome", "Sets your home waypoint", "waypoints save home"), new ah((a)object, "home", "Path to your home waypoint", "waypoints goto home"), new bk((a)object), new ak((a)object)));
        object = new al((a)object);
        arrayList.add(((al)object).a);
        arrayList.add(((al)object).a);
        arrayList.add(((al)object).a);
        arrayList.add(((al)object).a);
        return Collections.unmodifiableList(arrayList);
    }
}

