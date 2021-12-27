package com.example.game.actor.bullet;

import com.example.game.game.ActorContainer;
import com.example.game.game.creational.BulletCreateConfig;

public class RapidBullet extends Bullet {
    public RapidBullet(ActorContainer actorContainer, String tag, BulletCreateConfig config) {
        super(actorContainer, tag, config);
    }

    public BulletType getBulletType(){
        return BulletType.Rapid;
    }
}