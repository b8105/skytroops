package com.example.game.collision.visitor;

import com.example.game.actor.bullet.Bullet;
import com.example.game.collision.collision_component.BulletCollisionComponent;
import com.example.game.actor.Actor;

public class BulletCollisionComponentVisitor {
    public Bullet actor;
    public BulletCollisionComponentVisitor(){
    }
    public void visit(BulletCollisionComponent component){
        this.actor = component.getPlaneOwner();
    }
}