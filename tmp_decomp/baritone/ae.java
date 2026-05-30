/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Vec3i
 *  org.apache.commons.io.FilenameUtils
 */
package baritone;

import baritone.a;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.RelativeBlockPos;
import baritone.api.command.datatypes.RelativeFile;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.utils.BetterBlockPos;
import baritone.gc;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;
import net.minecraft.core.Vec3i;
import org.apache.commons.io.FilenameUtils;

public final class ae
extends Command {
    private final File a;

    public ae(a a2) {
        super(a2, "build");
        this.a = new File(a2.getPlayerContext().minecraft().gameDirectory, "schematics");
    }

    @Override
    public final void execute(String object, IArgConsumer iArgConsumer) {
        object = ((File)iArgConsumer.getDatatypePost(RelativeFile.INSTANCE, this.a)).getAbsoluteFile();
        File file = object;
        if (FilenameUtils.getExtension((String)((File)object).getAbsolutePath()).isEmpty()) {
            file = new File(file.getAbsolutePath() + "." + (String)baritone.a.a().schematicFallbackExtension.value);
        }
        if (!file.exists()) {
            if (((File)object).exists()) {
                throw new CommandInvalidStateException(String.format("Cannot load %s because I do not know which schematic format that is. Please rename the file to include the correct file extension.", file));
            }
            throw new CommandInvalidStateException("Cannot find " + String.valueOf(file));
        }
        if (!gc.a.getByFile(file).isPresent()) {
            object = new StringJoiner(", ");
            gc.a.getFileExtensions().forEach(((StringJoiner)object)::add);
            throw new CommandInvalidStateException(String.format("Unsupported schematic format. Reckognized file extensions are: %s", object));
        }
        object = this.ctx.playerFeet();
        if (iArgConsumer.hasAny()) {
            iArgConsumer.requireMax(3);
            object = (BetterBlockPos)((Object)iArgConsumer.getDatatypePost(RelativeBlockPos.INSTANCE, object));
        } else {
            iArgConsumer.requireMax(0);
        }
        if (!this.baritone.getBuilderProcess().build(file.getName(), file, (Vec3i)object)) {
            throw new CommandInvalidStateException("Couldn't load the schematic. Either your schematic is corrupt or this is a bug.");
        }
        this.logDirect(String.format("Successfully loaded schematic for building\nOrigin: %s", object));
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        if (iArgConsumer.hasExactlyOne()) {
            return RelativeFile.tabComplete(iArgConsumer, this.a);
        }
        if (iArgConsumer.has(2)) {
            iArgConsumer.get();
            return iArgConsumer.tabCompleteDatatype(RelativeBlockPos.INSTANCE);
        }
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Build a schematic";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Build a schematic from a file.", "", "Usage:", "> build <filename> - Loads and builds '<filename>.schematic'", "> build <filename> <x> <y> <z> - Custom position");
    }
}

