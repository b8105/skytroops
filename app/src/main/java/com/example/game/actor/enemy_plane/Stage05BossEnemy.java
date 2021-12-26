package com.example.game.actor.enemy_plane;

import com.example.game.actor.bullet.BulletType;
import com.example.game.game.ActorContainer;
import com.example.game.weapon.AnyWayGun;
import com.example.game.weapon.BasicGun;

public class Stage05BossEnemy extends BossEnemyPlane {
    public Stage05BossEnemy(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
        super.addWeapon("SubBasicGun", new BasicGun());
        super.addWeapon("SubAnyWayGun", new AnyWayGun());

        super.getWeapon("BasicGun").setBulletType(BulletType.Stage01Boss);
        super.getWeapon("SubBasicGun").setBulletType(BulletType.Stage02Boss);
        super.getWeapon("SubAnyWayGun").setBulletType(BulletType.Stage02Boss);

    }

    @Override
    public EnemyPlaneType getEnemyPlaneType() {
        return EnemyPlaneType.Stage05Boss;
    }

    public void initialize() {
        super.initialize();
    }
}