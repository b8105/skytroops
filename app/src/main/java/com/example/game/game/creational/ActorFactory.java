package com.example.game.game.creational;

import android.graphics.PointF;

import com.example.game.action.action_component.bullet.BulletFrontMoveComponent;
import com.example.game.actor.bullet.BulletForStage01Boss;
import com.example.game.actor.bullet.BulletForStage02Boss;
import com.example.game.actor.bullet.HomingBullet;
import com.example.game.actor.enemy_plane.CommanderEnemyPlane;
import com.example.game.actor.enemy_plane.FollowEnemyPlane;
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
import com.example.game.observation.boss_enemy_dead.BossEnemyDeadSubject;
import com.example.game.actor.enemy_plane.BossEnemyPlane;
import com.example.game.actor.enemy_plane.EnemyPlane;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.actor.player.PlayerPlane;
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
import com.example.game.ui.UIChangeBulletPanel;
import com.example.game.weapon.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
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
    private UIChangeBulletPanel uiChangeBullePanel = null;
    private EffectSystem effectSystem = null;

    private int playerCollisionRectSizeDecrease = 100;
    private int enemyCollisionRectSizeDecrease = 60;
    private int bulletCollisionRectSizeDecrease = 80;

    private HashMap<EnemyPlaneType, Integer> enemyPlaneHp = new HashMap<>();

    public ActorFactory(
            GamePlayScene gamePlayScene,
            ImageResource imageResource,
            ActorContainer actorContainer,
            ComponentExecutor componentExecutor,
            GameSystem gameSystem,
            UIChangeBulletPanel uiChangeBullePanel,
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

        enemyPlaneHp.put(EnemyPlaneType.Stage01Boss, 20);
        enemyPlaneHp.put(EnemyPlaneType.Stage02Boss, 40);
        enemyPlaneHp.put(EnemyPlaneType.Stage03Boss, 60);
        enemyPlaneHp.put(EnemyPlaneType.Stage04Boss, 80);
        enemyPlaneHp.put(EnemyPlaneType.Stage05Boss, 120);
        enemyPlaneHp.put(EnemyPlaneType.Strong, 5);
        enemyPlaneHp.put(EnemyPlaneType.Commander, 9);
        enemyPlaneHp.put(EnemyPlaneType.Follow, 7);
    }

    public PlayerPlane createPlayerPlane(float positionX, float positionY, String tag) {
        PlayerPlane actor = new PlayerPlane(actorContainer, tag);
        actor.setExplosionEffectEmitter(this.effectSystem.getSharedEmitter(EffectType.Explosion));
        this.uiChangeBullePanel.setEvent(actor.getWeapon("MainWeapon"));
        actor.resetHp(10);

        PlaneCollisionComponent collisionable = new PlaneCollisionComponent(collisionLayer);
        PlaneSpriteRenderComponent spriteRenderComponent = this.componentFactory.createPlaneSpriteRenderComponent(
                this.imageResource,
                ImageResourceType.PlayerPlane
        );
        PlaneHpBarRenderComponent hpBarRenderComponent = new PlaneHpBarRenderComponent(renderLayer);
        hpBarRenderComponent.setHpBarRenderer(new PlayerPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
        collisionable.setCollisionRectSizeOffset(-playerCollisionRectSizeDecrease, -playerCollisionRectSizeDecrease);
        PlaneActionComponent actionComponent =
                componentFactory.createPlayerPlaneActionComponent(actionLayer, actor.getWeapon("MainWeapon"), this);

        // add
        actor.addComponent(actionComponent);
        actor.addComponent(collisionable);
        actor.addComponent(spriteRenderComponent);
        actor.addComponent(hpBarRenderComponent);


        actor.getWeapon("MainWeapon").setPosition(new PointF(
                spriteRenderComponent.getBitmapSize().x * 0.5f, 0.0f
        ));

        float bitmapHalfSizeX = spriteRenderComponent.getBitmapSize().x * 0.5f;
        float bitmapHalfSizeY = spriteRenderComponent.getBitmapSize().y * 0.5f;
        //actor.setPosition(positionX - bitmapHalfSizeX, positionY - bitmapHalfSizeY);
        actor.initialize(
                new PointF(positionX - bitmapHalfSizeX, positionY - bitmapHalfSizeY), 0.0f
        );
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

    public Bullet createStage04BossBullet(float positionX, float positionY, float rotation, String tag, BulletCreateConfig bulletCreateConfig) {
        HomingBullet actor = new HomingBullet(actorContainer, tag, bulletCreateConfig);
        BulletFrontMoveComponent moveComponent = new BulletFrontMoveComponent(actionLayer);
        SpriteRenderComponent spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                this.imageResource, ImageResourceType.Stage04BossBullet
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

        if(enemyPlaneType == EnemyPlaneType.Basic){
            return stageType.ordinal() + 1;
        } // if
        if(enemyPlaneType == EnemyPlaneType.Weak){
            return stageType.ordinal() + 2;
        } // else if
        return this.enemyPlaneHp.get(enemyPlaneType).intValue();
    }

    public EnemyPlane createEnemy(
            float positionX, float positionY,
            String tag, EnemyPlaneType enemyPlaneType,
            StageType stageType,
            EnemyCreateConfig config) {
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
            } // case

            case Commander:
                actor = new CommanderEnemyPlane(actorContainer, tag,config);
                break;
            case Follow:
                actor = new FollowEnemyPlane(actorContainer, tag,config);
                break;
            default:
                actor = new EnemyPlane(actorContainer, tag);
        } // switch

        {
            int clacedHp = this.clacEnemyHp(enemyPlaneType, stageType);
            actor.resetHp(clacedHp);
        }
        actor.setActorType(ActorType.Plane);
        actor.setGameScorer(this.gameSystem.getGameScorer());
        actor.setScoreEffectEmitter(this.effectSystem.getSharedEmitter(EffectType.Score));
        actor.setExplosionEffectEmitter(this.effectSystem.getSharedEmitter(EffectType.Explosion));

        PlaneActionComponent actionComponent = null;
        switch (enemyPlaneType) {
            case Basic:
                actionComponent = this.componentFactory.createBasicPlaneActionComponent(
                        actionLayer, this, this.actorContainer, actor.getWeapon("BasicGun"), stageType);
                break;
            case Weak:
                actionComponent = this.componentFactory.createWeakPlaneActionComponent(
                        actionLayer, this, this.actorContainer, actor.getWeapon("BasicGun"), stageType);
                break;
            case Strong:
                actionComponent = this.componentFactory.createStrongPlaneActionComponent(
                        actionLayer, this, this.actorContainer, actor.getWeapon("BasicGun"), stageType);
                break;
            case Commander:
                actionComponent = this.componentFactory.createCommanderPlaneActionComponent(
                        actionLayer, this, this.actorContainer, actor.getWeapon("BasicGun"), stageType);
                break;
            case Follow:
                actionComponent = this.componentFactory.createFollowPlaneActionComponent(
                        actionLayer, this, this.actorContainer, actor.getWeapon("BasicGun"), stageType);
                break;
            case Stage01Boss:
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this, this.actorContainer, actor.getWeapon("BasicGun"), null, null, stageType);
                break;
            case Stage02Boss:
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this, this.actorContainer, actor.getWeapon("AnyWayGun"), null, null, stageType);
                break;
            case Stage03Boss:
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this, this.actorContainer, actor.getWeapon("BasicGun"), actor.getWeapon("AnyWayGun"), null, stageType);
                break;
            case Stage04Boss:
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this, this.actorContainer, actor.getWeapon("BasicGun"), actor.getWeapon("AnyWayGun"), null, stageType);
                break;
            case Stage05Boss:
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this, this.actorContainer,
                        actor.getWeapon("BasicGun"), actor.getWeapon("SubBasicGun"), actor.getWeapon("SubAnyWayGun"), stageType);
                break;

        } // switch
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
                hpBarRenderComponent.inactivate();
                break;
            case Stage02Boss:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.Stage02BossEnemyPlane);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                hpBarRenderComponent.inactivate();
                break;
            case Stage03Boss:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.Stage03BossEnemyPlane);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                hpBarRenderComponent.inactivate();
                break;
            case Stage04Boss:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.Stage04BossEnemyPlane);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                hpBarRenderComponent.inactivate();
                break;
            case Stage05Boss:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        this.imageResource, ImageResourceType.Stage05BossEnemyPlane);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, this.imageResource));
                hpBarRenderComponent.inactivate();
                break;
        } // switch


        EnemyCollisionComponent collisionable = new EnemyCollisionComponent(collisionLayer);
        collisionable.setCollisionRectSizeOffset(-enemyCollisionRectSizeDecrease);


        actor.addComponent(actionComponent);
        actor.addComponent(collisionable);
        actor.addComponent(spriteRenderComponent);
        actor.addComponent(hpBarRenderComponent);


        for (HashMap.Entry<String, Weapon> pair : actor.getWeaponHashMap().entrySet()) {
            pair.getValue().setPosition(new PointF(
                    spriteRenderComponent.getBitmapSize().x * 0.5f, 0.0f
            ));
        } // if

        actor.initialize(new PointF(positionX, positionY), 0.0f);
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
                case Stage04Boss:
                    this.createStage04BossBullet(
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