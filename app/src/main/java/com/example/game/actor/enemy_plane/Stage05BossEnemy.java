package com.example.game.actor.enemy_plane;

import com.example.game.actor.bullet.BulletType;
import com.example.game.game.ActorContainer;

public class Stage05BossEnemy extends BossEnemyPlane {
    public Stage05BossEnemy(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
    }

    @Override
    public EnemyPlaneType getEnemyPlaneType() {
        return EnemyPlaneType.Stage04Boss;
    }

    public void initialize() {
        super.initialize();
        super.getWeapon().setBulletType(BulletType.Stage01Boss);
        super.getSubWeapon().setBulletType(BulletType.Stage02Boss);
        super.getSubWeapon2().setBulletType(BulletType.Stage02Boss);
    }
}