package com.example.game.actor.enemy_plane;

import com.example.game.actor.ActorTagString;
import com.example.game.game.ActorContainer;

public class CommanderEnemyPlane extends EnemyPlane {
    private int commanderId = -1;

    public CommanderEnemyPlane(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);

        actorContainer.addCommanderEnemyPlane(this);
    }

    public void setCommanderId(int commanderId) {
        this.commanderId = commanderId;
    }

    public int getCommanderId() {
        assert (this.commanderId == -1);
        return this.commanderId;
    }

    public void release(ActorContainer actorContainer) {
        super.release(actorContainer);
        actorContainer.removeCommanderEnemyPlane(this);
    }
}