package com.example.game.collision.visitor;

import android.graphics.PointF;

import com.example.game.actor.Plane;
import com.example.game.collision.collision_component.PlaneCollisionComponent;
import com.example.game.collision.collision_component.StageCollisionComponent;
import com.example.game.stage.Stage;

public class StageCollisionComponentVisitor {
    public PointF stageSize;
    public StageCollisionComponentVisitor(){
    }
    public void visit(StageCollisionComponent component){
        this.stageSize = component.getScreenSize();
    }
}