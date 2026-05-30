/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.ImmutableSet
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.commands.Commands$CommandSelection
 *  net.minecraft.core.LayeredRegistryAccess
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.RegistryAccess$Frozen
 *  net.minecraft.resources.RegistryDataLoader
 *  net.minecraft.resources.RegistryDataLoader$RegistryData
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.RegistryLayer
 *  net.minecraft.server.ReloadableServerRegistries$Holder
 *  net.minecraft.server.ReloadableServerResources
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.progress.ChunkProgressListener
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.VanillaPackResources
 *  net.minecraft.server.packs.repository.PackRepository
 *  net.minecraft.server.packs.repository.ServerPacksSource
 *  net.minecraft.server.packs.resources.MultiPackResourceManager
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.world.RandomSequences
 *  net.minecraft.world.flag.FeatureFlagSet
 *  net.minecraft.world.flag.FeatureFlags
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.CustomSpawner
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.dimension.LevelStem
 *  net.minecraft.world.level.storage.LevelStorageSource$LevelStorageAccess
 *  net.minecraft.world.level.storage.ServerLevelData
 *  net.minecraft.world.level.storage.loot.BuiltInLootTables
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootParams
 *  net.minecraft.world.level.storage.loot.LootParams$Builder
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.Vec3
 */
package baritone.api.utils;

import baritone.api.utils.BlockUtils;
import baritone.api.utils.accessor.IItemStack;
import baritone.api.utils.accessor.ILootTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import sun.misc.Unsafe;

public final class BlockOptionalMeta {
    private static final Pattern PATTERN = Pattern.compile("^(?<id>.+?)(?:\\[(?<properties>.+?)?\\])?$");
    private final Block block;
    private final String propertiesDescription;
    private final Set<BlockState> blockstates;
    private final ImmutableSet<Integer> stateHashes;
    private final ImmutableSet<Integer> stackHashes;
    private static Map<Block, List<Item>> drops = new HashMap<Block, List<Item>>();
    private static Method getVanillaServerPack;

    public BlockOptionalMeta(@Nonnull Block block) {
        this.block = block;
        this.propertiesDescription = "{}";
        this.blockstates = BlockOptionalMeta.getStates(block, Collections.emptyMap());
        this.stateHashes = BlockOptionalMeta.getStateHashes(this.blockstates);
        this.stackHashes = BlockOptionalMeta.getStackHashes(this.blockstates);
    }

    public BlockOptionalMeta(@Nonnull String object) {
        object = PATTERN.matcher((CharSequence)object);
        if (!((Matcher)object).find()) {
            throw new IllegalArgumentException("invalid block selector");
        }
        this.block = BlockUtils.stringToBlockRequired(((Matcher)object).group("id"));
        Map map = (object = ((Matcher)object).group("properties")) == null || ((String)object).equals("") ? Collections.emptyMap() : BlockOptionalMeta.parseProperties(this.block, (String)object);
        this.propertiesDescription = object == null ? "{}" : "{" + ((String)object).replace("=", ":") + "}";
        this.blockstates = BlockOptionalMeta.getStates(this.block, map);
        this.stateHashes = BlockOptionalMeta.getStateHashes(this.blockstates);
        this.stackHashes = BlockOptionalMeta.getStackHashes(this.blockstates);
    }

    private static <C extends Comparable<C>, P extends Property<C>> P castToIProperty(Object object) {
        return (P)((Property)object);
    }

