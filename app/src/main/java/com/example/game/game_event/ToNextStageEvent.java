package com.example.game.game_event;

import android.content.res.Resources;

import com.example.game.game.EnemySpawnSystem;
import com.example.game.game.GameSystem;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GamePlayScene;
import com.example.game.stage.Stage;
import com.example.game.stage.StageType;
import com.example.game.utility.StopWatch;

public class ToNextStageEvent extends GameEvent {
    private StopWatch existTimer;
    private float time = 1.0f;
    private Stage stage = null;
    private GameSystem gameSystem= null;
    public ToNextStageEvent(
            GameSystem gameSystem,
            Stage stage) {
        this.existTimer = new StopWatch(time);
        this.stage = stage;
        this.stage.changeType(this.stage.getNextType());
        this.gameSystem = gameSystem;
    }

    @Override
    public boolean update(float deltaIime) {
        if (existTimer.tick(deltaIime)) {
            this.stage.setScrollSpeed(this.stage.getDefaultScrollSpeed());
            this.gameSystem.resetSpawnSystem(this.stage.getCurrentType());
            return true;
        } // if
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
    }
}
