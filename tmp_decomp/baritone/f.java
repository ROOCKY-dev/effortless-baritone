/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.game.ServerboundMovePlayerPacket
 *  net.minecraft.network.protocol.game.ServerboundMovePlayerPacket$PosRot
 *  net.minecraft.network.protocol.game.ServerboundMovePlayerPacket$Rot
 */
package baritone;

import baritone.api.Settings;
import baritone.api.behavior.ILookBehavior;
import baritone.api.behavior.look.IAimProcessor;
import baritone.api.behavior.look.ITickableAimProcessor;
import baritone.api.event.events.PacketEvent;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.RotationMoveEvent;
import baritone.api.event.events.TickEvent;
import baritone.api.event.events.WorldEvent;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.Rotation;
import baritone.h;
import baritone.l;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class f
extends baritone.c
implements ILookBehavior {
    public c a;
    public Rotation a;
    private Rotation b;
    public final b a;
    private final Deque<Float> a;
    private final Deque<Float> b;

    public f(baritone.a a2) {
        super(a2);
        this.a = new b(a2.getPlayerContext());
        this.a = new ArrayDeque();
        this.b = new ArrayDeque();
    }

    @Override
    public final void updateTarget(Rotation rotation, boolean bl2) {
        this.a = new c(rotation, c.a.a(((baritone.c)this).a, bl2));
    }

    @Override
    public final IAimProcessor getAimProcessor() {
        return this.a;
    }

    @Override
    public final void onTick(TickEvent tickEvent) {
        if (tickEvent.getType() == TickEvent.Type.IN) {
            this.a.tick();
        }
    }

    @Override
    public final void onPlayerUpdate(PlayerUpdateEvent object) {
        if (this.a == null) {
            return;
        }
        switch (((PlayerUpdateEvent)object).getState()) {
            case PRE: {
                if (this.a.a == c.a.b) {
                    return;
                }
                this.b = new Rotation(((baritone.c)this).a.player().getYRot(), ((baritone.c)this).a.player().getXRot());
                object = this.a.peekRotation(this.a.a);
                ((baritone.c)this).a.player().setYRot(((Rotation)object).getYaw());
                ((baritone.c)this).a.player().setXRot(((Rotation)object).getPitch());
                return;
            }
            case POST: {
                if (this.b != null) {
                    this.a.addLast(Float.valueOf(this.a.a.getYaw()));
                    while (this.a.size() > (Integer)baritone.a.a().smoothLookTicks.value) {
                        this.a.removeFirst();
                    }
                    this.b.addLast(Float.valueOf(this.a.a.getPitch()));
                    while (this.b.size() > (Integer)baritone.a.a().smoothLookTicks.value) {
                        this.b.removeFirst();
                    }
                    if (this.a.a == c.a.a) {
                        ((baritone.c)this).a.player().setYRot(this.b.getYaw());
                        ((baritone.c)this).a.player().setXRot(this.b.getPitch());
                    } else if ((((baritone.c)this).a.player().isFallFlying() ? (Boolean)baritone.a.a().elytraSmoothLook.value : (Boolean)baritone.a.a().smoothLook.value).booleanValue()) {
                        ((baritone.c)this).a.player().setYRot((float)this.a.stream().mapToDouble(f2 -> f2.floatValue()).average().orElse(this.b.getYaw()));
                        if (((baritone.c)this).a.player().isFallFlying()) {
                            ((baritone.c)this).a.player().setXRot((float)this.b.stream().mapToDouble(f2 -> f2.floatValue()).average().orElse(this.b.getPitch()));
                        }
                    }
                    this.b = null;
                }
                this.a = null;
            }
        }
    }

    @Override
    public final void onSendPacket(PacketEvent packetEvent) {
        if (!(packetEvent.getPacket() instanceof ServerboundMovePlayerPacket)) {
            return;
        }
        if ((packetEvent = (ServerboundMovePlayerPacket)packetEvent.getPacket()) instanceof ServerboundMovePlayerPacket.Rot || packetEvent instanceof ServerboundMovePlayerPacket.PosRot) {
            this.a = new Rotation(packetEvent.getYRot(0.0f), packetEvent.getXRot(0.0f));
        }
    }

    @Override
    public final void onWorldEvent(WorldEvent worldEvent) {
        this.a = null;
        this.a = null;
    }

    @Override
    public final void onPlayerRotationMove(RotationMoveEvent rotationMoveEvent) {
        if (this.a != null) {
            Rotation rotation = this.a.peekRotation(this.a.a);
            rotationMoveEvent.setYaw(rotation.getYaw());
            rotationMoveEvent.setPitch(rotation.getPitch());
        }
    }

    public static final class b
    extends a {
        public b(IPlayerContext iPlayerContext) {
            super(iPlayerContext);
        }

        @Override
        protected final Rotation a() {
            return this.a.playerRotations();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class c {
        public final Rotation a;
        public final a a;

        public c(Rotation rotation, a a2) {
            this.a = rotation;
            this.a = a2;
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        static final class a
        extends Enum<a> {
            private static /* enum */ a c = new a();
            public static final /* enum */ a a = new a();
            public static final /* enum */ a b = new a();
            private static final /* synthetic */ a[] a;

            public static a[] values() {
                return (a[])a.clone();
            }

            public static a valueOf(String string) {
                return Enum.valueOf(a.class, string);
            }

            static a a(IPlayerContext iPlayerContext, boolean bl2) {
                Settings settings = baritone.a.a();
                boolean bl3 = (Boolean)settings.antiCheatCompatibility.value;
                boolean bl4 = (Boolean)settings.blockFreeLook.value;
                if (iPlayerContext.player().isFallFlying()) {
                    if (((Boolean)settings.elytraFreeLook.value).booleanValue()) {
                        return a;
                    }
                    return c;
                }
                if (((Boolean)settings.freeLook.value).booleanValue()) {
                    if (bl2) {
                        if (bl4) {
                            return a;
                        }
                        return c;
                    }
                    if (bl3) {
                        return a;
                    }
                    return b;
                }
                return c;
            }

            static {
                a = new a[]{c, a, b};
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static abstract class a
    implements ITickableAimProcessor {
        protected final IPlayerContext a;
        private final l a;
        private double a;
        private double b;

        public a(IPlayerContext iPlayerContext) {
            this.a = iPlayerContext;
            this.a = new l();
        }

        a(a a2) {
            this.a = a2.a;
            l l2 = a2.a;
            this.a = new l(Arrays.copyOf(l2.a, 4));
            this.a = a2.a;
            this.b = a2.b;
        }

        @Override
        public final Rotation peekRotation(Rotation rotation) {
            Rotation rotation2 = this.a();
            float f2 = rotation.getYaw();
            float f3 = rotation.getPitch();
            if (f3 == rotation2.getPitch()) {
                f3 = f3 < -20.0f ? f3 + 1.0f : (f3 > 10.0f ? f3 - 1.0f : f3);
            }
            f2 = (float)((double)f2 + this.a);
            f3 = (float)((double)f3 + this.b);
            return new Rotation(this.a(rotation2.getYaw(), f2), this.a(rotation2.getPitch(), f3)).clamp();
        }

        @Override
        public final void tick() {
            double d2;
            this.a = (this.a.a() - 0.5) * (Double)baritone.a.a().randomLooking.value;
            this.b = (this.a.a() - 0.5) * (Double)baritone.a.a().randomLooking.value;
            double d3 = this.a.a() - 0.5;
            if (Math.abs(d2) < 0.1) {
                d3 *= 4.0;
            }
            this.a += d3 * (Double)baritone.a.a().randomLooking113.value;
        }

        @Override
        public final void advance(int n2) {
            for (int i2 = 0; i2 < n2; ++i2) {
                this.tick();
            }
        }

        @Override
        public Rotation nextRotation(Rotation rotation) {
            rotation = this.peekRotation(rotation);
            this.tick();
            return rotation;
        }

        @Override
        public final ITickableAimProcessor fork() {
            a a2 = this;
            return new h(a2, a2);
        }

        protected abstract Rotation a();

        private float a(float f2, float f3) {
            double d2 = this.a(f3 -= f2);
            return f2 + this.a(d2);
        }

        private double a(float f2) {
            float f3 = this.a(1.0);
            return Math.round(f2 / f3);
        }

        private float a(double d2) {
            double d3 = (Double)this.a.minecraft().options.sensitivity().get() * (double)0.6f + (double)0.2f;
            return (float)(d2 * d3 * d3 * d3 * 8.0) * 0.15f;
        }
    }
}

