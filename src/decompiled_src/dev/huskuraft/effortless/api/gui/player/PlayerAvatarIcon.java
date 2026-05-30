/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.gui.player;

import dev.huskuraft.effortless.api.core.PlayerInfo;
import dev.huskuraft.effortless.api.gui.AbstractWidget;
import dev.huskuraft.effortless.api.platform.Entrance;
import dev.huskuraft.effortless.api.renderer.PlayerHeadRenderer;
import dev.huskuraft.effortless.api.renderer.Renderer;

public class PlayerAvatarIcon
extends AbstractWidget {
    private PlayerInfo playerInfo;

    public PlayerAvatarIcon(Entrance entrance, int x, int y, int size, PlayerInfo playerInfo) {
        super(entrance, x, y, size, size, null);
        this.playerInfo = playerInfo;
    }

    @Override
    public void renderWidget(Renderer renderer, int mouseX, int mouseY, float deltaTick) {
        super.renderWidget(renderer, mouseX, mouseY, deltaTick);
        renderer.renderRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), -1620284308);
        if (this.playerInfo.getSkin() != null) {
            PlayerHeadRenderer.draw(renderer, this.playerInfo.getSkin(), this.getX(), this.getY(), this.getWidth());
        }
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public void setSize(int size) {
        this.setWidth(size);
        this.setHeight(size);
    }
}
