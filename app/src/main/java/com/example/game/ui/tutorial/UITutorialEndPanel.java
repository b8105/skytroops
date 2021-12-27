package com.example.game.ui.tutorial;

import android.graphics.PointF;

import com.example.game.common.Activatable;
import com.example.game.common.InputEvent;
import com.example.game.common.shape.Circle;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.game_event.TutorialEvent;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GamePlayScene;
import com.example.game.ui.UIPanel;

//! チュートリアルイベント終了ボタンを表示します
public class UITutorialEndPanel implements UIPanel, Activatable {
    private boolean active;
    private UITutotialEndButton uiTutotialEndButton;
    private int touchRadius = 6;

    //! 保持するボタンがクリックされたらイベントの終了フラグを立てます
    private TutorialEvent tutorialEvent;

    public UITutorialEndPanel(ImageResource imageResource) {
        PointF center = new PointF(
                Game.getDisplayRealSize().x * 0.5f,
                Game.getDisplayRealSize().y * 0.72f);
        this.uiTutotialEndButton = new UITutotialEndButton(imageResource,
                ImageResourceType.ContinueButton,
                center);
    }

    public void setTutorialEvent(TutorialEvent tutorialEvent) {
        this.tutorialEvent = tutorialEvent;
    }

    @Override
    public void input(InputEvent input) {
        if (this.uiTutotialEndButton.containCircle(new Circle(
                input.positionX, input.positionY, touchRadius
        ))) {
            this.tutorialEvent.setEndFlag(true);
            this.inactivate();
        } // if
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void draw(RenderCommandQueue out) {
        this.uiTutotialEndButton.draw(out);
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void activate() {
        this.active = true;
    }

    @Override
    public void inactivate() {
        this.active = false;
    }

}