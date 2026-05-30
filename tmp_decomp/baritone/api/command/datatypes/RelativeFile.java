/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package baritone.api.command.datatypes;

import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypePost;
import baritone.api.utils.Helper;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;

public enum RelativeFile implements IDatatypePost<File, File>
{
    INSTANCE;


    @Override
    public final File apply(IDatatypeContext object, File file) {
        if (file == null) {
            file = new File("./");
        }
        try {
            object = FileSystems.getDefault().getPath(object.getConsumer().getString(), new String[0]);
        }
        catch (InvalidPathException invalidPathException) {
            throw new IllegalArgumentException("invalid path");
        }
        return RelativeFile.getCanonicalFileUnchecked(file.toPath().resolve((Path)object).toFile());
    }

    @Override
    public final Stream<String> tabComplete(IDatatypeContext iDatatypeContext) {
        return Stream.empty();
    }

    private static File getCanonicalFileUnchecked(File file) {
        try {
            return file.getCanonicalFile();
        }
        catch (IOException iOException) {
            throw new UncheckedIOException(iOException);
        }
    }

    public static Stream<String> tabComplete(IArgConsumer object, File file2) {
        file2 = RelativeFile.getCanonicalFileUnchecked(file2);
        object = object.getString();
        Path path = FileSystems.getDefault().getPath((String)object, new String[0]);
        Path path2 = path.isAbsolute() ? path.getRoot() : file2.toPath();
        boolean bl2 = !((String)object).isEmpty() && !((String)object).endsWith(File.separator);
        file2 = path.isAbsolute() ? path.toFile() : new File(file2, (String)object);
        return Stream.of(Objects.requireNonNull(RelativeFile.getCanonicalFileUnchecked(bl2 ? file2.getParentFile() : file2).listFiles())).map(file -> String.valueOf(path.isAbsolute() ? file : path2.relativize(file.toPath()).toString()) + (file.isDirectory() ? File.separator : "")).filter(arg_0 -> RelativeFile.lambda$tabComplete$1((String)object, arg_0)).filter(string -> !string.contains(" "));
    }

    @Deprecated
    public static File gameDir() {
        return RelativeFile.gameDir(Helper.mc);
    }

    public static File gameDir(Minecraft object) {
        object = ((Minecraft)object).gameDirectory.getAbsoluteFile();
        if (((File)object).getName().equals(".")) {
            return ((File)object).getParentFile();
        }
        return object;
    }

    private static /* synthetic */ boolean lambda$tabComplete$1(String string, String string2) {
        return string2.toLowerCase(Locale.US).startsWith(string.toLowerCase(Locale.US));
    }
}

