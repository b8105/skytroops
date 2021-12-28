package com.example.game.actor.bullet;

import com.example.game.game.ActorContainer;
import com.example.game.game.creational.BulletCreateConfig;

public class Stage02BossBullet extends Bullet{
    public Stage02BossBullet(ActorContainer actorContainer, String tag, BulletCreateConfig config) {
        super(actorContainer, tag, config);
        super.changeMass(3);
    }

    public BulletType getBulletType(){
        return BulletType.Stage02Boss;
    }

}