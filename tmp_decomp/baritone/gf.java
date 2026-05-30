/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FilenameUtils
 */
package baritone;

import baritone.api.schematic.format.ISchematicFormat;
import baritone.gg;
import baritone.gh;
import baritone.gi;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract sealed class gf
extends Enum<gf>
implements ISchematicFormat
permits gg, gh, gi {
    private static /* enum */ gg a = new gg();
    private static /* enum */ gh a = new gh();
    private static /* enum */ gi a = new gi();
    private final String a;
    private static final /* synthetic */ gf[] a;

    public static gf[] values() {
        return (gf[])a.clone();
    }

    public static gf valueOf(String string) {
        return Enum.valueOf(gf.class, string);
    }

    gf(String string2) {
        this.a = string2;
    }

    @Override
    public boolean isFileType(File file) {
        return this.a.equalsIgnoreCase(FilenameUtils.getExtension((String)file.getAbsolutePath()));
    }

    @Override
    public List<String> getFileExtensions() {
        return Collections.singletonList(this.a);
    }

    static {
        a = new gf[]{a, a, a};
    }
}

