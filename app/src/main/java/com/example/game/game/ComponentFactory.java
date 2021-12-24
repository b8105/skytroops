package com.example.game.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.game.action.ActionLayer;
import com.example.game.action.action_component.enemy.AutoTargetingShotComponent;
import com.example.game.action.action_component.common.MoveComponent;
import com.example.game.action.action_component.PlaneActionComponent;
import com.example.game.action.action_component.common.ShotComponent;
import com.example.game.action.action_component.enemy.FollowMoveComponent;
import com.example.game.action.action_component.enemy.WaveMoveComponent;
import com.example.game.action.input.enemy.AIBossMoveInput;
import com.example.game.action.input.enemy.AIBossTweenMoveInput;
import com.example.game.action.input.enemy.AIFadeoutMoveInput;
import com.example.game.action.input.enemy.AIShotInput;
import com.example.game.action.input.enemy.AIStraightMoveInput;
import com.example.game.action.input.enemy.EnemyPlaneActionInput;
import com.example.game.action.input.player.PlayerActionInput;
import com.example.game.action.input.player.PlayerMoveInput;
import com.example.game.action.input.player.PlayerShotInput;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.render.RenderLayer;
import com.example.game.render.render_component.PlaneSpriteRenderComponent;
import com.example.game.render.render_component.SpriteRenderComponent;
import com.example.game.stage.StageType;
import com.example.game.weapon.BasicGun;
import com.example.game.weapon.Weapon;

public class ComponentFactory {
    private Resources resources = null;
    private RenderLayer renderLayer = null;

    private float basicEnemyShotInterval = 0.6f;

    public ComponentFactory(
            Resources resources,
            RenderLayer renderLayer) {
        this.resources = resources;
        this.renderLayer = renderLayer;
    }

    public SpriteRenderComponent createSpriteRenderComponent(int bitmapSize, int drawableId) {
        SpriteRenderComponent spriteRenderComponent = new SpriteRenderComponent(renderLayer);

        Bitmap bitmap = BitmapFactory.decodeResource(this.resources, drawableId);
        bitmap = Bitmap.createScaledBitmap(
                bitmap,
                bitmapSize,
                bitmapSize,
                false);
        spriteRenderComponent.setBitmap(bitmap);

        return spriteRenderComponent;
    }

    public SpriteRenderComponent createSpriteRenderComponent(
            ImageResource imageResource,
            ImageResourceType type) {
        SpriteRenderComponent spriteRenderComponent = new SpriteRenderComponent(renderLayer);

        spriteRenderComponent.setBitmap(imageResource.getImageResource(type));
        return spriteRenderComponent;
    }

    public PlaneSpriteRenderComponent createPlaneSpriteRenderComponent(int bitmapSize, int drawableId) {
        PlaneSpriteRenderComponent spriteRenderComponent = new PlaneSpriteRenderComponent(renderLayer);

        Bitmap bitmap = BitmapFactory.decodeResource(this.resources, drawableId);
        bitmap = Bitmap.createScaledBitmap(
                bitmap,
                bitmapSize,
                bitmapSize,
                false);
        spriteRenderComponent.setBitmap(bitmap);

        return spriteRenderComponent;
    }


