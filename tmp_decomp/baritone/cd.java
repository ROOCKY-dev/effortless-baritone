/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.pathing.movement.MovementStatus;
import baritone.api.utils.Rotation;
import baritone.api.utils.input.Input;
import java.util.HashMap;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class cd {
    public MovementStatus a;
    public a a;
    final Map<Input, Boolean> a = new HashMap();

    public final cd a(a a2) {
        this.a = a2;
        return this;
    }

    public final cd a(Input input, boolean bl2) {
        this.a.put(input, bl2);
        return this;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class a {
        public Rotation a;
        boolean a;

        public a() {
            this(null, false);
        }

        public a(Rotation rotation, boolean bl2) {
            this.a = rotation;
            this.a = bl2;
        }
    }
}

