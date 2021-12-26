package com.example.game.game_event;

import android.graphics.PointF;

import com.example.game.action.action_component.PlaneActionComponent;
import com.example.game.actor.player.PlayerPlane;
import com.example.game.collision.collision_component.PlaneCollisionComponent;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.component.ComponentType;
import com.example.game.game.ActorContainer;
import com.example.game.main.Game;
import com.example.game.parameter.recovery.Recovery;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GamePlayScene;
import com.example.game.stage.Stage;
import com.example.game.stage.StageType;
import com.example.game.ui.UIChangeBulletPanel;
import com.example.game.utility.PointFUtilities;
import com.example.game.utility.StopWatch;

public class ToNextStageEvent extends GameEvent{
    private StopWatch existTimer;
    private float time = 1.4f;
    private GamePlayScene gamePlayScene;
    private PlayerPlane player;
    private PlaneActionComponent planeActionComponent;
    private PlaneCollisionComponent planeCollisionComponent;

    private float toCenterSpeed = 15.0f;
    private float toNextSpeed = 38.0f;
    private int moveSequence = 0;
    private PointF centerPosiotion = new PointF();

    private int recoveryBonus = 5;

    public ToNextStageEvent(GamePlayScene gamePlayScene,
                            ActorContainer actorContainer,
                            Stage stage,
                            UIChangeBulletPanel uiChangeBullePanel) {
        this.existTimer = new StopWatch(time);
        this.gamePlayScene = gamePlayScene;
        this.player = actorContainer.getMainChara();
        this.planeActionComponent = this.player.getComponent(ComponentType.PlaneAction);
        this.planeCollisionComponent = this.player.getComponent(ComponentType.PlaneCollision);
        this.planeActionComponent.inactivate();
        this.planeCollisionComponent.inactivate();
        this.centerPosiotion.x = Game.getDisplayRealSize().x * 0.5f;
        this.centerPosiotion.y = Game.getDisplayRealSize().y * 0.8f;
        this.centerPosiotion.x -= BitmapSizeStatic.player.x * 0.5f;

        this.player.getHpParameter().increaseValueMax(this.recoveryBonus);
        this.player.applyRecovery(new Recovery(this.recoveryBonus) );

        StageType stageType =stage.getCurrentType();
        if(stageType == StageType.Type01){
            uiChangeBullePanel.unlockToHomingButton();
        } // if
        else if(stageType == StageType.Type02){
            uiChangeBullePanel.unlockToThreeWayButton();
        } // else if
    }

    public void toCenter(){
        float distance = PointFUtilities.magnitude(
                this.player.getPosition(),
                this.centerPosiotion
        );
        if(distance < toCenterSpeed){
            this.moveSequence++;
            return;
        } // if

        PointF position =  this.player.getPosition();
        PointF move =  PointFUtilities.normal(
                this.player.getPosition(),
                this.centerPosiotion
        );
        move.x *= this.toCenterSpeed;
        move.y *= this.toCenterSpeed;
        position.x += move.x;
        position.y += move.y;
        this.player.setPosition( position);
    }
    public void toNext(){
        PointF position =  this.player.getPosition();
        position.y -= this.toNextSpeed;
        this.player.setPosition(position);
    }

    public void initialize() {
    }

    @Override
    public boolean update(float deltaIime) {
        switch (this.moveSequence){
            case 0:
                this.toCenter();
                break;
            case 1:
                this.toNext();
                break;
        } // switch

        if (existTimer.tick(deltaIime)) {
            this.player.getWeapon("MainWeapon").incrementShotPower();
            this.planeActionComponent.activate();
            this.planeCollisionComponent.activate();
            this.gamePlayScene.createTransitionStageExitEvent(
            this.player,
            this.centerPosiotion);
            return true;
        } // if
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
    }
}