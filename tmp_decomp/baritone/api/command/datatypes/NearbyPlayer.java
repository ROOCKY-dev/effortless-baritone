/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package baritone.api.command.datatypes;

import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public enum NearbyPlayer implements IDatatypeFor<Player>
{
    INSTANCE;


    @Override
    public final Player get(IDatatypeContext iDatatypeContext) {
        String string = iDatatypeContext.getConsumer().getString();
        return NearbyPlayer.getPlayers(iDatatypeContext).stream().filter(player -> player.getName().getString().equalsIgnoreCase(string)).findFirst().orElse(null);
    }

    @Override
    public final Stream<String> tabComplete(IDatatypeContext iDatatypeContext) {
        return new TabCompleteHelper().append(NearbyPlayer.getPlayers(iDatatypeContext).stream().map(Player::getName).map(Component::getString)).filterPrefix(iDatatypeContext.getConsumer().getString()).sortAlphabetically().stream();
    }

    private static List<? extends Player> getPlayers(IDatatypeContext iDatatypeContext) {
        return iDatatypeContext.getBaritone().getPlayerContext().world().players();
    }
}

