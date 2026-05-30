/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.SystemUtils
 */
package baritone.api.utils;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.IOException;
import org.apache.commons.lang3.SystemUtils;

public class NotificationHelper {
    private static TrayIcon trayIcon;

    public static void notify(String string, boolean bl2) {
        if (SystemUtils.IS_OS_WINDOWS) {
            NotificationHelper.windows(string, bl2);
            return;
        }
        if (SystemUtils.IS_OS_MAC_OSX) {
            NotificationHelper.mac(string);
            return;
        }
        if (SystemUtils.IS_OS_LINUX) {
            NotificationHelper.linux(string);
        }
    }

    private static void windows(String string, boolean bl2) {
        if (SystemTray.isSupported()) {
            try {
                if (trayIcon == null) {
                    SystemTray systemTray = SystemTray.getSystemTray();
                    Image image = Toolkit.getDefaultToolkit().createImage("");
                    trayIcon = new TrayIcon(image, "Baritone");
                    trayIcon.setImageAutoSize(true);
                    trayIcon.setToolTip("Baritone");
                    systemTray.add(trayIcon);
                }
                trayIcon.displayMessage("Baritone", string, bl2 ? TrayIcon.MessageType.ERROR : TrayIcon.MessageType.INFO);
                return;
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return;
            }
        }
        System.out.println("SystemTray is not supported");
    }

    private static void mac(String string) {
        ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
        processBuilder.command("osascript", "-e", "display notification \"" + string + "\" with title \"Baritone\"");
        try {
            processBuilder.start();
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    private static void linux(String string) {
        ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
        processBuilder.command("notify-send", "-a", "Baritone", string);
        try {
            processBuilder.start();
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }
}

