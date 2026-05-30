/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.platform;

import dev.huskuraft.effortless.api.platform.PlatformLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ServiceConfigurationError;

private static class PlatformLoader.LazyIterator<S>
implements Iterator<PlatformLoader.Loader<S>> {
    private final Class<S> clazz;
    private final ClassLoader loader;
    private Enumeration<URL> configs = null;
    private Iterator<String> pending = null;
    private String nextClassName = null;

    private PlatformLoader.LazyIterator(Class<S> clazz, ClassLoader loader) {
        this.clazz = clazz;
        this.loader = loader;
    }

    private static int parseLine(Class<?> service, URL u, BufferedReader r, int lc, List<String> names) throws IOException, ServiceConfigurationError {
        int n;
        String ln = r.readLine();
        if (ln == null) {
            return -1;
        }
        int ci = ln.indexOf(35);
        if (ci >= 0) {
            ln = ln.substring(0, ci);
        }
        if ((n = (ln = ln.trim()).length()) != 0) {
            int cp;
            if (ln.indexOf(32) >= 0 || ln.indexOf(9) >= 0) {
                PlatformLoader.LazyIterator.fail(service, u, lc, "Illegal configuration-file syntax");
            }
            if (!Character.isJavaIdentifierStart(cp = ln.codePointAt(0))) {
                PlatformLoader.LazyIterator.fail(service, u, lc, "Illegal provider-class name: " + ln);
            }
            for (int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
                cp = ln.codePointAt(i);
                if (Character.isJavaIdentifierPart(cp) || cp == 46) continue;
                PlatformLoader.LazyIterator.fail(service, u, lc, "Illegal provider-class name: " + ln);
            }
            if (!names.contains(ln)) {
                names.add(ln);
            }
        }
        return lc + 1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Iterator<String> parse(Class<?> service, URL url) throws ServiceConfigurationError {
        InputStream inputStream = null;
        BufferedReader reader = null;
        ArrayList<String> names = new ArrayList<String>();
        try {
            inputStream = url.openStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            int lc = 1;
            while ((lc = PlatformLoader.LazyIterator.parseLine(service, url, reader, lc, names)) >= 0) {
            }
        }
        catch (IOException x) {
            PlatformLoader.LazyIterator.fail(service, "Error reading configuration file", x);
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (IOException y) {
                PlatformLoader.LazyIterator.fail(service, "Error closing configuration file", y);
            }
        }
        return names.iterator();
    }

    private static void fail(Class<?> service, String msg, Throwable cause) throws ServiceConfigurationError {
        throw new ServiceConfigurationError(service.getName() + ": " + msg, cause);
    }

    private static void fail(Class<?> service, String msg) throws ServiceConfigurationError {
        throw new ServiceConfigurationError(service.getName() + ": " + msg);
    }

    private static void fail(Class<?> service, URL u, int line, String msg) throws ServiceConfigurationError {
        PlatformLoader.LazyIterator.fail(service, String.valueOf(u) + ":" + line + ": " + msg);
    }

    @Override
    public boolean hasNext() {
        if (this.nextClassName != null) {
            return true;
        }
        if (this.configs == null) {
            try {
                String fullName = PlatformLoader.PREFIX + this.clazz.getName();
                this.configs = this.loader == null ? ClassLoader.getSystemResources(fullName) : this.loader.getResources(fullName);
            }
            catch (IOException x) {
                PlatformLoader.LazyIterator.fail(this.clazz, "Error locating configuration files", x);
            }
        }
        while (this.pending == null || !this.pending.hasNext()) {
            if (!this.configs.hasMoreElements()) {
                return false;
            }
            this.pending = PlatformLoader.LazyIterator.parse(this.clazz, this.configs.nextElement());
        }
        this.nextClassName = this.pending.next();
        return true;
    }

    @Override
    public PlatformLoader.Loader<S> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        String className = this.nextClassName;
        this.nextClassName = null;
        return new PlatformLoader.Loader<S>(this.clazz, className, this.loader);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
