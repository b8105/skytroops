package com.example.game.game_event;

import android.graphics.PointF;

import com.example.game.game.enemy_spawn.EnemySpawnSystem;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.ui.UILabel;
import com.example.game.utility.StopWatch;

public class TutorialEvent extends GameEvent {
    private UILabel uiLabel;
    private StopWatch existStopWatch = new StopWatch(2.0f);
    private EnemySpawnSystem enemySpawnSystem;

    public TutorialEvent(
            ImageResource imageResource,
            ImageResourceType imageResourceType,
            EnemySpawnSystem enemySpawnSystem
    ) {
        this.enemySpawnSystem = enemySpawnSystem;
        this.enemySpawnSystem.inactivate();

        PointF position = Game.getDisplayHalfRealSize();
        position.x -= Game.getDisplayHalfRealSize().x * 0.5f;
        position.y -= Game.getDisplayHalfRealSize().y * 0.5f;
        this.uiLabel = new UILabel(
                imageResource, imageResourceType,
                position);
    }

    @Override
    public boolean update(float deltaTime) {
        if (this.existStopWatch.tick(deltaTime)) {
            this.enemySpawnSystem.activate();
            return true;
        } // if
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
        this.uiLabel.draw(out);
    }
}