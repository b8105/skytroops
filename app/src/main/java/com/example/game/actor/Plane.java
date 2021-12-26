package com.example.game.actor;

import android.graphics.PointF;

import com.example.game.actor.enemy_plane.CommanderEnemyPlane;
import com.example.game.component.ComponentType;
import com.example.game.parameter.damage.DamageApplicable;
import com.example.game.parameter.HpParameter;
import com.example.game.effect.EffectEmitter;
import com.example.game.game.ActorContainer;
import com.example.game.game.GameScorer;
import com.example.game.weapon.Weapon;

import java.util.HashMap;
import java.util.Map;

abstract public class Plane extends Actor
        implements DamageApplicable {

    private GameScorer gameScorer = null;
    private HpParameter hpParameter = new HpParameter(1);
    private EffectEmitter scoreEffectEmitter = null;
    private EffectEmitter explosionEffectEmitter = null;
    private HashMap<String,Weapon> weaponHashMap  = null;

    public Plane(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
        this.weaponHashMap = new HashMap<>();
    }

    public void setGameScorer(GameScorer gameScorer) {
        this.gameScorer = gameScorer;
    }

    public void setScoreEffectEmitter(EffectEmitter scoreEffectEmitter) {
        this.scoreEffectEmitter = scoreEffectEmitter;
    }

    public void addWeapon(String key, Weapon weapon){
        this.weaponHashMap.put(key, weapon);
    }

    public Weapon getWeapon(String key){
        return this.weaponHashMap.get(key);
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

    public void resetHp(int hp) {
        this.hpParameter.resetHp(hp);
    }

    public HashMap<String, Weapon> getWeaponHashMap() {
        return this.weaponHashMap;
    }

    @Override
    public void initialize() {
        super.initialize();

        PointF position = this.getCenterPosition();
        position.y = this.getPosition().y;
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        for (HashMap.Entry<String, Weapon> pair : this.weaponHashMap.entrySet()) {
            pair.getValue().update(deltaTime);
        } // if
    }

    public void release(ActorContainer actorContainer) {
        super.release(actorContainer);
    }
}