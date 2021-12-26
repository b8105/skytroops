package com.example.game.game_event;

import android.graphics.PointF;

import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GamePlayScene;
import com.example.game.ui.UILabel;
import com.example.game.utility.StopWatch;
import com.example.game.utility.animation.BezierCurveAnimation;

public class GameOverStartEvent extends GameEvent {
    class GameOverDrawPositionController {
        StopWatch timer;
        float timeMax = 2.5f;
        UILabel target;
        float deltaTimeMultiply = 2.0f;
        boolean end = false;
        BezierCurveAnimation animation_position = new BezierCurveAnimation(1.0f);

        GameOverDrawPositionController(
                UILabel target, float x) {
            this.timer = new StopWatch(timeMax);
            this.target = target;
            float y  = Game.getDisplayRealSize().y * 0.5f;
            this.animation_position.addControlPoint(new PointF(x, -y));
            this.animation_position.addControlPoint(new PointF(x, y));
            this.animation_position.addControlPoint(new PointF(x, y));
            this.animation_position.addControlPoint(new PointF(x, y));
            this.animation_position.addControlPoint(new PointF(x, y));
            this.timer.reset(this.timeMax);
        }

        void update(float delta_time) {
            if (!this.end) {
                float nomalized_time = this.timer.getTime() / this.timeMax;
                PointF point = this.animation_position.calculatePointPosition(nomalized_time);

                this.target.setPosition(point);
                if (this.timer.tick(delta_time * this.deltaTimeMultiply)) {
                    this.end = true;
                } // if
            } // if
        }
    }

    private GamePlayScene gamePlayScene;
    private UILabel mainImage = null;
    private PointF position = new PointF(
            Game.getDisplayRealSize().x * 0.5f,
            Game.getDisplayRealSize().y * 0.5f);
    private GameOverDrawPositionController drawPositionController;

    private StopWatch stopWatch = new StopWatch(0.5f);

    public GameOverStartEvent(GamePlayScene gamePlayScene,ImageResource imageResource) {
        this.gamePlayScene = gamePlayScene;
        this.mainImage = new UILabel(
                imageResource,
                ImageResourceType.GameOverG,
                position);
        drawPositionController = new GameOverDrawPositionController(mainImage, 500.0f);
    }
    public GameOverStartEvent(GamePlayScene gamePlayScene,ImageResource imageResource,ImageResourceType imageResourceType) {
        this.gamePlayScene = gamePlayScene;
        this.mainImage = new UILabel(
                imageResource,
                imageResourceType,
                position);
        drawPositionController = new GameOverDrawPositionController(mainImage, 500.0f);
    }

    @Override
    public boolean update(float deltaTime) {
        drawPositionController.update(deltaTime);
        if(this.stopWatch.tick(deltaTime)){

        } // if
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
        this.mainImage.draw(out);
    }
}