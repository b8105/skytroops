package com.example.game.actor.enemy_plane;

import com.example.game.actor.ActorTagString;
import com.example.game.game.ActorContainer;
import com.example.game.game.creational.EnemyCreateConfig;

public class CommanderEnemyPlane extends EnemyPlane {
    private int commanderId = -1;

    public CommanderEnemyPlane(ActorContainer actorContainer, String tag, EnemyCreateConfig config) {
        super(actorContainer, tag);

        actorContainer.addCommanderEnemyPlane(this);
        this.commanderId = config.commanderId;
    }

    public void setCommanderId(int commanderId) {
        this.commanderId = commanderId;
    }

    public int getCommanderId() {
        return this.commanderId;
    }

    public EnemyPlaneType getEnemyPlaneType(){
        return EnemyPlaneType.Commander;
    }

    public void release(ActorContainer actorContainer) {
        super.release(actorContainer);
        actorContainer.removeCommanderEnemyPlane(this);
        this.commanderId = -1;
    }
}