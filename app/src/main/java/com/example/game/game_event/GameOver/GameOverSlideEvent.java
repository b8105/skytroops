package com.example.game.game_event.GameOver;

import android.graphics.PointF;

import com.example.game.game_event.GameEvent;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.scene.GamePlayScene;
import com.example.game.ui.UILabel;

import java.util.List;

public class GameOverSlideEvent extends GameEvent {
    List<UILabel> uiLabels = null;
    float positionY = Game.getDisplayRealSize().y * 0.5f;
    final float speed = -24.0f;
    float time = 0.0f;
    final float timeMax = 0.5f;
    boolean eventFired = false;
    GamePlayScene gamePlayScene;
    public GameOverSlideEvent(List<UILabel> uiLabels, GamePlayScene gamePlayScene) {
        this.uiLabels = uiLabels;
        this.gamePlayScene = gamePlayScene;
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
        else {

            if(!eventFired){
                eventFired = true;
                gamePlayScene.createGameOverEvent();
            } // if
        } // else
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
        for (UILabel label : this.uiLabels) {
            label.draw(out.getRenderCommandList(RenderLayerType.FrontEvent));
        } // for
    }
}