package com.example.game.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.example.game.observation.BossEnemyDeadSubject;
import com.example.game.actor.enemy_plane.BossEnemyPlane;
import com.example.game.actor.enemy_plane.EnemyPlane;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.actor.PlayerPlane;
import com.example.game.render.hp_renderer.BossEnemyPlaneHpBarRenderer;
import com.example.game.render.hp_renderer.EnemyPlaneHpBarRenderer;
import com.example.game.R;
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
import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.Transform2D;
import com.example.game.effect.EffectSystem;
import com.example.game.effect.EffectType;
import com.example.game.render.RenderLayer;
import com.example.game.render.hp_renderer.PlayerPlaneHpBarRenderer;
import com.example.game.render.render_component.PlaneHpBarRenderComponent;
import com.example.game.render.render_component.PlaneSpriteRenderComponent;
import com.example.game.render.render_component.SpriteRenderComponent;
import com.example.game.scene.GamePlayScene;
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

    private GamePlayScene gamePlayScene;
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

    public ActorFactory(
            GamePlayScene gamePlayScene,
            Resources resources,
                        ActorContainer actorContainer,
                        ComponentExecutor componentExecutor,
                        GameSystem gameSystem,
                        UIChangeBullePanel uiChangeBullePanel,
                        EffectSystem effectSystem) {
        this.gamePlayScene = gamePlayScene;
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

        actor.resetHp(10);

        PlaneCollisionComponent collisionable = new PlaneCollisionComponent(collisionLayer);
        PlaneSpriteRenderComponent spriteRenderComponent = this.componentFactory.createPlaneSpriteRenderComponent(
                this.playerBitmapSize, R.drawable.plane1up);
        PlaneHpBarRenderComponent hpBarRenderComponent = new PlaneHpBarRenderComponent(renderLayer);
        hpBarRenderComponent.setHpBarRenderer(new PlayerPlaneHpBarRenderer(hpBarRenderComponent, resources));
        collisionable.setCollisionRectSizeOffset(-playerCollisionRectSizeDecrease,-playerCollisionRectSizeDecrease);
        PlaneActionComponent actionComponent =
                componentFactory.createPlayerPlaneActionComponent(actionLayer, weapon, this);

        // add
        actor.addComponent(actionComponent);
        actor.addComponent(collisionable);
        actor.addComponent(spriteRenderComponent);
        actor.addComponent(hpBarRenderComponent);



        weapon.setPosition(new PointF(
                spriteRenderComponent.getBitmapSize().x * 0.5f,0.0f
        ));

        float bitmapHalfSizeX = spriteRenderComponent.getBitmapSize().x * 0.5f;
        float bitmapHalfSizeY = spriteRenderComponent.getBitmapSize().y * 0.5f;
        actor.setPosition(positionX - bitmapHalfSizeX, positionY - bitmapHalfSizeY);
        actor.initialize();
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
        bitmap = Bitmap.createScaledBitmap(bitmap, BitmapSizeStatic.bullet.x, BitmapSizeStatic.bullet.y, false);
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
    public EnemyPlane createEnemy(float positionX, float positionY, String tag, EnemyPlaneType enemyPlaneType) {
        EnemyPlane actor = null;

        switch (enemyPlaneType){
            case Boss:
            case Boss2:
            case Boss3:
                BossEnemyPlane temp = new BossEnemyPlane(actorContainer, tag);
                BossEnemyDeadSubject bossEnemyDeadSubject = new BossEnemyDeadSubject();
                bossEnemyDeadSubject.addObserver(gamePlayScene);
                temp.setBossEnemyDeadSubject(bossEnemyDeadSubject);
                actor = temp;
                actor.resetHp(50);
                break;
            default:
                actor = new EnemyPlane(actorContainer, tag);
                actor.resetHp(1);
        } // switch
        Weapon weapon = new BasicGun();
        actor.setWeapon(weapon);

        actor.setActorType(ActorType.Plane);
        actor.setGameScorer(this.gameSystem.getGameScorer());
        actor.setScoreEffectEmitter(this.effectSystem.getSharedEmitter(EffectType.Score));
        actor.setExplosionEffectEmitter(this.effectSystem.getSharedEmitter(EffectType.Explosion));

        PlaneActionComponent actionComponent = null;
        switch (enemyPlaneType){
            case Basic:
                actionComponent = this.componentFactory.createBasicPlaneActionComponent(
                        actionLayer, this,this.actorContainer,weapon );
                break;
            case Weak:
                actionComponent = this.componentFactory.createWeakPlaneActionComponent(
                        actionLayer, this,this.actorContainer,weapon );
                break;
            case Strong:
                actionComponent = this.componentFactory.createStrongPlaneActionComponent(
                        actionLayer, this,this.actorContainer,weapon );
                break;
            case Commander:
                actionComponent = this.componentFactory.createBasicPlaneActionComponent(
                        actionLayer, this,this.actorContainer,weapon );
                break;
            case Follow:
                actionComponent = this.componentFactory.createFollowPlaneActionComponent(
                        actionLayer, this,this.actorContainer,weapon );
                break;
            case Boss:
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this,this.actorContainer,weapon );
                break;
            case Boss2:
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this,this.actorContainer,weapon );
                break;
            case Boss3:
                actionComponent = this.componentFactory.createBossPlaneActionComponent(
                        actionLayer, this,this.actorContainer,weapon );
                break;
        } // switch
        PlaneHpBarRenderComponent hpBarRenderComponent = new PlaneHpBarRenderComponent(renderLayer);
        SpriteRenderComponent spriteRenderComponent = null;
        switch (enemyPlaneType){
            case Basic:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        enemyBitmapSize, R.drawable.enemy01);
                hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, resources));
                break;
            case Weak:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        enemyBitmapSize, R.drawable.enemy02);
                hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, resources));
                break;
            case Strong:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        enemyBitmapSize, R.drawable.enemy05);
                hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, resources));
                break;
            case Commander:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        enemyBitmapSize, R.drawable.enemy04);
                hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, resources));
                break;
            case Follow:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        enemyBitmapSize, R.drawable.enemy03);
                hpBarRenderComponent.setHpBarRenderer(new EnemyPlaneHpBarRenderer(hpBarRenderComponent, resources));
                break;
            case Boss:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        BitmapSizeStatic.boss.x, R.drawable.enemy08);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, resources));
                break;
            case Boss2:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        BitmapSizeStatic.boss.x, R.drawable.enemy13);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, resources));
                break;
            case Boss3:
                spriteRenderComponent = this.componentFactory.createSpriteRenderComponent(
                        BitmapSizeStatic.boss.x, R.drawable.enemy14);
                hpBarRenderComponent.setHpBarRenderer(new BossEnemyPlaneHpBarRenderer(hpBarRenderComponent, resources));
                break;
        } // switch





        EnemyCollisionComponent collisionable = new EnemyCollisionComponent(collisionLayer);


        collisionable.setCollisionRectSizeOffset(-enemyCollisionRectSizeDecrease);


        actor.addComponent(actionComponent);
        actor.addComponent(collisionable);
        actor.addComponent(spriteRenderComponent);
        actor.addComponent(hpBarRenderComponent);

        weapon.setPosition(new PointF(
                spriteRenderComponent.getBitmapSize().x * 0.5f,0.0f
        ));

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
}