/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.schematic.format;

import baritone.api.schematic.IStaticSchematic;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface ISchematicFormat {
    public IStaticSchematic parse(InputStream var1);

    public boolean isFileType(File var1);

    public List<String> getFileExtensions();
}

