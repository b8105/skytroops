package com.example.game.collision.visitor;

import com.example.game.actor.Actor;
import com.example.game.actor.enemy_plane.EnemyPlane;
import com.example.game.collision.collision_component.EnemyCollisionComponent;

public class EnemyCollisionComponentVisitor {
    public EnemyPlane actor;
    public EnemyCollisionComponentVisitor(){
    }
    public void visit(EnemyCollisionComponent component){
        this.actor = component.getEnemyPlaneOwner();
    }
}