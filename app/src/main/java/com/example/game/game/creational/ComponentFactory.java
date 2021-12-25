package com.example.game.game.creational;

import android.graphics.Bitmap;

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
import com.example.game.game.ActorContainer;
import com.example.game.game.creational.ActorFactory;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.render.RenderLayer;
import com.example.game.render.render_component.PlaneSpriteRenderComponent;
import com.example.game.render.render_component.SpriteRenderComponent;
import com.example.game.stage.StageType;
import com.example.game.weapon.AnyWayGun;
import com.example.game.weapon.Weapon;

import java.util.HashMap;

public class ComponentFactory {
    private RenderLayer renderLayer = null;

    private float strongEnemyShotInterval = 0.1f;
    private float basicEnemyShotInterval = 0.6f;
    private float weakEnemyShotInterval = 1.5f;
    private float bossEnemyShotInterval = 0.6f;
    private float bossEnemy2ShotInterval = 0.6f;
    private float bossEnemy3ShotInterval = 3.0f;

    private float bossEnemy4ShotInterval = 6.0f;
    private float bossEnemy4SubShotInterval = 3.0f;

    private float bossEnemy5ShotInterval = 1.0f;
    private float bossEnemy5SubShotInterval = 2.0f;
    private float bossEnemy5SubShotInterval2 = 3.0f;

    private float bossEnemy2MoveSpeed = 8.0f;
    private float bossEnemy3MoveSpeed = 12.0f;

    HashMap<StageType, Float> basicEnemyMoveSpeedOnStage = new HashMap<>();

    public ComponentFactory(
            RenderLayer renderLayer) {
        this.renderLayer = renderLayer;

        basicEnemyMoveSpeedOnStage.put(StageType.Type01, 24.0f);
        basicEnemyMoveSpeedOnStage.put(StageType.Type02, 30.0f);
        basicEnemyMoveSpeedOnStage.put(StageType.Type03, 36.0f);
        basicEnemyMoveSpeedOnStage.put(StageType.Type04, 42.0f);
        basicEnemyMoveSpeedOnStage.put(StageType.Type05, 48.0f);
    }

    public SpriteRenderComponent createSpriteRenderComponent(
            ImageResource imageResource,
            ImageResourceType type) {
        SpriteRenderComponent spriteRenderComponent = new SpriteRenderComponent(renderLayer);

        spriteRenderComponent.setBitmap(imageResource.getImageResource(type));
        return spriteRenderComponent;
    }


    public PlaneSpriteRenderComponent createPlaneSpriteRenderComponent(ImageResource imageResource, ImageResourceType imageResourceType) {
        PlaneSpriteRenderComponent spriteRenderComponent = new PlaneSpriteRenderComponent(renderLayer);

        Bitmap bitmap = imageResource.getImageResource(imageResourceType);
        spriteRenderComponent.setBitmap(bitmap);

        return spriteRenderComponent;
    }

