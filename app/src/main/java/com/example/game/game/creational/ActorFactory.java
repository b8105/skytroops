package com.example.game.game.creational;

import android.graphics.PointF;

import com.example.game.actor.bullet.BulletForStage01Boss;
import com.example.game.actor.bullet.BulletForStage02Boss;
import com.example.game.actor.bullet.HomingBullet;
import com.example.game.actor.enemy_plane.Stage01BossEnemy;
import com.example.game.actor.enemy_plane.Stage02BossEnemy;
import com.example.game.actor.enemy_plane.Stage03BossEnemy;
import com.example.game.actor.enemy_plane.Stage04BossEnemy;
import com.example.game.actor.enemy_plane.Stage05BossEnemy;
import com.example.game.game.ActorContainer;
import com.example.game.game.ComponentExecutor;
import com.example.game.game.GameSystem;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.observation.BossEnemyDeadSubject;
import com.example.game.actor.enemy_plane.BossEnemyPlane;
import com.example.game.actor.enemy_plane.EnemyPlane;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.actor.PlayerPlane;
import com.example.game.render.hp_renderer.BossEnemyPlaneHpBarRenderer;
import com.example.game.render.hp_renderer.EnemyPlaneHpBarRenderer;
import com.example.game.action.action_component.bullet.BasicBulletMoveComponent;
import com.example.game.action.action_component.bullet.HomingBulletMoveComponent;
import com.example.game.action.action_component.PlaneActionComponent;
import com.example.game.action.ActionLayer;
import com.example.game.actor.ActorType;
import com.example.game.actor.bullet.Bullet;
import com.example.game.actor.bullet.BulletType;
import com.example.game.collision.CollisionLayer;
import com.example.game.collision.collision_component.BulletCollisionComponent;
import com.example.game.collision.collision_component.EnemyCollisionComponent;
import com.example.game.collision.collision_component.PlaneCollisionComponent;
import com.example.game.common.Transform2D;
import com.example.game.effect.EffectSystem;
import com.example.game.effect.EffectType;
import com.example.game.render.RenderLayer;
import com.example.game.render.hp_renderer.PlayerPlaneHpBarRenderer;
import com.example.game.render.render_component.PlaneHpBarRenderComponent;
import com.example.game.render.render_component.PlaneSpriteRenderComponent;
import com.example.game.render.render_component.SpriteRenderComponent;
import com.example.game.scene.GamePlayScene;
import com.example.game.stage.StageType;
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
        public BulletCreateConfig config;
    }

    private GamePlayScene gamePlayScene;
    private ImageResource imageResource = null;
    private ActorContainer actorContainer = null;
    private GameSystem gameSystem = null;
    private ActionLayer actionLayer = null;
    private CollisionLayer collisionLayer = null;
    private RenderLayer renderLayer = null;
    private List<BulletCreateRequest> bulletCreateRequest = new ArrayList<>();
    private ComponentFactory componentFactory = null;
    private UIChangeBullePanel uiChangeBullePanel = null;
    private EffectSystem effectSystem = null;

    private int playerCollisionRectSizeDecrease = 60;
    private int enemyCollisionRectSizeDecrease = 40;
    private int bulletCollisionRectSizeDecrease = 60;


    public ActorFactory(
            GamePlayScene gamePlayScene,
            ImageResource imageResource,
            ActorContainer actorContainer,
            ComponentExecutor componentExecutor,
            GameSystem gameSystem,
            UIChangeBullePanel uiChangeBullePanel,
            EffectSystem effectSystem) {
        this.gamePlayScene = gamePlayScene;
        this.imageResource = imageResource;
        this.actorContainer = actorContainer;
        this.gameSystem = gameSystem;
        this.actionLayer = componentExecutor.getActionLayer();
        this.collisionLayer = componentExecutor.getCollisionLayer();
        this.renderLayer = componentExecutor.getRenderLayer();
        this.uiChangeBullePanel = uiChangeBullePanel;
        this.effectSystem = effectSystem;

        this.componentFactory = new ComponentFactory(this.renderLayer);
    }

    public PlayerPlane createPlayerPlane(float positionX, float positionY, String tag) {
        PlayerPlane actor = new PlayerPlane(actorContainer, tag);
        this.uiChangeBullePanel.setEvent(actor.getWeapon());
        actor.resetHp(50);

        PlaneCollisionComponent collisionable = new PlaneCollisionComponent(collisionLayer);
        PlaneSpriteRenderComponent spriteRenderComponent = this.componentFactory.createPlaneSpriteRenderComponent(
                this.imageResource,
                ImageResourceType.PlayerPlane
        );
        PlaneHpBarRenderComponent hpBarRenderComponent = new PlaneHpBarRenderComponent(renderLayer);
        hpBarRenderComponent.setHpBarRenderer(new PlayerPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
        collisionable.setCollisionRectSizeOffset(-playerCollisionRectSizeDecrease, -playerCollisionRectSizeDecrease);
        PlaneActionComponent actionComponent =
                componentFactory.createPlayerPlaneActionComponent(actionLayer, actor.getWeapon(), this);

        // add
        actor.addComponent(actionComponent);
        actor.addComponent(collisionable);
        actor.addComponent(spriteRenderComponent);
        actor.addComponent(hpBarRenderComponent);


        actor.getWeapon().setPosition(new PointF(
                spriteRenderComponent.getBitmapSize().x * 0.5f, 0.0f
        ));

        float bitmapHalfSizeX = spriteRenderComponent.getBitmapSize().x * 0.5f;
        float bitmapHalfSizeY = spriteRenderComponent.getBitmapSize().y * 0.5f;
        actor.setPosition(positionX - bitmapHalfSizeX, positionY - bitmapHalfSizeY);
        actor.initialize();
        return actor;
    }

    public Bullet createBasicBullet(float positionX, float positionY, float rotation, String tag, BulletCreateConfig bulletCreateConfig) {
        Bullet actor = new Bullet(actorContainer, tag, bulletCreateConfig);
        actor.setActorType(ActorType.Bullet);

        BasicBulletMoveComponent moveComponent = new BasicBulletMoveComponent(actionLayer);
        BulletCollisionComponent collisionable = new BulletCollisionComponent(collisionLayer);
        collisionable.setCollisionRectSizeOffset(-bulletCollisionRectSizeDecrease);

        SpriteRenderComponent spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                this.imageResource, ImageResourceType.BasicBullet
        );

        // add
        actor.addComponent(collisionable);
        actor.addComponent(moveComponent);
        actor.addComponent(spriteRenderComponent);

        actor.initialize();

        actor.setPosition(positionX, positionY);
        actor.setRotation(rotation);
        return actor;
    }

    public BulletForStage01Boss createStage01BossBullet(float positionX, float positionY, float rotation, String tag, BulletCreateConfig bulletCreateConfig) {
        BulletForStage01Boss actor = new BulletForStage01Boss(actorContainer, tag, bulletCreateConfig);
        BasicBulletMoveComponent moveComponent = new BasicBulletMoveComponent(actionLayer);
        SpriteRenderComponent spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                this.imageResource, ImageResourceType.Stage01BossBullet
        );

        BulletCollisionComponent collisionable = new BulletCollisionComponent(collisionLayer);
        collisionable.setCollisionRectSizeOffset(-bulletCollisionRectSizeDecrease);

        // add
        actor.addComponent(collisionable);
        actor.addComponent(moveComponent);
        actor.addComponent(spriteRenderComponent);

        actor.initialize();

        actor.setPosition(positionX, positionY);
        actor.setRotation(rotation);
        return actor;
    }


    public BulletForStage02Boss createStage02BossBullet(float positionX, float positionY, float rotation, String tag, BulletCreateConfig bulletCreateConfig) {
        BulletForStage02Boss actor = new BulletForStage02Boss(actorContainer, tag, bulletCreateConfig);
        BasicBulletMoveComponent moveComponent = new BasicBulletMoveComponent(actionLayer);
        SpriteRenderComponent spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                this.imageResource, ImageResourceType.Stage02BossBullet
        );
        BulletCollisionComponent collisionable = new BulletCollisionComponent(collisionLayer);
        collisionable.setCollisionRectSizeOffset(-bulletCollisionRectSizeDecrease);

        // add
        actor.addComponent(collisionable);
        actor.addComponent(moveComponent);
        actor.addComponent(spriteRenderComponent);

        actor.initialize();

        actor.setPosition(positionX, positionY);
        actor.setRotation(rotation);
        return actor;
    }

    private BulletForStage02Boss createStage03BossBullet(float positionX, float positionY, float rotation, String tag, BulletCreateConfig bulletCreateConfig) {
        BulletForStage02Boss actor = new BulletForStage02Boss(actorContainer, tag, bulletCreateConfig);
        BasicBulletMoveComponent moveComponent = new BasicBulletMoveComponent(actionLayer);
        SpriteRenderComponent spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                this.imageResource, ImageResourceType.Stage03BossBullet
        );
        BulletCollisionComponent collisionable = new BulletCollisionComponent(collisionLayer);
        collisionable.setCollisionRectSizeOffset(-bulletCollisionRectSizeDecrease);

        // add
        actor.addComponent(collisionable);
        actor.addComponent(moveComponent);
        actor.addComponent(spriteRenderComponent);

        actor.initialize();

        actor.setPosition(positionX, positionY);
        actor.setRotation(rotation);
        return actor;
    }

    public Bullet createHomingBullet(float positionX, float positionY, float rotation, String tag, BulletCreateConfig bulletCreateConfig) {
        HomingBullet actor = new HomingBullet(actorContainer, tag, bulletCreateConfig);
        HomingBulletMoveComponent moveComponent = new HomingBulletMoveComponent(actionLayer);
        SpriteRenderComponent spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                this.imageResource, ImageResourceType.HomingBullet
        );
        BulletCollisionComponent collisionable = new BulletCollisionComponent(collisionLayer);
        collisionable.setCollisionRectSizeOffset(-bulletCollisionRectSizeDecrease);

        moveComponent.setActorContainer(this.actorContainer);
        // add
        actor.addComponent(collisionable);
        actor.addComponent(moveComponent);
        actor.addComponent(spriteRenderComponent);

        actor.initialize();
        actor.setPosition(positionX, positionY);
        actor.setRotation(rotation);
        return actor;
    }

    private int clacEnemyHp(EnemyPlaneType enemyPlaneType,
                            StageType stageType) {
        switch (enemyPlaneType) {
            case Stage01Boss:
                return 20;
//                return 1;
            case Stage02Boss:
                return 40;
//                return 1;
            case Stage03Boss:
                return 80;
//                return 10;
            case Stage04Boss:
                return 300;
//                return 10;
            case Stage05Boss:
                return 700;
//                return 10;
            case Basic:
                return stageType.ordinal() + 1;
            case Weak:
                return stageType.ordinal() + 2;
            case Strong:
                return 3;
        } // switch
        return 1;
    }

    public EnemyPlane createEnemy(
            float positionX, float positionY,
            String tag, EnemyPlaneType enemyPlaneType,
            StageType stageType) {
        EnemyPlane actor = null;

        switch (enemyPlaneType) {
            case Stage01Boss: {
                BossEnemyPlane temp = new Stage01BossEnemy(actorContainer, tag);
                BossEnemyDeadSubject bossEnemyDeadSubject = new BossEnemyDeadSubject();
                bossEnemyDeadSubject.addObserver(gamePlayScene);
                temp.setBossEnemyDeadSubject(bossEnemyDeadSubject);
                actor = temp;
                break;
            }
            case Stage02Boss: {
                BossEnemyPlane temp = new Stage02BossEnemy(actorContainer, tag);
                BossEnemyDeadSubject bossEnemyDeadSubject = new BossEnemyDeadSubject();
                bossEnemyDeadSubject.addObserver(gamePlayScene);
                temp.setBossEnemyDeadSubject(bossEnemyDeadSubject);
                actor = temp;
                break;
            }
            case Stage03Boss: {
                BossEnemyPlane temp = new Stage03BossEnemy(actorContainer, tag);
                BossEnemyDeadSubject bossEnemyDeadSubject = new BossEnemyDeadSubject();
                bossEnemyDeadSubject.addObserver(gamePlayScene);
                temp.setBossEnemyDeadSubject(bossEnemyDeadSubject);
                actor = temp;
                break;
            }
            case Stage04Boss: {
                BossEnemyPlane temp = new Stage04BossEnemy(actorContainer, tag);
                BossEnemyDeadSubject bossEnemyDeadSubject = new BossEnemyDeadSubject();
                bossEnemyDeadSubject.addObserver(gamePlayScene);
                temp.setBossEnemyDeadSubject(bossEnemyDeadSubject);
                actor = temp;
                break;
            }
            case Stage05Boss: {
                BossEnemyPlane temp = new Stage05BossEnemy(actorContainer, tag);
                BossEnemyDeadSubject bossEnemyDeadSubject = new BossEnemyDeadSubject();
                bossEnemyDeadSubject.addObserver(gamePlayScene);
                temp.setBossEnemyDeadSubject(bossEnemyDeadSubject);
                actor = temp;
                break;
            }
            default:
                actor = new EnemyPlane(actorContainer, tag);
        } // switch

        {
            int clacedHp = this.clacEnemyHp(enemyPlaneType, stageType);
            actor.resetHp(clacedHp);
        }
        Weapon subWeapon2 = null;
        Weapon subWeapon = null;
        Weapon weapon = null;
        actor.setActorType(ActorType.Plane);
        actor.setGameScorer(this.gameSystem.getGameScorer());
        actor.setScoreEffectEmitter(this.effectSystem.getSharedEmitter(EffectType.Score));
        actor.setExplosionEffectEmitter(this.effectSystem.getSharedEmitter(EffectType.Explosion));

        PlaneActionComponent actionComponent = null;
        switch (enemyPlaneType) {
            case Basic:
                weapon = new BasicGun();
                actionComponent = this.componentFactory.createBasicPlaneActionComponent(
                        actionLayer, this, this.actorContainer, weapon, stageType);
                break;
            case Weak:
                weapon = new BasicGun();
                actionComponent = this.componentFactory.createWeakPlaneActionComponent(
                        actionLayer, this, this.actorContainer, weapon, stageType);
                break;
            case Strong:
                weapon = new BasicGun();
                actionComponent = this.componentFactory.createStrongPlaneActionComponent(
                        actionLayer, this, this.actorContainer, weapon, stageType);
                break;
            case Commander:
                weapon = new BasicGun();
                actionComponent = this.componentFactory.createBasicPlaneActionComponent(
                        actionLayer, this, this.actorContainer, weapon, stageType);
                break;
            case Follow:
                weapon = new BasicGun();
                actionComponent = this.componentFactory.createFollowPlaneActionComponent(
                        actionLayer, this, this.actorContainer, weapon, stageType);
                break;
            case Stage01Boss:
                weapon = new BasicGun();
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this, this.actorContainer, weapon, null, null, stageType);
                break;
            case Stage02Boss:
                weapon = new AnyWayGun();
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this, this.actorContainer, weapon, null, null, stageType);
                break;
            case Stage03Boss:
                weapon = new BasicGun();
                AnyWayGun temp = new AnyWayGun();
                temp.setWayAngle(15);
                        subWeapon = temp;
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this, this.actorContainer, weapon,subWeapon, null, stageType);
                actor.setSubWeapon(subWeapon);
                break;
            case Stage04Boss:
                weapon = new BasicGun();
                subWeapon = new AnyWayGun();
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this, this.actorContainer, weapon,subWeapon,null, stageType);
                actor.setSubWeapon(subWeapon);
                break;
            case Stage05Boss:
                weapon = new BasicGun();
                subWeapon = new BasicGun();
                subWeapon2 = new AnyWayGun();
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this, this.actorContainer, weapon,subWeapon,subWeapon2, stageType);
                actor.setSubWeapon(subWeapon);
                actor.setSubWeapon2(subWeapon2);
                break;

        } // switch
        actor.setWeapon(weapon);
        PlaneHpBarRenderComponent hpBarRenderComponent = new PlaneHpBarRenderComponent(renderLayer);
        SpriteRenderComponent spriteRenderComponent = null;
        switch (enemyPlaneType) {
            case Basic:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.BasicEnemyPlane);
                hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                break;
            case Weak:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.WeakEnemyPlane);

                hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                break;
            case Strong:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.StrongEnemyPlane);

                hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                break;
            case Commander:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.CommanderEnemyPlane);

                hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                break;
            case Follow:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.FollowEnemyPlane);

                hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                break;
            case Stage01Boss:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.Stage01BossEnemyPlane);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                break;
            case Stage02Boss:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.Stage02BossEnemyPlane);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                break;
            case Stage03Boss:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.Stage03BossEnemyPlane);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                break;
            case Stage04Boss:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.Stage04BossEnemyPlane);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                break;
            case Stage05Boss:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.Stage05BossEnemyPlane);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                break;
        } // switch


        EnemyCollisionComponent collisionable = new EnemyCollisionComponent(collisionLayer);
        collisionable.setCollisionRectSizeOffset(-enemyCollisionRectSizeDecrease);


        actor.addComponent(actionComponent);
        actor.addComponent(collisionable);
        actor.addComponent(spriteRenderComponent);
        actor.addComponent(hpBarRenderComponent);

        weapon.setPosition(new PointF(
                spriteRenderComponent.getBitmapSize().x * 0.5f, 0.0f
        ));
        if(subWeapon != null){
            subWeapon.setPosition(new PointF(
                    spriteRenderComponent.getBitmapSize().x * 0.5f, 0.0f
            ));
        } // if
        if(subWeapon2 != null){
            subWeapon2.setPosition(new PointF(
                    spriteRenderComponent.getBitmapSize().x * 0.5f, 0.0f
            ));
        } // if

        actor.setPosition(positionX, positionY);
        actor.initialize();
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
                            request.tag,
                            request.config);
                    break;
                case Homing:
                    this.createHomingBullet(
                            request.transform.position.x,
                            request.transform.position.y,
                            request.transform.rotation,
                            request.tag,
                            request.config
                    );
                    break;
                case Stage01Boss:
                    this.createStage01BossBullet(
                            request.transform.position.x,
                            request.transform.position.y,
                            request.transform.rotation,
                            request.tag,
                            request.config
                    );
                    break;
                case Stage02Boss:
                    this.createStage02BossBullet(
                            request.transform.position.x,
                            request.transform.position.y,
                            request.transform.rotation,
                            request.tag,
                            request.config
                    );
                    break;
                case Stage03Boss:
                    this.createStage03BossBullet(
                            request.transform.position.x,
                            request.transform.position.y,
                            request.transform.rotation,
                            request.tag,
                            request.config
                    );
                    break;
            } // switch
        } // for
        this.bulletCreateRequest.clear();
    }


    public void createBulletRequest(float positionX, float positionY,
                                    float rotation, BulletType type, String tag, float force) {
        Transform2D transform = new Transform2D();
        transform.position.x = positionX;
        transform.position.y = positionY;
        transform.rotation = rotation;

        BulletCreateConfig config = new BulletCreateConfig();
        config.shotSpeed = force;

        BulletCreateRequest bulletCreateRequest = new BulletCreateRequest();
        bulletCreateRequest.transform = transform;
        bulletCreateRequest.type = type;
        bulletCreateRequest.tag = tag;
        bulletCreateRequest.config = config;
        this.bulletCreateRequest.add(
                bulletCreateRequest
        );
    }
}