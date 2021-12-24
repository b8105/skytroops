package com.example.game.game_event;

import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GamePlayScene;
import com.example.game.scene.transition_state.TransitionStateType;
import com.example.game.stage.Stage;
import com.example.game.utility.StopWatch;

public class EnemyDestroyedEvent extends GameEvent {
    private StopWatch existTimer;
    private float time = 3.0f;
    private GamePlayScene gamePlayScene;

    public EnemyDestroyedEvent(GamePlayScene gamePlayScene) {
        this.existTimer = new StopWatch(time);
        this.gamePlayScene = gamePlayScene;
    }

    public void initialize() {
    }

    @Override
    public boolean update(float deltaIime) {
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