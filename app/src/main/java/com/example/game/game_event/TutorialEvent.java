package com.example.game.game_event;

import android.graphics.PointF;

import com.example.game.game.enemy_spawn.EnemySpawnSystem;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.ui.UILabel;
import com.example.game.ui.tutorial.UITutorialEndPanel;
import com.example.game.utility.StopWatch;

public class TutorialEvent extends GameEvent {
    private UILabel uiLabel;
    private StopWatch existStopWatch = new StopWatch(2.0f);
    private EnemySpawnSystem enemySpawnSystem;
    private UITutorialEndPanel uiTutorialEndPanel;
    private boolean endFlag = false;

    public TutorialEvent(
            ImageResource imageResource,
            ImageResourceType imageResourceType,
            UITutorialEndPanel uiTutorialEndPanel,
            EnemySpawnSystem enemySpawnSystem) {
        this.enemySpawnSystem = enemySpawnSystem;
        this.uiTutorialEndPanel = uiTutorialEndPanel;
        this.enemySpawnSystem.inactivate();
        this.uiTutorialEndPanel.setTutorialEvent(this);
        this.uiTutorialEndPanel.activate();

        PointF position = Game.getDisplayHalfRealSize();
        position.x -= Game.getDisplayHalfRealSize().x * 0.5f;
        position.y -= Game.getDisplayHalfRealSize().y * 0.5f;
        this.uiLabel = new UILabel(
                imageResource, imageResourceType,
                position);
    }

    public void setEndFlag(boolean endFlag) {
        this.endFlag = endFlag;
    }

    @Override
    public boolean update(float deltaTime) {
        if (this.endFlag) {
            this.uiTutorialEndPanel.inactivate();
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