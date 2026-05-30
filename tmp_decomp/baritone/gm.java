/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.bytes.ByteArrayList
 */
package baritone;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class gm {
    public final int a;
    private final byte[] a;
    public final int b;

    public gm(int n2) {
        n2 = this.a = n2;
        ByteArrayList byteArrayList = new ByteArrayList();
        while ((n2 & 0x80) != 0) {
            byteArrayList.add((byte)(n2 & 0x7F | 0x80));
            n2 >>>= 7;
        }
        byteArrayList.add((byte)n2);
        this.a = byteArrayList.toByteArray();
        this.b = this.a.length;
    }
}

