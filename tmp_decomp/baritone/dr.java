/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.AirBlock
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.LiquidBlock
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.PipeBlock
 *  net.minecraft.world.level.block.RotatedPillarBlock
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.TrapDoorBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 */
package baritone;

import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.process.IBuilderProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.api.schematic.FillSchematic;
import baritone.api.schematic.ISchematic;
import baritone.api.schematic.IStaticSchematic;
import baritone.api.schematic.MirroredSchematic;
import baritone.api.schematic.RotatedSchematic;
import baritone.api.schematic.SubstituteSchematic;
import baritone.api.schematic.format.ISchematicFormat;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.RayTraceUtils;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.input.Input;
import baritone.ca;
import baritone.cb;
import baritone.cc;
import baritone.ds;
import baritone.dt;
import baritone.du;
import baritone.dv;
import baritone.dw;
import baritone.dx;
import baritone.ey;
import baritone.fb;
import baritone.fh;
import baritone.gb;
import baritone.gc;
import baritone.gd;
import baritone.utils.schematic.litematica.LitematicaHelper;
import baritone.utils.schematic.schematica.SchematicaHelper;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.io.File;
import java.io.FileInputStream;
import java.lang.invoke.LambdaMetafactory;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class dr
extends ey
implements IBuilderProcess {
    private static final Set<Property<?>> a = ImmutableSet.of((Object)RotatedPillarBlock.AXIS, (Object)HorizontalDirectionalBlock.FACING, (Object)StairBlock.FACING, (Object)StairBlock.HALF, (Object)StairBlock.SHAPE, (Object)PipeBlock.NORTH, (Object[])new Property[]{PipeBlock.EAST, PipeBlock.SOUTH, PipeBlock.WEST, PipeBlock.UP, TrapDoorBlock.OPEN, TrapDoorBlock.HALF});
    private HashSet<BetterBlockPos> a;
    private LongOpenHashSet a;
    private String a;
    private ISchematic b;
    public ISchematic a;
    public Vec3i a;
    private int a;
    private boolean a;
    private int b;
    private int c;
    public List<BlockState> a;
    private int d = 0;

    public dr(baritone.a a2) {
        super(a2);
    }

    @Override
    public final void build(String string, ISchematic iSchematic, Vec3i vec3i) {
        this.a = string;
        this.a = iSchematic;
        this.b = null;
        boolean bl2 = iSchematic instanceof gd;
        if (!((Map)baritone.a.a().buildSubstitutes.value).isEmpty()) {
            this.a = new SubstituteSchematic(this.a, (Map)baritone.a.a().buildSubstitutes.value);
        }
        if (baritone.a.a().buildSchematicMirror.value != Mirror.NONE) {
            this.a = new MirroredSchematic(this.a, (Mirror)baritone.a.a().buildSchematicMirror.value);
        }
        if (baritone.a.a().buildSchematicRotation.value != net.minecraft.world.level.block.Rotation.NONE) {
            this.a = new RotatedSchematic(this.a, (net.minecraft.world.level.block.Rotation)baritone.a.a().buildSchematicRotation.value);
        }
        this.a = new ds(this.a);
        int n2 = vec3i.getX();
        int n3 = vec3i.getY();
        int n4 = vec3i.getZ();
        if (((Boolean)baritone.a.a().schematicOrientationX.value).booleanValue()) {
            n2 += iSchematic.widthX();
        }
        if (((Boolean)baritone.a.a().schematicOrientationY.value).booleanValue()) {
            n3 += iSchematic.heightY();
        }
        if (((Boolean)baritone.a.a().schematicOrientationZ.value).booleanValue()) {
            n4 += iSchematic.lengthZ();
        }
        this.a = new Vec3i(n2, n3, n4);
        this.a = false;
        this.b = (Integer)baritone.a.a().startAtLayer.value;
        this.d = iSchematic.heightY();
        if (((Boolean)baritone.a.a().buildOnlySelection.value).booleanValue() && bl2) {
            if (((ey)this).a.a.getSelections().length == 0) {
                this.logDirect("Poor little kitten forgot to set a selection while BuildOnlySelection is true");
                this.d = 0;
            } else if (((Boolean)baritone.a.a().buildInLayers.value).booleanValue()) {
                OptionalInt optionalInt = Stream.of(((ey)this).a.a.getSelections()).mapToInt(iSelection -> iSelection.min().y).min();
                OptionalInt optionalInt2 = Stream.of(((ey)this).a.a.getSelections()).mapToInt(iSelection -> iSelection.max().y).max();
                if (optionalInt.isPresent() && optionalInt2.isPresent()) {
                    n2 = (Boolean)baritone.a.a().layerOrder.value != false ? n3 + iSchematic.heightY() - optionalInt2.getAsInt() : optionalInt.getAsInt() - n3;
                    this.d = ((Boolean)baritone.a.a().layerOrder.value != false ? n3 + iSchematic.heightY() - optionalInt.getAsInt() : optionalInt2.getAsInt() - n3) + 1;
                    this.b = Math.max(this.b, n2 / (Integer)baritone.a.a().layerHeight.value);
                    this.logDebug(String.format("Schematic starts at y=%s with height %s", n3, iSchematic.heightY()));
                    this.logDebug(String.format("Selection starts at y=%s and ends at y=%s", optionalInt.getAsInt(), optionalInt2.getAsInt()));
                    this.logDebug(String.format("Considering relevant height %s - %s", n2, this.d));
                }
            }
        }
        this.c = 0;
        this.a = new LongOpenHashSet();
        this.a = null;
    }

    @Override
    public final void resume() {
        this.a = false;
    }

    @Override
    public final void pause() {
        this.a = true;
    }

    @Override
    public final boolean isPaused() {
        return this.a;
    }

    @Override
    public final boolean build(String string, File object, Vec3i vec3i) {
        Optional<ISchematicFormat> optional = gc.a.getByFile((File)object);
        if (!optional.isPresent()) {
            return false;
        }
        try {
            object = optional.get().parse(new FileInputStream((File)object));
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        object = this.a(vec3i, (IStaticSchematic)object);
        this.build(string, (ISchematic)object, vec3i);
        return true;
    }

    private ISchematic a(Vec3i vec3i, IStaticSchematic iStaticSchematic) {
        ISchematic iSchematic = iStaticSchematic;
        if (((Boolean)baritone.a.a().mapArtMode.value).booleanValue()) {
            iSchematic = new gb(iStaticSchematic);
        }
        if (((Boolean)baritone.a.a().buildOnlySelection.value).booleanValue()) {
            iSchematic = new gd(iSchematic, vec3i, ((ey)this).a.a.getSelections());
        }
        return iSchematic;
    }

    @Override
    public final void buildOpenSchematic() {
        if (SchematicaHelper.a()) {
            BlockPos blockPos = SchematicaHelper.a();
            if (blockPos.isPresent()) {
                IStaticSchematic iStaticSchematic = (IStaticSchematic)blockPos.get().getA();
                blockPos = (BlockPos)blockPos.get().getB();
                ISchematic iSchematic = this.a((Vec3i)blockPos, iStaticSchematic);
                this.build(iStaticSchematic.toString(), iSchematic, (Vec3i)blockPos);
                return;
            }
            this.logDirect("No schematic currently open");
            return;
        }
        this.logDirect("Schematica is not present");
    }

    @Override
    public final void buildOpenLitematic(int n2) {
        if (LitematicaHelper.a()) {
            if (LitematicaHelper.a(n2)) {
                Tuple<IStaticSchematic, Vec3i> tuple = LitematicaHelper.a(n2);
                Vec3i vec3i = (Vec3i)tuple.getB();
                ISchematic iSchematic = this.a(vec3i, (IStaticSchematic)tuple.getA());
                this.build(((IStaticSchematic)tuple.getA()).toString(), iSchematic, vec3i);
                return;
            }
            this.logDirect(String.format("List of placements has no entry %s", n2 + 1));
            return;
        }
        this.logDirect("Litematica is not present");
    }

    @Override
    public final void clearArea(BlockPos blockPos, BlockPos blockPos2) {
        BlockPos blockPos3 = new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ()));
        int n2 = Math.abs(blockPos.getX() - blockPos2.getX()) + 1;
        int n3 = Math.abs(blockPos.getY() - blockPos2.getY()) + 1;
        int n4 = Math.abs(blockPos.getZ() - blockPos2.getZ()) + 1;
        this.build("clear area", new FillSchematic(n2, n3, n4, Blocks.AIR.defaultBlockState()), (Vec3i)blockPos3);
    }

    @Override
    public final List<BlockState> getApproxPlaceable() {
        return new ArrayList(this.a);
    }

    @Override
    public final boolean isActive() {
        return this.a != null;
    }

    public final boolean a(BlockPos blockPos, BlockState blockState) {
        return (blockState = blockState.getCollisionShape((BlockGetter)((ey)this).a.world(), blockPos)).isEmpty() || ((ey)this).a.world().isUnobstructed(null, blockState.move((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()));
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Override
    public final PathingCommand onTick(boolean var1_1, boolean var2_3) {
        block49: {
            var3_4 = 0;
            var1_2 = this;
            while (true) {
                block48: {
                    block45: {
                        block50: {
                            if (var3_4 > 100) {
                                return new PathingCommand(null, PathingCommandType.SET_GOAL_AND_PATH);
                            }
                            var1_2.a = var1_2.a(36);
                            var1_2.a = var1_2.a.a.isInputForcedDown(Input.CLICK_LEFT) ? 5 : --var1_2.a;
                            var1_2.a.a.clearAllKeys();
                            if (var1_2.a) {
                                return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                            }
                            if (((Boolean)baritone.a.a().buildInLayers.value).booleanValue()) {
                                if (var1_2.b == null) {
                                    var1_2.b = var1_2.a;
                                }
                                var4_5 = var1_2.b;
                                if (((Boolean)baritone.a.a().layerOrder.value).booleanValue()) {
                                    var6_8 = var4_5.heightY() - 1;
                                    var5_7 = var4_5.heightY() - var1_2.b * (Integer)baritone.a.a().layerHeight.value;
                                } else {
                                    var6_8 = var1_2.b * (Integer)baritone.a.a().layerHeight.value - 1;
                                    var5_7 = 0;
                                }
                                var1_2.a = new du(var1_2, (ISchematic)var4_5, var5_7, var6_8);
                            }
                            var4_5 = new a(var1_2);
                            var7_12 = var4_5;
                            var5_6 = var1_2;
                            if (var5_6.a != null) break block50;
                            var5_6.a = new HashSet<Property<?>>();
                            var5_6.a((a)var7_12);
                            if (var5_6.a.isEmpty()) ** GOTO lbl-1000
                        }
                        var9_14 = var7_12;
                        var8_13 = var5_6;
                        var10_16 = var8_13.a.playerFeet();
                        var11_18 = (Integer)baritone.a.a().builderTickScanRadius.value;
                        for (var12_19 = -var11_18; var12_19 <= var11_18; ++var12_19) {
                            for (var13_20 = -var11_18; var13_20 <= var11_18; ++var13_20) {
                                for (var14_25 = -var11_18; var14_25 <= var11_18; ++var14_25) {
                                    var15_30 = var10_16.x + var12_19;
                                    var16_31 = var10_16.y + var13_20;
                                    var17_35 = var10_16.z + var14_25;
                                    var18_39 = var9_14.a(var15_30, var16_31, var17_35, var9_14.a.a(var15_30, var16_31, var17_35));
                                    if (var18_39 == null) continue;
                                    var19_40 = new BetterBlockPos(var15_30, var16_31, var17_35);
                                    if (dr.a(var9_14.a.a(var15_30, var16_31, var17_35), (BlockState)var18_39, false)) {
                                        var8_13.a.remove(var19_40);
                                        var8_13.a.add(BetterBlockPos.longHash((BetterBlockPos)var19_40));
                                        continue;
                                    }
                                    var8_13.a.add((Property<?>)((BetterBlockPos)var19_40));
                                    var8_13.a.remove(BetterBlockPos.longHash((BetterBlockPos)var19_40));
                                }
                            }
                        }
                        if (var5_6.a.isEmpty()) {
                            var5_6.a((a)var7_12);
                        }
                        if (!var5_6.a.isEmpty()) {
                            v0 = true;
                        } else lbl-1000:
                        // 2 sources

                        {
                            v0 = false;
                        }
                        if (!v0) {
                            if (((Boolean)baritone.a.a().buildInLayers.value).booleanValue() && var1_2.b * (Integer)baritone.a.a().layerHeight.value < var1_2.d) {
                                v1 = var1_2;
                                v1.logDirect("Starting layer " + v1.b);
                                ++var1_2.b;
                                ++var3_4;
                                continue;
                            }
                            var5_6 = (Vec3i)baritone.a.a().buildRepeat.value;
                            var6_8 = (Integer)baritone.a.a().buildRepeatCount.value;
                            ++var1_2.c;
                            if (var5_6.equals(new Vec3i(0, 0, 0)) || var6_8 != -1 && var1_2.c >= var6_8) {
                                var1_2.logDirect("Done building");
                                if (((Boolean)baritone.a.a().notificationOnBuildFinished.value).booleanValue()) {
                                    var1_2.logNotification("Done building", false);
                                }
                                var1_2.onLostControl();
                                return null;
                            }
                            var1_2.b = 0;
                            var1_2.a = new BlockPos(var1_2.a).offset((Vec3i)var5_6);
                            if (!((Boolean)baritone.a.a().buildRepeatSneaky.value).booleanValue()) {
                                var1_2.a.reset();
                            }
                            var1_2.logDirect("Repeating build in vector " + String.valueOf(var5_6) + ", new origin is " + String.valueOf(var1_2.a));
                            ++var3_4;
                            continue;
                        }
                        if (((Boolean)baritone.a.a().distanceTrim.value).booleanValue()) {
                            var5_6 = var1_2;
                            var7_12 = new HashSet<Property<?>>(var5_6.a);
                            var7_12.removeIf((Predicate<BetterBlockPos>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, a(baritone.api.utils.BetterBlockPos ), (Lbaritone/api/utils/BetterBlockPos;)Z)((dr)var5_6));
                            if (!var7_12.isEmpty()) {
                                var5_6.a = var7_12;
                            }
                        }
                        var7_12 = var4_5;
                        var5_6 = var1_2;
                        var8_13 = var5_6.a.playerFeet();
                        var9_14 = var5_6.a.a.a();
                        for (var10_17 = -5; var10_17 <= 5; ++var10_17) {
                            v2 = var11_18 = (Boolean)baritone.a.a().breakFromAbove.value != false ? -1 : 0;
                            while (var11_18 <= 5) {
                                for (var12_19 = -5; var12_19 <= 5; ++var12_19) {
                                    var13_21 = var8_13.x + var10_17;
                                    var14_26 = var8_13.y + var11_18;
                                    var15_30 = var8_13.z + var12_19;
                                    if (var11_18 == -1 && var13_21 == var9_14.x && var15_30 == var9_14.z || (var16_32 = var7_12.a(var13_21, var14_26, var15_30, var7_12.a.a(var13_21, var14_26, var15_30))) == null || (var17_36 = var7_12.a.a(var13_21, var14_26, var15_30)).getBlock() instanceof AirBlock || var17_36.getBlock() == Blocks.WATER || var17_36.getBlock() == Blocks.LAVA || dr.a(var17_36, var16_32, false) || !(var14_27 = RotationUtils.reachable(var5_6.a, (BlockPos)(var13_22 = new BetterBlockPos(var13_21, var14_26, var15_30)), var5_6.a.playerController().getBlockReachDistance())).isPresent()) continue;
                                    v3 /* !! */  = Optional.of(new Tuple((Object)var13_22, (Object)var14_27.get()));
                                    break block45;
                                }
                                ++var11_18;
                            }
                        }
                        v3 /* !! */  = var5_6 = Optional.empty();
                    }
                    if (v3 /* !! */ .isPresent() && var2_3 && var1_2.a.player().onGround()) {
                        var6_9 = (Rotation)((Tuple)var5_6.get()).getB();
                        var5_6 = (BetterBlockPos)((Tuple)var5_6.get()).getA();
                        var1_2.a.a.updateTarget(var6_9, true);
                        cc.a(var1_2.a, var4_5.a((BetterBlockPos)var5_6));
                        if (var1_2.a.player().isCrouching()) {
                            var1_2.a.a.setInputForceState(Input.SNEAK, true);
                        }
                        if (var1_2.a.isLookingAt((BlockPos)var5_6) || var1_2.a.playerRotations().isReallyCloseTo(var6_9)) {
                            var1_2.a.a.setInputForceState(Input.CLICK_LEFT, true);
                        }
                        return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                    }
                    var8_13 = var6_10 = new ArrayList<E>();
                    var7_12 = var4_5;
                    var5_6 = var1_2;
                    var9_14 = var5_6.a.playerFeet();
                    for (var10_17 = -5; var10_17 <= 5; ++var10_17) {
                        for (var11_18 = -5; var11_18 <= 1; ++var11_18) {
                            for (var12_19 = -5; var12_19 <= 5; ++var12_19) {
                                block47: {
                                    var13_23 = var9_14.x + var10_17;
                                    var14_28 = var9_14.y + var11_18;
                                    var15_30 = var9_14.z + var12_19;
                                    var16_33 = var7_12.a(var13_23, var14_28, var15_30, var7_12.a.a(var13_23, var14_28, var15_30));
                                    if (var16_33 == null || !cc.a(var13_23, var15_30, var17_37 = var7_12.a.a(var13_23, var14_28, var15_30), var7_12.a) || dr.a(var17_37, var16_33, false) || var11_18 == 1 && var7_12.a.a(var13_23, var14_28 + 1, var15_30).getBlock() instanceof AirBlock) continue;
                                    var8_13.add(var16_33);
                                    var18_39 = var7_12.a;
                                    var17_38 = var15_30;
                                    var16_34 = var14_28;
                                    var15_30 = var13_23;
                                    var14_29 = var16_33;
                                    var13_24 = var5_6;
                                    for (Direction var22_43 : Direction.values()) {
                                        var23_44 = new BetterBlockPos(var15_30, var16_34, var17_38).relative(var22_43);
                                        var24_45 = var18_39.a(var23_44);
                                        var23_44.y;
                                        if (cc.a(var23_44.x, var23_44.z, var24_45, (fb)var18_39) || !var14_29.canSurvive((LevelReader)var13_24.a.world(), (BlockPos)new BetterBlockPos(var15_30, var16_34, var17_38)) || !var13_24.a(new BetterBlockPos(var15_30, var16_34, var17_38), var14_29) || (var24_45 = var24_45.getShape((BlockGetter)var13_24.a.world(), (BlockPos)var23_44)).isEmpty()) continue;
                                        var24_45 = var24_45.bounds();
                                        var29_50 = var22_43;
                                        switch (dx.a[var29_50.ordinal()]) {
                                            case 1: {
                                                v4 = new Vec3[5];
                                                v4[0] = new Vec3(0.5, 1.0, 0.5);
                                                v4[1] = new Vec3(0.1, 1.0, 0.5);
                                                v4[2] = new Vec3(0.9, 1.0, 0.5);
                                                v4[3] = new Vec3(0.5, 1.0, 0.1);
                                                v5 = v4;
                                                v4[4] = new Vec3(0.5, 1.0, 0.9);
                                                break;
                                            }
                                            case 2: {
                                                v6 = new Vec3[5];
                                                v6[0] = new Vec3(0.5, 0.0, 0.5);
                                                v6[1] = new Vec3(0.1, 0.0, 0.5);
                                                v6[2] = new Vec3(0.9, 0.0, 0.5);
                                                v6[3] = new Vec3(0.5, 0.0, 0.1);
                                                v5 = v6;
                                                v6[4] = new Vec3(0.5, 0.0, 0.9);
                                                break;
                                            }
                                            case 3: 
                                            case 4: 
                                            case 5: 
                                            case 6: {
                                                var40_58 = var29_50.getStepX() == 0 ? 0.5 : (double)(1 + var29_50.getStepX()) / 2.0;
                                                var42_61 = var29_50.getStepZ() == 0 ? 0.5 : (double)(1 + var29_50.getStepZ()) / 2.0;
                                                v7 = new Vec3[2];
                                                v7[0] = new Vec3(var40_58, 0.25, var42_61);
                                                v5 = v7;
                                                v7[1] = new Vec3(var40_58, 0.75, var42_61);
                                                break;
                                            }
                                            default: {
                                                throw new IllegalStateException("Unexpected side " + String.valueOf(var29_50));
                                            }
                                        }
                                        var25_46 = v5;
                                        var26_47 = v5.length;
                                        for (var27_48 = 0; var27_48 < var26_47; ++var27_48) {
                                            block46: {
                                                var28_49 /* !! */  = var25_46[var27_48];
                                                var34_55 = (double)var23_44.x + var24_45.minX * var28_49 /* !! */ .x + var24_45.maxX * (1.0 - var28_49 /* !! */ .x);
                                                var36_56 = (double)var23_44.y + var24_45.minY * var28_49 /* !! */ .y + var24_45.maxY * (1.0 - var28_49 /* !! */ .y);
                                                var38_57 = (double)var23_44.z + var24_45.minZ * var28_49 /* !! */ .z + var24_45.maxZ * (1.0 - var28_49 /* !! */ .z);
                                                var28_49 /* !! */  = RotationUtils.calcRotationFromVec3d(RayTraceUtils.inferSneakingEyePosition((Entity)var13_24.a.player()), new Vec3(var34_55, var36_56, var38_57), var13_24.a.playerRotations());
                                                var29_50 = var13_24.a.a.getAimProcessor().peekRotation((Rotation)var28_49 /* !! */ );
                                                var30_51 = RayTraceUtils.rayTraceTowards((Entity)var13_24.a.player(), (Rotation)var29_50, var13_24.a.playerController().getBlockReachDistance(), true);
                                                if (var30_51 == null || var30_51.getType() != HitResult.Type.BLOCK || !((BlockHitResult)var30_51).getBlockPos().equals((Object)var23_44) || ((BlockHitResult)var30_51).getDirection() != var22_43.getOpposite()) continue;
                                                var42_62 = var29_50;
                                                var41_60 = var30_51;
                                                var40_59 = var14_29;
                                                var29_50 = var13_24;
                                                for (var43_63 = 0; var43_63 < 9; ++var43_63) {
                                                    var30_51 = (ItemStack)var29_50.a.player().getInventory().items.get(var43_63);
                                                    if (var30_51.isEmpty() || !(var30_51.getItem() instanceof BlockItem)) continue;
                                                    var31_52 = var29_50.a.player().getYRot();
                                                    var32_53 = var29_50.a.player().getXRot();
                                                    var29_50.a.player().setYRot(var42_62.getYaw());
                                                    var29_50.a.player().setXRot(var42_62.getPitch());
                                                    var33_54 = new BlockPlaceContext((UseOnContext)new dt(var29_50.a.world(), (Player)var29_50.a.player(), InteractionHand.MAIN_HAND, (ItemStack)var30_51, (BlockHitResult)var41_60));
                                                    var30_51 = ((BlockItem)var30_51.getItem()).getBlock().getStateForPlacement(var33_54);
                                                    var29_50.a.player().setYRot(var31_52);
                                                    var29_50.a.player().setXRot(var32_53);
                                                    if (var30_51 == null || !var33_54.canPlace() || !dr.a((BlockState)var30_51, var40_59, true)) continue;
                                                    v8 = OptionalInt.of(var43_63);
                                                    break block46;
                                                }
                                                v8 = var29_50 = OptionalInt.empty();
                                            }
                                            if (!v8.isPresent()) continue;
                                            v9 /* !! */  = Optional.of(new f(var29_50.getAsInt(), var23_44, var22_43.getOpposite(), (Rotation)var28_49 /* !! */ ));
                                            break block47;
                                        }
                                    }
                                    v9 /* !! */  = var13_24 = Optional.empty();
                                }
                                if (!v9 /* !! */ .isPresent()) continue;
                                v10 = var13_24;
                                break block48;
                            }
                        }
                    }
                    v10 = var5_6 = Optional.empty();
                }
                if (v10.isPresent() && var2_3 && var1_2.a.player().onGround() && var1_2.a <= 0) {
                    var7_12 = ((f)var5_6.get()).a;
                    var1_2.a.a.updateTarget((Rotation)var7_12, true);
                    var1_2.a.player().getInventory().selected = ((f)var5_6.get()).a;
                    var1_2.a.a.setInputForceState(Input.SNEAK, true);
                    if (var1_2.a.isLookingAt(((f)var5_6.get()).a) && ((BlockHitResult)var1_2.a.objectMouseOver()).getDirection().equals((Object)((f)var5_6.get()).a) || var1_2.a.playerRotations().isReallyCloseTo((Rotation)var7_12)) {
                        var1_2.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                    }
                    return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
                if (((Boolean)baritone.a.a().allowInventory.value).booleanValue()) {
                    var7_12 = new ArrayList<E>();
                    var5_6 = new ArrayList<E>();
                    var6_10 = var6_10.iterator();
                    block18: while (var6_10.hasNext()) {
                        var8_13 = (BlockState)var6_10.next();
                        for (var9_15 = 0; var9_15 < 9; ++var9_15) {
                            if (!dr.a((BlockState)var1_2.a.get(var9_15), (BlockState)var8_13, true)) continue;
                            var7_12.add(var9_15);
                            continue block18;
                        }
                        var5_6.add(var8_13);
                    }
                    block20: for (var6_11 = 9; var6_11 < 36; ++var6_11) {
                        var8_13 = var5_6.iterator();
                        while (var8_13.hasNext()) {
                            var9_14 = (BlockState)var8_13.next();
                            if (!dr.a((BlockState)var1_2.a.get(var6_11), var9_14, true)) continue;
                            if (var1_2.a.a.a(var6_11, (Predicate<Integer>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, contains(java.lang.Object ), (Ljava/lang/Integer;)Z)((ArrayList)var7_12))) break block20;
                            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
                        }
                    }
                }
                if ((var7_12 = var1_2.a((a)var4_5, var1_2.a.subList(0, 9), false)) != null || (var7_12 = var1_2.a((a)var4_5, (List<BlockState>)var1_2.a, true)) != null) break block49;
                if (!((Boolean)baritone.a.a().skipFailedLayers.value).booleanValue() || !((Boolean)baritone.a.a().buildInLayers.value).booleanValue() || var1_2.b * (Integer)baritone.a.a().layerHeight.value >= var1_2.b.heightY()) break;
                v11 = var1_2;
                v11.logDirect("Skipping layer that I cannot construct! Layer #" + v11.b);
                ++var1_2.b;
                ++var3_4;
            }
            var1_2.logDirect("Unable to do it. Pausing. resume to resume, cancel to cancel");
            var1_2.a = true;
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        return new fh((Goal)var7_12, PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH, (ca)var4_5);
    }

    private void a(a a2) {
        this.a = new HashSet();
        for (int i2 = 0; i2 < this.a.heightY(); ++i2) {
            for (int i3 = 0; i3 < this.a.lengthZ(); ++i3) {
                for (int i4 = 0; i4 < this.a.widthX(); ++i4) {
                    int n2;
                    int n3;
                    int n4 = i4 + this.a.getX();
                    BlockState blockState = ((ca)a2).a.a(n4, n3 = i2 + this.a.getY(), n2 = i3 + this.a.getZ());
                    if (!this.a.inSchematic(i4, i2, i3, blockState)) continue;
                    if (((ca)a2).a.a(n4, n2)) {
                        if (dr.a(((ca)a2).a.a(n4, n3, n2), this.a.desiredState(i4, i2, i3, blockState, (List<BlockState>)((Object)this.a)), false)) {
                            this.a.add(BetterBlockPos.longHash(n4, n3, n2));
                            continue;
                        }
                        ((HashSet)this.a).add((Property<?>)new BetterBlockPos(n4, n3, n2));
                        this.a.remove(BetterBlockPos.longHash(n4, n3, n2));
                        if (((HashSet)this.a).size() <= (Integer)baritone.a.a().incorrectSize.value) continue;
                        return;
                    }
                    if (this.a.contains(BetterBlockPos.longHash(n4, n3, n2))) continue;
                    ((HashSet)this.a).add((Property<?>)new BetterBlockPos(n4, n3, n2));
                    if (((HashSet)this.a).size() <= (Integer)baritone.a.a().incorrectSize.value) continue;
                    return;
                }
            }
        }
    }

    private Goal a(a a2, List<BlockState> list, boolean bl2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        HashMap hashMap = new HashMap();
        ArrayList arrayList5 = new ArrayList();
        this.a.forEach(betterBlockPos -> {
            BlockState blockState = ((ca)a2).a.a((BlockPos)betterBlockPos);
            if (blockState.getBlock() instanceof AirBlock) {
                boolean bl2;
                block6: {
                    if ((a2 = a2.a(betterBlockPos.x, betterBlockPos.y, betterBlockPos.z, blockState)) == null) {
                        arrayList5.add(betterBlockPos);
                        return;
                    }
                    List list5 = list;
                    list = a2;
                    arrayList5 = list5.iterator();
                    while (arrayList5.hasNext()) {
                        if (!dr.a((BlockState)arrayList5.next(), (BlockState)list)) continue;
                        bl2 = true;
                        break block6;
                    }
                    bl2 = false;
                }
                if (bl2) {
                    arrayList.add(betterBlockPos);
                    return;
                }
                hashMap.put(a2, 1 + hashMap.getOrDefault(a2, 0));
                return;
            }
            if (blockState.getBlock() instanceof LiquidBlock) {
                if (!cc.g(blockState)) {
                    arrayList3.add(betterBlockPos);
                    return;
                }
                arrayList4.add(betterBlockPos);
                return;
            }
            arrayList2.add(betterBlockPos);
        });
        ((AbstractSet)this.a).removeAll(arrayList5);
        list = new ArrayList<BlockState>();
        arrayList2.forEach(object2 -> {
            BetterBlockPos betterBlockPos = object2;
            object2 = a2;
            a2 = betterBlockPos;
            list.add((BlockState)((Boolean)baritone.a.a().goalBreakFromAbove.value != false && ((ca)object2).a.a(a2.above()).getBlock() instanceof AirBlock && ((ca)object2).a.a(a2.above(2)).getBlock() instanceof AirBlock ? new e(new c((BlockPos)a2), new dv(a2.above())) : new c((BlockPos)a2)));
        });
        arrayList2 = new ArrayList();
        arrayList.forEach(betterBlockPos -> {
            if (!arrayList.contains((Object)betterBlockPos.below()) && !arrayList.contains((Object)betterBlockPos.below(2))) {
                Goal goal;
                List list;
                block4: {
                    list = arrayList2;
                    arrayList2 = betterBlockPos;
                    arrayList = this;
                    if (!(((ey)object).a.world().getBlockState((BlockPos)arrayList2).getBlock() instanceof AirBlock)) {
                        goal = new d((BetterBlockPos)((Object)((Object)arrayList2)));
                    } else {
                        boolean bl2 = !(((ey)object).a.world().getBlockState(arrayList2.above()).getBlock() instanceof AirBlock);
                        BlockState blockState = ((ey)object).a.world().getBlockState((BlockPos)arrayList2);
                        Direction[] directionArray = cb.a;
                        int n2 = cb.a.length;
                        for (int i2 = 0; i2 < n2; ++i2) {
                            Direction direction = directionArray[i2];
                            if (!cc.c(((ey)object).a, arrayList2.relative(direction)) || !((dr)((Object)arrayList)).a((BlockPos)arrayList2, a2.a(arrayList2.getX(), arrayList2.getY(), arrayList2.getZ(), blockState))) continue;
                            Object object3 = arrayList2;
                            goal = new b((BetterBlockPos)((Object)((Object)object3)), object3.relative(direction), bl2);
                            break block4;
                        }
                        goal = new d((BetterBlockPos)((Object)((Object)arrayList2)));
                    }
                }
                list.add(goal);
            }
        });
        arrayList3.forEach(betterBlockPos -> arrayList2.add(new GoalBlock(betterBlockPos.above())));
        if (!arrayList2.isEmpty()) {
            return new e(new GoalComposite(arrayList2.toArray(new Goal[0])), new GoalComposite(list.toArray(new Goal[0])));
        }
        if (list.isEmpty()) {
            if (bl2 && !hashMap.isEmpty()) {
                this.logDirect("Missing materials for at least:");
                this.logDirect(hashMap.entrySet().stream().map(entry -> String.format("%sx %s", entry.getValue(), entry.getKey())).collect(Collectors.joining("\n")));
            }
            if (bl2 && !arrayList4.isEmpty()) {
                this.logDirect("Unreplaceable liquids at at least:");
                this.logDirect(arrayList4.stream().map(betterBlockPos -> String.format("%s %s %s", betterBlockPos.x, betterBlockPos.y, betterBlockPos.z)).collect(Collectors.joining("\n")));
            }
            return null;
        }
        return new GoalComposite(list.toArray(new Goal[0]));
    }

    @Override
    public final void onLostControl() {
        this.a = null;
        this.a = null;
        this.a = null;
        this.b = null;
        this.b = (Integer)baritone.a.a().startAtLayer.value;
        this.c = 0;
        this.a = false;
        this.a = null;
    }

    @Override
    public final String displayName0() {
        if (this.a) {
            return "Builder Paused";
        }
        return "Building " + this.a;
    }

    @Override
    public final Optional<Integer> getMinLayer() {
        if (((Boolean)baritone.a.a().buildInLayers.value).booleanValue()) {
            return Optional.of(this.b);
        }
        return Optional.empty();
    }

    @Override
    public final Optional<Integer> getMaxLayer() {
        if (((Boolean)baritone.a.a().buildInLayers.value).booleanValue()) {
            return Optional.of(this.d);
        }
        return Optional.empty();
    }

    final List<BlockState> a(int n2) {
        ArrayList<BlockState> arrayList = new ArrayList<BlockState>();
        for (int i2 = 0; i2 < n2; ++i2) {
            ItemStack itemStack = (ItemStack)((ey)this).a.player().getInventory().items.get(i2);
            if (itemStack.isEmpty() || !(itemStack.getItem() instanceof BlockItem)) {
                arrayList.add(Blocks.AIR.defaultBlockState());
                continue;
            }
            if ((itemStack = ((BlockItem)itemStack.getItem()).getBlock().getStateForPlacement(new BlockPlaceContext((UseOnContext)new dw(((ey)this).a.world(), (Player)((ey)this).a.player(), InteractionHand.MAIN_HAND, itemStack, new BlockHitResult(new Vec3(((ey)this).a.player().position().x, ((ey)this).a.player().position().y, ((ey)this).a.player().position().z), Direction.UP, (BlockPos)((ey)this).a.playerFeet(), false))))) != null) {
                arrayList.add((BlockState)itemStack);
                continue;
            }
            arrayList.add(Blocks.AIR.defaultBlockState());
        }
        return arrayList;
    }

    private static boolean a(BlockState object, BlockState object2) {
        if (object.getBlock() != object2.getBlock()) {
            return false;
        }
        boolean bl2 = (Boolean)baritone.a.a().buildIgnoreDirection.value;
        List list = (List)baritone.a.a().buildIgnoreProperties.value;
        if (!bl2 && list.isEmpty()) {
            return object.equals(object2);
        }
        object = object.getValues();
        object2 = object2.getValues();
        for (Property property : object.keySet()) {
            if (object.get(property) == object2.get(property) || bl2 && a.contains(property) || list.contains(property.getName())) continue;
            return false;
        }
        return true;
    }

    static boolean a(BlockState blockState, BlockState blockState2, boolean bl2) {
        if (blockState2 == null) {
            return true;
        }
        if (blockState.getBlock() instanceof LiquidBlock && ((Boolean)baritone.a.a().okIfWater.value).booleanValue()) {
            return true;
        }
        if (blockState.getBlock() instanceof AirBlock && blockState2.getBlock() instanceof AirBlock) {
            return true;
        }
        if (blockState.getBlock() instanceof AirBlock && ((List)baritone.a.a().okIfAir.value).contains(blockState2.getBlock())) {
            return true;
        }
        if (blockState2.getBlock() instanceof AirBlock && ((List)baritone.a.a().buildIgnoreBlocks.value).contains(blockState.getBlock())) {
            return true;
        }
        if (!(blockState.getBlock() instanceof AirBlock) && ((Boolean)baritone.a.a().buildIgnoreExisting.value).booleanValue() && !bl2) {
            return true;
        }
        if (((Map)baritone.a.a().buildValidSubstitutes.value).getOrDefault(blockState2.getBlock(), Collections.emptyList()).contains(blockState.getBlock()) && !bl2) {
            return true;
        }
        if (blockState.equals(blockState2)) {
            return true;
        }
        return dr.a(blockState, blockState2);
    }

    private /* synthetic */ boolean a(BetterBlockPos betterBlockPos) {
        return betterBlockPos.distSqr((Vec3i)((ey)this).a.player().blockPosition()) > 200.0;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public final class a
    extends ca {
        private final List<BlockState> b;
        private final ISchematic a;
        private final int e;
        private final int f;
        private final int g;
        private /* synthetic */ dr a;

        public a(dr dr2) {
            this.a = dr2;
            super(((ey)dr2).a, true);
            this.b = dr2.a(9);
            this.a = dr2.a;
            this.e = dr2.a.getX();
            this.f = dr2.a.getY();
            this.g = dr2.a.getZ();
            ((ca)this).e += 10.0;
            this.d = 1.0;
        }

        final BlockState a(int n2, int n3, int n4, BlockState blockState) {
            if (this.a.inSchematic(n2 - this.e, n3 - this.f, n4 - this.g, blockState)) {
                return this.a.desiredState(n2 - this.e, n3 - this.f, n4 - this.g, blockState, (List<BlockState>)((Object)this.a.a));
            }
            return null;
        }

        @Override
        public final double a(int n2, int n3, int n4, BlockState blockState) {
            if (!((ca)this).a.b(n2, n4)) {
                return 1000000.0;
            }
            BlockState blockState2 = this.a(n2, n3, n4, blockState);
            if (blockState2 != null) {
                if (blockState2.getBlock() instanceof AirBlock) {
                    return ((ca)this).a * (Double)baritone.a.a().placeIncorrectBlockPenaltyMultiplier.value;
                }
                if (this.b.contains(blockState2)) {
                    return 0.0;
                }
                if (!this.c) {
                    return 1000000.0;
                }
                return ((ca)this).a * 1.5 * (Double)baritone.a.a().placeIncorrectBlockPenaltyMultiplier.value;
            }
            if (this.c) {
                return ((ca)this).a;
            }
            return 1000000.0;
        }

        @Override
        public final double b(int n2, int n3, int n4, BlockState blockState) {
            if (!((ca)this).e && !((ca)this).a.contains(blockState.getBlock())) {
                return 1000000.0;
            }
            blockState = this.a(n2, n3, n4, blockState);
            if (blockState != null) {
                if (blockState.getBlock() instanceof AirBlock) {
                    return 1.0;
                }
                if (dr.a(((ca)this).a.a(n2, n3, n4), blockState, false)) {
                    return (Double)baritone.a.a().breakCorrectBlockPenaltyMultiplier.value;
                }
                return 1.0;
            }
            return 1.0;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class f {
        final int a;
        final BetterBlockPos a;
        final Direction a;
        final Rotation a;

        public f(int n2, BetterBlockPos betterBlockPos, Direction direction, Rotation rotation) {
            this.a = n2;
            this.a = betterBlockPos;
            this.a = direction;
            this.a = rotation;
        }
    }

    public static final class e
    implements Goal {
        private final Goal a;
        private final Goal b;

        public e(Goal goal, Goal goal2) {
            this.a = goal;
            this.b = goal2;
        }

        @Override
        public final boolean isInGoal(int n2, int n3, int n4) {
            return this.a.isInGoal(n2, n3, n4) || this.b.isInGoal(n2, n3, n4);
        }

        @Override
        public final double heuristic(int n2, int n3, int n4) {
            return this.a.heuristic(n2, n3, n4);
        }

        public final boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            object = (e)object;
            return Objects.equals(this.a, ((e)object).a) && Objects.equals(this.b, ((e)object).b);
        }

        public final int hashCode() {
            return (1544707182 + this.a.hashCode()) * -80327868 + this.b.hashCode();
        }

        public final String toString() {
            return "JankyComposite Primary: " + String.valueOf(this.a) + " Fallback: " + String.valueOf(this.b);
        }
    }

    public static final class d
    extends GoalBlock {
        public d(BetterBlockPos betterBlockPos) {
            super(betterBlockPos.above());
        }

        @Override
        public final double heuristic(int n2, int n3, int n4) {
            return (double)(this.y * 100) + super.heuristic(n2, n3, n4);
        }

        @Override
        public final int hashCode() {
            return super.hashCode() * 1910811835;
        }

        @Override
        public final String toString() {
            return String.format("GoalPlace{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z));
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class b
    extends GoalGetToBlock {
        private boolean a;
        private BlockPos a;

        public b(BetterBlockPos betterBlockPos, BlockPos blockPos, boolean bl2) {
            super(betterBlockPos);
            this.a = blockPos;
            this.a = bl2;
        }

        @Override
        public final boolean isInGoal(int n2, int n3, int n4) {
            if (n2 == this.x && n3 == this.y && n4 == this.z) {
                return false;
            }
            if (n2 == this.a.getX() && n3 == this.a.getY() && n4 == this.a.getZ()) {
                return false;
            }
            if (!this.a && n3 == this.y - 1) {
                return false;
            }
            if (n3 < this.y - 1) {
                return false;
            }
            return super.isInGoal(n2, n3, n4);
        }

        @Override
        public final double heuristic(int n2, int n3, int n4) {
            return (double)(this.y * 100) + super.heuristic(n2, n3, n4);
        }

        @Override
        public final boolean equals(Object object) {
            if (!super.equals(object)) {
                return false;
            }
            object = (b)object;
            return this.a == ((b)object).a && Objects.equals(this.a, ((b)object).a);
        }

        @Override
        public final int hashCode() {
            return ((-2112107180 + super.hashCode()) * 1730799370 + (int)BetterBlockPos.longHash(this.a.getX(), this.a.getY(), this.a.getZ())) * 260592149 + (this.a ? -1314802005 : 1565710265);
        }

        @Override
        public final String toString() {
            return String.format("GoalAdjacent{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z));
        }
    }

    public static final class c
    extends GoalGetToBlock {
        public c(BlockPos blockPos) {
            super(blockPos);
        }

        @Override
        public final boolean isInGoal(int n2, int n3, int n4) {
            if (n3 > this.y) {
                return false;
            }
            return super.isInGoal(n2, n3, n4);
        }

        @Override
        public final String toString() {
            return String.format("GoalBreak{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z));
        }

        @Override
        public final int hashCode() {
            return super.hashCode() * 1636324008;
        }
    }
}

