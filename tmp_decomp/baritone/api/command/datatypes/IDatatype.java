/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.datatypes;

import baritone.api.command.datatypes.IDatatypeContext;
import java.util.stream.Stream;

public interface IDatatype {
    public Stream<String> tabComplete(IDatatypeContext var1);
}

