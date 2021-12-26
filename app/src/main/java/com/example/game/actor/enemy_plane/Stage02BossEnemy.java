package com.example.game.actor.enemy_plane;

import com.example.game.actor.bullet.BulletType;
import com.example.game.game.ActorContainer;
import com.example.game.weapon.AnyWayGun;

public class Stage02BossEnemy  extends BossEnemyPlane{
    public Stage02BossEnemy(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
        AnyWayGun anyWayGun = new AnyWayGun();
        super.addWeapon("AnyWayGun",anyWayGun);
        anyWayGun.setWayAngle(15);
        anyWayGun.setBulletType(BulletType.Stage02Boss);
    }

    @Override
    public EnemyPlaneType getEnemyPlaneType() {
        return EnemyPlaneType.Stage02Boss;
    }

    public void initialize() {
        super.initialize();
    }
}