    public PlaneActionComponent createPlayerPlaneActionComponent(
            ActionLayer actionLayer,
            Weapon weapon,
            ActorFactory actorFactory) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        PlayerActionInput playerActionInput = new PlayerActionInput();
        actionComponent.setActionInput(playerActionInput);
        {
            {
                MoveComponent moveComponent = new MoveComponent(actionComponent);
                PlayerMoveInput playerMoveInput = new PlayerMoveInput(moveComponent);
                moveComponent.setActionInput(playerMoveInput);
                playerActionInput.setPlayerMoveInput(playerMoveInput);
            }
            {
                ShotComponent shotComponent = new ShotComponent(actionComponent);
                PlayerShotInput playerShotInput = new PlayerShotInput(shotComponent);
                playerActionInput.setPlayerShotInput(playerShotInput);
                shotComponent.setWeapon(weapon);
                weapon.setActorFactory(actorFactory);
            }
        }
        return actionComponent;
    }

    public PlaneActionComponent createBasicPlaneActionComponent(
            ActionLayer actionLayer,
            ActorFactory actorFactory,
            ActorContainer actorContainer, Weapon weapon, StageType stageType
    ) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);

            MoveComponent moveComponent = new MoveComponent(actionComponent);
            AIStraightMoveInput input = new AIStraightMoveInput();
            input.setMoveComponent(moveComponent);
            moveComponent.setActionInput(input);
            enemyPlaneActionInput.addActionInput(input);
        return actionComponent;
    }

    public PlaneActionComponent createWeakPlaneActionComponent(ActionLayer actionLayer, ActorFactory actorFactory, ActorContainer actorContainer
            , Weapon weapon
            , StageType stageType
    ) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);
        {
            WaveMoveComponent moveComponent = new WaveMoveComponent(actionComponent);
        }
        {
            AutoTargetingShotComponent shotComponent = new AutoTargetingShotComponent(actionComponent);
            shotComponent.setActorContainer(actorContainer);
            shotComponent.setWeapon(weapon);
            shotComponent.setShotInterval(1.5f);
            weapon.setActorFactory(actorFactory);
        }
        return actionComponent;
    }


    public PlaneActionComponent createFollowPlaneActionComponent(ActionLayer actionLayer, ActorFactory actorFactory, ActorContainer actorContainer, Weapon weapon
            , StageType stageType
    ) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);
        {
            FollowMoveComponent moveComponent = new FollowMoveComponent(actionComponent);
            moveComponent.setFollowTarget(actorContainer.getMainChara());
        }
        {
            AutoTargetingShotComponent shotComponent = new AutoTargetingShotComponent(actionComponent);
            shotComponent.setActorContainer(actorContainer);
//            Weapon weapon = new BasicGun();
            shotComponent.setWeapon(weapon);
            shotComponent.setShotInterval(this.basicEnemyShotInterval);
            weapon.setActorFactory(actorFactory);
        }
        return actionComponent;
    }


    public PlaneActionComponent createStrongPlaneActionComponent(ActionLayer actionLayer, ActorFactory actorFactory, ActorContainer actorContainer, Weapon weapon
            , StageType stageType
    ) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);


        ShotComponent shotComponent = new ShotComponent(actionComponent);
        AIShotInput aiShotInput = new AIShotInput(shotComponent);
        {
            aiShotInput.setActorContainer(actorContainer);
            aiShotInput.setShotComponent(shotComponent);
            //        Weapon weapon = new BasicGun();
            shotComponent.setWeapon(weapon);
            shotComponent.setShotInterval(this.basicEnemyShotInterval);
            weapon.setActorFactory(actorFactory);
            shotComponent.setActionInput(aiShotInput);
            enemyPlaneActionInput.addActionInput(aiShotInput);
        }

        {
            MoveComponent moveComponent = new MoveComponent(actionComponent);
            AIFadeoutMoveInput input = new AIFadeoutMoveInput();
            input.setShotInput(aiShotInput);
            input.setMoveComponent(moveComponent);
            moveComponent.setActionInput(input);
            enemyPlaneActionInput.addActionInput(input);
        }
        return actionComponent;
    }


    public PlaneActionComponent createBossPlaneActionComponent(
            ActionLayer actionLayer,
            ActorFactory actorFactory,
            ActorContainer actorContainer, Weapon weapon, StageType stageType
    ) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);
        {
            MoveComponent moveComponent = new MoveComponent(actionComponent);
            ShotComponent shotComponent = new ShotComponent(actionComponent);
            AIShotInput aiShotInput = new AIShotInput(shotComponent);

            if(stageType == StageType.Type01){
                {
                    AIBossMoveInput input = new AIBossMoveInput();
                    input.setShotInput(aiShotInput);
                    input.setMoveComponent(moveComponent);
                    moveComponent.setActionInput(input);
                    enemyPlaneActionInput.addActionInput(input);
                }
                {
                    aiShotInput.setActorContainer(actorContainer);
                    aiShotInput.setShotComponent(shotComponent);
                    aiShotInput.inactivate();

                    shotComponent.setWeapon(weapon);
                    shotComponent.setShotInterval(this.basicEnemyShotInterval);
                    weapon.setActorFactory(actorFactory);
                    shotComponent.setActionInput(aiShotInput);
                    enemyPlaneActionInput.addActionInput(aiShotInput);
                }
            } // if
            else if(stageType == StageType.Type02){
                {
                    AIBossTweenMoveInput input = new AIBossTweenMoveInput();
                    input.setMoveComponent(moveComponent);
                    input.setShotInput(aiShotInput);
                    moveComponent.setActionInput(input);
                    enemyPlaneActionInput.addActionInput(input);
                }
                {
                    aiShotInput.setActorContainer(actorContainer);
                    aiShotInput.setShotComponent(shotComponent);
                    aiShotInput.inactivate();

                    aiShotInput.setTargeting(false);
                    shotComponent.setWeapon(weapon);
                    weapon.setShotCount(1);
                    shotComponent.setShotInterval(0.6f);
                    weapon.setActorFactory(actorFactory);
                    shotComponent.setActionInput(aiShotInput);
                    enemyPlaneActionInput.addActionInput(aiShotInput);
                }

            } // else if
        }
        return actionComponent;
    }
}
