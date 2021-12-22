package com.example.game.action.action_component;

import android.graphics.PointF;

import com.example.game.actor.Plane;
import com.example.game.game.ActorContainer;
import com.example.game.game.FindNearestEnemyVisitor;
import com.example.game.weapon.Weapon;
import com.example.game.action.ActionLayer;
import com.example.game.action.command.ShotCommand;
import com.example.game.component.ComponentType;
import com.example.game.utility.StopWatch;

public class AutoTargetingShotComponent extends ActionComponent {
    private StopWatch shotTime = new StopWatch(0.0f);
    private Weapon weapon;
    private Plane target;
    private ActorContainer actorContainer;

    public AutoTargetingShotComponent(ActionComponent actionComponent) {
        super(actionComponent);
    }

    public void setActorContainer(ActorContainer actorContainer) {
        this.actorContainer = actorContainer;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setShotInterval(float time) {
        this.shotTime.reset(time);
    }

    void clacTarget() {
        assert (this.actorContainer != null);
        this.target = this.actorContainer .getMainChara();
    }

    private float clacDirection(final PointF position, final PointF targetPosition) {
        float directionX = targetPosition.x - position.x;
        float directionY = targetPosition.y - position.y;

        float magnitude = (float) Math.sqrt(Math.pow(directionX, 2.0) + Math.pow(directionY, 2.0));
        PointF normalize = new PointF(directionX / magnitude, directionY / magnitude);

        double rotateRadian = Math.atan2((double) (normalize.y), (double) (normalize.x));
        rotateRadian += this.degreeToRadian(90);
        return (float)rotateRadian;
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

    @Override
    public void execute(float deltaTime) {
        this.clacTarget();

        if (shotTime.tick(deltaTime)) {
            weapon.setRotation(this.radianToDegree(this.clacDirection(
                    this.getOwner().getPosition(),
                    this.target.getPosition()
            )));

            this.weapon.shot(
                    this.getOwner().getPosition(),
                    this.getOwner().getRotation(),
                    this.getOwner().getTag()
            );
            shotTime.reset();
        } // if
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.AutoTargetingShot;
    }
}