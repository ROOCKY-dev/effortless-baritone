package dev.roocky.effortlessbaritone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.building.Context;
import dev.huskuraft.effortless.session.config.ConstraintConfig;
import dev.huskuraft.effortless.building.config.BuilderConfig;
import dev.huskuraft.effortless.EffortlessClient;
import dev.huskuraft.effortless.building.config.ClientConfig;

@Mixin(targets = "dev.huskuraft.effortless.EffortlessClientStructureBuilder", remap = false)
public abstract class EffortlessClientStructureBuilderMixin {
    
    @Shadow public abstract EffortlessClient getEntrance();

    @Inject(method = "isSessionValid(Ldev/huskuraft/effortless/api/core/Player;)Z", at = @At("HEAD"), cancellable = true)
    private void forceSessionValid(Player player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isPermissionGranted(Ldev/huskuraft/effortless/api/core/Player;)Z", at = @At("HEAD"), cancellable = true)
    private void forcePermissionGranted(Player player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "getDefaultContext(Ldev/huskuraft/effortless/api/core/Player;)Ldev/huskuraft/effortless/building/Context;", at = @At("HEAD"), cancellable = true)
    private void overrideDefaultContext(Player player, CallbackInfoReturnable<Context> cir) {
        if (this.getEntrance().getSessionManager().getServerSessionConfig() == null) {
            BuilderConfig builderConfig = ((ClientConfig)this.getEntrance().getConfigStorage().get()).builderConfig();
            cir.setReturnValue(Context.defaultSet().withConstraintConfig(ConstraintConfig.DEFAULT).withBuilderConfig(builderConfig));
        }
    }
}
