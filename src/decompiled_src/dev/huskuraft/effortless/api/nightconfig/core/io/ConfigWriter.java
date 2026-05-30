/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.io;

import dev.huskuraft.effortless.api.nightconfig.core.UnmodifiableConfig;
import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingException;
import dev.huskuraft.effortless.api.nightconfig.core.io.WritingMode;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface ConfigWriter {
    public void write(UnmodifiableConfig var1, Writer var2);

    default public void write(UnmodifiableConfig config, OutputStream output, Charset charset) {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, charset));
        this.write(config, writer);
        try {
            ((Writer)writer).flush();
        }
        catch (IOException e) {
            throw new WritingException("Failed to flush the writer", e);
        }
    }

    default public void write(UnmodifiableConfig config, OutputStream output) {
        this.write(config, output, StandardCharsets.UTF_8);
    }

    default public void write(UnmodifiableConfig config, Path file, WritingMode writingMode) {
        this.write(config, file, writingMode, StandardCharsets.UTF_8);
    }

    default public void write(UnmodifiableConfig config, Path file, WritingMode writingMode, Charset charset) {
        OpenOption[] options = writingMode == WritingMode.APPEND ? new StandardOpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND} : new StandardOpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
        try (OutputStream output = Files.newOutputStream(file, options);){
            this.write(config, output, charset);
        }
        catch (IOException e) {
            throw new WritingException("An I/O error occured", e);
        }
    }

    default public void write(UnmodifiableConfig config, File file, WritingMode writingMode) {
        this.write(config, file, writingMode, StandardCharsets.UTF_8);
    }

    default public void write(UnmodifiableConfig config, File file, WritingMode writingMode, Charset charset) {
        this.write(config, file.toPath(), writingMode, charset);
    }

    default public void write(UnmodifiableConfig config, URL url) {
        URLConnection connection;
        try {
            connection = url.openConnection();
        }
        catch (IOException e) {
            throw new WritingException("Unable to connect to the URL", e);
        }
        String encoding = connection.getContentEncoding();
        Charset charset = encoding == null ? StandardCharsets.UTF_8 : Charset.forName(encoding);
        try (OutputStream output = connection.getOutputStream();){
            this.write(config, output, charset);
        }
        catch (IOException e) {
            throw new WritingException("An I/O error occured", e);
        }
    }

    default public String writeToString(UnmodifiableConfig config) {
        CharsWrapper.Builder builder = new CharsWrapper.Builder(64);
        this.write(config, builder);
        return builder.toString();
    }
}
