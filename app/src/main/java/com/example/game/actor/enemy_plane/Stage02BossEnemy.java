package com.example.game.actor.enemy_plane;

import com.example.game.actor.bullet.BulletType;
import com.example.game.game.ActorContainer;

public class Stage02BossEnemy  extends BossEnemyPlane{
    public Stage02BossEnemy(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
    }

    @Override
    public EnemyPlaneType getEnemyPlaneType() {
        return EnemyPlaneType.Stage02Boss;
    }

    public void initialize() {
        super.initialize();
        super.getWeapon().setBulletType(BulletType.Stage02Boss);
    }
}
