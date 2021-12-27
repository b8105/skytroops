package com.example.game.weapon;

import android.graphics.PointF;

import com.example.game.actor.bullet.BulletType;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.game.creational.ActorFactory;
import com.example.game.parameter.ShotInterval;

//! Planeに持たれます
//! このゲームでの役割は弾の生成リクエストを投げるだけです
//! Actorを継承させていません
abstract public class Weapon {
    private ActorFactory actorFactory;
    private BulletType bulletType;
    private PointF position = new PointF();
    private float rotation = 0.0f;
    private int shotCount = 0;
    private float shotPower = 28.0f;
    private float shotPowerIncremental = 2.0f;
    private ShotInterval shotInterval;
    private boolean ready = true;

    public Weapon() {
        this.bulletType = BulletType.Basic;
        this.shotInterval = new ShotInterval();
    }

    public BulletType getBulletType() {
        return this.bulletType;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void resetShotInterval(){
        this.shotInterval.resetInterval();
    }
    public void resetShotInterval(float time){
        this.shotInterval.resetInterval(time);
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

    public boolean isReady() {
        return this.ready;
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

    //! サブクラスのshotで呼び出します
    protected void requestCreateBullet(PointF parentGlobalPosition, float parentGlobalRotation, String tag) {
        float sizeOffsetX = BitmapSizeStatic.bullet.x * 0.5f;

        this.actorFactory.createBulletRequest(
                parentGlobalPosition.x + this.position.x - sizeOffsetX,
                parentGlobalPosition.y+ this.position.y,
                parentGlobalRotation + this.rotation,
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

    //! 弾を発射します
    //! サンドボックスにはしてません
    abstract public void shot(PointF parentGlobalPosition, float parentGlobalRotation, String tag);

    public void update(float deltaTime){
        if(this.shotInterval.update(deltaTime)){
            this.ready = true;
        } // if
    }
}