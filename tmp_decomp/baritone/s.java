/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.cache.IWaypoint;
import baritone.api.cache.IWaypointCollection;
import baritone.api.cache.Waypoint;
import baritone.api.utils.BetterBlockPos;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class s
implements IWaypointCollection {
    private final Path a;
    private final Map<IWaypoint.Tag, Set<IWaypoint>> a;

    s(Path object) {
        this.a = object;
        if (!Files.exists((Path)object, new LinkOption[0])) {
            try {
                Files.createDirectories((Path)object, new FileAttribute[0]);
            }
            catch (IOException iOException) {}
        }
        System.out.println("Would save waypoints to " + String.valueOf(object));
        this.a = new HashMap();
        object = this;
        for (IWaypoint.Tag tag : IWaypoint.Tag.values()) {
            ((s)object).a(tag);
        }
    }

    private synchronized void a(IWaypoint.Tag tag) {
        this.a.put(tag, new HashSet());
        Object object = this.a.resolve(tag.name().toLowerCase() + ".mp4");
        if (!Files.exists((Path)object, new LinkOption[0])) {
            return;
        }
        try {
            object = new FileInputStream(object.toFile());
            try {
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream((InputStream)object);
                     DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);){
                    long l2 = dataInputStream.readLong();
                    if (l2 != 121977993584L) {
                        throw new IOException("Bad magic value " + l2);
                    }
                    long l3 = dataInputStream.readLong();
                    while (l3-- > 0L) {
                        String string = dataInputStream.readUTF();
                        long l4 = dataInputStream.readLong();
                        int n2 = dataInputStream.readInt();
                        int n3 = dataInputStream.readInt();
                        int n4 = dataInputStream.readInt();
                        ((Set)this.a.get((Object)tag)).add(new Waypoint(string, tag, new BetterBlockPos(n2, n3, n4), l4));
                    }
                }
                return;
            }
            finally {
                ((FileInputStream)object).close();
            }
        }
        catch (IOException iOException) {
            return;
        }
    }

    private synchronized void b(IWaypoint.Tag object) {
        Object object2 = this.a.resolve(((Enum)object).name().toLowerCase() + ".mp4");
        try {
            object2 = new FileOutputStream(object2.toFile());
            try {
                try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream((OutputStream)object2);
                     DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);){
                    dataOutputStream.writeLong(121977993584L);
                    dataOutputStream.writeLong(((Set)this.a.get(object)).size());
                    for (IWaypoint iWaypoint : (Set)this.a.get(object)) {
                        dataOutputStream.writeUTF(iWaypoint.getName());
                        dataOutputStream.writeLong(iWaypoint.getCreationTimestamp());
                        dataOutputStream.writeInt(iWaypoint.getLocation().getX());
                        dataOutputStream.writeInt(iWaypoint.getLocation().getY());
                        dataOutputStream.writeInt(iWaypoint.getLocation().getZ());
                    }
                }
                return;
            }
            finally {
                ((FileOutputStream)object2).close();
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    @Override
    public final void addWaypoint(IWaypoint iWaypoint) {
        if (((Set)this.a.get((Object)iWaypoint.getTag())).add(iWaypoint)) {
            this.b(iWaypoint.getTag());
        }
    }

    @Override
    public final void removeWaypoint(IWaypoint iWaypoint) {
        if (((Set)this.a.get((Object)iWaypoint.getTag())).remove(iWaypoint)) {
            this.b(iWaypoint.getTag());
        }
    }

    @Override
    public final IWaypoint getMostRecentByTag(IWaypoint.Tag tag) {
        return ((Set)this.a.get((Object)tag)).stream().min(Comparator.comparingLong(iWaypoint -> -iWaypoint.getCreationTimestamp())).orElse(null);
    }

    @Override
    public final Set<IWaypoint> getByTag(IWaypoint.Tag tag) {
        return Collections.unmodifiableSet((Set)this.a.get((Object)tag));
    }

    @Override
    public final Set<IWaypoint> getAllWaypoints() {
        return this.a.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }
}

