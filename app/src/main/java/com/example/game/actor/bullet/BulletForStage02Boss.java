package com.example.game.actor.bullet;

import com.example.game.game.ActorContainer;
import com.example.game.game.BulletCreateConfig;

public class BulletForStage02Boss extends Bullet{
    public BulletForStage02Boss(ActorContainer actorContainer, String tag, BulletCreateConfig config) {
        super(actorContainer, tag, config);
        super.changeMass(3);
    }

    public BulletType getBulletType(){
        return BulletType.Stage02Boss;
    }

}