/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone;

import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.ForAxis;
import baritone.api.command.datatypes.ForBlockOptionalMeta;
import baritone.api.command.datatypes.ForDirection;
import baritone.api.command.datatypes.RelativeBlockPos;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.schematic.CompositeSchematic;
import baritone.api.schematic.FillSchematic;
import baritone.api.schematic.ISchematic;
import baritone.api.schematic.MaskSchematic;
import baritone.api.schematic.ReplaceSchematic;
import baritone.api.schematic.ShellSchematic;
import baritone.api.schematic.WallsSchematic;
import baritone.api.schematic.mask.shape.CylinderMask;
import baritone.api.schematic.mask.shape.SphereMask;
import baritone.api.selection.ISelection;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.bl;
import baritone.fb;
import baritone.ge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class bk
extends Command {
    private ISelectionManager a;
    BetterBlockPos a;
    private CompositeSchematic a;
    private Vec3i a;

    public bk(baritone.a a2) {
        super(a2, "sel", "selection", "s");
        this.a = this.baritone.getSelectionManager();
        this.a = null;
        this.a = null;
        this.a = null;
        a2.getGameEventHandler().registerEventListener(new bl(this));
    }

    @Override
    public final void execute(String object, IArgConsumer axis) {
        object = baritone.bk$a.a(axis.getString());
        if (object == null) {
            throw new CommandInvalidTypeException(axis.consumed(), "an action");
        }
        if (object == baritone.bk$a.a || object == baritone.bk$a.b) {
            if (object == baritone.bk$a.b && this.a == null) {
                throw new CommandInvalidStateException("Set pos1 first before using pos2");
            }
            BetterBlockPos betterBlockPos = this.ctx.viewerPos();
            BetterBlockPos betterBlockPos2 = axis.hasAny() ? (BetterBlockPos)((Object)axis.getDatatypePost(RelativeBlockPos.INSTANCE, betterBlockPos)) : betterBlockPos;
            axis.requireMax(0);
            if (object == baritone.bk$a.a) {
                this.a = betterBlockPos2;
                this.logDirect("Position 1 has been set");
                return;
            }
            this.a.addSelection(this.a, betterBlockPos2);
            this.a = null;
            this.logDirect("Selection added");
            return;
        }
        if (object == baritone.bk$a.c) {
            axis.requireMax(0);
            this.a = null;
            this.logDirect(String.format("Removed %d selections", this.a.removeAllSelections().length));
            return;
        }
        if (object == baritone.bk$a.d) {
            axis.requireMax(0);
            if (this.a != null) {
                this.a = null;
                this.logDirect("Undid pos1");
                return;
            }
            ISelection[] iSelectionArray = this.a.getSelections();
            if (iSelectionArray.length <= 0) {
                throw new CommandInvalidStateException("Nothing to undo!");
            }
            this.a = this.a.removeSelection(iSelectionArray[iSelectionArray.length - 1]).pos1();
            this.logDirect("Undid pos2");
            return;
        }
        if (((a)((Object)object)).a()) {
            BetterBlockPos betterBlockPos;
            int n2;
            BlockOptionalMetaLookup blockOptionalMetaLookup;
            ISelection[] iSelectionArray;
            BlockOptionalMeta blockOptionalMeta;
            BlockOptionalMeta blockOptionalMeta2 = blockOptionalMeta = object == baritone.bk$a.g ? new BlockOptionalMeta(Blocks.AIR) : (BlockOptionalMeta)axis.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
            if (object == baritone.bk$a.h) {
                axis.requireMin(1);
                iSelectionArray = new ArrayList();
                iSelectionArray.add(blockOptionalMeta);
                while (axis.has(2)) {
                    iSelectionArray.add((BlockOptionalMeta)axis.getDatatypeFor(ForBlockOptionalMeta.INSTANCE));
                }
                blockOptionalMeta = (BlockOptionalMeta)axis.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
                blockOptionalMetaLookup = new BlockOptionalMetaLookup(iSelectionArray.toArray(new BlockOptionalMeta[0]));
                axis = null;
            } else if (object == baritone.bk$a.e || object == baritone.bk$a.f) {
                axis.requireMax(1);
                axis = axis.hasAny() ? (Direction.Axis)axis.getDatatypeFor(ForAxis.INSTANCE) : Direction.Axis.Y;
                blockOptionalMetaLookup = null;
            } else {
                axis.requireMax(0);
                blockOptionalMetaLookup = null;
                axis = null;
            }
            iSelectionArray = this.a.getSelections();
            if (iSelectionArray.length == 0) {
                throw new CommandInvalidStateException("No selections");
            }
            BetterBlockPos betterBlockPos3 = iSelectionArray[0].min();
            CompositeSchematic compositeSchematic = new CompositeSchematic(0, 0, 0);
            ISelection[] iSelectionArray2 = iSelectionArray;
            int n3 = iSelectionArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                betterBlockPos = iSelectionArray2[n2].min();
                betterBlockPos3 = new BetterBlockPos(Math.min(betterBlockPos3.x, betterBlockPos.x), Math.min(betterBlockPos3.y, betterBlockPos.y), Math.min(betterBlockPos3.z, betterBlockPos.z));
            }
            iSelectionArray2 = iSelectionArray;
            n3 = iSelectionArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                Object object2 = iSelectionArray2[n2];
                betterBlockPos = object2.size();
                object2 = object2.min();
                ISchematic iSchematic = (ISchematic)((UnaryOperator)arg_0 -> bk.a((a)((Object)object), blockOptionalMetaLookup, axis, arg_0)).apply(new FillSchematic(betterBlockPos.getX(), betterBlockPos.getY(), betterBlockPos.getZ(), blockOptionalMeta));
                compositeSchematic.put(iSchematic, ((BetterBlockPos)((Object)object2)).x - betterBlockPos3.x, ((BetterBlockPos)((Object)object2)).y - betterBlockPos3.y, ((BetterBlockPos)((Object)object2)).z - betterBlockPos3.z);
            }
            this.baritone.getBuilderProcess().build("Fill", compositeSchematic, (Vec3i)betterBlockPos3);
            this.logDirect("Filling now");
            return;
        }
        if (object == baritone.bk$a.j) {
            BetterBlockPos betterBlockPos;
            BlockState[][][] blockStateArray = this.ctx.viewerPos();
            BlockState[][][] blockStateArray2 = axis.hasAny() ? (BetterBlockPos)((Object)axis.getDatatypePost(RelativeBlockPos.INSTANCE, blockStateArray)) : blockStateArray;
            axis.requireMax(0);
            axis = this.a.getSelections();
            if (((ISelection[])axis).length <= 0) {
                throw new CommandInvalidStateException("No selections");
            }
            fb fb2 = new fb(this.ctx);
            BetterBlockPos betterBlockPos4 = axis[0].min();
            CompositeSchematic compositeSchematic = new CompositeSchematic(0, 0, 0);
            Direction.Axis axis2 = axis;
            int n4 = ((Direction.Axis)axis2).length;
            for (int i2 = 0; i2 < n4; ++i2) {
                betterBlockPos = axis2[i2].min();
                betterBlockPos4 = new BetterBlockPos(Math.min(betterBlockPos4.x, betterBlockPos.x), Math.min(betterBlockPos4.y, betterBlockPos.y), Math.min(betterBlockPos4.z, betterBlockPos.z));
            }
            for (Object object3 : axis) {
                betterBlockPos = object3.size();
                object3 = object3.min();
                blockStateArray = new BlockState[betterBlockPos.getX()][betterBlockPos.getZ()][betterBlockPos.getY()];
                for (int i3 = 0; i3 < betterBlockPos.getX(); ++i3) {
                    for (int i4 = 0; i4 < betterBlockPos.getY(); ++i4) {
                        for (int i5 = 0; i5 < betterBlockPos.getZ(); ++i5) {
                            blockStateArray[i3][i5][i4] = fb2.a(object3.x + i3, object3.y + i4, object3.z + i5);
                        }
                    }
                }
                ge ge2 = new ge(blockStateArray);
                compositeSchematic.put(ge2, object3.x - betterBlockPos4.x, object3.y - betterBlockPos4.y, object3.z - betterBlockPos4.z);
            }
            this.a = compositeSchematic;
            this.a = betterBlockPos4.subtract((Vec3i)blockStateArray2);
            this.logDirect("Selection copied");
            return;
        }
        if (object == baritone.bk$a.k) {
            BetterBlockPos betterBlockPos = this.ctx.viewerPos();
            BetterBlockPos betterBlockPos5 = axis.hasAny() ? (BetterBlockPos)((Object)axis.getDatatypePost(RelativeBlockPos.INSTANCE, betterBlockPos)) : betterBlockPos;
            axis.requireMax(0);
            if (this.a == null) {
                throw new CommandInvalidStateException("You need to copy a selection first");
            }
            this.baritone.getBuilderProcess().build("Fill", this.a, (Vec3i)betterBlockPos5.offset(this.a));
            this.logDirect("Building now");
            return;
        }
        if (object == baritone.bk$a.i || object == baritone.bk$a.l || object == baritone.bk$a.m) {
            axis.requireExactly(3);
            b b2 = b.a(axis.getString());
            if (b2 == null) {
                throw new CommandInvalidStateException("Invalid transform type");
            }
            Direction direction = (Direction)axis.getDatatypeFor(ForDirection.INSTANCE);
            int n5 = axis.getAs(Integer.class);
            ISelection[] iSelectionArray = this.a.getSelections();
            if (iSelectionArray.length <= 0) {
                throw new CommandInvalidStateException("No selections found");
            }
            iSelectionArray = (ISelection[])b2.a.apply(iSelectionArray);
            ISelection[] iSelectionArray3 = iSelectionArray;
            int n6 = iSelectionArray.length;
            for (int i6 = 0; i6 < n6; ++i6) {
                ISelection iSelection = iSelectionArray3[i6];
                if (object == baritone.bk$a.i) {
                    this.a.expand(iSelection, direction, n5);
                    continue;
                }
                if (object == baritone.bk$a.l) {
                    this.a.contract(iSelection, direction, n5);
                    continue;
                }
                this.a.shift(iSelection, direction, n5);
            }
            this.logDirect(String.format("Transformed %d selections", iSelectionArray.length));
        }
    }

    @Override
    public final Stream<String> tabComplete(String object, IArgConsumer iArgConsumer) {
        if (iArgConsumer.hasExactlyOne()) {
            return new TabCompleteHelper().append(baritone.bk$a.a()).filterPrefix(iArgConsumer.getString()).sortAlphabetically().stream();
        }
        object = baritone.bk$a.a(iArgConsumer.getString());
        if (object != null) {
            if (object == baritone.bk$a.a || object == baritone.bk$a.b) {
                if (iArgConsumer.hasAtMost(3)) {
                    return iArgConsumer.tabCompleteDatatype(RelativeBlockPos.INSTANCE);
                }
            } else if (((a)((Object)object)).a()) {
                if (iArgConsumer.hasExactlyOne() || object == baritone.bk$a.h) {
                    while (iArgConsumer.has(2)) {
                        iArgConsumer.get();
                    }
                    return iArgConsumer.tabCompleteDatatype(ForBlockOptionalMeta.INSTANCE);
                }
                if (iArgConsumer.hasExactly(2) && (object == baritone.bk$a.e || object == baritone.bk$a.f)) {
                    iArgConsumer.get();
                    return iArgConsumer.tabCompleteDatatype(ForAxis.INSTANCE);
                }
            } else if (object == baritone.bk$a.i || object == baritone.bk$a.l || object == baritone.bk$a.m) {
                if (iArgConsumer.hasExactlyOne()) {
                    return new TabCompleteHelper().append(b.a()).filterPrefix(iArgConsumer.getString()).sortAlphabetically().stream();
                }
                if (b.a(iArgConsumer.getString()) != null && iArgConsumer.hasExactlyOne()) {
                    return iArgConsumer.tabCompleteDatatype(ForDirection.INSTANCE);
                }
            }
        }
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "WorldEdit-like commands";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The sel command allows you to manipulate Baritone's selections, similarly to WorldEdit.", "", "Using these selections, you can clear areas, fill them with blocks, or something else.", "", "The expand/contract/shift commands use a kind of selector to choose which selections to target. Supported ones are a/all, n/newest, and o/oldest.", "", "Usage:", "> sel pos1/p1/1 - Set position 1 to your current position.", "> sel pos1/p1/1 <x> <y> <z> - Set position 1 to a relative position.", "> sel pos2/p2/2 - Set position 2 to your current position.", "> sel pos2/p2/2 <x> <y> <z> - Set position 2 to a relative position.", "", "> sel clear/c - Clear the selection.", "> sel undo/u - Undo the last action (setting positions, creating selections, etc.)", "> sel set/fill/s/f [block] - Completely fill all selections with a block.", "> sel walls/w [block] - Fill in the walls of the selection with a specified block.", "> sel shell/shl [block] - The same as walls, but fills in a ceiling and floor too.", "> sel sphere/sph [block] - Fills the selection with a sphere bounded by the sides.", "> sel hsphere/hsph [block] - The same as sphere, but hollow.", "> sel cylinder/cyl [block] <axis> - Fills the selection with a cylinder bounded by the sides, oriented about the given axis. (default=y)", "> sel hcylinder/hcyl [block] <axis> - The same as cylinder, but hollow.", "> sel cleararea/ca - Basically 'set air'.", "> sel replace/r <blocks...> <with> - Replaces blocks with another block.", "> sel copy/cp <x> <y> <z> - Copy the selected area relative to the specified or your position.", "> sel paste/p <x> <y> <z> - Build the copied area relative to the specified or your position.", "", "> sel expand <target> <direction> <blocks> - Expand the targets.", "> sel contract <target> <direction> <blocks> - Contract the targets.", "> sel shift <target> <direction> <blocks> - Shift the targets (does not resize).");
    }

    private static /* synthetic */ ISchematic a(a a2, BlockOptionalMetaLookup blockOptionalMetaLookup, Direction.Axis axis, ISchematic iSchematic) {
        int n2 = iSchematic.widthX();
        int n3 = iSchematic.heightY();
        int n4 = iSchematic.lengthZ();
        switch (a2.ordinal()) {
            case 5: {
                return new WallsSchematic(iSchematic);
            }
            case 6: {
                return new ShellSchematic(iSchematic);
            }
            case 12: {
                return new ReplaceSchematic(iSchematic, blockOptionalMetaLookup);
            }
            case 7: {
                return MaskSchematic.create(iSchematic, new SphereMask(n2, n3, n4, true).compute());
            }
            case 8: {
                return MaskSchematic.create(iSchematic, new SphereMask(n2, n3, n4, false).compute());
            }
            case 9: {
                return MaskSchematic.create(iSchematic, new CylinderMask(n2, n3, n4, true, axis).compute());
            }
            case 10: {
                return MaskSchematic.create(iSchematic, new CylinderMask(n2, n3, n4, false, axis).compute());
            }
        }
        return iSchematic;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class a
    extends Enum<a> {
        public static final /* enum */ a a = new a("pos1", "p1", "1");
        public static final /* enum */ a b = new a("pos2", "p2", "2");
        public static final /* enum */ a c = new a("clear", "c");
        public static final /* enum */ a d = new a("undo", "u");
        private static /* enum */ a n = new a("set", "fill", "s", "f");
        private static /* enum */ a o = new a("walls", "w");
        private static /* enum */ a p = new a("shell", "shl");
        private static /* enum */ a q = new a("sphere", "sph");
        private static /* enum */ a r = new a("hsphere", "hsph");
        public static final /* enum */ a e = new a("cylinder", "cyl");
        public static final /* enum */ a f = new a("hcylinder", "hcyl");
        public static final /* enum */ a g = new a("cleararea", "ca");
        public static final /* enum */ a h = new a("replace", "r");
        public static final /* enum */ a i = new a("expand", "ex");
        public static final /* enum */ a j = new a("copy", "cp");
        public static final /* enum */ a k = new a("paste", "p");
        public static final /* enum */ a l = new a("contract", "ct");
        public static final /* enum */ a m = new a("shift", "sh");
        private final String[] a;
        private static final /* synthetic */ a[] a;

        public static a[] values() {
            return (a[])a.clone();
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        private a(String ... stringArray) {
            this.a = stringArray;
        }

        public static a a(String string) {
            for (a a2 : baritone.bk$a.values()) {
                String[] stringArray = a2.a;
                int n2 = a2.a.length;
                for (int i2 = 0; i2 < n2; ++i2) {
                    if (!stringArray[i2].equalsIgnoreCase(string)) continue;
                    return a2;
                }
            }
            return null;
        }

        public static String[] a() {
            HashSet<String> hashSet = new HashSet<String>();
            for (a a2 : baritone.bk$a.values()) {
                hashSet.addAll(Arrays.asList(a2.a));
            }
            return hashSet.toArray(new String[0]);
        }

        public final boolean a() {
            return this == n || this == o || this == p || this == q || this == r || this == e || this == f || this == g || this == h;
        }

        static {
            a = new a[]{a, b, c, d, n, o, p, q, r, e, f, g, h, i, j, k, l, m};
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     * Exception performing whole class analysis.
     */
    static final class b
    extends Enum<b> {
        private static /* enum */ b a;
        private static /* enum */ b b;
        private static /* enum */ b c;
        final Function<ISelection[], ISelection[]> a;
        private final String[] a;
        private static final /* synthetic */ b[] a;

        public static b[] values() {
            return (b[])a.clone();
        }

        public static b valueOf(String string) {
            return Enum.valueOf(b.class, string);
        }

        private b(Function<ISelection[], ISelection[]> function, String ... stringArray) {
            super(string, n2);
            this.a = function;
            this.a = stringArray;
        }

        public static b a(String string) {
            for (b b2 : baritone.bk$b.values()) {
                String[] stringArray = b2.a;
                int n2 = b2.a.length;
                for (int i2 = 0; i2 < n2; ++i2) {
                    if (!stringArray[i2].equalsIgnoreCase(string)) continue;
                    return b2;
                }
            }
            return null;
        }

        public static String[] a() {
            HashSet<String> hashSet = new HashSet<String>();
            for (b b2 : baritone.bk$b.values()) {
                hashSet.addAll(Arrays.asList(b2.a));
            }
            return hashSet.toArray(new String[0]);
        }

        private static /* synthetic */ ISelection[] a(ISelection[] iSelectionArray) {
            return new ISelection[]{iSelectionArray[0]};
        }

        /*
         * Exception decompiling
         */
        static {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * java.lang.UnsupportedOperationException
             *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.NewAnonymousArray.getDimSize(NewAnonymousArray.java:142)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.isNewArrayLambda(LambdaRewriter.java:455)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:409)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:167)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:105)
             *     at org.benf.cfr.reader.bytecode.analysis.parse.rewriters.ExpressionRewriterHelper.applyForwards(ExpressionRewriterHelper.java:12)
             *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractConstructorInvokation.applyExpressionRewriter(AbstractConstructorInvokation.java:65)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:103)
             *     at org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredAssignment.rewriteExpressions(StructuredAssignment.java:146)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewrite(LambdaRewriter.java:88)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.rewriteLambdas(Op04StructuredStatement.java:1137)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:912)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }
    }
}

