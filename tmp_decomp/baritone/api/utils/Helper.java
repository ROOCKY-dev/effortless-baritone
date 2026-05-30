/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.GuiMessageTag
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 */
package baritone.api.utils;

import baritone.api.BaritoneAPI;
import java.util.Arrays;
import java.util.Calendar;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public interface Helper {
    public static final Helper HELPER = new Helper(){};
    @Deprecated
    public static final Minecraft mc = Minecraft.getInstance();
    public static final GuiMessageTag MESSAGE_TAG = new GuiMessageTag(0xFF55FF, null, (Component)Component.literal((String)"Baritone message."), "Baritone");

    public static Component getPrefix() {
        Calendar calendar = Calendar.getInstance();
        calendar = Component.literal((String)(calendar.get(2) == 3 && calendar.get(5) <= 3 ? "Baritoe" : ((Boolean)BaritoneAPI.getSettings().shortBaritonePrefix.value != false ? "B" : "Baritone")));
        calendar.setStyle(calendar.getStyle().withColor(ChatFormatting.LIGHT_PURPLE));
        MutableComponent mutableComponent = Component.literal((String)"");
        mutableComponent.setStyle(calendar.getStyle().withColor(ChatFormatting.DARK_PURPLE));
        mutableComponent.append("[");
        mutableComponent.append((Component)calendar);
        mutableComponent.append("]");
        return mutableComponent;
    }

    default public void logToast(Component component, Component component2) {
        Minecraft.getInstance().execute(() -> ((BiConsumer)BaritoneAPI.getSettings().toaster.value).accept(component, component2));
    }

    default public void logToast(String string, String string2) {
        this.logToast((Component)Component.literal((String)string), (Component)Component.literal((String)string2));
    }

    default public void logToast(String string) {
        this.logToast(Helper.getPrefix(), (Component)Component.literal((String)string));
    }

    default public void logNotification(String string) {
        this.logNotification(string, false);
    }

    default public void logNotification(String string, boolean bl2) {
        if (((Boolean)BaritoneAPI.getSettings().desktopNotifications.value).booleanValue()) {
            this.logNotificationDirect(string, bl2);
        }
    }

    default public void logNotificationDirect(String string) {
        this.logNotificationDirect(string, false);
    }

    default public void logNotificationDirect(String string, boolean bl2) {
        Minecraft.getInstance().execute(() -> ((BiConsumer)BaritoneAPI.getSettings().notifier.value).accept(string, bl2));
    }

    default public void logDebug(String string) {
        if (!((Boolean)BaritoneAPI.getSettings().chatDebug.value).booleanValue()) {
            return;
        }
        this.logDirect(string, false);
    }

    default public void logDirect(boolean bl2, Component ... componentArray) {
        MutableComponent mutableComponent = Component.literal((String)"");
        if (!bl2 && !((Boolean)BaritoneAPI.getSettings().useMessageTag.value).booleanValue()) {
            mutableComponent.append(Helper.getPrefix());
            mutableComponent.append((Component)Component.literal((String)" "));
        }
        Arrays.asList(componentArray).forEach(arg_0 -> ((MutableComponent)mutableComponent).append(arg_0));
        if (bl2) {
            this.logToast(Helper.getPrefix(), (Component)mutableComponent);
            return;
        }
        Minecraft.getInstance().execute(() -> ((Consumer)BaritoneAPI.getSettings().logger.value).accept(mutableComponent));
    }

    default public void logDirect(Component ... componentArray) {
        this.logDirect((Boolean)BaritoneAPI.getSettings().logAsToast.value, componentArray);
    }

    default public void logDirect(String string2, ChatFormatting chatFormatting, boolean bl2) {
        Stream.of(string2.split("\n")).forEach(string -> {
            string = Component.literal((String)string.replace("\t", "    "));
            string.setStyle(string.getStyle().withColor(chatFormatting));
            this.logDirect(bl2, new Component[]{string});
        });
    }

    default public void logDirect(String string, ChatFormatting chatFormatting) {
        this.logDirect(string, chatFormatting, (Boolean)BaritoneAPI.getSettings().logAsToast.value);
    }

    default public void logDirect(String string, boolean bl2) {
        this.logDirect(string, ChatFormatting.GRAY, bl2);
    }

    default public void logDirect(String string) {
        this.logDirect(string, (Boolean)BaritoneAPI.getSettings().logAsToast.value);
    }

    default public void logUnhandledException(Throwable throwable) {
        HELPER.logDirect("An unhandled exception occurred. The error is in your game's log, please report this at https://github.com/cabaletta/baritone/issues", ChatFormatting.RED);
        throwable.printStackTrace();
    }
}

