package com.example.game.game.container_visitor;

import com.example.game.actor.enemy_plane.CommanderEnemyPlane;
import com.example.game.game.ActorContainer;

import java.util.List;

public class CommanderEnemyPlaneVisitor {
    public int commanderId;
    public CommanderEnemyPlane find;

    public CommanderEnemyPlaneVisitor(int commanderId) {
        this. commanderId = commanderId;
    }

    public void visit(ActorContainer actorContainer) {
        List<CommanderEnemyPlane> enemies = actorContainer.getCommanderEnemies();
        assert (enemies != null);
        if(enemies.isEmpty()){
            this.find = null;
            return;
        } // if

        for(CommanderEnemyPlane actor : enemies){
            if(actor.getCommanderId() == this.commanderId) {
                this.find = actor;
            } // if
        } // for
    }
}