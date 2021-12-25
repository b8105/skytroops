package com.example.game.actor.enemy_plane;

import com.example.game.actor.bullet.BulletType;
import com.example.game.game.ActorContainer;
import com.example.game.weapon.AnyWayGun;
import com.example.game.weapon.BasicGun;

public class Stage03BossEnemy  extends BossEnemyPlane{
    public Stage03BossEnemy(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);


        AnyWayGun anyWayGun = new AnyWayGun();
        super.addWeapon("AnyWayGun",anyWayGun);
        anyWayGun .setWayAngle(15);
        super.getWeapon("BasicGun").setBulletType(BulletType.Stage01Boss);
        anyWayGun.setBulletType(BulletType.Stage02Boss);

    }

    @Override
    public EnemyPlaneType getEnemyPlaneType() {
        return EnemyPlaneType.Stage03Boss;
    }

    public void initialize() {
        super.initialize();
    }
}