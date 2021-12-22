package com.example.game.action.action_component.bullet;

import android.graphics.PointF;

import com.example.game.game.ActorContainer;
import com.example.game.game.FindNearestEnemyVisitor;
import com.example.game.action.ActionLayer;
import com.example.game.action.action_component.ActionComponent;
import com.example.game.actor.Actor;
import com.example.game.actor.bullet.Bullet;
import com.example.game.component.ComponentType;

public class HomingBulletMoveComponent extends ActionComponent {
    private float speed = 0.0f;

    private Bullet ownerBullet;
    private ActorContainer actorContainer;
    private Actor target;
    private PointF previsousMove;

    public HomingBulletMoveComponent(ActionLayer layer) {
        super(layer);
        this.speed = 28.0f;
        this.previsousMove = new PointF(0.0f, -this.speed);
    }

    public void setOwner(Actor owner) {
        super.setOwner(owner);
        this.ownerBullet = (Bullet) super.getOwner();
    }

    public void setActorContainer(ActorContainer actorContainer) {
        this.actorContainer = actorContainer;
    }

    public Bullet getOwnerBullet() {
        return this.ownerBullet;
    }

    double radianToDegree(double radians) {
        return radians * (180.0 / Math.PI);
    }
    float radianToDegree(float radians) {
        return (float) this.radianToDegree((double) radians);
    }

    double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }

    float degreeToRadian(float degree) {
        return (float) this.degreeToRadian((double) degree);
    }

    void clacTarget() {
        assert (this.actorContainer != null);
        FindNearestEnemyVisitor visitor = new FindNearestEnemyVisitor(
                getOwner().getPosition()
        );
        this.actorContainer.visitorAccept(visitor);
        this.target = visitor.find;
    }

    private PointF moveHoming(float speed, final PointF position , final PointF targetPosition){
        float directionX = targetPosition.x - position.x;
        float directionY = targetPosition.y - position.y;

        float magnitude = (float) Math.sqrt(Math.pow(directionX, 2.0) + Math.pow(directionY, 2.0));
        PointF normalize = new PointF(directionX / magnitude, directionY / magnitude);
        return new PointF(
                normalize.x * speed,
                normalize.y * speed);
    }
    private PointF moveDefault(float speed){
        return this.previsousMove;
        //return new PointF(0.0f, -speed);
    }
    @Override
    public void execute(float deltaTime) {
        this.clacTarget();
        Actor owner = super.getOwner();

        PointF position = this.getOwner().getPosition();
        PointF move = null;
        float rotateRadian = 0.0f;

        if(this.target != null){
            move = this.moveHoming(
                    this.speed,
                    position,
                    this.target.getPosition());
            this.previsousMove.x = move.x;
            this.previsousMove.y = move.y;
            rotateRadian = (float)Math.atan2((double)(move.y),(double)(move.x));
            rotateRadian += this.degreeToRadian(90);
        } // if
        else {
            move = this.moveDefault(this.speed);
            this.previsousMove.x = move.x;
            this.previsousMove.y = move.y;
            rotateRadian = (float)Math.atan2((double)(move.y),(double)(move.x));
            rotateRadian += this.degreeToRadian(90);
        } // else
        position.x += move.x;
        position.y += move.y;
        owner.setRotation(this.radianToDegree(rotateRadian));
        owner.setPosition(position);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.BasicBulletMove;
    }
}