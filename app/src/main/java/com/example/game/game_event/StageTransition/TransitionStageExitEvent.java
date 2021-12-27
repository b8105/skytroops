package com.example.game.game_event.StageTransition;

import android.graphics.Color;
import android.graphics.PointF;

import com.example.game.actor.player.PlayerPlane;
import com.example.game.common.shape.Rectangle;
import com.example.game.game.GameScorer;
import com.example.game.game_event.GameEvent;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderRectangleInfo;
import com.example.game.scene.GamePlayScene;
import com.example.game.utility.StopWatch;

public class TransitionStageExitEvent extends GameEvent {
    private float time = 1.0f;
    private float timeCoefficient = 3.0f;
    private StopWatch existTimer;
    private StopWatch transitionExistTimer;
    private GamePlayScene gamePlayScene;
    private PlayerPlane player;
    private PointF idealPosiotion = new PointF();
    private GameScorer gameScorer = null;

    public TransitionStageExitEvent(
            GamePlayScene gamePlayScene,
            PlayerPlane player,
            PointF idealPosiotion,
            GameScorer gameScorer
    ) {
        this.existTimer = new StopWatch(time);
        this.transitionExistTimer = new StopWatch(time);
        this.gamePlayScene = gamePlayScene;
        this.player = player;
        this.idealPosiotion = idealPosiotion;
        this.gameScorer = gameScorer;
    }

    @Override
    public boolean update(float deltaIime) {
        if (existTimer.tick(deltaIime * timeCoefficient)) {
            this.gamePlayScene.createTransitionStageEnterEvent();
            this.player.setPosition(this.idealPosiotion);
            this.gameScorer.resetStageScore();
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