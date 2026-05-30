/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.ReloadableServerRegistries$Holder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package baritone.launch.mixins;

import baritone.api.utils.BlockOptionalMeta;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={LootContext.Builder.class})
public abstract class MixinLootContextBuilder {
    @Shadow
    public abstract ServerLevel getLevel();

    @Redirect(method={"create"}, at=@At(value="INVOKE", target="Lnet/minecraft/server/MinecraftServer;reloadableRegistries()Lnet/minecraft/server/ReloadableServerRegistries$Holder;"))
    private ReloadableServerRegistries.Holder create(MinecraftServer minecraftServer) {
        if (minecraftServer != null) {
            return minecraftServer.reloadableRegistries();
        }
        minecraftServer = this.getLevel();
        if (minecraftServer instanceof BlockOptionalMeta.ServerLevelStub) {
            return ((BlockOptionalMeta.ServerLevelStub)minecraftServer).holder();
        }
        return null;
    }
}

