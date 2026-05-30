/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.core.Interaction;
import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.api.core.PlayerInfo;
import dev.huskuraft.effortless.api.core.World;
import dev.huskuraft.effortless.api.gui.Screen;
import dev.huskuraft.effortless.api.gui.Typeface;
import dev.huskuraft.effortless.api.platform.Options;
import dev.huskuraft.effortless.api.platform.ParticleEngine;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.renderer.Camera;
import dev.huskuraft.effortless.api.renderer.Window;
import dev.huskuraft.effortless.api.sound.SoundManager;
import java.util.List;

public interface Client
extends PlatformReference {
    public Window getWindow();

    public Camera getCamera();

    public Screen getPanel();

    public void setPanel(Screen var1);

    public Player getPlayer();

    public List<PlayerInfo> getOnlinePlayers();

    public Typeface getTypeface();

    public World getWorld();

    public boolean isLoaded();

    public Interaction getLastInteraction();

    public String getClipboard();

    public void setClipboard(String var1);

    public SoundManager getSoundManager();

    public void sendChat(String var1);

    public void sendCommand(String var1);

    public void execute(Runnable var1);

    public Options getOptions();

    public ParticleEngine getParticleEngine();

    public boolean isLocalServer();

    public boolean hasSinglePlayerServer();
}
