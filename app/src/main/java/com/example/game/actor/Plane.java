package com.example.game.actor;

import com.example.game.actor.enemy_plane.CommanderEnemyPlane;
import com.example.game.parameter.damage.DamageApplicable;
import com.example.game.parameter.HpParameter;
import com.example.game.effect.EffectEmitter;
import com.example.game.game.ActorContainer;
import com.example.game.game.GameScorer;
import com.example.game.weapon.Weapon;

abstract public class Plane extends Actor
        implements DamageApplicable {

    private GameScorer gameScorer = null;
    private HpParameter hpParameter = new HpParameter(1);
    private EffectEmitter scoreEffectEmitter = null;
    private EffectEmitter explosionEffectEmitter = null;
    private Weapon weapon;
    private CommanderEnemyPlane commanderEnemyPlane;

    public Plane(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
    }

    public void setGameScorer(GameScorer gameScorer) {
        this.gameScorer = gameScorer;
    }

    public void setScoreEffectEmitter(EffectEmitter scoreEffectEmitter) {
        this.scoreEffectEmitter = scoreEffectEmitter;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setCommanderEnemyPlane(CommanderEnemyPlane commanderEnemyPlane) {
        this.commanderEnemyPlane = commanderEnemyPlane;
    }

    public void setExplosionEffectEmitter(EffectEmitter explosionEffectEmitter) {
        this.explosionEffectEmitter = explosionEffectEmitter;
    }

    public abstract PlaneType getPlaneType();

    public GameScorer getGameScorer() {
        return this.gameScorer;
    }

    public HpParameter getHpParameter() {
        return this.hpParameter;
    }

    public EffectEmitter getExplosionEffectEmitter() {
        return this.explosionEffectEmitter;
    }

    public EffectEmitter getScoreEffectEmitter() {
        return this.scoreEffectEmitter;
    }

    public ActorType getActorType() {
        return ActorType.Plane;
    }

    public HpParameter getHp() {
        return this.hpParameter;
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    public void resetHp(int hp) {
        this.hpParameter.resetHp(hp);
    }


    public void release(ActorContainer actorContainer) {
        super.release(actorContainer);
    }
}