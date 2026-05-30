/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.toasts.Toast
 *  net.minecraft.client.gui.components.toasts.Toast$Visibility
 *  net.minecraft.client.gui.components.toasts.ToastComponent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 */
package baritone.api.utils.gui;

import baritone.api.BaritoneAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BaritoneToast
implements Toast {
    private String title;
    private String subtitle;
    private long firstDrawTime;
    private boolean newDisplay;
    private long totalShowTime;

    public BaritoneToast(Component component, Component component2, long l2) {
        this.title = component.getString();
        this.subtitle = component2 == null ? null : component2.getString();
        this.totalShowTime = l2;
    }

    public Toast.Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long l2) {
        if (this.newDisplay) {
            this.firstDrawTime = l2;
            this.newDisplay = false;
        }
        guiGraphics.blit(ResourceLocation.parse((String)"textures/gui/toasts.png"), 0, 0, 0, 32, 160, 32);
        if (this.subtitle == null) {
            guiGraphics.drawString(toastComponent.getMinecraft().font, this.title, 18, 12, -11534256);
        } else {
            guiGraphics.drawString(toastComponent.getMinecraft().font, this.title, 18, 7, -11534256);
            guiGraphics.drawString(toastComponent.getMinecraft().font, this.subtitle, 18, 18, -16777216);
        }
        if (l2 - this.firstDrawTime < this.totalShowTime) {
            return Toast.Visibility.SHOW;
        }
        return Toast.Visibility.HIDE;
    }

    public void setDisplayedText(Component component, Component component2) {
        this.title = component.getString();
        this.subtitle = component2 == null ? null : component2.getString();
        this.newDisplay = true;
    }

    public static void addOrUpdate(ToastComponent toastComponent, Component component, Component component2, long l2) {
        BaritoneToast baritoneToast = (BaritoneToast)toastComponent.getToast(BaritoneToast.class, new Object());
        if (baritoneToast == null) {
            toastComponent.addToast((Toast)new BaritoneToast(component, component2, l2));
            return;
        }
        baritoneToast.setDisplayedText(component, component2);
    }

    public static void addOrUpdate(Component component, Component component2) {
        BaritoneToast.addOrUpdate(Minecraft.getInstance().getToasts(), component, component2, (Long)BaritoneAPI.getSettings().toastTimer.value);
    }
}

