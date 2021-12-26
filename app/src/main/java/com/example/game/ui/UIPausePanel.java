package com.example.game.ui;

import android.graphics.PointF;
import android.media.Image;

import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.InputEvent;
import com.example.game.common.InputTouchType;
import com.example.game.common.shape.Circle;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GamePlayScene;
import com.example.game.scene.GamePlaySceneStateType;

public class UIPausePanel implements UIPanel {
    private UIButton uiPauseButton = null;
    private float tapCircleRadius = 4.0f;
    private GamePlayScene gamePlayScene = null;

    private UILabel board = null;
    private UIButton toTitleButton = null;
    private UIButton continueButton = null;


    public UIPausePanel(ImageResource imageResource, GamePlayScene gamePlayScene) {
        PointF screenSize = new PointF(Game.getDisplayRealSize().x, Game.getDisplayRealSize().y);
        this.gamePlayScene = gamePlayScene;
        this.uiPauseButton = new UIButton(
                imageResource,
                ImageResourceType.PauseMeneIcon,
                new PointF(
                        Game.getDisplayRealSize().x - BitmapSizeStatic.menuIcon.x * 0.75f,
                        BitmapSizeStatic.menuIcon.y * 0.75f)
        );

        this.board = new UILabel(imageResource, ImageResourceType.GameResultBackground,
                new PointF(screenSize.x * 0.5f, screenSize.y * 0.5f));

        this.toTitleButton = new UIButton(
                imageResource,
                ImageResourceType.RestartButton,
                new PointF(screenSize.x * 0.3f,
                        screenSize.y * 0.85f)
        );
        this.continueButton = new UIButton(
                imageResource,
                ImageResourceType.StartButton,
                new PointF(screenSize.x * 0.7f,
                        screenSize.y * 0.85f)
        );
    }

    private void touchIcon(Circle touchCircle) {
        if (this.uiPauseButton.containCircle(touchCircle)) {
            GamePlaySceneStateType sceneStateType = this.gamePlayScene.getGamePlaySceneStateType();
            if (sceneStateType == GamePlaySceneStateType.Play) {
                this.gamePlayScene.gamePause();
            } // if
            else if (sceneStateType == GamePlaySceneStateType.Pause) {
                this.gamePlayScene.gamePlay();
            } // else if
        } // if
    }

    private void touchToTitle(Circle touchCircle) {
        if (this.toTitleButton.containCircle(touchCircle)) {
            this.gamePlayScene.sceneExitToTitle();
        } // if
    }

    private void touchRestart(Circle touchCircle) {
        if (this.continueButton.containCircle(touchCircle)) {
            GamePlaySceneStateType sceneStateType = this.gamePlayScene.getGamePlaySceneStateType();
            if (sceneStateType == GamePlaySceneStateType.Play) {
                this.gamePlayScene.gamePause();
            } // if
            else if (sceneStateType == GamePlaySceneStateType.Pause) {
                this.gamePlayScene.gamePlay();
            } // else if
        } // if
    }

    @Override
    public void input(InputEvent input) {
        if (input.enable && input.touchType == InputTouchType.Touch) {
            Circle circle = new Circle(
                    input.positionX, input.positionY,
                    this.tapCircleRadius);
            this.touchIcon(circle);
            if (this.gamePlayScene.getGamePlaySceneStateType() == GamePlaySceneStateType.Pause) {
                this.touchToTitle(circle);
                this.touchRestart(circle);
            } // if
        } // if
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void draw(RenderCommandQueue out) {
        if (this.gamePlayScene.getGamePlaySceneStateType() == GamePlaySceneStateType.Pause) {
            this.board.draw(out);
            this.toTitleButton.draw(out);
            this.continueButton.draw(out);
        } // if
        this.uiPauseButton.draw(out);
    }
}
