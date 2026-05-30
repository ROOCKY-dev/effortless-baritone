/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.GuiMessageTag
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.Vec3i
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package baritone.api;

import baritone.api.utils.Helper;
import baritone.api.utils.NotificationHelper;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.TypeUtils;
import baritone.api.utils.gui.BaritoneToast;
import java.awt.Color;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Settings {
    private static final Logger LOGGER = LoggerFactory.getLogger((String)"Baritone");
    public final Setting<Boolean> allowBreak = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<List<Block>> allowBreakAnyway = new Setting(new ArrayList());
    public final Setting<Boolean> allowSprint = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> allowPlace = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> allowPlaceInFluidsSource = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> allowPlaceInFluidsFlow = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> allowInventory = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Integer> ticksBetweenInventoryMoves = new Setting<Integer>(1);
    public final Setting<Boolean> inventoryMoveOnlyIfStationary = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> assumeExternalAutoTool = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> autoTool = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Double> blockPlacementPenalty = new Setting<Double>(20.0);
    public final Setting<Double> blockBreakAdditionalPenalty = new Setting<Double>(2.0);
    public final Setting<Double> jumpPenalty = new Setting<Double>(2.0);
    public final Setting<Double> walkOnWaterOnePenalty = new Setting<Double>(3.0);
    public final Setting<Boolean> strictLiquidCheck = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> allowWaterBucketFall = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> assumeWalkOnWater = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> assumeWalkOnLava = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> assumeStep = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> assumeSafeWalk = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> allowJumpAtBuildLimit = new Setting<Boolean>(Boolean.FALSE);
    @Deprecated
    @JavaOnly
    public final Setting<Boolean> allowJumpAt256 = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> allowParkourAscend = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> allowDiagonalDescend = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> allowDiagonalAscend = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> allowDownward = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<List<Item>> acceptableThrowawayItems = new Setting<ArrayList<Item>>(new ArrayList<Item>(Arrays.asList(Blocks.DIRT.asItem(), Blocks.COBBLESTONE.asItem(), Blocks.NETHERRACK.asItem(), Blocks.STONE.asItem())));
    public final Setting<List<Block>> blocksToAvoid = new Setting(new ArrayList());
    public final Setting<List<Block>> blocksToDisallowBreaking = new Setting(new ArrayList());
    public final Setting<List<Block>> blocksToAvoidBreaking = new Setting<ArrayList<Block>>(new ArrayList<Block>(Arrays.asList(Blocks.CRAFTING_TABLE, Blocks.FURNACE, Blocks.CHEST, Blocks.TRAPPED_CHEST)));
    public final Setting<Double> avoidBreakingMultiplier = new Setting<Double>(0.1);
    public final Setting<List<Block>> buildIgnoreBlocks = new Setting<ArrayList<Block>>(new ArrayList<Block>(Arrays.asList(new Block[0])));
    public final Setting<List<Block>> buildSkipBlocks = new Setting<ArrayList<Block>>(new ArrayList<Block>(Arrays.asList(new Block[0])));
    public final Setting<Map<Block, List<Block>>> buildValidSubstitutes = new Setting(new HashMap());
    public final Setting<Map<Block, List<Block>>> buildSubstitutes = new Setting(new HashMap());
    public final Setting<List<Block>> okIfAir = new Setting<ArrayList<Block>>(new ArrayList<Block>(Arrays.asList(new Block[0])));
    public final Setting<Boolean> buildIgnoreExisting = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> buildIgnoreDirection = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<List<String>> buildIgnoreProperties = new Setting<ArrayList<String>>(new ArrayList<String>(Arrays.asList(new String[0])));
    public final Setting<Boolean> avoidUpdatingFallingBlocks = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> allowVines = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> allowWalkOnBottomSlab = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> allowParkour = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> allowParkourPlace = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> considerPotionEffects = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> sprintAscends = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> overshootTraverse = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> pauseMiningForFallingBlocks = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Integer> rightClickSpeed = new Setting<Integer>(4);
    public final Setting<Double> randomLooking113 = new Setting<Double>(2.0);
    public final Setting<Float> blockReachDistance = new Setting<Float>(Float.valueOf(4.5f));
    public final Setting<Integer> blockBreakSpeed = new Setting<Integer>(6);
    public final Setting<Double> randomLooking = new Setting<Double>(0.01);
    public final Setting<Double> costHeuristic = new Setting<Double>(3.563);
    public final Setting<Integer> pathingMaxChunkBorderFetch = new Setting<Integer>(50);
    public final Setting<Double> backtrackCostFavoringCoefficient = new Setting<Double>(0.5);
    public final Setting<Boolean> avoidance = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Double> mobSpawnerAvoidanceCoefficient = new Setting<Double>(2.0);
    public final Setting<Integer> mobSpawnerAvoidanceRadius = new Setting<Integer>(16);
    public final Setting<Double> mobAvoidanceCoefficient = new Setting<Double>(1.5);
    public final Setting<Integer> mobAvoidanceRadius = new Setting<Integer>(8);
    public final Setting<Boolean> rightClickContainerOnArrival = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> enterPortal = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> minimumImprovementRepropagation = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> cutoffAtLoadBoundary = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Double> maxCostIncrease = new Setting<Double>(10.0);
    public final Setting<Integer> costVerificationLookahead = new Setting<Integer>(5);
    public final Setting<Double> pathCutoffFactor = new Setting<Double>(0.9);
    public final Setting<Integer> pathCutoffMinimumLength = new Setting<Integer>(30);
    public final Setting<Integer> planningTickLookahead = new Setting<Integer>(150);
    public final Setting<Integer> pathingMapDefaultSize = new Setting<Integer>(1024);
    public final Setting<Float> pathingMapLoadFactor = new Setting<Float>(Float.valueOf(0.75f));
    public final Setting<Integer> maxFallHeightNoWater = new Setting<Integer>(3);
    public final Setting<Integer> maxFallHeightBucket = new Setting<Integer>(20);
    public final Setting<Boolean> allowOvershootDiagonalDescend = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> simplifyUnloadedYCoord = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> repackOnAnyBlockChange = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Integer> movementTimeoutTicks = new Setting<Integer>(100);
    public final Setting<Long> primaryTimeoutMS = new Setting<Long>(500L);
    public final Setting<Long> failureTimeoutMS = new Setting<Long>(2000L);
    public final Setting<Long> planAheadPrimaryTimeoutMS = new Setting<Long>(4000L);
    public final Setting<Long> planAheadFailureTimeoutMS = new Setting<Long>(5000L);
    public final Setting<Boolean> slowPath = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Long> slowPathTimeDelayMS = new Setting<Long>(100L);
    public final Setting<Long> slowPathTimeoutMS = new Setting<Long>(40000L);
    public final Setting<Boolean> doBedWaypoints = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> doDeathWaypoints = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> chunkCaching = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> pruneRegionsFromRAM = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Integer> chunkPackerQueueMaxSize = new Setting<Integer>(2000);
    public final Setting<Boolean> backfill = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> logAsToast = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Long> toastTimer = new Setting<Long>(5000L);
    public final Setting<Boolean> chatDebug = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> chatControl = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> chatControlAnyway = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> renderPath = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> renderPathAsLine = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> renderGoal = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> renderGoalAnimated = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> renderSelectionBoxes = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> renderGoalIgnoreDepth = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> renderGoalXZBeacon = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> renderSelectionBoxesIgnoreDepth = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> renderPathIgnoreDepth = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Float> pathRenderLineWidthPixels = new Setting<Float>(Float.valueOf(5.0f));
    public final Setting<Float> goalRenderLineWidthPixels = new Setting<Float>(Float.valueOf(3.0f));
    public final Setting<Boolean> fadePath = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> freeLook = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> blockFreeLook = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> elytraFreeLook = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> smoothLook = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> elytraSmoothLook = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Integer> smoothLookTicks = new Setting<Integer>(5);
    public final Setting<Boolean> remainWithExistingLookDirection = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> antiCheatCompatibility = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> pathThroughCachedOnly = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> sprintInWater = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> blacklistClosestOnFailure = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> renderCachedChunks = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Float> cachedChunksOpacity = new Setting<Float>(Float.valueOf(0.5f));
    public final Setting<Boolean> prefixControl = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<String> prefix = new Setting<String>("#");
    public final Setting<Boolean> shortBaritonePrefix = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> useMessageTag = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> echoCommands = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> censorCoordinates = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> censorRanCommands = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> itemSaver = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Integer> itemSaverThreshold = new Setting<Integer>(10);
    public final Setting<Boolean> preferSilkTouch = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> walkWhileBreaking = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> splicePath = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Integer> maxPathHistoryLength = new Setting<Integer>(300);
    public final Setting<Integer> pathHistoryCutoffAmount = new Setting<Integer>(50);
    public final Setting<Integer> mineGoalUpdateInterval = new Setting<Integer>(5);
    public final Setting<Integer> maxCachedWorldScanCount = new Setting<Integer>(10);
    public final Setting<Integer> mineMaxOreLocationsCount = new Setting<Integer>(64);
    public final Setting<Integer> minYLevelWhileMining = new Setting<Integer>(0);
    public final Setting<Integer> maxYLevelWhileMining = new Setting<Integer>(2031);
    public final Setting<Boolean> allowOnlyExposedOres = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Integer> allowOnlyExposedOresDistance = new Setting<Integer>(1);
    public final Setting<Boolean> exploreForBlocks = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Integer> worldExploringChunkOffset = new Setting<Integer>(0);
    public final Setting<Integer> exploreChunkSetMinimumSize = new Setting<Integer>(10);
    public final Setting<Integer> exploreMaintainY = new Setting<Integer>(64);
    public final Setting<Boolean> replantCrops = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> replantNetherWart = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Integer> farmMaxScanSize = new Setting<Integer>(256);
    public final Setting<Boolean> extendCacheOnThreshold = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> buildInLayers = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> layerOrder = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Integer> layerHeight = new Setting<Integer>(1);
    public final Setting<Integer> startAtLayer = new Setting<Integer>(0);
    public final Setting<Boolean> skipFailedLayers = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> buildOnlySelection = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Vec3i> buildRepeat = new Setting<Vec3i>(new Vec3i(0, 0, 0));
    public final Setting<Integer> buildRepeatCount = new Setting<Integer>(-1);
    public final Setting<Boolean> buildRepeatSneaky = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> breakFromAbove = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> goalBreakFromAbove = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> mapArtMode = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> okIfWater = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Integer> incorrectSize = new Setting<Integer>(100);
    public final Setting<Double> breakCorrectBlockPenaltyMultiplier = new Setting<Double>(10.0);
    public final Setting<Double> placeIncorrectBlockPenaltyMultiplier = new Setting<Double>(2.0);
    public final Setting<Boolean> schematicOrientationX = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> schematicOrientationY = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> schematicOrientationZ = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Rotation> buildSchematicRotation = new Setting<Rotation>(Rotation.NONE);
    public final Setting<Mirror> buildSchematicMirror = new Setting<Mirror>(Mirror.NONE);
    public final Setting<String> schematicFallbackExtension = new Setting<String>("schematic");
    public final Setting<Integer> builderTickScanRadius = new Setting<Integer>(5);
    public final Setting<Boolean> mineScanDroppedItems = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Long> mineDropLoiterDurationMSThanksLouca = new Setting<Long>(250L);
    public final Setting<Boolean> distanceTrim = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> cancelOnGoalInvalidation = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Integer> axisHeight = new Setting<Integer>(120);
    public final Setting<Boolean> disconnectOnArrival = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> legitMine = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Integer> legitMineYLevel = new Setting<Integer>(-59);
    public final Setting<Boolean> legitMineIncludeDiagonals = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Boolean> forceInternalMining = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Boolean> internalMiningAirException = new Setting<Boolean>(Boolean.TRUE);
    public final Setting<Double> followOffsetDistance = new Setting<Double>(0.0);
    public final Setting<Float> followOffsetDirection = new Setting<Float>(Float.valueOf(0.0f));
    public final Setting<Integer> followRadius = new Setting<Integer>(3);
    public final Setting<Integer> followTargetMaxDistance = new Setting<Integer>(0);
    public final Setting<Boolean> disableCompletionCheck = new Setting<Boolean>(Boolean.FALSE);
    public final Setting<Long> cachedChunksExpirySeconds = new Setting<Long>(-1L);
    @JavaOnly
    public final Setting<Consumer<Component>> logger;
    @JavaOnly
    public final Setting<BiConsumer<String, Boolean>> notifier;
    @JavaOnly
    public final Setting<BiConsumer<Component, Component>> toaster;
    public final Setting<Boolean> verboseCommandExceptions;
    public final Setting<Double> yLevelBoxSize;
    public final Setting<Color> colorCurrentPath;
    public final Setting<Color> colorNextPath;
    public final Setting<Color> colorBlocksToBreak;
    public final Setting<Color> colorBlocksToPlace;
    public final Setting<Color> colorBlocksToWalkInto;
    public final Setting<Color> colorBestPathSoFar;
    public final Setting<Color> colorMostRecentConsidered;
    public final Setting<Color> colorGoalBox;
    public final Setting<Color> colorInvertedGoalBox;
    public final Setting<Color> colorSelection;
    public final Setting<Color> colorSelectionPos1;
    public final Setting<Color> colorSelectionPos2;
    public final Setting<Float> selectionOpacity;
    public final Setting<Float> selectionLineWidth;
    public final Setting<Boolean> renderSelection;
    public final Setting<Boolean> renderSelectionIgnoreDepth;
    public final Setting<Boolean> renderSelectionCorners;
    public final Setting<Boolean> useSwordToMine;
    public final Setting<Boolean> desktopNotifications;
    public final Setting<Boolean> notificationOnPathComplete;
    public final Setting<Boolean> notificationOnFarmFail;
    public final Setting<Boolean> notificationOnBuildFinished;
    public final Setting<Boolean> notificationOnExploreFinished;
    public final Setting<Boolean> notificationOnMineFail;
    public final Setting<Integer> elytraSimulationTicks;
    public final Setting<Integer> elytraPitchRange;
    public final Setting<Double> elytraFireworkSpeed;
    public final Setting<Integer> elytraFireworkSetbackUseDelay;
    public final Setting<Double> elytraMinimumAvoidance;
    public final Setting<Boolean> elytraConserveFireworks;
    public final Setting<Boolean> elytraRenderRaytraces;
    public final Setting<Boolean> elytraRenderHitboxRaytraces;
    public final Setting<Boolean> elytraRenderSimulation;
    public final Setting<Boolean> elytraAutoJump;
    public final Setting<Long> elytraNetherSeed;
    public final Setting<Boolean> elytraPredictTerrain;
    public final Setting<Boolean> elytraAutoSwap;
    public final Setting<Integer> elytraMinimumDurability;
    public final Setting<Integer> elytraMinFireworksBeforeLanding;
    public final Setting<Boolean> elytraAllowEmergencyLand;
    public final Setting<Long> elytraTimeBetweenCacheCullSecs;
    public final Setting<Integer> elytraCacheCullDistance;
    public final Setting<Boolean> elytraAllowLandOnNetherFortress;
    public final Setting<Boolean> elytraTermsAccepted;
    public final Setting<Boolean> elytraChatSpam;
    public final Map<String, Setting<?>> byLowerName;
    public final List<Setting<?>> allSettings;
    public final Map<Setting<?>, Type> settingTypes;

    Settings() {
        Settings settings = this;
        this.logger = settings.new Setting<Consumer<Component>>(component -> {
            try {
                GuiMessageTag guiMessageTag = (Boolean)this.useMessageTag.value != false ? Helper.MESSAGE_TAG : null;
                Minecraft.getInstance().gui.getChat().addMessage(component, null, guiMessageTag);
                return;
            }
            catch (Throwable throwable) {
                LOGGER.warn("Failed to log message to chat: " + component.getString(), throwable);
                return;
            }
        });
        this.notifier = new Setting<BiConsumer<String, Boolean>>(NotificationHelper::notify);
        this.toaster = new Setting<BiConsumer<Component, Component>>(BaritoneToast::addOrUpdate);
        this.verboseCommandExceptions = new Setting<Boolean>(Boolean.FALSE);
        this.yLevelBoxSize = new Setting<Double>(15.0);
        this.colorCurrentPath = new Setting<Color>(Color.RED);
        this.colorNextPath = new Setting<Color>(Color.MAGENTA);
        this.colorBlocksToBreak = new Setting<Color>(Color.RED);
        this.colorBlocksToPlace = new Setting<Color>(Color.GREEN);
        this.colorBlocksToWalkInto = new Setting<Color>(Color.MAGENTA);
        this.colorBestPathSoFar = new Setting<Color>(Color.BLUE);
        this.colorMostRecentConsidered = new Setting<Color>(Color.CYAN);
        this.colorGoalBox = new Setting<Color>(Color.GREEN);
        this.colorInvertedGoalBox = new Setting<Color>(Color.RED);
        this.colorSelection = new Setting<Color>(Color.CYAN);
        this.colorSelectionPos1 = new Setting<Color>(Color.BLACK);
        this.colorSelectionPos2 = new Setting<Color>(Color.ORANGE);
        this.selectionOpacity = new Setting<Float>(Float.valueOf(0.5f));
        this.selectionLineWidth = new Setting<Float>(Float.valueOf(2.0f));
        this.renderSelection = new Setting<Boolean>(Boolean.TRUE);
        this.renderSelectionIgnoreDepth = new Setting<Boolean>(Boolean.TRUE);
        this.renderSelectionCorners = new Setting<Boolean>(Boolean.TRUE);
        this.useSwordToMine = new Setting<Boolean>(Boolean.TRUE);
        this.desktopNotifications = new Setting<Boolean>(Boolean.FALSE);
        this.notificationOnPathComplete = new Setting<Boolean>(Boolean.TRUE);
        this.notificationOnFarmFail = new Setting<Boolean>(Boolean.TRUE);
        this.notificationOnBuildFinished = new Setting<Boolean>(Boolean.TRUE);
        this.notificationOnExploreFinished = new Setting<Boolean>(Boolean.TRUE);
        this.notificationOnMineFail = new Setting<Boolean>(Boolean.TRUE);
        this.elytraSimulationTicks = new Setting<Integer>(20);
        this.elytraPitchRange = new Setting<Integer>(25);
        this.elytraFireworkSpeed = new Setting<Double>(1.2);
        this.elytraFireworkSetbackUseDelay = new Setting<Integer>(15);
        this.elytraMinimumAvoidance = new Setting<Double>(0.2);
        this.elytraConserveFireworks = new Setting<Boolean>(Boolean.FALSE);
        this.elytraRenderRaytraces = new Setting<Boolean>(Boolean.FALSE);
        this.elytraRenderHitboxRaytraces = new Setting<Boolean>(Boolean.FALSE);
        this.elytraRenderSimulation = new Setting<Boolean>(Boolean.TRUE);
        this.elytraAutoJump = new Setting<Boolean>(Boolean.FALSE);
        this.elytraNetherSeed = new Setting<Long>(146008555100680L);
        this.elytraPredictTerrain = new Setting<Boolean>(Boolean.FALSE);
        this.elytraAutoSwap = new Setting<Boolean>(Boolean.TRUE);
        this.elytraMinimumDurability = new Setting<Integer>(5);
        this.elytraMinFireworksBeforeLanding = new Setting<Integer>(5);
        this.elytraAllowEmergencyLand = new Setting<Boolean>(Boolean.TRUE);
        this.elytraTimeBetweenCacheCullSecs = new Setting<Long>(TimeUnit.MINUTES.toSeconds(3L));
        this.elytraCacheCullDistance = new Setting<Integer>(5000);
        this.elytraAllowLandOnNetherFortress = new Setting<Boolean>(Boolean.FALSE);
        this.elytraTermsAccepted = new Setting<Boolean>(Boolean.FALSE);
        this.elytraChatSpam = new Setting<Boolean>(Boolean.FALSE);
        Field[] fieldArray = this.getClass().getFields();
        HashMap<String, Setting> hashMap = new HashMap<String, Setting>();
        ArrayList<Setting> arrayList = new ArrayList<Setting>();
        HashMap<Setting, Type> hashMap2 = new HashMap<Setting, Type>();
        try {
            for (Field field : fieldArray) {
                String string;
                if (!field.getType().equals(Setting.class)) continue;
                Setting setting = (Setting)field.get(this);
                setting.name = string = field.getName();
                setting.javaOnly = field.isAnnotationPresent(JavaOnly.class);
                if (hashMap.containsKey(string = string.toLowerCase())) {
                    throw new IllegalStateException("Duplicate setting name");
                }
                hashMap.put(string, setting);
                arrayList.add(setting);
                hashMap2.put(setting, ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0]);
            }
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalStateException(illegalAccessException);
        }
        this.byLowerName = Collections.unmodifiableMap(hashMap);
        this.allSettings = Collections.unmodifiableList(arrayList);
        this.settingTypes = Collections.unmodifiableMap(hashMap2);
    }

    public final <T> List<Setting<T>> getAllValuesByType(Class<T> clazz) {
        ArrayList<Setting<T>> arrayList = new ArrayList<Setting<T>>();
        for (Setting<?> setting : this.allSettings) {
            if (!setting.getValueClass().equals(clazz)) continue;
            arrayList.add(setting);
        }
        return arrayList;
    }

    public final class Setting<T> {
        public T value;
        public final T defaultValue;
        String name;
        boolean javaOnly;

        Setting(T t) {
            if (t == null) {
                throw new IllegalArgumentException("Cannot determine value type class from null");
            }
            this.value = t;
            this.defaultValue = t;
            this.javaOnly = false;
        }

        @Deprecated
        public final T get() {
            return this.value;
        }

        public final String getName() {
            return this.name;
        }

        public final Class<T> getValueClass() {
            return TypeUtils.resolveBaseClass(this.getType());
        }

        public final String toString() {
            return SettingsUtil.settingToString(this);
        }

        public final void reset() {
            this.value = this.defaultValue;
        }

        public final Type getType() {
            return Settings.this.settingTypes.get(this);
        }

        public final boolean isJavaOnly() {
            return this.javaOnly;
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    static @interface JavaOnly {
    }
}
