/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.gui.Screen;
import dev.huskuraft.effortless.api.platform.Client;

public interface ClientManager {
    public Client getRunningClient();

    public void setRunningClient(Client var1);

    public void pushScreen(Screen var1);

    public void popScreen(Screen var1);
}
