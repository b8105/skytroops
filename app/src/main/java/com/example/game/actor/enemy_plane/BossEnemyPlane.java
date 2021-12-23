package com.example.game.actor.enemy_plane;

import com.example.game.actor.ActorTagString;
import com.example.game.game.ActorContainer;

public class BossEnemyPlane extends EnemyPlane {
    public BossEnemyPlane(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
        assert (super.getTag().equals(ActorTagString.enemy));
        actorContainer.setBossEnemy(this);
    }

    public void release(ActorContainer actorContainer) {
        super.release(actorContainer);
        assert (super.getTag().equals(ActorTagString.enemy));
        actorContainer.setBossEnemy(null);
    }

}