/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.networking;

import dev.huskuraft.effortless.api.networking.CodecException;

public class EncoderException
extends CodecException {
    public EncoderException() {
    }

    public EncoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncoderException(String message) {
        super(message);
    }

    public EncoderException(Throwable cause) {
        super(cause);
    }
}
