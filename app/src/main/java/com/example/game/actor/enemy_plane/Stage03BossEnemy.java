package com.example.game.actor.enemy_plane;

import com.example.game.actor.bullet.BulletType;
import com.example.game.game.ActorContainer;

public class Stage03BossEnemy  extends BossEnemyPlane{
    public Stage03BossEnemy(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
    }

    @Override
    public EnemyPlaneType getEnemyPlaneType() {
        return EnemyPlaneType.Stage03Boss;
    }

    public void initialize() {
        super.initialize();
        super.getWeapon().setBulletType(BulletType.Stage03Boss);
    }
}
