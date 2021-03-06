package com.example.game.ui.scene_exit;

import android.graphics.PointF;

import com.example.game.common.Activatable;
import com.example.game.common.InputEvent;
import com.example.game.common.shape.Circle;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.game_event.GameOver.GameOverInfoEvent;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GamePlayScene;
import com.example.game.ui.UIPanel;

//! プレイヤーが倒されゲームオーバーとなった後にでるパネル
//! リザルトシーンに遷移するボタンを持ちます
public class UISceneExitPanel implements UIPanel , Activatable {
    private boolean active;
    private UISceneExitButton uiToTitleButton;
    private int touchRadius = 6;
    private GamePlayScene gamePlayScene;

    //! 保持するボタンがクリックされたらイベントの終了フラグを立てます
    private GameOverInfoEvent gameOverInfoDrawEvent;

    public UISceneExitPanel(ImageResource imageResource, GamePlayScene gamePlayScene){
        this.gamePlayScene = gamePlayScene;
        PointF center = new PointF(
                Game.getDisplayRealSize().x * 0.5f,
                Game.getDisplayRealSize().y * 0.72f         );
        this.uiToTitleButton = new UISceneExitButton(
                imageResource,
                ImageResourceType.ContinueButton,
                center);
    }

    public void setGameOverInfoDrawEvent(GameOverInfoEvent gameOverInfoDrawEvent) {
        this.gameOverInfoDrawEvent = gameOverInfoDrawEvent;
    }

    //! シーンの遷移を開始します
    @Override
    public void input(InputEvent input) {
        if(this.uiToTitleButton.containCircle(new Circle(
                input.positionX,input.positionY,touchRadius))){
            this.gamePlayScene.sceneExit();
            this.gameOverInfoDrawEvent.setEndFlag(true);
            this.inactivate();
        } // if
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void draw(RenderCommandQueue out) {
        uiToTitleButton.draw(out);
    }

    @Override
    public boolean isActive() {
        return this. active ;
    }

    @Override
    public void activate() {
        this. active = true;
    }

    @Override
    public void inactivate() {
        this. active = false;
    }
}
