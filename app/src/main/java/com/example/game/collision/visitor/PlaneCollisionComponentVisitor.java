package com.example.game.collision.visitor;

import com.example.game.actor.Actor;
import com.example.game.actor.Plane;
import com.example.game.collision.collision_component.PlaneCollisionComponent;

public class PlaneCollisionComponentVisitor {
    public Plane actor;
    public PlaneCollisionComponentVisitor(){
    }
    public void visit(PlaneCollisionComponent component){
        this.actor = component.getPlaneOwner();
    }
}
