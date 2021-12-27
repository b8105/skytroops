package com.example.game.game_event.GameOver;

import android.graphics.PointF;
import android.media.Image;

import com.example.game.common.BitmapSizeStatic;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.game_event.GameEvent;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.scene.GamePlayScene;
import com.example.game.ui.UILabel;
import com.example.game.utility.StopWatch;
import com.example.game.utility.animation.BezierCurveAnimation;

import java.util.ArrayList;
import java.util.List;

public class GameOverStartEvent extends GameEvent {
    class GameOverDrawPositionController {
        private StopWatch timer;
        private float timeMax = 1.0f;
        private UILabel target;
        private boolean end = false;
        private BezierCurveAnimation animation_position = new BezierCurveAnimation(1.0f);
        private boolean active = false;

        GameOverDrawPositionController(
                UILabel target, PointF position, PointF offset ) {
            this.timer = new StopWatch(timeMax);
            this.target = target;
            this.animation_position.addControlPoint(new PointF(position.x, -position.y));
            this.animation_position.addControlPoint(new PointF(position.x + offset.x, position.y+ offset.y));
            this.animation_position.addControlPoint(new PointF(position.x+ offset.x, position.y+ offset.y));
            this.animation_position.addControlPoint(new PointF(position.x+ offset.x, position.y+ offset.y));
            this.animation_position.addControlPoint(new PointF(position.x+ offset.x, position.y+ offset.y));
            this.timer.reset(this.timeMax);
        }

        public void activete() {
            this.active = true;
        }

        public boolean isActive() {
            return active;
        }

        public UILabel getTarget() {
            return this.target;
        }

        void update(float delta_time) {
            if (!this.end) {
                float nomalized_time = this.timer.getTime() / this.timeMax;
                PointF point = this.animation_position.calculatePoint(nomalized_time);

                this.target.setPosition(point);
                if (this.timer.tick(delta_time )) {
                    this.end = true;
                } // if
            } // if
        }
    }

    GamePlayScene gamePlayScene;
    private List<GameOverDrawPositionController> drawPositionController = new ArrayList<>();
    private List<UILabel> images = new ArrayList<>();
    private StopWatch stopWatch = new StopWatch(0.1f);
    private StopWatch existStopWatch = new StopWatch(1.8f);
    private int index = 0;

    public GameOverStartEvent(
            GamePlayScene gamePlayScene,
            ImageResource imageResource) {
        this.gamePlayScene = gamePlayScene;
        PointF position = new PointF();
        float y = Game.getDisplayRealSize().y * 0.5f;
        position.x = 160.0f;
        position.y = y;
        PointF offset = new PointF();
        PointF offsetSmall = new PointF(0.0f, (BitmapSizeStatic.gameOverText.y - BitmapSizeStatic.gameOverTextSmall.y) * 0.5f);

        this.createDrawEvent(imageResource, ImageResourceType.GameOverG, position,offset);
        position.x += BitmapSizeStatic.gameOverText.x;
        this.createDrawEvent(imageResource, ImageResourceType.GameOvera, position,offsetSmall);
        position.x += BitmapSizeStatic.gameOverTextSmall.x;
        this.createDrawEvent(imageResource, ImageResourceType.GameOverm, position,offsetSmall);
        position.x += BitmapSizeStatic.gameOverTextSmall.x;
        this.createDrawEvent(imageResource, ImageResourceType.GameOvere, position,offsetSmall);
        position.x += BitmapSizeStatic.gameOverText.x;
        this.createDrawEvent(imageResource, ImageResourceType.GameOverO, position,offset);
        position.x += BitmapSizeStatic.gameOverTextSmall.x;
        this.createDrawEvent(imageResource, ImageResourceType.GameOverv, position,offsetSmall);
        position.x += BitmapSizeStatic.gameOverTextSmall.x;
        this.createDrawEvent(imageResource, ImageResourceType.GameOvere, position,offsetSmall);
        position.x += BitmapSizeStatic.gameOverTextSmall.x;
        this.createDrawEvent(imageResource, ImageResourceType.GameOverr, position,offsetSmall);
        position.x += BitmapSizeStatic.gameOverTextSmall.x;
    }

    private void createDrawEvent(ImageResource imageResource, ImageResourceType imageResourceType,PointF position, PointF offset){
        UILabel image = new UILabel(
                imageResource,
                imageResourceType,
                new PointF(-position.x, -position.y));
        this.images.add(image);
        this.drawPositionController.add
                (new GameOverDrawPositionController(image, position, offset));
    }

    @Override
    public boolean update(float deltaTime) {
        if(this.existStopWatch.tick(deltaTime)){
            this.gamePlayScene.createGameOverSlideEvent(this.images);
            return true;
        } // if
        for (GameOverDrawPositionController controller : this.drawPositionController) {
            if (controller.isActive()) {
                controller.update(deltaTime);
            } // if
        } // for
        if (this.stopWatch.tick(deltaTime)) {
            this.stopWatch.reset();
            if (this.index < this.drawPositionController.size()) {
                this.drawPositionController.get(this.index).activete();
            } // if
            this.index++;
        } // if
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
        for (GameOverDrawPositionController controller : this.drawPositionController) {
            if (controller.isActive()) {
                controller.getTarget().draw(out.getRenderCommandList(RenderLayerType.FrontEvent));
            } // if
        } // for
    }
}