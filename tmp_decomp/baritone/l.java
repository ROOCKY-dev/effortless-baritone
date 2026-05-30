/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class l {
    public final long[] a;

    public l() {
        this(System.nanoTime() ^ System.currentTimeMillis());
    }

    private l(long l2) {
        LongSupplier longSupplier = () -> l.a(new AtomicLong(l2));
        this.a = new long[]{longSupplier.getAsLong(), longSupplier.getAsLong(), longSupplier.getAsLong(), longSupplier.getAsLong()};
    }

    public l(long[] lArray) {
        this.a = lArray;
    }

    public final double a() {
        return (double)(this.a() >>> 11) * (double)1.110223E-16f;
    }

    private long a() {
        long l2 = l.a(this.a[0] + this.a[3], 23) + this.a[0];
        long l3 = this.a[1] << 17;
        this.a[2] = this.a[2] ^ this.a[0];
        this.a[3] = this.a[3] ^ this.a[1];
        this.a[1] = this.a[1] ^ this.a[2];
        this.a[0] = this.a[0] ^ this.a[3];
        this.a[2] = this.a[2] ^ l3;
        this.a[3] = l.a(this.a[3], 45);
        return l2;
    }

    private static long a(long l2, int n2) {
        return l2 << n2 | l2 >>> 64 - n2;
    }

    private static /* synthetic */ long a(AtomicLong atomicLong) {
        long l2 = atomicLong.addAndGet(-7046029254386353131L);
        long l3 = (l2 ^ l2 >>> 30) * -4658895280553007687L;
        long l4 = (l3 ^ l3 >>> 27) * -7723592293110705685L;
        return l4 ^ l4 >>> 31;
    }
}

