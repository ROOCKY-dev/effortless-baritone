/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.session;

import dev.huskuraft.effortless.api.core.Player;
import dev.huskuraft.effortless.session.Session;
import dev.huskuraft.effortless.session.config.SessionConfig;

public interface SessionManager {
    public void onSession(Session var1, Player var2);

    public void onSessionConfig(SessionConfig var1, Player var2);

    public boolean isSessionValid();

    public SessionStatus getSessionStatus();

    public Session getLastSession();

    public SessionConfig getLastSessionConfig();

    public static enum SessionStatus {
        SUCCESS,
        MOD_MISSING,
        SERVER_MOD_MISSING,
        CLIENT_MOD_MISSING,
        PROTOCOL_NOT_MATCH;

    }
}
