package com.example.game.actor.bullet;

import com.example.game.game.ActorContainer;
import com.example.game.game.creational.BulletCreateConfig;

public class Stage01BossBullet extends Bullet {
    public Stage01BossBullet(ActorContainer actorContainer, String tag, BulletCreateConfig config) {
        super(actorContainer, tag, config);
        super.changeMass(3);
    }
    public BulletType getBulletType(){
        return BulletType.Stage01Boss;
    }
}