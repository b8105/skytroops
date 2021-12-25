package com.example.game.actor.enemy_plane;

import com.example.game.actor.bullet.BulletType;
import com.example.game.component.ComponentType;
import com.example.game.game.ActorContainer;

public class Stage01BossEnemy extends BossEnemyPlane{
    public Stage01BossEnemy(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);

        super.getWeapon("BasicGun").setBulletType(BulletType.Stage01Boss);
    }

    @Override
    public EnemyPlaneType getEnemyPlaneType() {
        return EnemyPlaneType.Stage01Boss;
    }

    public void initialize() {
        super.initialize();
    }
}
