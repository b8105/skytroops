package com.example.game.ui.plane_upgrade;

import android.graphics.PointF;

import com.example.game.common.Activatable;
import com.example.game.common.InputEvent;
import com.example.game.common.shape.Circle;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.game_event.StageClearInfoEvent;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GamePlayScene;
import com.example.game.ui.UIPanel;


public class UIUpgradePanel implements UIPanel, Activatable {
    private boolean active;
    private UIUpgradeButton uiUpgradeButton;
    private int touchRadius = 6;
    private GamePlayScene gamePlayScene;
    //! 保持するボタンがクリックされたらイベントの終了フラグを立てます
    private StageClearInfoEvent stageClearInfoDrawEvent;
    public UIUpgradePanel(ImageResource imageResource,GamePlayScene gamePlayScene){
        this.gamePlayScene = gamePlayScene;
        PointF center = new PointF(
                Game.getDisplayRealSize().x * 0.5f,
                Game.getDisplayRealSize().y * 0.72f         );
        this.uiUpgradeButton = new UIUpgradeButton(imageResource,
                ImageResourceType.UpgradeButton,
                center);
    }

    public void setStageClearInfoDrawEvent(StageClearInfoEvent stageClearInfoDrawEvent) {
        this.stageClearInfoDrawEvent = stageClearInfoDrawEvent;
    }

    @Override
    public void input(InputEvent input) {
        if(this.uiUpgradeButton.containCircle(new Circle(
                input.positionX,input.positionY,touchRadius))){

            this.gamePlayScene.createUpgradeEvent();
            this.stageClearInfoDrawEvent.setEndFlag(true);
            this.inactivate();
        } // if
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void draw(RenderCommandQueue out) {
        uiUpgradeButton.draw(out);
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
