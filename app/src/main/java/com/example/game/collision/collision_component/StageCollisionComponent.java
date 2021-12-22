package com.example.game.collision.collision_component;

import android.graphics.Point;
import android.graphics.PointF;

import com.example.game.actor.Plane;
import com.example.game.collision.CollisionInfo;
import com.example.game.collision.CollisionLayer;
import com.example.game.collision.Collisionable;
import com.example.game.collision.CollisionableType;
import com.example.game.component.ComponentType;
import com.example.game.common.shape.Rectangle;
import com.example.game.stage.Stage;

public class StageCollisionComponent
        extends CollisionComponent {
    private Point screenSize;

    public StageCollisionComponent(CollisionLayer layer) {
        super(layer);
        screenSize = new Point();
    }

    public void setScreenSize(Point screenSize) {
        this.screenSize.x = screenSize.x;
        this.screenSize.y = screenSize.y;
    }

    public CollisionableType getCollisionableType() {
        return CollisionableType.Stage;
    }

    public PointF getScreenSize() {
        return new PointF(this.screenSize.x,this.screenSize.y);
    }


    public Rectangle getCollisionRectangle() {
        Rectangle rect = new Rectangle(
                0,
                0,
                this.screenSize.x,
                this.screenSize.y);
        return rect;
    }

    public boolean isCollision(Collisionable target, CollisionInfo info) {
        return false;
    }

    public void executeEnterFunction(Collisionable target, CollisionInfo info) {
    }

    public void executeStayFunction(Collisionable target, CollisionInfo info) {
    }

    public void executeExitFunction(Collisionable target, CollisionInfo info) {
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.StageCollision;
    }
}