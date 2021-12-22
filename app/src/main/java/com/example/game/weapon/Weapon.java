package com.example.game.weapon;

import android.graphics.PointF;

import com.example.game.actor.bullet.BulletType;
import com.example.game.game.ActorFactory;

abstract public class Weapon {
    private ActorFactory actorFactory;
    private BulletType bulletType;
    private float rotation = 0.0f;
    private int shotCount = 0;

    public Weapon() {
        this.bulletType = BulletType.Basic;
    }

    public void setShotCount(int shotCount) {
        this.shotCount = shotCount;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public int getShotCount() {
        return this.shotCount;
    }

    protected void requestCreateBullet(PointF parentGlobalPosition, float parentGlobalRotation, String tag) {
        this.actorFactory.createBulletRequest(
                parentGlobalPosition.x,
                parentGlobalPosition.y,
                parentGlobalRotation + rotation,
                this.bulletType,
                tag);
    }

    public void setActorFactory(ActorFactory actorFactory) {
        this.actorFactory = actorFactory;
    }

    public void setBulletType(BulletType bulletType) {
        this.bulletType = bulletType;
    }

    abstract public void shot(PointF parentGlobalPosition, float parentGlobalRotation, String tag);
}