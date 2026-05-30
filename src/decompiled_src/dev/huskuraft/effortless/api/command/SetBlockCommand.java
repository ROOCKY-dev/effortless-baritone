/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.command;

import dev.huskuraft.effortless.api.command.Command;
import dev.huskuraft.effortless.api.core.BlockPosition;
import dev.huskuraft.effortless.api.core.BlockState;
import dev.huskuraft.effortless.api.core.PropertyHolder;
import java.util.Locale;
import java.util.stream.Collectors;

public record SetBlockCommand(BlockState blockState, BlockPosition blockPosition, Mode mode) implements Command
{
    public static final String COMMAND = "setblock";

    @Override
    public String build() {
        return "%s %d %d %d %s %s".formatted(COMMAND, this.blockPosition.x(), this.blockPosition.y(), this.blockPosition.z(), this.getPropertiesString(this.blockState), this.mode.name().toLowerCase(Locale.ROOT));
    }

    public String getPropertiesString(BlockState blockState) {
        return blockState.getItem().getId().getString() + "[" + blockState.getProperties().stream().map(PropertyHolder::getAsString).collect(Collectors.joining(",")) + "]";
    }

    public static enum Mode {
        DESTROY,
        KEEP,
        REPLACE;

    }
}
