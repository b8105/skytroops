package com.example.game.actor.bullet;

import android.graphics.PointF;

import com.example.game.actor.Actor;
import com.example.game.actor.ActorTagString;
import com.example.game.actor.ActorType;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.game.ActorContainer;
import com.example.game.game.creational.BulletCreateConfig;

// mass(重さは)衝突ダメージの時に参照する
public class Bullet extends Actor {
    private float appliedShotSpeed = 0.0f;
    private PointF appliedShotDirection = new PointF();
    private int mass = 1;

    protected void changeMass(int mass){
        this.mass = mass;
    }

    public Bullet(ActorContainer actorContainer, String tag, BulletCreateConfig config) {
        super(actorContainer, tag);
        actorContainer.addBullet(this);

        if(super.getTag().equals(ActorTagString.player)){
            actorContainer.addPlayerBullet(this);
        } // if
        else if(super.getTag().equals(ActorTagString.enemy)){
            actorContainer.addEnemyBullet(this);
        } // else if

        this.appliedShotSpeed = config.shotSpeed;
        this.appliedShotDirection.x = config.shotDirection.x;
        this.appliedShotDirection.y = config.shotDirection.y;
    }

    public float getAppliedShotSpeed() {
        return this.appliedShotSpeed;
    }
    public PointF getAppliedShotDirection() {
        return this.appliedShotDirection;
    }

    public ActorType getActorType() {
        return ActorType.Bullet;
    }

    public BulletType getBulletType(){
        return BulletType.Basic;
    }

    public int getMass() {
        return this.mass;
    }

    @Override
    public PointF getCenterPosition() {
        PointF position = super.getPosition();

        position.x += BitmapSizeStatic.bullet.x * 0.5f;
        position.y += BitmapSizeStatic.bullet.y * 0.5f;
        return position;
    }

    public void release(ActorContainer actorContainer) {
        super.release(actorContainer);
        actorContainer.removeBullet(this);
        if(super.getTag().equals(ActorTagString.player)){
            actorContainer.removePlayerBullet(this);
        } // if
        else if(super.getTag().equals(ActorTagString.enemy)){
            actorContainer.removeEnemyBullet(this);
        } // else if
    }
}