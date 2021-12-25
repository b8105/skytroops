package com.example.game.collision.collision_component;

import android.graphics.PointF;
import android.graphics.Rect;

import com.example.game.actor.Actor;
import com.example.game.actor.ActorState;
import com.example.game.actor.Plane;
import com.example.game.actor.bullet.Bullet;
import com.example.game.collision.CollisionInfo;
import com.example.game.collision.CollisionLayer;
import com.example.game.collision.Collisionable;
import com.example.game.collision.CollisionableType;
import com.example.game.collision.detector.RectangleCollisionDetector;
import com.example.game.collision.visitor.BulletCollisionComponentVisitor;
import com.example.game.collision.visitor.EnemyCollisionComponentVisitor;
import com.example.game.collision.visitor.PlaneCollisionComponentVisitor;
import com.example.game.component.ComponentType;
import com.example.game.common.shape.Rectangle;

public class BulletCollisionComponent
        extends CollisionComponent {

    public BulletCollisionComponent(CollisionLayer layer) {
        super(layer);
    }

    public CollisionableType getCollisionableType() {
        return CollisionableType.Bullet;
    }

    public Bullet getPlaneOwner(){
        return (Bullet) super.getOwner();
    }

    public boolean isCollisionAtEnemy(Collisionable target, CollisionInfo info) {
        EnemyCollisionComponentVisitor visitor = new EnemyCollisionComponentVisitor();
        target.visitorAccept(visitor);
        Actor targetOwner = visitor.actor;
        return super.isCollisionRect(this, target, this.getOwner(), targetOwner, info);
    }

    public boolean isCollision(Collisionable target, CollisionInfo info) {
        if (target.getCollisionableType() == CollisionableType.Enemy) {
            return this.isCollisionAtEnemy(target, info);
        } // else if
        else if (target.getCollisionableType() == CollisionableType.Stage) {
            RectangleCollisionDetector detector = new RectangleCollisionDetector();
            return detector.CollisionRectangle(
                    this.getCollisionRectangle(),
                    target.getCollisionRectangle());
        } // else if
        return false;
    }

    public void executeEnterFunction(Collisionable target, CollisionInfo info) {
        if(super.getOwner().getActorState() == ActorState.End){
            return;
        } // if

        if (target.getCollisionableType() == CollisionableType.Enemy) {
            System.out.println("Buller End");
            this.getOwner().end();
        } // if
    }

    public void executeStayFunction(Collisionable target, CollisionInfo info) {
        if(super.getOwner().getActorState() == ActorState.End){
            return;
        } // if

    }

    public void executeExitFunction(Collisionable target, CollisionInfo info) {
        if(super.getOwner().getActorState() == ActorState.End){
            return;
        } // if

        if (target.getCollisionableType() == CollisionableType.Stage) {
            this.getOwner().end();
        } // if
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.BulletCollision;
    }
}