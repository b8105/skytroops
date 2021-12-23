package com.example.game.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.game.action.ActionLayer;
import com.example.game.action.action_component.enemy.AutoTargetingShotComponent;
import com.example.game.action.action_component.enemy.FadeoutMoveComponent;
import com.example.game.action.action_component.MoveComponent;
import com.example.game.action.action_component.PlaneActionComponent;
import com.example.game.action.action_component.ShotComponent;
import com.example.game.action.action_component.enemy.FollowMoveComponent;
import com.example.game.action.action_component.enemy.WaveMoveComponent;
import com.example.game.action.input.AIStraightMoveInput;
import com.example.game.action.input.EnemyPlaneActionInput;
import com.example.game.action.input.player.PlayerActionInput;
import com.example.game.action.input.player.PlayerMoveInput;
import com.example.game.action.input.player.PlayerShotInput;
import com.example.game.render.RenderLayer;
import com.example.game.render.render_component.PlaneSpriteRenderComponent;
import com.example.game.render.render_component.SpriteRenderComponent;
import com.example.game.weapon.BasicGun;
import com.example.game.weapon.Weapon;

public class ComponentFactory {
    private Resources resources = null;
    private RenderLayer renderLayer = null;


    private float basicEnemyShotInterval = 0.6f;

    public ComponentFactory(
            Resources resources,
            RenderLayer renderLayer
    ) {
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
            MoveComponent moveComponent = new MoveComponent(actionComponent);
            ShotComponent shotComponent = new ShotComponent(actionComponent);
            PlayerMoveInput playerMoveInput = new PlayerMoveInput(moveComponent);
            PlayerShotInput playerShotInput = new PlayerShotInput(shotComponent);
            moveComponent.setActionInput(playerMoveInput);
            playerActionInput.setPlayerMoveInput(playerMoveInput);
            playerActionInput.setPlayerShotInput(playerShotInput);
            shotComponent.setWeapon(weapon);
            weapon.setActorFactory(actorFactory);
        }
        return actionComponent;
    }

    public PlaneActionComponent createBasicPlaneActionComponent(
            ActionLayer actionLayer,
            ActorFactory actorFactory,
            ActorContainer actorContainer) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);

        {
            EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
            actionComponent.setActionInput(enemyPlaneActionInput);

            MoveComponent moveComponent = new MoveComponent(actionComponent);
            AIStraightMoveInput input = new AIStraightMoveInput();
            input.setMoveComponent(moveComponent);
            moveComponent.setActionInput(input);
            enemyPlaneActionInput.addActionInput(input);

            {
                AutoTargetingShotComponent shotComponent = new AutoTargetingShotComponent(actionComponent);
                shotComponent.setActorContainer(actorContainer);
                Weapon weapon = new BasicGun();
                shotComponent.setWeapon(weapon);
                shotComponent.setShotInterval(basicEnemyShotInterval);
                weapon.setActorFactory(actorFactory);
            }
        }
        return actionComponent;
    }

    public PlaneActionComponent createWeakPlaneActionComponent(ActionLayer actionLayer, ActorFactory actorFactory, ActorContainer actorContainer) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        {
            EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
            actionComponent.setActionInput(enemyPlaneActionInput);

            WaveMoveComponent moveComponent = new WaveMoveComponent(actionComponent);
            {
                AutoTargetingShotComponent shotComponent = new AutoTargetingShotComponent(actionComponent);
                shotComponent.setActorContainer(actorContainer);
                Weapon weapon = new BasicGun();
                shotComponent.setWeapon(weapon);
                shotComponent.setShotInterval(this.basicEnemyShotInterval);
                weapon.setActorFactory(actorFactory);
            }
        }
        return actionComponent;
    }


    public PlaneActionComponent createFollowPlaneActionComponent(ActionLayer actionLayer, ActorFactory actorFactory, ActorContainer actorContainer) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        {
            EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
            actionComponent.setActionInput(enemyPlaneActionInput);

            FollowMoveComponent moveComponent = new FollowMoveComponent(actionComponent);

            moveComponent.setFollowTarget(
                    actorContainer.getMainChara()

            );

            {
                AutoTargetingShotComponent shotComponent = new AutoTargetingShotComponent(actionComponent);
                shotComponent.setActorContainer(actorContainer);
                Weapon weapon = new BasicGun();
                shotComponent.setWeapon(weapon);
                shotComponent.setShotInterval(this.basicEnemyShotInterval);
                weapon.setActorFactory(actorFactory);
            }
        }
        return actionComponent;
    }


    public PlaneActionComponent createStrongPlaneActionComponent(ActionLayer actionLayer, ActorFactory actorFactory, ActorContainer actorContainer) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);

        FadeoutMoveComponent moveComponent = new FadeoutMoveComponent(actionComponent);
        {
            AutoTargetingShotComponent shotComponent = new AutoTargetingShotComponent(actionComponent);
            shotComponent.setActorContainer(actorContainer);
            Weapon weapon = new BasicGun();
            shotComponent.setWeapon(weapon);
            shotComponent.setShotInterval(this.basicEnemyShotInterval);
            weapon.setActorFactory(actorFactory);
        }
        return actionComponent;
    }


}
