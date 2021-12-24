package com.example.game.actor.enemy_plane;

import android.graphics.PointF;

import com.example.game.component.Component;
import com.example.game.component.ComponentType;
import com.example.game.observation.BossEnemyDeadMessage;
import com.example.game.observation.BossEnemyDeadSubject;
import com.example.game.actor.ActorTagString;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.effect.EffectEmitter;
import com.example.game.effect.EffectInfo;
import com.example.game.effect.EffectType;
import com.example.game.game.ActorContainer;
import com.example.game.game.GameScorer;
import com.example.game.parameter.HpParameter;
import com.example.game.parameter.damage.Damage;
import com.example.game.parameter.invincible.PlaneInvincibleParameter;

import java.util.Random;

public class BossEnemyPlane extends EnemyPlane {
    private int explosionEffectCount = 10;
    private BossEnemyDeadSubject bossEnemyDeadSubject = null;
    private PlaneInvincibleParameter invincibleParameter = new PlaneInvincibleParameter();

    public BossEnemyPlane(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
        assert (super.getTag().equals(ActorTagString.enemy));
        actorContainer.setBossEnemy(this);
        this.invincibleParameter.setInvincibleTime(60.0f);
    }
    public EnemyPlaneType getEnemyPlaneType(){
        return EnemyPlaneType.Boss;
    }

    public void update(float deltaTime){
        if(this.invincibleParameter != null){
            this.invincibleParameter.update(deltaTime);
        } // if
    }

    public void initialize() {
        super.initialize();
        this.invincibleParameter.setPlaneSpriteRenderComponent(
                super.getComponent(ComponentType.PlaneSpriteRender));
    }

    public void release(ActorContainer actorContainer) {
        super.release(actorContainer);
        assert (super.getTag().equals(ActorTagString.enemy));
        actorContainer.setBossEnemy(null);
    }


    public void setBossEnemyDeadSubject(BossEnemyDeadSubject bossEnemyDeadSubject) {
        this.bossEnemyDeadSubject = bossEnemyDeadSubject;
    }
    public PlaneInvincibleParameter getInvincibleParameter() {
        return this.invincibleParameter;
    }

    @Override
    public void applyDamage(Damage damage) {
        HpParameter hpParameter = super.getHpParameter();
        GameScorer gameScorer = super.getGameScorer();
        EffectEmitter scoreEffectEmitter = super.getScoreEffectEmitter();
        EffectEmitter explosionEffectEmitter = super.getExplosionEffectEmitter();

        hpParameter.decrease(damage.value);

        if (hpParameter.isLessEqualZero()) {
            PointF position =  super.getPosition();
            PointF random =  new PointF();

            for(int i = 0 ; i < this.explosionEffectCount; i++){
                random.x = new Random().nextInt(BitmapSizeStatic.boss.x);
                random.y = new Random().nextInt(BitmapSizeStatic.boss.y);
                EffectInfo info = new EffectInfo(
                        EffectType.Explosion,
                        new PointF(position.x + random.x,position.y + random.y),
                        1.0f
                );
                explosionEffectEmitter.emit(info);
            } // for
            this.bossEnemyDeadSubject.notify(new BossEnemyDeadMessage());
            super.end();
        } // if
    }
}