package com.example.game.actor.enemy_plane;

import com.example.game.actor.bullet.BulletType;
import com.example.game.game.ActorContainer;
import com.example.game.weapon.AnyWayGun;

public class Stage04BossEnemy extends BossEnemyPlane {
    public Stage04BossEnemy(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);

        AnyWayGun anyWayGun = new AnyWayGun();
        super.addWeapon("AnyWayGun",anyWayGun);

        super.getWeapon("BasicGun").setBulletType(BulletType.Stage04Boss);
        super.getWeapon("AnyWayGun").setBulletType(BulletType.Stage02Boss);
    }

    @Override
    public EnemyPlaneType getEnemyPlaneType() {
        return EnemyPlaneType.Stage04Boss;
    }

    public void initialize() {
        super.initialize();
    }
}
