package com.example.game.game_event.StageTransition;

import android.graphics.Color;

import com.example.game.common.shape.Rectangle;
import com.example.game.game.GameSystem;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.game_event.GameEvent;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderRectangleInfo;
import com.example.game.scene.GamePlayScene;
import com.example.game.stage.Stage;
import com.example.game.stage.StageType;
import com.example.game.ui.change_bullet.UIChangeBulletPanel;
import com.example.game.utility.StopWatch;

public class TransitionStageEnterEvent extends GameEvent {
    private StopWatch existTimer;
    private float time = 1.0f;
    private float timeCoefficient = 3.0f;
    private Stage stage = null;
    private GameSystem gameSystem= null;
    private GamePlayScene gamePlayScene= null;
    public TransitionStageEnterEvent(
            GameSystem gameSystem,
            Stage stage,
            GamePlayScene gamePlayScene,
            UIChangeBulletPanel uiChangeBulletPanel) {
        this.existTimer = new StopWatch(time);
        this.stage = stage;
        this.gameSystem = gameSystem;
        this.gamePlayScene = gamePlayScene;
        this.stage.changeType(this.stage.getNextType());
        if(this.stage.getCurrentType() == StageType.Type02){
            uiChangeBulletPanel.changeToHoming();
        } // if
        else if(this.stage.getCurrentType() == StageType.Type03){
            uiChangeBulletPanel.changeToThreeway();
        } // else if

    }

    @Override
    public boolean update(float deltaIime) {
        if (existTimer.tick(deltaIime * timeCoefficient)) {
            this.gameSystem.resetSpawnSystem(this.stage.getCurrentType());
            if(this.stage.getCurrentType() == StageType.Type02){
                this.gamePlayScene.createTutorialEvent(ImageResourceType.HowtoBulletChage);
            } // if
            return true;
        } // if
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.Blackout);
        Rectangle rectangle = new Rectangle();

        StopWatch time = this.existTimer;
        float alpha = 255 - (time.getDevidedTime() * 255.0f);
        RenderRectangleInfo info = new RenderRectangleInfo(
                Color.BLACK,
                (int) alpha);

        rectangle.right = Game.getDisplayRealSize().x;
        rectangle.bottom = Game.getDisplayRealSize().y;
        list.drawRectangle(
                rectangle,
                info
        );
    }
}