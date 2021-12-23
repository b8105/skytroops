package com.example.game.game_event;

import android.content.res.Resources;

import com.example.game.game.EnemySpawnSystem;
import com.example.game.game.GameSystem;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GamePlayScene;
import com.example.game.stage.Stage;
import com.example.game.stage.StageType;

public class ToNextStageEvent extends GameEvent {
    GamePlayScene gamePlayScene;

    public ToNextStageEvent(GamePlayScene gamePlayScene) {
        this.gamePlayScene = gamePlayScene;
    }

    @Override
    public boolean update(float deltaIime) {
        this.gamePlayScene.toNextStage();
        return true;
    }

    @Override
    public void draw(RenderCommandQueue out) {
    }
}
