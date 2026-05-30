/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonSyntaxException
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.RelativeFile;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.exception.CommandInvalidTypeException;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class as
extends Command {
    public as(a a2) {
        super(a2, "explorefilter");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        iArgConsumer.requireMax(2);
        object = (File)iArgConsumer.getDatatypePost(RelativeFile.INSTANCE, this.ctx.minecraft().gameDirectory.getAbsoluteFile().getParentFile());
        boolean bl2 = false;
        if (iArgConsumer.hasAny()) {
            if (iArgConsumer.getString().equalsIgnoreCase("invert")) {
                bl2 = true;
            } else {
                throw new CommandInvalidTypeException(iArgConsumer.consumed(), "either \"invert\" or nothing");
            }
        }
        try {
            this.baritone.getExploreProcess().applyJsonFilter(((File)object).toPath().toAbsolutePath(), bl2);
        }
        catch (NoSuchFileException noSuchFileException) {
            throw new CommandInvalidStateException("File not found");
        }
        catch (JsonSyntaxException jsonSyntaxException) {
            throw new CommandInvalidStateException("Invalid JSON syntax");
        }
        catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
        this.logDirect(String.format("Explore filter applied. Inverted: %s", Boolean.toString(bl2)));
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        if (iArgConsumer.hasExactlyOne()) {
            return RelativeFile.tabComplete(iArgConsumer, RelativeFile.gameDir(this.ctx.minecraft()));
        }
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Explore chunks from a json";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Apply an explore filter before using explore, which tells the explore process which chunks have been explored/not explored.", "", "The JSON file will follow this format: [{\"x\":0,\"z\":0},...]", "", "If 'invert' is specified, the chunks listed will be considered NOT explored, rather than explored.", "", "Usage:", "> explorefilter <path> [invert] - Load the JSON file referenced by the specified path. If invert is specified, it must be the literal word 'invert'.");
    }
}

