package com.example.game.game_event;

import android.graphics.Color;
import android.graphics.PointF;

import com.example.game.actor.PlayerPlane;
import com.example.game.common.shape.Rectangle;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderRectangleInfo;
import com.example.game.scene.GamePlayScene;
import com.example.game.stage.Stage;
import com.example.game.utility.StopWatch;

public class TransitionStageExitEvent extends GameEvent {
    private float time = 1.0f;
    private StopWatch existTimer;
    private StopWatch transitionExistTimer;
    private GamePlayScene gamePlayScene;
    private Stage stage;
    private PlayerPlane player;
    private PointF idealPosiotion = new PointF();

    public TransitionStageExitEvent(
            GamePlayScene gamePlayScene,
            Stage stage,
            PlayerPlane player,
            PointF idealPosiotion
    ) {
        this.existTimer = new StopWatch(time);
        this.transitionExistTimer = new StopWatch(time);
        this.gamePlayScene = gamePlayScene;
        this.stage = stage;
        this.player = player;
        this.idealPosiotion = idealPosiotion;
    }

    @Override
    public boolean update(float deltaIime) {
        if (existTimer.tick(deltaIime)) {
            this.gamePlayScene.createTransitionStageEnterEvent();
            this.player.setPosition(this.idealPosiotion);
            return true;
        } // if

        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.Blackout);
        Rectangle rectangle = new Rectangle();

        StopWatch time = this.existTimer;
        float t = time.getDevidedTime();
        float alpha = (t * 255.0f);
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