package com.example.game.game_event;

import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GamePlayScene;
import com.example.game.stage.Stage;
import com.example.game.utility.StopWatch;

public class ToTransitionStageEvent extends GameEvent {
    private float time = 1.0f;
    private StopWatch existTimer;
    private StopWatch  transitionExistTimer;
    private GamePlayScene gamePlayScene;
    private Stage stage;


    public ToTransitionStageEvent(
            GamePlayScene gamePlayScene,
            Stage stage) {
        this.existTimer = new StopWatch(time);
        this.transitionExistTimer = new StopWatch(time);
        this.gamePlayScene = gamePlayScene;
        this.stage = stage;
        this.stage.setScrollSpeed(this.stage.getAcceleScrollSpeed());
    }

    @Override
    public boolean update(float deltaIime) {
        if (this.transitionExistTimer != null) {
            if (this.transitionExistTimer.tick(deltaIime)) {
                this.stage.changeTransitionStage();
                this.transitionExistTimer = null;
            } // if
            return false;
        } // if

        if (existTimer.tick(deltaIime)) {
            this.gamePlayScene.createToNextStageEvent();
            return true;
        } // if

        return false;
    }
    @Override
    public void draw(RenderCommandQueue out) {

    }
}