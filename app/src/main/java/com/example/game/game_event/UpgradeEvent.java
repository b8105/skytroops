package com.example.game.game_event;

import android.graphics.Point;
import android.graphics.PointF;

import com.example.game.action.action_component.PlaneActionComponent;
import com.example.game.actor.player.PlayerPlane;
import com.example.game.collision.collision_component.PlaneCollisionComponent;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.component.ComponentType;
import com.example.game.effect.Effect;
import com.example.game.effect.EffectEmitter;
import com.example.game.effect.EffectInfo;
import com.example.game.effect.EffectSystem;
import com.example.game.effect.EffectType;
import com.example.game.game.ActorContainer;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.main.Game;
import com.example.game.parameter.recovery.Recovery;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.render_component.PlaneSpriteRenderComponent;
import com.example.game.scene.GamePlayScene;
import com.example.game.stage.Stage;
import com.example.game.stage.StageType;
import com.example.game.ui.UIChangeBulletPanel;
import com.example.game.utility.PointFUtilities;
import com.example.game.utility.StopWatch;

public class UpgradeEvent extends GameEvent{
    private StopWatch existTimer;
    private float time = 1.4f;
    private GamePlayScene gamePlayScene;
    private PlayerPlane player;
    private PlaneActionComponent planeActionComponent;
    private PlaneSpriteRenderComponent planeSpriteRenderComponent;
    private PlaneCollisionComponent planeCollisionComponent;

    private float toCenterSpeed = 15.0f;
    private float toNextSpeed = 38.0f;
    private int moveSequence = 0;
    private PointF centerPosiotion = new PointF();

    private int recoveryBonus = 5;

    private EffectEmitter hpUpgradeEffect;
    private EffectEmitter planeUpgradeEffect;

    public UpgradeEvent(GamePlayScene gamePlayScene,
                            ActorContainer actorContainer,
                            Stage stage,
                            EffectSystem effectSystem,
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
        StageType stageType =stage.getCurrentType();
        if(stageType == StageType.Type01){
            uiChangeBullePanel.unlockToHomingButton();
            this.planeSpriteRenderComponent.setBitmap(
                    imageResource.getImageResource(ImageResourceType.PlayerPlane2)
            );
        } // if
        else if(stageType == StageType.Type02){
            uiChangeBullePanel.unlockToThreeWayButton();
            this.planeSpriteRenderComponent.setBitmap(
                    imageResource.getImageResource(ImageResourceType.PlayerPlane3)
            );
        } // else if

        this.hpUpgradeEffect = effectSystem.getSharedEmitter(EffectType.HPUpgrade);
        this.planeUpgradeEffect = effectSystem.getSharedEmitter(EffectType.PlaneUpgrade);




        {
            PointF emitPos = player.getCenterPosition();
            emitPos.x -= BitmapSizeStatic.hpUpgrade.x * 0.5f;
            emitPos.y -= BitmapSizeStatic.hpUpgrade.y * 0.5f;
            EffectInfo info = new EffectInfo(
                    EffectType.HPUpgrade,
                    emitPos,
                    1.0f,
                    new PointF(0.0f ,-2.0f));
            hpUpgradeEffect.emit(info);

        }
        {
            PointF emitPos = player.getCenterPosition();
            emitPos.x -= BitmapSizeStatic.planeUpgrade.x * 1.5f;
            emitPos.y -= BitmapSizeStatic.planeUpgrade.y * 0.5;
            EffectInfo info = new EffectInfo(
                    EffectType.PlaneUpgrade,
                    emitPos,
                    1.0f);
            planeUpgradeEffect.emit(info);
        }
        {
            PointF emitPos = player.getCenterPosition();
            emitPos.x += BitmapSizeStatic.planeUpgrade.x;
            emitPos.y -= BitmapSizeStatic.planeUpgrade.y * 0.5;
            EffectInfo info = new EffectInfo(
                    EffectType.PlaneUpgrade,
                    emitPos,
                    1.0f);
            planeUpgradeEffect.emit(info);
        }



    }


    public void initialize() {
    }

    @Override
    public boolean update(float deltaIime) {
        if (existTimer.tick(deltaIime)) {
            this.gamePlayScene.createToNextStageEvent();
            return true;
        } // if
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
    }
}
