/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.command.argparser.IArgParser;
import baritone.api.command.argument.ICommandArgument;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class y {
    public static final List<IArgParser<?>> a = Arrays.asList(d.a, e.a, c.a, b.a, baritone.y$a.a);

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class d
    extends Enum<d>
    implements IArgParser.Stateless<Integer> {
        public static final /* enum */ d a = new d("INSTANCE");
        private static final /* synthetic */ d[] a;

        public static d[] values() {
            return (d[])a.clone();
        }

        public static d valueOf(String string) {
            return Enum.valueOf(d.class, string);
        }

        @Override
        public final Class<Integer> getTarget() {
            return Integer.class;
        }

        @Override
        public final /* synthetic */ Object parseArg(ICommandArgument iCommandArgument) {
            return Integer.parseInt(iCommandArgument.getValue());
        }

        static {
            a = new d[]{a};
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class e
    extends Enum<e>
    implements IArgParser.Stateless<Long> {
        public static final /* enum */ e a = new e("INSTANCE");
        private static final /* synthetic */ e[] a;

        public static e[] values() {
            return (e[])a.clone();
        }

        public static e valueOf(String string) {
            return Enum.valueOf(e.class, string);
        }

        @Override
        public final Class<Long> getTarget() {
            return Long.class;
        }

        @Override
        public final /* synthetic */ Object parseArg(ICommandArgument iCommandArgument) {
            return Long.parseLong(iCommandArgument.getValue());
        }

        static {
            a = new e[]{a};
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class c
    extends Enum<c>
    implements IArgParser.Stateless<Float> {
        public static final /* enum */ c a = new c("INSTANCE");
        private static final /* synthetic */ c[] a;

        public static c[] values() {
            return (c[])a.clone();
        }

        public static c valueOf(String string) {
            return Enum.valueOf(c.class, string);
        }

        @Override
        public final Class<Float> getTarget() {
            return Float.class;
        }

        @Override
        public final /* synthetic */ Object parseArg(ICommandArgument object) {
            if (!((String)(object = object.getValue())).matches("^([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)|)$")) {
                throw new IllegalArgumentException("failed float format check");
            }
            return Float.valueOf(Float.parseFloat((String)object));
        }

        static {
            a = new c[]{a};
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class b
    extends Enum<b>
    implements IArgParser.Stateless<Double> {
        public static final /* enum */ b a = new b("INSTANCE");
        private static final /* synthetic */ b[] a;

        public static b[] values() {
            return (b[])a.clone();
        }

        public static b valueOf(String string) {
            return Enum.valueOf(b.class, string);
        }

        @Override
        public final Class<Double> getTarget() {
            return Double.class;
        }

        @Override
        public final /* synthetic */ Object parseArg(ICommandArgument object) {
            if (!((String)(object = object.getValue())).matches("^([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)|)$")) {
                throw new IllegalArgumentException("failed double format check");
            }
            return Double.parseDouble((String)object);
        }

        static {
            a = new b[]{a};
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class a
    implements IArgParser.Stateless<Boolean> {
        public static final a a = new a();
        private static List<String> a;
        private static List<String> b;

        @Override
        public final Class<Boolean> getTarget() {
            return Boolean.class;
        }

        @Override
        public final /* synthetic */ Object parseArg(ICommandArgument object) {
            if (a.contains(((String)(object = object.getValue())).toLowerCase(Locale.US))) {
                return Boolean.TRUE;
            }
            if (b.contains(((String)object).toLowerCase(Locale.US))) {
                return Boolean.FALSE;
            }
            throw new IllegalArgumentException("invalid boolean");
        }

        static {
            a = Arrays.asList("1", "true", "yes", "t", "y", "on", "enable");
            b = Arrays.asList("0", "false", "no", "f", "n", "off", "disable");
        }
    }
}

