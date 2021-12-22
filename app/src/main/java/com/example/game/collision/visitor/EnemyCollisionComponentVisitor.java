package com.example.game.collision.visitor;

import com.example.game.actor.Actor;
import com.example.game.collision.collision_component.EnemyCollisionComponent;

public class EnemyCollisionComponentVisitor {
    public Actor actor;
    public EnemyCollisionComponentVisitor(){
    }
    public void visit(EnemyCollisionComponent component){
        this.actor = component.getOwner();
    }
}