package com.example.game.game_event;

import android.graphics.PointF;

import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.ui.UILabel;

import java.util.List;

public class GameOverSlideEvent extends GameEvent {
    List<UILabel> uiLabels = null;
    float positionY = Game.getDisplayRealSize().y * 0.5f;
    final float speed = -36.0f;
    float time = 0.0f;
    final float timeMax = 0.4f;


    public GameOverSlideEvent(List<UILabel> uiLabels) {
        this.uiLabels = uiLabels;
    }

    @Override
    public boolean update(float deltaTime) {
        this.time += deltaTime;

        if (this.time < this.timeMax) {
            this.positionY += this.speed;
            for (UILabel label : this.uiLabels) {
                label.setPositionY(positionY);
            } // for
        } // if
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
        for (UILabel label : this.uiLabels) {
            label.draw(out.getRenderCommandList(RenderLayerType.FrontEvent));
        } // for
    }
}