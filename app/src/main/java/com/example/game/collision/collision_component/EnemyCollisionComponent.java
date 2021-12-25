package com.example.game.collision.collision_component;

import com.example.game.actor.ActorState;
import com.example.game.actor.PlaneType;
import com.example.game.actor.bullet.Bullet;
import com.example.game.actor.bullet.BulletType;
import com.example.game.actor.enemy_plane.BossEnemyPlane;
import com.example.game.actor.enemy_plane.EnemyPlane;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
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

import java.util.HashMap;

public class EnemyCollisionComponent
        extends CollisionComponent {
    public EnemyCollisionComponent(CollisionLayer layer) {
        super(layer);
    }

    private void clacCollisionInfoForce(Bullet bullet, CollisionInfo info) {
        info.force = bullet.getMass();
    }
    public boolean isCollisionAtBullet(Collisionable target, CollisionInfo info) {
        BulletCollisionComponentVisitor visitor = new BulletCollisionComponentVisitor();
        target.visitorAccept(visitor);
        Bullet targetOwner = visitor.actor;
        this.clacCollisionInfoForce(targetOwner, info);
        return super.isCollisionRect(this, target, this.getOwner(), targetOwner, info);
    }

    public CollisionableType getCollisionableType() {
        return CollisionableType.Enemy;
    }
    public Plane getPlaneOwner(){
        return (Plane) super.getOwner();
    }
    public EnemyPlane getEnemyPlaneOwner(){
        return (EnemyPlane) super.getOwner();
    }

    public boolean isCollision(Collisionable target, CollisionInfo info) {
        EnemyPlane enemyPlane =this.getEnemyPlaneOwner();
        if(enemyPlane != null){
            if(enemyPlane.getEnemyPlaneType() == EnemyPlaneType.Boss){
                BossEnemyPlane boss = (BossEnemyPlane)(enemyPlane);
                if(boss.getInvincibleParameter().isActive()){
                    return false;
                } // if
            } // if
        } // if

        if (target.getCollisionableType() == CollisionableType.Bullet) {
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