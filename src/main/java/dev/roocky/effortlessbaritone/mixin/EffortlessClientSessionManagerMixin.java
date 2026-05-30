package dev.roocky.effortlessbaritone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import dev.huskuraft.effortless.EffortlessClient;
import dev.huskuraft.effortless.api.text.ChatFormatting;
import dev.huskuraft.effortless.api.text.Text;
import dev.huskuraft.effortless.api.core.Player;

@Mixin(targets = "dev.huskuraft.effortless.EffortlessClientSessionManager", remap = false)
public abstract class EffortlessClientSessionManagerMixin {
    
    @Shadow public abstract EffortlessClient getEntrance();

    /**
     * @author Roocky
     * @reason Overwrites the notification to show an "Active" status instead of the "Mod missing" error.
     *         This fulfills the user request to keep the notification but cancel the "reason" (the error).
     */
    @Overwrite
    public void notifyPlayer() {
        // [Effortless Building] 
        Text id = Text.text("[").append(Text.translate("effortless.name")).append(Text.text("] ")).withStyle(ChatFormatting.GRAY);
        
        // Active (Spoofed) - Replacing the error "reason" with a positive status
        Text message = Text.text("Active (Spoofed)").withStyle(ChatFormatting.GREEN);
        
        Player player = this.getEntrance().getClient().getPlayer();
        if (player != null) {
            player.sendMessage(id.append(message));
        }
    }
}
