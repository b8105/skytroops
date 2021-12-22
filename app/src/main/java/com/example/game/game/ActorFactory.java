package com.example.game.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.game.actor.PlayerPlane;
import com.example.game.render.hp_renderer.EnemyPlaneHpBarRenderer;
import com.example.game.R;
import com.example.game.action.action_component.AutoTargetingShotComponent;
import com.example.game.action.action_component.WaveMoveComponent;
import com.example.game.action.action_component.bullet.BasicBulletMoveComponent;
import com.example.game.action.action_component.bullet.HomingBulletMoveComponent;
import com.example.game.action.action_component.PlaneActionComponent;
import com.example.game.action.action_component.ShotComponent;
import com.example.game.action.input.AIStraightMoveInput;
import com.example.game.action.ActionLayer;
import com.example.game.action.input.EnemyPlaneActionInput;
import com.example.game.action.input.player.PlayerActionInput;
import com.example.game.action.input.player.PlayerMoveInput;
import com.example.game.action.action_component.MoveComponent;
import com.example.game.action.input.player.PlayerShotInput;
import com.example.game.actor.Actor;
import com.example.game.actor.ActorType;
import com.example.game.actor.bullet.Bullet;
import com.example.game.actor.bullet.BulletType;
import com.example.game.actor.Plane;
import com.example.game.collision.CollisionLayer;
import com.example.game.collision.collision_component.BulletCollisionComponent;
import com.example.game.collision.collision_component.EnemyCollisionComponent;
import com.example.game.collision.collision_component.PlaneCollisionComponent;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.Transform2D;
import com.example.game.effect.EffectSystem;
import com.example.game.effect.EffectType;
import com.example.game.render.RenderLayer;
import com.example.game.render.hp_renderer.PlayerPlaneHpBarRenderer;
import com.example.game.render.render_component.PlaneHpBarRenderComponent;
import com.example.game.render.render_component.PlaneSpriteRenderComponent;
import com.example.game.render.render_component.SpriteRenderComponent;
import com.example.game.ui.UIChangeBullePanel;
import com.example.game.weapon.AnyWayGun;
import com.example.game.weapon.BasicGun;
import com.example.game.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;

public class ActorFactory {
    class BulletCreateRequest {
        public Transform2D transform;
        public BulletType type;
        public String tag;
    }

    private Resources resources = null;
    private ActorContainer actorContainer = null;
    private GameSystem gameSystem = null;
    private ActionLayer actionLayer = null;
    private CollisionLayer collisionLayer = null;
    private RenderLayer renderLayer = null;
    private List<BulletCreateRequest> bulletCreateRequest = new ArrayList<>();
    private ComponentFactory componentFactory = null;
    private UIChangeBullePanel uiChangeBullePanel = null;
    private EffectSystem effectSystem = null;

    private int playerBitmapSize = BitmapSizeStatic.player.x;
    private int enemyBitmapSize = BitmapSizeStatic.enemy.y;

    private int playerCollisionRectSizeDecrease = 60;
    private int enemyCollisionRectSizeDecrease = 40;
    private int bulletCollisionRectSizeDecrease = 60;

    public ActorFactory(Resources resources,
                        ActorContainer actorContainer,
                        ComponentExecutor componentExecutor,
                        GameSystem gameSystem,
                        UIChangeBullePanel uiChangeBullePanel,
                        EffectSystem effectSystem) {
        this.resources = resources;
        this.actorContainer = actorContainer;
        this.gameSystem = gameSystem;
        this.actionLayer = componentExecutor.getActionLayer();
        this.collisionLayer = componentExecutor.getCollisionLayer();
        this.renderLayer = componentExecutor.getRenderLayer();
        this.uiChangeBullePanel = uiChangeBullePanel;
        this.effectSystem = effectSystem;

        this.componentFactory = new ComponentFactory(resources, this.renderLayer);
    }

