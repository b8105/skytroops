package com.example.game.collision.collision_component;

import android.graphics.PointF;
import android.graphics.Rect;

import com.example.game.actor.Actor;
import com.example.game.actor.ActorState;
import com.example.game.actor.ActorTagString;
import com.example.game.actor.ActorType;
import com.example.game.actor.Plane;
import com.example.game.collision.visitor.BulletCollisionComponentVisitor;
import com.example.game.collision.CollisionInfo;
import com.example.game.collision.CollisionLayer;
import com.example.game.collision.Collisionable;
import com.example.game.collision.CollisionableType;
import com.example.game.collision.detector.RectangleCollisionDetector;
import com.example.game.collision.visitor.EnemyCollisionComponentVisitor;
import com.example.game.collision.visitor.StageCollisionComponentVisitor;
import com.example.game.component.ComponentType;
import com.example.game.common.shape.Rectangle;

public class PlaneCollisionComponent
        extends CollisionComponent {

    public PlaneCollisionComponent(CollisionLayer layer) {
        super(layer);
    }

    public Plane getPlaneOwner() {
        return (Plane) super.getOwner();
    }

    public boolean isCollisionAtBullet(Collisionable target, CollisionInfo info) {
        BulletCollisionComponentVisitor visitor = new BulletCollisionComponentVisitor();
        target.visitorAccept(visitor);
        Actor targetOwner = visitor.actor;
        return super.isCollisionRect(this, target, this.getOwner(), targetOwner, info);
    }

    public boolean isCollisionAtEnemy(Collisionable target, CollisionInfo info) {
        EnemyCollisionComponentVisitor visitor = new EnemyCollisionComponentVisitor();
        target.visitorAccept(visitor);
        Actor targetOwner = visitor.actor;
        return super.isCollisionRect(this, target, this.getOwner(), targetOwner, info);
    }

    @Override
    public void onComponentInitialize(Actor owner) {
        super.setSpriteRenderComponent(owner.getComponent(ComponentType.PlaneSpriteRender));
    }

    public CollisionableType getCollisionableType() {
        return CollisionableType.Plane;
    }

    public boolean isCollision(Collisionable target, CollisionInfo info) {
        if (target.getCollisionableType() == CollisionableType.Bullet) {
            info.force = 1;
            return this.isCollisionAtBullet(target, info);
        } // if
        else if (target.getCollisionableType() == CollisionableType.Enemy) {
            info.force = 1;
            return this.isCollisionAtEnemy(target, info);
        } // else if
        else if (target.getCollisionableType() == CollisionableType.Stage) {
            StageCollisionComponentVisitor visitor = new StageCollisionComponentVisitor();
            target.visitorAccept(visitor);
            info.targetSize = visitor.stageSize;

            RectangleCollisionDetector detector = new RectangleCollisionDetector();
            return detector.CollisionRectangle(
                    this.getCollisionRectangle(),
                    target.getCollisionRectangle());
        } // else if
        return false;
    }


    private void onCollisionStage(PointF stageSize) {
        Plane owner = this.getPlaneOwner();
        if (owner.getActorType() == ActorType.Plane &&
                owner.getTag() == ActorTagString.player) {
            PointF pos = owner.getPosition();
            PointF size = this.getCollisionRectangle().getSize();


            if (pos.x < 0.0f) {
                pos.x = 0.0f;
            } // if
            else if (pos.x > stageSize.x - size.x) {
                pos.x = stageSize.x - size.x;
            } // else if
            if (pos.y < 0.0f) {
                pos.y = 0.0f;
            } // if
            else if (pos.y > stageSize.y - size.y) {
                pos.y = stageSize.y - size.y;
            } // else if
            owner.setPosition(pos);
        } // if

    }


    public void executeEnterFunction(Collisionable target, CollisionInfo info) {
        if (target.getCollisionableType() == CollisionableType.Enemy) {
            this.getPlaneOwner().damege(info.force);
        } // if
    }

    public void executeStayFunction(Collisionable target, CollisionInfo info) {
        if (target.getCollisionableType() == CollisionableType.Enemy) {
        } // if
        else if (target.getCollisionableType() == CollisionableType.Stage) {
            if (this.getOwner().getTag() == ActorTagString.player) {
                this.onCollisionStage(info.targetSize);
            } // if
        } // if

    }

    public void executeExitFunction(Collisionable target, CollisionInfo info) {
        if (target.getCollisionableType() == CollisionableType.Enemy) {
        } // if
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.PlaneCollision;
    }
}