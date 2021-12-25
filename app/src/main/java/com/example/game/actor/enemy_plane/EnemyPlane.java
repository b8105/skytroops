package com.example.game.actor.enemy_plane;

import android.graphics.PointF;

import com.example.game.actor.PlaneType;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.parameter.damage.Damage;
import com.example.game.actor.ActorTagString;
import com.example.game.actor.Plane;
import com.example.game.effect.EffectEmitter;
import com.example.game.effect.EffectInfo;
import com.example.game.effect.EffectType;
import com.example.game.game.ActorContainer;
import com.example.game.game.GameScorer;
import com.example.game.parameter.HpParameter;
import com.example.game.weapon.AnyWayGun;
import com.example.game.weapon.BasicGun;

public class EnemyPlane extends Plane {
    public EnemyPlane(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
        assert (super.getTag().equals(ActorTagString.enemy));
        actorContainer.addEnemyPlane(this);
//        super.weapon = new BasicGun();

        BasicGun basicGun = new BasicGun();
        super.addWeapon("BasicGun",basicGun);
//        basicGun.resetShotInterval(defaultShotInterval);
    }

    @Override
    public PlaneType getPlaneType() {
        return PlaneType.Enemy;
    }

    public EnemyPlaneType getEnemyPlaneType(){
        return EnemyPlaneType.Basic;
    }

    @Override
    public PointF getCenterPosition() {
        PointF position = super.getPosition();

        position.x += BitmapSizeStatic.enemy.x * 0.5f;
        position.y += BitmapSizeStatic.enemy.y * 0.5f;
        return position;
    }

    public boolean isBoss(){
        return false;
    }

    public void release(ActorContainer actorContainer) {
        super.release(actorContainer);
        assert (super.getTag().equals(ActorTagString.enemy));
        actorContainer.removeEnemyPlane(this);
    }

    @Override
    public void applyDamage(Damage damage) {
        HpParameter hpParameter = super.getHpParameter();
        GameScorer gameScorer = super.getGameScorer();
        EffectEmitter scoreEffectEmitter = super.getScoreEffectEmitter();
        EffectEmitter explosionEffectEmitter = super.getExplosionEffectEmitter();

        hpParameter.decrease(damage.value);

        if (hpParameter.isLessEqualZero()) {
            gameScorer.addScore(100);
            {
                PointF emitPos = super.getPosition();
                emitPos.y -= BitmapSizeStatic.score.y * 0.5f;
                EffectInfo info = new EffectInfo(
                        EffectType.Score,
                        emitPos,
                        0.3f,
                        new PointF(0.0f, -6.0f)
                );
                scoreEffectEmitter.emit(info);
            }
            {
                EffectInfo info = new EffectInfo(
                        EffectType.Explosion,
                        super.getPosition(),
                        1.0f
                );
                explosionEffectEmitter.emit(info);
            }
            super.end();
        } // if

    }
}
