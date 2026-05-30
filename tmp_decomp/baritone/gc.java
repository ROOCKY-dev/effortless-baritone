/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.command.registry.Registry;
import baritone.api.schematic.ISchematicSystem;
import baritone.api.schematic.format.ISchematicFormat;
import baritone.gf;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class gc
extends Enum<gc>
implements ISchematicSystem {
    public static final /* enum */ gc a = new gc("INSTANCE");
    private final Registry<ISchematicFormat> a = new Registry();
    private static final /* synthetic */ gc[] a;

    public static gc[] values() {
        return (gc[])a.clone();
    }

    public static gc valueOf(String string) {
        return Enum.valueOf(gc.class, string);
    }

    private gc() {
        Arrays.stream(gf.values()).forEach(((Registry)((Object)this.a))::register);
    }

    @Override
    public final Registry<ISchematicFormat> getRegistry() {
        return this.a;
    }

    @Override
    public final Optional<ISchematicFormat> getByFile(File file) {
        return ((Registry)((Object)this.a)).stream().filter(iSchematicFormat -> iSchematicFormat.isFileType(file)).findFirst();
    }

    @Override
    public final List<String> getFileExtensions() {
        return ((Registry)((Object)this.a)).stream().map(ISchematicFormat::getFileExtensions).flatMap(Collection::stream).toList();
    }

    static {
        a = new gc[]{a};
    }
}

