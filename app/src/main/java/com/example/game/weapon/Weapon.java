package com.example.game.weapon;

import android.graphics.PointF;

import com.example.game.actor.bullet.BulletType;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.game.creational.ActorFactory;

abstract public class Weapon {
    private ActorFactory actorFactory;
    private BulletType bulletType;
    private PointF position = new PointF(); // local translation
    private float rotation = 0.0f; // local rotation
    private int shotCount = 0;
    private float shotPower = 28.0f;
    private float shotPowerIncremental = 2.0f;


    public Weapon() {
        this.bulletType = BulletType.Basic;
    }

    public BulletType getBulletType() {
        return this.bulletType;
    }

    public void setShotCount(int shotCount) {
        this.shotCount = shotCount;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public int getShotCount() {
        return this.shotCount;
    }

    public void incrementShotPower(){
        this.shotPower += this.shotPowerIncremental;
    }

    private float clacShotSpeed(BulletType bulletType){
        if(bulletType == BulletType.Homing){
            return this.shotPower * 0.55f;
        } // if
        return this.shotPower;
    }

    protected void requestCreateBullet(PointF parentGlobalPosition, float parentGlobalRotation, String tag) {
        float sizeOffsetX = BitmapSizeStatic.bullet.x * 0.5f;

        this.actorFactory.createBulletRequest(
                parentGlobalPosition.x + position.x - sizeOffsetX,
                parentGlobalPosition.y+ position.y,
                parentGlobalRotation + rotation,
                this.bulletType,
                tag,
                this.clacShotSpeed(this.bulletType));
    }

    public void setActorFactory(ActorFactory actorFactory) {
        this.actorFactory = actorFactory;
    }

    public void setBulletType(BulletType bulletType) {
        this.bulletType = bulletType;
    }

    abstract public void shot(PointF parentGlobalPosition, float parentGlobalRotation, String tag);
}