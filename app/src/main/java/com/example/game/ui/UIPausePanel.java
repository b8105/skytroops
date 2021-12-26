package com.example.game.ui;

import android.graphics.PointF;
import android.media.Image;

import com.example.game.common.InputEvent;
import com.example.game.common.shape.Circle;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GamePlayScene;

public class UIPausePanel implements UIPanel {
    private UIButton uiPauseButton = null;
    private float tapCircleRadius = 4.0f;
    private GamePlayScene gamePlayScene = null;

    public UIPausePanel(ImageResource imageResource, GamePlayScene gamePlayScene){
        this.gamePlayScene = gamePlayScene;
        this.uiPauseButton = new UIButton(
                imageResource,
                ImageResourceType.PauseMeneIcon,
                new PointF(800.0f, 200.0f)
        );
    }

    @Override
    public void input(InputEvent input) {
        if(this.uiPauseButton.containCircle(new Circle(
                    input.positionX,input.positionY,
                    this.tapCircleRadius))){
            this.gamePlayScene.gamePause();
        } // if
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void draw(RenderCommandQueue out) {
        this.uiPauseButton.draw(out);
    }
}
