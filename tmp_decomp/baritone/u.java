/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.storage.LevelResource
 *  org.apache.commons.lang3.SystemUtils
 */
package baritone;

import baritone.a;
import baritone.api.cache.IWorldData;
import baritone.api.cache.IWorldProvider;
import baritone.api.utils.IPlayerContext;
import baritone.t;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import org.apache.commons.lang3.SystemUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class u
implements IWorldProvider {
    private static final Map<Path, t> a = new HashMap<Path, t>();
    private final a a;
    private final IPlayerContext a;
    private t a;
    private Level a;

    public u(a a2) {
        this.a = a2;
        this.a = a2.getPlayerContext();
    }

    public final t a() {
        this.b();
        return this.a;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(Level level) {
        Optional<Tuple> optional;
        block6: {
            Object object2;
            Object object3;
            block2: {
                block4: {
                    block5: {
                        block3: {
                            block1: {
                                object3 = this;
                                if (!((u)object3).a.minecraft().hasSingleplayerServer()) break block1;
                                object2 = ((u)object3).a.minecraft().getSingleplayerServer().getWorldPath(LevelResource.ROOT);
                                if (object2.relativize(((u)object3).a.minecraft().gameDirectory.toPath()).getNameCount() != 2) {
                                    object2 = object2.getParent();
                                }
                                object3 = object2 = object2.resolve("baritone");
                                break block2;
                            }
                            object2 = ((u)object3).a.minecraft().getCurrentServer();
                            if (object2 == null) break block3;
                            Object object4 = object2 = object2.isRealm() ? "realms" : ((ServerData)object2).ip;
                            if (!SystemUtils.IS_OS_WINDOWS) break block4;
                            break block5;
                        }
                        System.out.println("World seems to be a replay. Not loading Baritone cache.");
                        ((u)object3).a = null;
                        ((u)object3).a = ((u)object3).a.world();
                        optional = Optional.empty();
                        break block6;
                    }
                    object2 = ((String)object2).replace(":", "_");
                }
                object2 = ((u)object3).a.a.resolve((String)object2);
                object3 = ((u)object3).a.a;
            }
            optional = Optional.of(new Tuple(object2, object3));
        }
        optional.ifPresent(object -> {
            Path path2 = (Path)object.getA();
            object = (Path)object.getB();
            try {
                Files.createDirectories((Path)object, new FileAttribute[0]);
                Files.write(object.resolve("readme.txt"), "https://github.com/cabaletta/baritone\n".getBytes(StandardCharsets.US_ASCII), new OpenOption[0]);
            }
            catch (IOException iOException) {}
            Path path3 = path2;
            path2 = level;
            object = path3;
            ResourceLocation resourceLocation = path2.dimension().location();
            int n2 = path2.dimensionType().logicalHeight();
            object = object.resolve(resourceLocation.getNamespace()).resolve(resourceLocation.getPath() + "_" + n2);
            try {
                Files.createDirectories((Path)object, new FileAttribute[0]);
            }
            catch (IOException iOException) {}
            System.out.println("Baritone world data dir: " + String.valueOf(object));
            Map<Path, t> map = a;
            synchronized (map) {
                this.a = a.computeIfAbsent((Path)object, path -> new t((Path)path, level.dimensionType()));
            }
            this.a = this.a.world();
        });
    }

    public final void a() {
        t t2 = this.a;
        this.a = null;
        this.a = null;
        if (t2 == null) {
            return;
        }
        t2.a();
    }

    private void b() {
        if (this.a != this.a.world()) {
            if (this.a != null) {
                System.out.println("mc.world unloaded unnoticed! Unloading Baritone cache now.");
                this.a();
            }
            if (this.a.world() != null) {
                System.out.println("mc.world loaded unnoticed! Loading Baritone cache now.");
                u u2 = this;
                u2.a(u2.a.world());
                return;
            }
        } else if (this.a == null && this.a.world() != null && (this.a.minecraft().hasSingleplayerServer() || this.a.minecraft().getCurrentServer() != null)) {
            System.out.println("Retrying to load Baritone cache");
            u u3 = this;
            u3.a(u3.a.world());
        }
    }

    @Override
    public final /* synthetic */ IWorldData getCurrentWorld() {
        return this.a();
    }
}

