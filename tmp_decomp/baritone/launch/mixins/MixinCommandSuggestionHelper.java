/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.context.StringRange
 *  com.mojang.brigadier.suggestion.Suggestion
 *  com.mojang.brigadier.suggestion.Suggestions
 *  net.minecraft.client.gui.components.CommandSuggestions
 *  net.minecraft.client.gui.components.CommandSuggestions$SuggestionsList
 *  net.minecraft.client.gui.components.EditBox
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package baritone.launch.mixins;

import baritone.api.BaritoneAPI;
import baritone.api.event.events.TabCompleteEvent;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={CommandSuggestions.class})
public class MixinCommandSuggestionHelper {
    @Shadow
    @Final
    EditBox input;
    @Shadow
    @Final
    private List<String> commandUsage;
    @Shadow
    private ParseResults currentParse;
    @Shadow
    private CompletableFuture<Suggestions> pendingSuggestions;
    @Shadow
    private CommandSuggestions.SuggestionsList suggestions;
    @Shadow
    boolean keepSuggestions;

    @Inject(method={"updateCommandInfo"}, at={@At(value="HEAD")}, cancellable=true)
    private void preUpdateSuggestion(CallbackInfo callbackInfo) {
        Object object = this.input.getValue().substring(0, Math.min(this.input.getValue().length(), this.input.getCursorPosition()));
        TabCompleteEvent tabCompleteEvent = new TabCompleteEvent((String)object);
        BaritoneAPI.getProvider().getPrimaryBaritone().getGameEventHandler().onPreTabComplete(tabCompleteEvent);
        if (tabCompleteEvent.isCancelled()) {
            callbackInfo.cancel();
            return;
        }
        if (tabCompleteEvent.completions != null) {
            callbackInfo.cancel();
            this.currentParse = null;
            if (this.keepSuggestions) {
                return;
            }
            this.input.setSuggestion(null);
            this.suggestions = null;
            this.commandUsage.clear();
            if (tabCompleteEvent.completions.length == 0) {
                this.pendingSuggestions = Suggestions.empty();
            } else {
                callbackInfo = StringRange.between((int)(((String)object).lastIndexOf(" ") + 1), (int)((String)object).length());
                object = Stream.of(tabCompleteEvent.completions).map(arg_0 -> MixinCommandSuggestionHelper.lambda$preUpdateSuggestion$0((StringRange)callbackInfo, arg_0)).collect(Collectors.toList());
                callbackInfo = new Suggestions((StringRange)callbackInfo, (List)object);
                this.pendingSuggestions = new CompletableFuture();
                this.pendingSuggestions.complete((Suggestions)callbackInfo);
            }
            ((CommandSuggestions)this).showSuggestions(true);
        }
    }

    private static /* synthetic */ Suggestion lambda$preUpdateSuggestion$0(StringRange stringRange, String string) {
        return new Suggestion(stringRange, string);
    }
}

