package com.example.game.actor;

import com.example.game.parameter.HpParameter;
import com.example.game.effect.EffectEmitter;
import com.example.game.effect.EffectInfo;
import com.example.game.effect.EffectType;
import com.example.game.game.ActorContainer;
import com.example.game.game.GameScorer;

public class Plane extends Actor {
    GameScorer gameScorer = null;
    HpParameter hpParameter = new HpParameter(1);

    EffectEmitter scoreEffectEmitter = null;
    EffectEmitter explosionEffectEmitter = null;

    public Plane(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);

        if (super.getTag().equals(ActorTagString.enemy)) {
            actorContainer.addEnemyPlane(this);
        } // else if
    }

    public void setGameScorer(GameScorer gameScorer) {
        this.gameScorer = gameScorer;
    }

    public void setScoreEffectEmitter(EffectEmitter scoreEffectEmitter) {
        this.scoreEffectEmitter = scoreEffectEmitter;
    }

    public void setExplosionEffectEmitter(EffectEmitter explosionEffectEmitter) {
        this.explosionEffectEmitter = explosionEffectEmitter;
    }

    public void resetHp(int hp) {

        this.hpParameter.resetHp(hp);
    }

    public ActorType getActorType() {
        return ActorType.Plane;
    }

    public HpParameter getHp() {
        return this.hpParameter;
    }

    public void release(ActorContainer actorContainer) {
        super.release(actorContainer);
        if (super.getTag().equals(ActorTagString.player)) {
            actorContainer.setMainChara(null);
        } // if
        else if (super.getTag().equals(ActorTagString.enemy)) {
            actorContainer.removeEnemyPlane(this);
        } // else if
    }

    public void damege(int value) {
        this.hpParameter.decrease(value);

        if (this.hpParameter.isLessEqualZero()) {
            if (this.getTag() == ActorTagString.enemy) {
                this.gameScorer.addScore(100);

                {
                    EffectInfo info = new EffectInfo(
                            EffectType.Score,
                            super.getPosition(),
                            1.0f
                    );
                    this.scoreEffectEmitter.emit(info);
                }


                {
                    EffectInfo info = new EffectInfo(
                            EffectType.Explosion,
                            super.getPosition(),
                            1.0f
                    );
                    this.explosionEffectEmitter.emit(info);
                }


            } // if
            super.end();
        } // if
    }
}