package com.example.game.collision.collision_component;

import com.example.game.actor.ActorState;
import com.example.game.parameter.damage.Damage;
import com.example.game.actor.Actor;
import com.example.game.actor.ActorTagString;
import com.example.game.actor.Plane;
import com.example.game.collision.visitor.BulletCollisionComponentVisitor;
import com.example.game.collision.CollisionInfo;
import com.example.game.collision.CollisionLayer;
import com.example.game.collision.Collisionable;
import com.example.game.collision.CollisionableType;
import com.example.game.collision.detector.RectangleCollisionDetector;
import com.example.game.component.ComponentType;

public class EnemyCollisionComponent
        extends CollisionComponent {

    public EnemyCollisionComponent(CollisionLayer layer) {
        super(layer);
    }

    public boolean isCollisionAtBullet(Collisionable target, CollisionInfo info) {
        BulletCollisionComponentVisitor visitor = new BulletCollisionComponentVisitor();
        target.visitorAccept(visitor);
        Actor targetOwner = visitor.actor;
        return super.isCollisionRect(this, target, this.getOwner(), targetOwner, info);
    }

    public CollisionableType getCollisionableType() {
        return CollisionableType.Enemy;
    }
    public Plane getPlaneOwner(){
        return (Plane) super.getOwner();
    }

    public boolean isCollision(Collisionable target, CollisionInfo info) {
        if (target.getCollisionableType() == CollisionableType.Bullet) {
            info.force = 1;
            return this.isCollisionAtBullet(target, info);
        } // if
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

        if (target.getCollisionableType() == CollisionableType.Bullet
                && info.targetTag.equals(ActorTagString.player)) {
            this.getPlaneOwner().applyDamage(new Damage(info));
        } // if
    }

    public void executeStayFunction(Collisionable target, CollisionInfo info) {
        if(super.getOwner().getActorState() == ActorState.End){
            return;
        } // if

        if (target.getCollisionableType() == CollisionableType.Bullet
                && info.targetTag.equals(ActorTagString.player)) {
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
        return ComponentType.EnemyCollision;
    }
}