    public PlayerPlane createPlayerPlane(float positionX, float positionY, String tag) {
        PlayerPlane actor = new PlayerPlane(actorContainer, tag);
        Weapon weapon = new AnyWayGun();
        this.uiChangeBullePanel.setEvent(weapon);

        actor.resetHp(2);

        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        PlaneCollisionComponent collisionable = new PlaneCollisionComponent(collisionLayer);
        PlaneSpriteRenderComponent spriteRenderComponent = this.componentFactory.createPlaneSpriteRenderComponent(
                this.playerBitmapSize, R.drawable.plane1up);
        PlaneHpBarRenderComponent hpBarRenderComponent = new PlaneHpBarRenderComponent(renderLayer);
        hpBarRenderComponent.setHpBarRenderer(new PlayerPlaneHpBarRenderer(hpBarRenderComponent, resources));
        collisionable.setCollisionRectSizeOffset(-playerCollisionRectSizeDecrease,-playerCollisionRectSizeDecrease);

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
            weapon.setActorFactory(this);
        }


        // add
        actor.addComponent(actionComponent);
        actor.addComponent(collisionable);
        actor.addComponent(spriteRenderComponent);
        actor.addComponent(hpBarRenderComponent);

        actor.initialize();

        float bitmapHalfSizeX = spriteRenderComponent.getBitmapSize().x * 0.5f;
        float bitmapHalfSizeY = spriteRenderComponent.getBitmapSize().y * 0.5f;
        actor.setPosition(positionX - bitmapHalfSizeX, positionY - bitmapHalfSizeY);
        return actor;
    }

    public Bullet createBasicBullet(float positionX, float positionY, float rotation, String tag) {
        Bullet actor = new Bullet(actorContainer, tag);
        actor.setActorType(ActorType.Bullet);

        BasicBulletMoveComponent moveComponent = new BasicBulletMoveComponent(actionLayer);
        SpriteRenderComponent spriteRenderComponent = new SpriteRenderComponent(renderLayer);
        BulletCollisionComponent collisionable = new BulletCollisionComponent(collisionLayer);
        collisionable.setCollisionRectSizeOffset(-bulletCollisionRectSizeDecrease);

        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.bullet01);
        spriteRenderComponent.setBitmap(bitmap);

        // add
        actor.addComponent(collisionable);
        actor.addComponent(moveComponent);
        actor.addComponent(spriteRenderComponent);

        actor.initialize();

        actor.setPosition(
                positionX
                        + (playerBitmapSize * 0.5f)
                        - (spriteRenderComponent.getBitmapSize().x * 0.5f),
                positionY);
        actor.setRotation(rotation);
        return actor;
    }

    public Bullet createHomingBullet(float positionX, float positionY, float rotation, String tag) {
        Bullet actor = new Bullet(actorContainer, tag);
        actor.setActorType(ActorType.Bullet);

        HomingBulletMoveComponent moveComponent = new HomingBulletMoveComponent(actionLayer);
        SpriteRenderComponent spriteRenderComponent = new SpriteRenderComponent(renderLayer);
        BulletCollisionComponent collisionable = new BulletCollisionComponent(collisionLayer);
        collisionable.setCollisionRectSizeOffset(-bulletCollisionRectSizeDecrease);

        moveComponent.setActorContainer(this.actorContainer);
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.bullet02);
        spriteRenderComponent.setBitmap(bitmap);

        // add
        actor.addComponent(collisionable);
        actor.addComponent(moveComponent);
        actor.addComponent(spriteRenderComponent);

        actor.initialize();
        actor.setPosition(positionX, positionY);
        actor.setRotation(rotation);
        return actor;
    }

    public void createBulletRequest(float positionX, float positionY, float rotation, BulletType type, String tag) {
        Transform2D transform = new Transform2D();
        transform.position.x = positionX;
        transform.position.y = positionY;
        transform.rotation = rotation;

        BulletCreateRequest bulletCreateRequest = new BulletCreateRequest();
        bulletCreateRequest.transform = transform;
        bulletCreateRequest.type = type;
        bulletCreateRequest.tag = tag;
        this.bulletCreateRequest.add(
                bulletCreateRequest
        );
    }


    public Plane createBasicEnemy(float positionX, float positionY, String tag) {
        Plane actor = new Plane(actorContainer, tag);
        actor.setActorType(ActorType.Plane);
        actor.setGameScorer(this.gameSystem.getGameScorer());

        actor.setScoreEffectEmitter(this.effectSystem.getSharedEmitter(EffectType.Score));
        actor.setExplosionEffectEmitter(this.effectSystem.getSharedEmitter(EffectType.Explosion));

        actor.resetHp(3);

        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyCollisionComponent collisionable = new EnemyCollisionComponent(collisionLayer);
        SpriteRenderComponent spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(enemyBitmapSize, R.drawable.enemy01);
        PlaneHpBarRenderComponent hpBarRenderComponent = new PlaneHpBarRenderComponent(renderLayer);
        hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, resources));
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
                shotComponent.setActorContainer(this.actorContainer);
                Weapon weapon = new BasicGun();
                shotComponent.setWeapon(weapon);
                shotComponent.setShotInterval(0.6f);
                weapon.setActorFactory(this);
            }
        }

        collisionable.setCollisionRectSizeOffset(-enemyCollisionRectSizeDecrease);


        actor.addComponent(actionComponent);
        actor.addComponent(collisionable);
        actor.addComponent(spriteRenderComponent);
        actor.addComponent(hpBarRenderComponent);

        actor.initialize();
        actor.setPosition(positionX, positionY);
        return actor;
    }


    public Plane createWeakEnemy(float positionX, float positionY, String tag) {
        Plane actor = new Plane(actorContainer, tag);
        actor.setActorType(ActorType.Plane);
        actor.setGameScorer(this.gameSystem.getGameScorer());
        actor.resetHp(3);

        actor.setScoreEffectEmitter(this.effectSystem.getSharedEmitter(EffectType.Score));
        actor.setExplosionEffectEmitter(this.effectSystem.getSharedEmitter(EffectType.Explosion));


        PlaneActionComponent actionComponent = new PlaneActionComponent(actionLayer);
        EnemyCollisionComponent collisionable = new EnemyCollisionComponent(collisionLayer);
        SpriteRenderComponent spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(enemyBitmapSize, R.drawable.enemy02);
        PlaneHpBarRenderComponent hpBarRenderComponent = new PlaneHpBarRenderComponent(renderLayer);
        hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, resources));

        {
            EnemyPlaneActionInput enemyPlaneActionInput = new EnemyPlaneActionInput();
            actionComponent.setActionInput(enemyPlaneActionInput);

            WaveMoveComponent moveComponent = new WaveMoveComponent(actionComponent);
            {
                AutoTargetingShotComponent shotComponent = new AutoTargetingShotComponent(actionComponent);
                shotComponent.setActorContainer(this.actorContainer);
                Weapon weapon = new BasicGun();
                shotComponent.setWeapon(weapon);
                shotComponent.setShotInterval(0.6f);
                weapon.setActorFactory(this);
            }
        }

        collisionable.setCollisionRectSizeOffset(-enemyCollisionRectSizeDecrease);

        actor.addComponent(actionComponent);
        actor.addComponent(collisionable);
        actor.addComponent(spriteRenderComponent);
        actor.addComponent(hpBarRenderComponent);

        actor.initialize();
        actor.setPosition(positionX, positionY);
        return actor;
    }

    public void update() {
        for (BulletCreateRequest request : this.bulletCreateRequest) {
            switch (request.type) {
                case Basic:
                    this.createBasicBullet(
                            request.transform.position.x,
                            request.transform.position.y,
                            request.transform.rotation,
                            request.tag);
                    break;
                case Homing:
                    this.createHomingBullet(
                            request.transform.position.x,
                            request.transform.position.y,
                            request.transform.rotation,
                            request.tag
                    );
                    break;
            } // switch
        } // for
        this.bulletCreateRequest.clear();
    }

}