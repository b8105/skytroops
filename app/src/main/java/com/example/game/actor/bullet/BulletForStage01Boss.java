package com.example.game.actor.bullet;

import com.example.game.game.ActorContainer;
import com.example.game.game.BulletCreateConfig;

public class BulletForStage01Boss extends Bullet {
    public BulletForStage01Boss(ActorContainer actorContainer, String tag, BulletCreateConfig config) {
        super(actorContainer, tag, config);
        super.changeMass(4);
    }

    public BulletType getBulletType(){
        return BulletType.Stage01Boss;
    }
}