package com.example.game.actor.bullet;

import com.example.game.game.ActorContainer;
import com.example.game.game.creational.BulletCreateConfig;

public class HomingBullet extends Bullet{
    public HomingBullet(ActorContainer actorContainer, String tag, BulletCreateConfig config) {
        super(actorContainer, tag, config);
        super.changeMass(2);
    }

    public BulletType getBulletType(){
        return BulletType.Homing;
    }

}
