package com.example.game.collision;

import com.example.game.collision.visitor.BulletCollisionComponentVisitor;
import com.example.game.collision.visitor.EnemyCollisionComponentVisitor;
import com.example.game.collision.visitor.PlaneCollisionComponentVisitor;
import com.example.game.collision.visitor.StageCollisionComponentVisitor;
import com.example.game.common.shape.Rectangle;

public interface Collisionable {
    public void onCollisionableDestroy();

    public void visitorAccept(BulletCollisionComponentVisitor visitor);

    public void visitorAccept(EnemyCollisionComponentVisitor visitor);

    public void visitorAccept(PlaneCollisionComponentVisitor visitor);

    public void visitorAccept(StageCollisionComponentVisitor visitor);

    public CollisionableType getCollisionableType();

    public Rectangle getCollisionRectangle();

    public boolean isCollision(Collisionable target, CollisionInfo info);

    public void executeEnterFunction(Collisionable target, CollisionInfo info);

    public void executeStayFunction(Collisionable target, CollisionInfo info);

    public void executeExitFunction(Collisionable target, CollisionInfo info);

    public boolean existCollisioned(Collisionable target);

    public void addCollisioned(Collisionable target);

    public void removeCollisioned(Collisionable target);
}