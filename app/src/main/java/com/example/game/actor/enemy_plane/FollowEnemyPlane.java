package com.example.game.actor.enemy_plane;

import com.example.game.game.ActorContainer;

public class FollowEnemyPlane extends EnemyPlane {
    private CommanderEnemyPlane commanderEnemyPlane;

    public FollowEnemyPlane(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
    }

    public void setCommanderEnemyPlane(CommanderEnemyPlane commanderEnemyPlane) {
        this.commanderEnemyPlane = commanderEnemyPlane;
    }

    public CommanderEnemyPlane getCommanderEnemyPlane() {
        return this.commanderEnemyPlane;
    }
}