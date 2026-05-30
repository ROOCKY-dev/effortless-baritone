/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 */
package baritone;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.behavior.IBehavior;
import baritone.api.event.listener.IEventBus;
import baritone.api.process.IBaritoneProcess;
import baritone.api.process.IElytraProcess;
import baritone.api.utils.IPlayerContext;
import baritone.bt;
import baritone.bu;
import baritone.d;
import baritone.dp;
import baritone.dr;
import baritone.dy;
import baritone.dz;
import baritone.ea;
import baritone.ec;
import baritone.eg;
import baritone.eh;
import baritone.ej;
import baritone.ek;
import baritone.ev;
import baritone.f;
import baritone.fb;
import baritone.fd;
import baritone.ff;
import baritone.fi;
import baritone.fz;
import baritone.i;
import baritone.k;
import baritone.u;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class a
implements IBaritone {
    private static final ThreadPoolExecutor a = new ThreadPoolExecutor(4, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    private final Minecraft a;
    public final Path a;
    private final bu a;
    public final i a;
    public final f a;
    public final d a;
    public final ff a;
    private final eg a;
    private final ek a;
    private final eh a;
    private final dy a;
    public final dr a;
    private final ea a;
    private final ec a;
    public final ej a;
    private final IElytraProcess a;
    public final fi a;
    public final ev a;
    public final bt a;
    private final fz a;
    public final u a;
    public fb a;

    a(Minecraft minecraft) {
        this.a = minecraft;
        this.a = new bu(this);
        this.a = minecraft.gameDirectory.toPath().resolve("baritone");
        if (!Files.exists(this.a, new LinkOption[0])) {
            try {
                Files.createDirectories(this.a, new FileAttribute[0]);
            }
            catch (IOException iOException) {}
        }
        this.a = new fz(this, minecraft);
        this.a = this.a(f::new);
        this.a = this.a(i::new);
        this.a = this.a(d::new);
        this.a = this.a(ff::new);
        this.a(k::new);
        this.a = new fi(this);
        this.a = this.a(eg::new);
        this.a = this.a(ek::new);
        this.a = this.a(dy::new);
        this.a = this.a(eh::new);
        this.a = this.a(dr::new);
        this.a = this.a(ea::new);
        this.a = this.a(ec::new);
        this.a = this.a(ej::new);
        this.a = this.a(dz::a);
        this.a(dp::new);
        this.a = new u(this);
        this.a = new ev(this);
        this.a = new bt(this);
    }

    private void a(IBehavior iBehavior) {
        this.a.registerEventListener(iBehavior);
    }

    public final <T extends IBehavior> T a(Function<a, T> object) {
        object = (IBehavior)object.apply((a)this);
        this.a((IBehavior)object);
        return (T)object;
    }

    private <T extends IBaritoneProcess> T a(Function<a, T> object) {
        object = (IBaritoneProcess)object.apply((a)this);
        this.a.registerProcess((IBaritoneProcess)object);
        return (T)object;
    }

    @Override
    public final IPlayerContext getPlayerContext() {
        return this.a;
    }

    @Override
    public final IEventBus getGameEventHandler() {
        return this.a;
    }

    @Override
    public final IElytraProcess getElytraProcess() {
        return this.a;
    }

    @Override
    public final void openClick() {
        new Thread(() -> {
            try {
                Thread.sleep(100L);
                this.a.execute(() -> this.a.setScreen((Screen)new fd()));
                return;
            }
            catch (Exception exception) {
                return;
            }
        }).start();
    }

    public static Settings a() {
        return BaritoneAPI.getSettings();
    }

    public static Executor a() {
        return a;
    }
}

