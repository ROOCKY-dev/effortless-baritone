/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.ConfigFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

@FunctionalInterface
public interface FileNotFoundAction {
    public static final FileNotFoundAction CREATE_EMPTY = (f, c) -> {
        Files.createFile(f, new FileAttribute[0]);
        c.initEmptyFile(f);
        return false;
    };
    public static final FileNotFoundAction READ_NOTHING = (f, c) -> false;
    public static final FileNotFoundAction THROW_ERROR = (f, c) -> {
        throw new NoSuchFileException(f.toAbsolutePath().toString());
    };

    public boolean run(Path var1, ConfigFormat<?> var2) throws IOException;

    public static FileNotFoundAction copyData(URL url) {
        return (f, c) -> {
            Files.copy(url.openStream(), f, new CopyOption[0]);
            return true;
        };
    }

    public static FileNotFoundAction copyData(File file) {
        return (f, c) -> {
            Files.copy(new FileInputStream(file), f, new CopyOption[0]);
            return true;
        };
    }

    public static FileNotFoundAction copyData(Path file) {
        return (f, c) -> {
            Files.copy(file, f, new CopyOption[0]);
            return true;
        };
    }

    public static FileNotFoundAction copyData(InputStream data) {
        return (f, c) -> {
            Files.copy(data, f, new CopyOption[0]);
            return true;
        };
    }

    public static FileNotFoundAction copyResource(String resourcePath) {
        return FileNotFoundAction.copyData(FileNotFoundAction.class.getResource(resourcePath));
    }
}
