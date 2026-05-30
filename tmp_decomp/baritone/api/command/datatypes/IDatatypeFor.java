/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.datatypes;

import baritone.api.command.datatypes.IDatatype;
import baritone.api.command.datatypes.IDatatypeContext;

public interface IDatatypeFor<T>
extends IDatatype {
    public T get(IDatatypeContext var1);
}