    private static Map<Property<?>, ?> parseProperties(Block block, String stringArray) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (String string : stringArray.split(",")) {
            Object object = string.split("=");
            if (((String[])object).length != 2) {
                throw new IllegalArgumentException(String.format("\"%s\" is not a valid property-value pair", string));
            }
            string = object[0];
            object = object[1];
            string = block.getStateDefinition().getProperty(string);
            object = (Comparable)BlockOptionalMeta.castToIProperty(string).getValue((String)object).orElseThrow(() -> BlockOptionalMeta.lambda$parseProperties$0((String)object, (Property)string, block));
            builder.put((Object)string, object);
        }
        return builder.build();
    }

    private static Set<BlockState> getStates(@Nonnull Block block, @Nonnull Map<Property<?>, ?> map) {
        return block.getStateDefinition().getPossibleStates().stream().filter(blockState -> map.entrySet().stream().allMatch(entry -> blockState.getValue((Property)entry.getKey()) == entry.getValue())).collect(Collectors.toSet());
    }

    private static ImmutableSet<Integer> getStateHashes(Set<BlockState> set) {
        return ImmutableSet.copyOf((Object[])((Integer[])set.stream().map(Object::hashCode).toArray(Integer[]::new)));
    }

    private static ImmutableSet<Integer> getStackHashes(Set<BlockState> set) {
        return ImmutableSet.copyOf((Object[])((Integer[])set.stream().flatMap(blockState -> BlockOptionalMeta.drops(blockState.getBlock()).stream().map(item -> new ItemStack((ItemLike)item, 1))).map(itemStack -> ((IItemStack)itemStack).getBaritoneHash()).toArray(Integer[]::new)));
    }

    public final Block getBlock() {
        return this.block;
    }

    public final boolean matches(@Nonnull Block block) {
        return block == this.block;
    }

    public final boolean matches(@Nonnull BlockState blockState) {
        return blockState.getBlock() == this.block && this.stateHashes.contains((Object)blockState.hashCode());
    }

    public final boolean matches(ItemStack itemStack) {
        int n2 = ((IItemStack)itemStack).getBaritoneHash() - itemStack.getDamageValue();
        return this.stackHashes.contains((Object)n2);
    }

    public final String toString() {
        return String.format("BlockOptionalMeta{block=%s,properties=%s}", this.block, this.propertiesDescription);
    }

    public final BlockState getAnyBlockState() {
        if (this.blockstates.size() > 0) {
            return this.blockstates.iterator().next();
        }
        return null;
    }

    public final Set<BlockState> getAllBlockStates() {
        return this.blockstates;
    }

    public final Set<Integer> stackHashes() {
        return this.stackHashes;
    }

    private static VanillaPackResources getVanillaServerPack() {
        if (getVanillaServerPack == null) {
            getVanillaServerPack = Arrays.stream(ServerPacksSource.class.getDeclaredMethods()).filter(method -> method.getReturnType() == VanillaPackResources.class).findFirst().orElseThrow();
            getVanillaServerPack.setAccessible(true);
        }
        try {
            return (VanillaPackResources)getVanillaServerPack.invoke(null, new Object[0]);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static synchronized List<Item> drops(Block block) {
        Block block3 = block;
        return drops.computeIfAbsent(block3, block2 -> {
            if (block2.getLootTable().location().equals((Object)BuiltInLootTables.EMPTY.location())) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            try {
                ServerLevelStub serverLevelStub = ServerLevelStub.fastCreate();
                block3 = new LootParams.Builder((ServerLevel)serverLevelStub).withParameter(LootContextParams.ORIGIN, (Object)Vec3.ZERO).withParameter(LootContextParams.BLOCK_STATE, (Object)block3.defaultBlockState()).withParameter(LootContextParams.TOOL, (Object)new ItemStack((ItemLike)Items.NETHERITE_PICKAXE, 1));
                BlockOptionalMeta.getDrops(block2, (LootParams.Builder)block3).stream().map(ItemStack::getItem).forEach(arrayList::add);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            return arrayList;
        });
    }

    private static List<ItemStack> getDrops(Block block, LootParams.Builder builder) {
        ResourceKey resourceKey = block.getLootTable();
        if (resourceKey == BuiltInLootTables.EMPTY) {
            return Collections.emptyList();
        }
        block = builder.withParameter(LootContextParams.BLOCK_STATE, (Object)block.defaultBlockState()).create(LootContextParamSets.BLOCK);
        return ((ILootTable)((ServerLevelStub)block.getLevel()).holder().getLootTable(resourceKey)).invokeGetRandomItems(new LootContext.Builder((LootParams)block).withOptionalRandomSeed(1L).create(null));
    }

    private static /* synthetic */ IllegalArgumentException lambda$parseProperties$0(String string, Property property, Block block) {
        return new IllegalArgumentException(String.format("\"%s\" is not a valid value for %s on %s", string, property, block));
    }

    public static class ServerLevelStub
    extends ServerLevel {
        private static Minecraft client = Minecraft.getInstance();
        private static Unsafe unsafe = ServerLevelStub.getUnsafe();
        private static CompletableFuture<RegistryAccess> registryAccess = ServerLevelStub.load();

        public ServerLevelStub(MinecraftServer minecraftServer, Executor executor, LevelStorageSource.LevelStorageAccess levelStorageAccess, ServerLevelData serverLevelData, ResourceKey<Level> resourceKey, LevelStem levelStem, ChunkProgressListener chunkProgressListener, boolean bl2, long l2, List<CustomSpawner> list, boolean bl3, @Nullable RandomSequences randomSequences) {
            super(minecraftServer, executor, levelStorageAccess, serverLevelData, resourceKey, levelStem, chunkProgressListener, bl2, l2, list, bl3, randomSequences);
        }

        public FeatureFlagSet enabledFeatures() {
            assert (ServerLevelStub.client.level != null);
            return ServerLevelStub.client.level.enabledFeatures();
        }

        public static ServerLevelStub fastCreate() {
            try {
                return (ServerLevelStub)((Object)unsafe.allocateInstance(ServerLevelStub.class));
            }
            catch (InstantiationException instantiationException) {
                throw new RuntimeException(instantiationException);
            }
        }

        public RegistryAccess registryAccess() {
            return registryAccess.join();
        }

        public ReloadableServerRegistries.Holder holder() {
            return new ReloadableServerRegistries.Holder(this.registryAccess().freeze());
        }

        public static Unsafe getUnsafe() {
            try {
                Field field = Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(true);
                return (Unsafe)field.get(null);
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        public static CompletableFuture<RegistryAccess> load() {
            PackRepository packRepository = Minecraft.getInstance().getResourcePackRepository();
            packRepository = new MultiPackResourceManager(PackType.SERVER_DATA, List.of(packRepository.getPack("vanilla").open()));
            LayeredRegistryAccess<RegistryLayer> layeredRegistryAccess = ServerLevelStub.loadAndReplaceLayer((ResourceManager)packRepository, (LayeredRegistryAccess<RegistryLayer>)RegistryLayer.createRegistryAccess(), RegistryLayer.WORLDGEN, RegistryDataLoader.WORLDGEN_REGISTRIES);
            return ReloadableServerResources.loadResources((ResourceManager)packRepository, layeredRegistryAccess, (FeatureFlagSet)FeatureFlags.VANILLA_SET, (Commands.CommandSelection)Commands.CommandSelection.INTEGRATED, (int)2, Runnable::run, (Executor)Minecraft.getInstance()).thenApply(reloadableServerResources -> reloadableServerResources.fullRegistries().get());
        }

        private static LayeredRegistryAccess<RegistryLayer> loadAndReplaceLayer(ResourceManager resourceManager, LayeredRegistryAccess<RegistryLayer> layeredRegistryAccess, RegistryLayer registryLayer, List<RegistryDataLoader.RegistryData<?>> list) {
            resourceManager = ServerLevelStub.loadLayer(resourceManager, layeredRegistryAccess, registryLayer, list);
            return layeredRegistryAccess.replaceFrom((Object)registryLayer, new RegistryAccess.Frozen[]{resourceManager});
        }

        private static RegistryAccess.Frozen loadLayer(ResourceManager resourceManager, LayeredRegistryAccess<RegistryLayer> frozen, RegistryLayer registryLayer, List<RegistryDataLoader.RegistryData<?>> list) {
            frozen = frozen.getAccessForLoading((Object)registryLayer);
            return RegistryDataLoader.load((ResourceManager)resourceManager, (RegistryAccess)frozen, list);
        }
    }
}

