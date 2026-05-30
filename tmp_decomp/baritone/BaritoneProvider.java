/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package baritone;

import baritone.a;
import baritone.api.IBaritone;
import baritone.api.IBaritoneProvider;
import baritone.api.cache.IWorldScanner;
import baritone.api.command.ICommandSystem;
import baritone.api.schematic.ISchematicSystem;
import baritone.gc;
import baritone.r;
import baritone.v;
import baritone.w;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;

public final class BaritoneProvider
implements IBaritoneProvider {
    private final List<IBaritone> a = new CopyOnWriteArrayList<IBaritone>();
    private final List<IBaritone> b = Collections.unmodifiableList(this.a);

    public BaritoneProvider() {
        ((a)this.createBaritone(Minecraft.getInstance())).a(w::new);
    }

    @Override
    public final IBaritone getPrimaryBaritone() {
        return this.a.get(0);
    }

    @Override
    public final List<IBaritone> getAllBaritones() {
        return this.b;
    }

    @Override
    public final synchronized IBaritone createBaritone(Minecraft minecraft) {
        IBaritone iBaritone = this.getBaritoneForMinecraft(minecraft);
        if (iBaritone == null) {
            iBaritone = new a(minecraft);
            this.a.add(iBaritone);
        }
        return iBaritone;
    }

    @Override
    public final synchronized boolean destroyBaritone(IBaritone iBaritone) {
        return iBaritone != this.getPrimaryBaritone() && this.a.remove(iBaritone);
    }

    @Override
    public final IWorldScanner getWorldScanner() {
        return r.a;
    }

    @Override
    public final ICommandSystem getCommandSystem() {
        return v.a;
    }

    @Override
    public final ISchematicSystem getSchematicSystem() {
        return gc.a;
    }
}

