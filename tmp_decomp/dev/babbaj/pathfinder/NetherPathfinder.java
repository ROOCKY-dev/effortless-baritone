/*
 * Decompiled with CFR 0.152.
 */
package dev.babbaj.pathfinder;

import dev.babbaj.pathfinder.PathSegment;
import dev.babbaj.pathfinder.xz.x;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class NetherPathfinder {
    private static int CACHE_MISS_GENERATE;
    private static int CACHE_MISS_AIR;
    public static int CACHE_MISS_SOLID;
    private static final boolean IS_LOADED;

    public static native long newContext(long var0);

    public static native void freeContext(long var0);

    public static native void insertChunkData(long var0, int var2, int var3, boolean[] var4);

    public static native long getOrCreateChunk(long var0, int var2, int var3);

    public static native long getChunkPointer(long var0, int var2, int var3);

    public static native boolean hasChunkFromJava(long var0, int var2, int var3);

    public static native void cullFarChunks(long var0, int var2, int var3, int var4);

    public static native PathSegment pathFind(long var0, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9, int var10, boolean var11);

    private static native void raytrace0(long var0, int var2, int var3, double[] var4, double[] var5, boolean[] var6, double[] var7);

    private static void raytrace(long l2, int n2, int n3, double[] dArray, double[] dArray2, boolean[] blArray, double[] dArray3) {
        if (dArray.length < n3 * 3 || dArray2.length < n3 * 3 || blArray.length < n3 || dArray3 != null && dArray3.length < n3 * 3) {
            throw new IllegalArgumentException("Bad array lengths idiot");
        }
        NetherPathfinder.raytrace0(l2, n2, n3, dArray, dArray2, blArray, dArray3);
    }

    private static native int isVisibleMulti0(long var0, int var2, int var3, double[] var4, double[] var5, boolean var6);

    public static int isVisibleMulti$eb34986(long l2, int n2, double[] dArray, double[] dArray2) {
        if (dArray.length < 24 || dArray2.length < 24) {
            throw new IllegalArgumentException("Bad array lengths idiot");
        }
        return NetherPathfinder.isVisibleMulti0(l2, n2, 8, dArray, dArray2, false);
    }

    public static native boolean isVisible(long var0, int var2, double var3, double var5, double var7, double var9, double var11, double var13);

    public static native boolean cancel(long var0);

    static native long getX2Index();

    public static boolean isThisSystemSupported() {
        return IS_LOADED;
    }

    private static String getNativeLibName() {
        if (Integer.parseInt(System.getProperty("sun.arch.data.model")) != 64) {
            throw new UnsupportedOperationException("Unsupported architecture (64-bit required)");
        }
        String string = System.getProperty("os.name").toLowerCase();
        String string2 = System.getProperty("os.arch").toLowerCase();
        if (string2.contains("arm") || string2.contains("aarch64")) {
            string2 = "aarch64";
        } else if (string2.equals("x86_64") || string2.equals("amd64")) {
            string2 = "x86_64";
        } else {
            throw new UnsupportedOperationException("Unsupported architecture: ".concat(String.valueOf(string2)));
        }
        if (string.contains("linux")) {
            return "libnether_pathfinder-" + string2 + ".so";
        }
        if (string.contains("windows")) {
            return "nether_pathfinder-" + string2 + ".dll";
        }
        if (string.contains("mac")) {
            return "libnether_pathfinder-" + string2 + ".dylib";
        }
        throw new UnsupportedOperationException("Unsupported operating system: ".concat(String.valueOf(string)));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static byte[] getNativeLib(String object) {
        Object object2;
        Object object3;
        InputStream inputStream;
        block26: {
            inputStream = NetherPathfinder.class.getClassLoader().getResourceAsStream("natives.zip.xz");
            Throwable throwable = null;
            try {
                object3 = new x(inputStream);
                Throwable throwable2 = null;
                try {
                    block25: {
                        object2 = new ZipInputStream((InputStream)object3);
                        Throwable throwable3 = null;
                        try {
                            Object object4;
                            while ((object4 = ((ZipInputStream)object2).getNextEntry()) != null) {
                                int n2;
                                if (!((ZipEntry)object4).getName().equals(object)) continue;
                                object = new ByteArrayOutputStream();
                                object4 = new byte[4096];
                                while ((n2 = ((FilterInputStream)object2).read((byte[])object4)) != -1) {
                                    ((ByteArrayOutputStream)object).write((byte[])object4, 0, n2);
                                }
                                object = ((ByteArrayOutputStream)object).toByteArray();
                                break block25;
                            }
                            break block26;
                        }
                        catch (Throwable throwable4) {
                            try {
                                Throwable throwable5 = throwable4;
                                throwable3 = throwable4;
                                throw throwable5;
                            }
                            catch (Throwable throwable6) {
                                if (throwable3 == null) {
                                    ((ZipInputStream)object2).close();
                                    throw throwable6;
                                }
                                try {
                                    ((ZipInputStream)object2).close();
                                    throw throwable6;
                                }
                                catch (Throwable throwable7) {
                                    throwable3.addSuppressed(throwable7);
                                    throw throwable6;
                                }
                            }
                        }
                    }
                    ((ZipInputStream)object2).close();
                    ((x)object3).close();
                    if (inputStream == null) return object;
                }
                catch (Throwable throwable8) {
                    try {
                        object2 = throwable8;
                        throwable2 = throwable8;
                        throw object2;
                    }
                    catch (Throwable throwable9) {
                        if (throwable2 == null) {
                            ((x)object3).close();
                            throw throwable9;
                        }
                        try {
                            ((x)object3).close();
                            throw throwable9;
                        }
                        catch (Throwable throwable10) {
                            throwable2.addSuppressed(throwable10);
                            throw throwable9;
                        }
                    }
                }
            }
            catch (Throwable throwable11) {
                try {
                    object3 = throwable11;
                    throwable = throwable11;
                    throw object3;
                }
                catch (Throwable throwable12) {
                    if (inputStream == null) throw throwable12;
                    if (throwable == null) {
                        inputStream.close();
                        throw throwable12;
                    }
                    try {
                        inputStream.close();
                        throw throwable12;
                    }
                    catch (Throwable throwable13) {
                        throwable.addSuppressed(throwable13);
                        throw throwable12;
                    }
                }
            }
            inputStream.close();
            return object;
        }
        ((ZipInputStream)object2).close();
        ((x)object3).close();
        if (inputStream == null) throw new NullPointerException("Failed to find pathfinder library: ".concat(String.valueOf(object)));
        inputStream.close();
        throw new NullPointerException("Failed to find pathfinder library: ".concat(String.valueOf(object)));
    }

    private static void tryLoadLibrary() {
        String string;
        if (Integer.parseInt(System.getProperty("sun.arch.data.model")) != 64) {
            throw new UnsupportedOperationException("Unsupported architecture (64-bit required)");
        }
        Object object = System.getProperty("os.name").toLowerCase();
        Object object2 = System.getProperty("os.arch").toLowerCase();
        if (((String)object2).contains("arm") || ((String)object2).contains("aarch64")) {
            object2 = "aarch64";
        } else if (((String)object2).equals("x86_64") || ((String)object2).equals("amd64")) {
            object2 = "x86_64";
        } else {
            throw new UnsupportedOperationException("Unsupported architecture: ".concat(String.valueOf(object2)));
        }
        if (((String)object).contains("linux")) {
            string = "libnether_pathfinder-" + (String)object2 + ".so";
        } else if (((String)object).contains("windows")) {
            string = "nether_pathfinder-" + (String)object2 + ".dll";
        } else if (((String)object).contains("mac")) {
            string = "libnether_pathfinder-" + (String)object2 + ".dylib";
        } else {
            throw new UnsupportedOperationException("Unsupported operating system: ".concat(String.valueOf(object)));
        }
        object = string;
        object2 = NetherPathfinder.getNativeLib(string);
        object = ((String)object).split("\\.");
        object = Files.createTempFile(object[0], "." + object[1], new FileAttribute[0]);
        System.out.println("[nether-pathfinder] Created temp file at " + object.toAbsolutePath());
        try {
            Files.write((Path)object, (byte[])object2, new OpenOption[0]);
            System.load(object.toAbsolutePath().toString());
        }
        catch (Throwable throwable) {
            try {
                Files.delete((Path)object);
            }
            catch (IOException iOException) {
                System.err.println("[nether-pathfinder] Failed to delete temp file");
            }
            if (!object.toFile().delete()) {
                object.toFile().deleteOnExit();
            }
            throw throwable;
        }
        try {
            Files.delete((Path)object);
        }
        catch (IOException iOException) {
            System.err.println("[nether-pathfinder] Failed to delete temp file");
        }
        if (!object.toFile().delete()) {
            object.toFile().deleteOnExit();
            return;
        }
    }

    static {
        CACHE_MISS_SOLID = 2;
        boolean bl2 = false;
        try {
            String string;
            if (Integer.parseInt(System.getProperty("sun.arch.data.model")) != 64) {
                throw new UnsupportedOperationException("Unsupported architecture (64-bit required)");
            }
            Object object = System.getProperty("os.name").toLowerCase();
            Object object2 = System.getProperty("os.arch").toLowerCase();
            if (((String)object2).contains("arm") || ((String)object2).contains("aarch64")) {
                object2 = "aarch64";
            } else if (((String)object2).equals("x86_64") || ((String)object2).equals("amd64")) {
                object2 = "x86_64";
            } else {
                throw new UnsupportedOperationException("Unsupported architecture: ".concat(String.valueOf(object2)));
            }
            if (((String)object).contains("linux")) {
                string = "libnether_pathfinder-" + (String)object2 + ".so";
            } else if (((String)object).contains("windows")) {
                string = "nether_pathfinder-" + (String)object2 + ".dll";
            } else if (((String)object).contains("mac")) {
                string = "libnether_pathfinder-" + (String)object2 + ".dylib";
            } else {
                throw new UnsupportedOperationException("Unsupported operating system: ".concat(String.valueOf(object)));
            }
            object = string;
            object2 = NetherPathfinder.getNativeLib(string);
            object = ((String)object).split("\\.");
            object = Files.createTempFile(object[0], "." + object[1], new FileAttribute[0]);
            System.out.println("[nether-pathfinder] Created temp file at " + object.toAbsolutePath());
            try {
                Files.write((Path)object, (byte[])object2, new OpenOption[0]);
                System.load(object.toAbsolutePath().toString());
            }
            catch (Throwable throwable) {
                try {
                    Files.delete((Path)object);
                }
                catch (IOException iOException) {
                    System.err.println("[nether-pathfinder] Failed to delete temp file");
                }
                if (!object.toFile().delete()) {
                    object.toFile().deleteOnExit();
                }
                throw throwable;
            }
            try {
                Files.delete((Path)object);
            }
            catch (IOException iOException) {
                System.err.println("[nether-pathfinder] Failed to delete temp file");
            }
            if (!object.toFile().delete()) {
                object.toFile().deleteOnExit();
            }
            System.out.println("[nether-pathfinder] Loaded shared library");
            bl2 = true;
        }
        catch (Throwable throwable) {
            System.err.println("[nether-pathfinder] Failed to load shared library");
            throwable.printStackTrace();
        }
        IS_LOADED = bl2;
    }
}

