/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.player.LocalPlayer
 */
package baritone.api;

import baritone.api.IBaritone;
import baritone.api.cache.IWorldScanner;
import baritone.api.command.ICommandSystem;
import baritone.api.schematic.ISchematicSystem;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;

public interface IBaritoneProvider {
    public IBaritone getPrimaryBaritone();

    public List<IBaritone> getAllBaritones();

    default public IBaritone getBaritoneForPlayer(LocalPlayer localPlayer) {
        for (IBaritone iBaritone : this.getAllBaritones()) {
            if (!Objects.equals(localPlayer, iBaritone.getPlayerContext().player())) continue;
            return iBaritone;
        }
        return null;
    }

    default public IBaritone getBaritoneForMinecraft(Minecraft minecraft) {
        for (IBaritone iBaritone : this.getAllBaritones()) {
            if (!Objects.equals(minecraft, iBaritone.getPlayerContext().minecraft())) continue;
            return iBaritone;
        }
        return null;
    }

    default public IBaritone getBaritoneForConnection(ClientPacketListener clientPacketListener) {
        for (IBaritone iBaritone : this.getAllBaritones()) {
            LocalPlayer localPlayer = iBaritone.getPlayerContext().player();
            if (localPlayer == null || localPlayer.connection != clientPacketListener) continue;
            return iBaritone;
        }
        return null;
    }

    public IBaritone createBaritone(Minecraft var1);

    public boolean destroyBaritone(IBaritone var1);

    public IWorldScanner getWorldScanner();

    public ICommandSystem getCommandSystem();

    public ISchematicSystem getSchematicSystem();
}

