package com.example.game.game_event.Plane;

import android.graphics.PointF;

import com.example.game.action.action_component.PlaneActionComponent;
import com.example.game.actor.player.PlayerPlane;
import com.example.game.collision.collision_component.PlaneCollisionComponent;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.component.ComponentType;
import com.example.game.game.ActorContainer;
import com.example.game.game.resource.ImageResource;
import com.example.game.game_event.GameEvent;
import com.example.game.main.Game;
import com.example.game.parameter.recovery.Recovery;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.render_component.PlaneSpriteRenderComponent;
import com.example.game.scene.GamePlayScene;
import com.example.game.stage.Stage;
import com.example.game.ui.change_bullet.UIChangeBulletPanel;
import com.example.game.utility.PointFUtilities;
import com.example.game.utility.StopWatch;

public class PlaneMoveToNextStageEvent extends GameEvent {
    private StopWatch existTimer;
    private float time = 1.4f;
    private GamePlayScene gamePlayScene;
    private PlayerPlane player;
    private PlaneActionComponent planeActionComponent;
    private PlaneSpriteRenderComponent planeSpriteRenderComponent;
    private PlaneCollisionComponent planeCollisionComponent;

    private float toCenterSpeed = 15.0f;
    private float toNextSpeed = 38.0f;
    private int moveSequence = 1;
    private PointF centerPosiotion = new PointF();

    private int recoveryBonus = 5;

    public PlaneMoveToNextStageEvent(GamePlayScene gamePlayScene,
                                     ActorContainer actorContainer,
                                     Stage stage,
                                     UIChangeBulletPanel uiChangeBullePanel,
                                     ImageResource imageResource) {
        this.existTimer = new StopWatch(time);
        this.gamePlayScene = gamePlayScene;
        this.player = actorContainer.getMainChara();
        this.planeActionComponent = this.player.getComponent(ComponentType.PlaneAction);
        this.planeSpriteRenderComponent = this.player.getComponent(ComponentType.PlaneSpriteRender);
        this.planeCollisionComponent = this.player.getComponent(ComponentType.PlaneCollision);
        this.planeActionComponent.inactivate();
        this.planeCollisionComponent.inactivate();
        this.centerPosiotion.x = Game.getDisplayRealSize().x * 0.5f;
        this.centerPosiotion.y = Game.getDisplayRealSize().y * 0.8f;
        this.centerPosiotion.x -= BitmapSizeStatic.player.x * 0.5f;

        this.player.getHpParameter().increaseValueMax(this.recoveryBonus);
        this.player.applyRecovery(new Recovery(this.recoveryBonus) );

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
        this.toNext();


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