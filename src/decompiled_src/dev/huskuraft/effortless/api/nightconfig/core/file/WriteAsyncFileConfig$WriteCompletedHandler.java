/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.file;

import dev.huskuraft.effortless.api.nightconfig.core.io.WritingException;
import java.io.IOException;
import java.nio.channels.CompletionHandler;

private final class WriteAsyncFileConfig.WriteCompletedHandler
implements CompletionHandler<Integer, Object> {
    private WriteAsyncFileConfig.WriteCompletedHandler() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void completed(Integer result, Object attachment) {
        WriteAsyncFileConfig.this.currentlyWriting.set(false);
        if (WriteAsyncFileConfig.this.mustWriteAgain.getAndSet(false)) {
            WriteAsyncFileConfig.this.save(false);
        } else {
            Object object = WriteAsyncFileConfig.this.channelGuard;
            synchronized (object) {
                try {
                    WriteAsyncFileConfig.this.channel.close();
                    WriteAsyncFileConfig.this.channel = null;
                }
                catch (IOException e) {
                    this.failed((Throwable)e, (Object)null);
                }
                finally {
                    WriteAsyncFileConfig.this.channelGuard.notify();
                }
            }
        }
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        throw new WritingException("Error while saving the FileConfig to " + WriteAsyncFileConfig.this.nioPath, exc);
    }
}
