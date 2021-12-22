package com.example.game.collision.visitor;

import com.example.game.collision.collision_component.BulletCollisionComponent;
import com.example.game.actor.Actor;

public class BulletCollisionComponentVisitor {
    public Actor actor;
    public BulletCollisionComponentVisitor(){
    }
    public void visit(BulletCollisionComponent component){
        this.actor = component.getOwner();
    }
}