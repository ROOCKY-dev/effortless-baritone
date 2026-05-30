/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.command.ICommandSystem;
import baritone.api.command.argparser.IArgParserManager;
import baritone.x;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class v
extends Enum<v>
implements ICommandSystem {
    public static final /* enum */ v a = new v("INSTANCE");
    private static final /* synthetic */ v[] a;

    public static v[] values() {
        return (v[])a.clone();
    }

    public static v valueOf(String string) {
        return Enum.valueOf(v.class, string);
    }

    @Override
    public final IArgParserManager getParserManager() {
        return x.a;
    }

    static {
        a = new v[]{a};
    }
}