    public PlaneActionComponent createPlayerPlaneActionComponent(
            ActionLayer actionLayer,
            Weapon weapon, ActorFactory actorFactory) {
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
            ActionLayer actionLayer, ActorFactory actorFactory,
            ActorContainer actorContainer, Weapon weapon, StageType stageType) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);

        MoveComponent moveComponent = new MoveComponent(actionComponent);


        AIStraightMoveInput input = new AIStraightMoveInput();
        input.setSpeed(this.basicEnemyMoveSpeedOnStage.get(stageType));


        input.setMoveComponent(moveComponent);
        moveComponent.setActionInput(input);
        enemyPlaneActionInput.addActionInput(input);
        return actionComponent;
    }

    public PlaneActionComponent createWeakPlaneActionComponent(
            ActionLayer actionLayer, ActorFactory actorFactory,
            ActorContainer actorContainer, Weapon weapon, StageType stageType) {
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
//            shotComponent.setShotInterval(this.weakEnemyShotInterval);
            weapon.setActorFactory(actorFactory);
            weapon.resetShotInterval(this.weakEnemyShotInterval);
        }
        return actionComponent;
    }


    public PlaneActionComponent createFollowPlaneActionComponent(
            ActionLayer actionLayer, ActorFactory actorFactory,
            ActorContainer actorContainer, Weapon weapon, StageType stageType) {
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
            shotComponent.setWeapon(weapon);
            weapon.resetShotInterval(this.basicEnemyShotInterval);
//            shotComponent.setShotInterval(this.basicEnemyShotInterval);
            weapon.setActorFactory(actorFactory);
        }
        return actionComponent;
    }


    public PlaneActionComponent createStrongPlaneActionComponent(
            ActionLayer actionLayer, ActorFactory actorFactory,
            ActorContainer actorContainer, Weapon weapon, StageType stageType) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);

        ShotComponent shotComponent = new ShotComponent(actionComponent);
        AIShotInput aiShotInput = new AIShotInput(shotComponent);
        {
            aiShotInput.setActorContainer(actorContainer);
            aiShotInput.setShotComponent(shotComponent);
            shotComponent.setInstanceFlag(true);
            shotComponent.setWeapon(weapon);
//            shotComponent.setShotInterval(this.strongEnemyShotInterval);
            weapon.resetShotInterval(this.strongEnemyShotInterval);
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


    private PlaneActionComponent createBoss1PlaneActionComponent(
            ActionLayer actionLayer,
            ActorFactory actorFactory,
            ActorContainer actorContainer, Weapon weapon) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);
        {
            MoveComponent moveComponent = new MoveComponent(actionComponent);
            ShotComponent shotComponent = new ShotComponent(actionComponent);
            AIShotInput aiShotInput = new AIShotInput(shotComponent);

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
                weapon.resetShotInterval(this.bossEnemyShotInterval);
//                shotComponent.setShotInterval(this.bossEnemyShotInterval);
                weapon.setActorFactory(actorFactory);
                shotComponent.setActionInput(aiShotInput);
                enemyPlaneActionInput.addActionInput(aiShotInput);
            }
        }
        return actionComponent;
    }


    private PlaneActionComponent createBoss2PlaneActionComponent(
            ActionLayer actionLayer,
            ActorFactory actorFactory,
            ActorContainer actorContainer, Weapon weapon) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);
        {
            MoveComponent moveComponent = new MoveComponent(actionComponent);
            ShotComponent shotComponent = new ShotComponent(actionComponent);
            AIShotInput aiShotInput = new AIShotInput(shotComponent);

            weapon.setShotCount(1);
            {
                AIBossTweenMoveInput input = new AIBossTweenMoveInput();
                input.setInputSpeed(this.bossEnemy2MoveSpeed);
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
                weapon.resetShotInterval(this.bossEnemy2ShotInterval);

//                shotComponent.setShotInterval(this.bossEnemy2ShotInterval);
                weapon.setActorFactory(actorFactory);
                shotComponent.setActionInput(aiShotInput);
                enemyPlaneActionInput.addActionInput(aiShotInput);
            }

        }
        return actionComponent;
    }


    private PlaneActionComponent createBoss3PlaneActionComponent(
            ActionLayer actionLayer,
            ActorFactory actorFactory,
            ActorContainer actorContainer,
            Weapon weapon,
            Weapon subWeapon
    ) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);
        {
            MoveComponent moveComponent = new MoveComponent(actionComponent);
            ShotComponent shotComponent = new ShotComponent(actionComponent);
            AIShotInput aiShotInput = new AIShotInput(shotComponent);
            ShotComponent subShotComponent = new ShotComponent(actionComponent);
            AIShotInput subAiShotInput = new AIShotInput(subShotComponent);

            weapon.setShotCount(1);
            {
                AIBossTweenMoveInput input = new AIBossTweenMoveInput();
                input.setInputSpeed(this.bossEnemy3MoveSpeed);
                input.setMoveComponent(moveComponent);
                input.setShotInput(aiShotInput);
                input.setSubShotInput(subAiShotInput);
                moveComponent.setActionInput(input);
                enemyPlaneActionInput.addActionInput(input);
            }
            {
                aiShotInput.setActorContainer(actorContainer);
                aiShotInput.setShotComponent(shotComponent);
                aiShotInput.inactivate();

                shotComponent.setWeapon(weapon);
                weapon.resetShotInterval(this.bossEnemy3ShotInterval);

//                shotComponent.setShotInterval(this.bossEnemy3ShotInterval);
                weapon.setActorFactory(actorFactory);
                shotComponent.setActionInput(aiShotInput);
                enemyPlaneActionInput.addActionInput(aiShotInput);
            }
            {
                subAiShotInput.setActorContainer(actorContainer);
                subAiShotInput.setShotComponent(subShotComponent);
                subAiShotInput.setTargeting(false);
                subAiShotInput.inactivate();

                ((AnyWayGun)(subWeapon)).setFrontShotFlag(false);
                ((AnyWayGun)(subWeapon)).setShotCount(2);
                subShotComponent.setWeapon(subWeapon);
                subWeapon.resetShotInterval(this.bossEnemy2ShotInterval);
                //subShotComponent.setShotInterval(this.bossEnemy2ShotInterval);
                subWeapon.setActorFactory(actorFactory);
                subShotComponent.setActionInput(subAiShotInput);
                enemyPlaneActionInput.addActionInput(subAiShotInput);
            }

        }
        return actionComponent;
    }


    private PlaneActionComponent createBoss4PlaneActionComponent(
            ActionLayer actionLayer,
            ActorFactory actorFactory,
            ActorContainer actorContainer,
            Weapon weapon,
            Weapon subWeapon
    ) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);
        {
            MoveComponent moveComponent = new MoveComponent(actionComponent);
            ShotComponent shotComponent = new ShotComponent(actionComponent);
            AIShotInput aiShotInput = new AIShotInput(shotComponent);
            ShotComponent subShotComponent = new ShotComponent(actionComponent);
            AIShotInput subAiShotInput = new AIShotInput(subShotComponent);

            weapon.setShotCount(1);
            subWeapon.setShotCount(1);
            {
                AIBossTweenMoveInput input = new AIBossTweenMoveInput();
                input.setInputSpeed(this.bossEnemy3MoveSpeed);
                input.setMoveComponent(moveComponent);
                input.setShotInput(aiShotInput);
                input.setSubShotInput(subAiShotInput);
                moveComponent.setActionInput(input);
                enemyPlaneActionInput.addActionInput(input);
            }
            {
                aiShotInput.setActorContainer(actorContainer);
                aiShotInput.setShotComponent(shotComponent);
                aiShotInput.inactivate();

                aiShotInput.setTargeting(false);
                shotComponent.setWeapon(weapon);
                weapon.resetShotInterval(this.bossEnemy2ShotInterval);

//                shotComponent.setShotInterval(this.bossEnemy2ShotInterval);
                weapon.setActorFactory(actorFactory);
                shotComponent.setActionInput(aiShotInput);
                enemyPlaneActionInput.addActionInput(aiShotInput);
            }
            {
                subAiShotInput.setActorContainer(actorContainer);
                subAiShotInput.setShotComponent(subShotComponent);
                subAiShotInput.inactivate();

                subShotComponent.setWeapon(subWeapon);
                subWeapon.resetShotInterval(this.bossEnemy3ShotInterval);

//                subShotComponent.setShotInterval(this.bossEnemy3ShotInterval);
                subWeapon.setActorFactory(actorFactory);
                subShotComponent.setActionInput(subAiShotInput);
                enemyPlaneActionInput.addActionInput(subAiShotInput);
            }

        }
        return actionComponent;
    }

    private PlaneActionComponent createBoss4PlaneActionComponent(
            ActionLayer actionLayer,
            ActorFactory actorFactory,
            ActorContainer actorContainer,
            Weapon weapon,
            Weapon subWeapon,
            Weapon subWeapon2
    ) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);
        {
            MoveComponent moveComponent = new MoveComponent(actionComponent);
            ShotComponent shotComponent = new ShotComponent(actionComponent);
            AIShotInput aiShotInput = new AIShotInput(shotComponent);
            ShotComponent subShotComponent = new ShotComponent(actionComponent);
            AIShotInput subAiShotInput = new AIShotInput(subShotComponent);

            weapon.setShotCount(1);
            subWeapon.setShotCount(1);
            {
                AIBossTweenMoveInput input = new AIBossTweenMoveInput();
                input.setInputSpeed(this.bossEnemy3MoveSpeed);
                input.setMoveComponent(moveComponent);
                input.setShotInput(aiShotInput);
                input.setSubShotInput(subAiShotInput);
                moveComponent.setActionInput(input);
                enemyPlaneActionInput.addActionInput(input);
            }
            {
                aiShotInput.setActorContainer(actorContainer);
                aiShotInput.setShotComponent(shotComponent);
                aiShotInput.inactivate();

                aiShotInput.setTargeting(false);
                shotComponent.setWeapon(weapon);
                weapon.resetShotInterval(this.bossEnemy4ShotInterval);
                //shotComponent.setShotInterval(this.bossEnemy4ShotInterval);
                weapon.setActorFactory(actorFactory);
                shotComponent.setActionInput(aiShotInput);
                enemyPlaneActionInput.addActionInput(aiShotInput);
            }
            {
                subAiShotInput.setActorContainer(actorContainer);
                subAiShotInput.setShotComponent(subShotComponent);
                subAiShotInput.inactivate();

                subShotComponent.setWeapon(subWeapon);
                subWeapon.resetShotInterval(this.bossEnemy4ShotInterval);
                //subShotComponent.setShotInterval(this.bossEnemy4ShotInterval);
                subWeapon.setActorFactory(actorFactory);
                subShotComponent.setActionInput(subAiShotInput);
                enemyPlaneActionInput.addActionInput(subAiShotInput);
            }

        }
        return actionComponent;
    }

    private PlaneActionComponent createBoss5PlaneActionComponent(
            ActionLayer actionLayer,
            ActorFactory actorFactory,
            ActorContainer actorContainer,
            Weapon weapon,
            Weapon subWeapon,
            Weapon subWeapon2
    ) {
        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
        actionComponent.setActionInput(enemyPlaneActionInput);
        {
            MoveComponent moveComponent = new MoveComponent(actionComponent);
            ShotComponent shotComponent = new ShotComponent(actionComponent);
            AIShotInput aiShotInput = new AIShotInput(shotComponent);
            ShotComponent subShotComponent = new ShotComponent(actionComponent);
            ShotComponent subShotComponent2 = new ShotComponent(actionComponent);
            AIShotInput subAiShotInput = new AIShotInput(subShotComponent);
            AIShotInput subAiShotInput2 = new AIShotInput(subShotComponent2);
            weapon.setShotCount(1);
            subWeapon.setShotCount(1);
            subWeapon2.setShotCount(2);

            {
                AIBossTweenMoveInput input = new AIBossTweenMoveInput();
                input.setInputSpeed(this.bossEnemy3MoveSpeed);
                input.setMoveComponent(moveComponent);
                input.setShotInput(aiShotInput);
                input.setSubShotInput(subAiShotInput);
                input.setSubShotInput2(subAiShotInput2);
                moveComponent.setActionInput(input);
                enemyPlaneActionInput.addActionInput(input);
            }
            {
                aiShotInput.setActorContainer(actorContainer);
                aiShotInput.setShotComponent(shotComponent);
                aiShotInput.inactivate();

                aiShotInput.setTargeting(false);
                shotComponent.setWeapon(weapon);
//                shotComponent.setShotInterval(this.bossEnemy5ShotInterval);
                weapon.resetShotInterval(this.bossEnemy5ShotInterval);

                weapon.setActorFactory(actorFactory);
                shotComponent.setActionInput(aiShotInput);
                enemyPlaneActionInput.addActionInput(aiShotInput);
            }
            {
                subAiShotInput.setActorContainer(actorContainer);
                subAiShotInput.setShotComponent(subShotComponent);
                subAiShotInput.inactivate();

                subShotComponent.setWeapon(subWeapon);
                subWeapon.resetShotInterval(this.bossEnemy5SubShotInterval);

//                subShotComponent.setShotInterval(this.bossEnemy5SubShotInterval);
                subWeapon.setActorFactory(actorFactory);
                subShotComponent.setActionInput(subAiShotInput);
                enemyPlaneActionInput.addActionInput(subAiShotInput);
            }

            {
                subAiShotInput2.setActorContainer(actorContainer);
                subAiShotInput2.setShotComponent(subShotComponent2);
                subAiShotInput2.inactivate();
                aiShotInput.setTargeting(false);

                subShotComponent2.setWeapon(subWeapon2);
                subWeapon2.resetShotInterval(this.bossEnemy5SubShotInterval2);

//                subShotComponent2.setShotInterval(this.bossEnemy5SubShotInterval2);
                subWeapon2.setActorFactory(actorFactory);
                subShotComponent2.setActionInput(subAiShotInput2);
                enemyPlaneActionInput.addActionInput(subAiShotInput2);
            }

        }
        return actionComponent;
    }


    public PlaneActionComponent createBossPlaneActionComponent(
            ActionLayer actionLayer,
            ActorFactory actorFactory,
            ActorContainer actorContainer,
            Weapon weapon,
            Weapon subWeapon,
            Weapon subWeapon2,
            StageType stageType) {
        if (stageType == StageType.Type01) {
            return this.createBoss1PlaneActionComponent(actionLayer, actorFactory, actorContainer, weapon);
        } // if
        else if (stageType == StageType.Type02) {
            return this.createBoss2PlaneActionComponent(actionLayer, actorFactory, actorContainer, weapon);
        } // else if
        else if (stageType == StageType.Type03) {
            return this.createBoss3PlaneActionComponent(actionLayer, actorFactory, actorContainer, weapon,subWeapon);
        } // else if
        else if (stageType == StageType.Type04) {
            return this.createBoss4PlaneActionComponent(actionLayer, actorFactory, actorContainer, weapon,subWeapon, subWeapon2);
        } // else if
        else if (stageType == StageType.Type05) {
            return this.createBoss5PlaneActionComponent(actionLayer, actorFactory, actorContainer, weapon,subWeapon, subWeapon2);
        } // else if
        return null;
    }
}
