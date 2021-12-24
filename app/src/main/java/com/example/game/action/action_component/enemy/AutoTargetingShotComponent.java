package com.example.game.action.action_component.enemy;

import android.graphics.PointF;

import com.example.game.action.action_component.ActionComponent;
import com.example.game.action.action_component.common.ShotComponent;
import com.example.game.actor.Plane;
import com.example.game.game.ActorContainer;
import com.example.game.utility.MathUtilities;
import com.example.game.utility.PointFUtilities;
import com.example.game.weapon.Weapon;
import com.example.game.component.ComponentType;
import com.example.game.utility.StopWatch;

public class AutoTargetingShotComponent extends ShotComponent {
    private Plane target;
    private ActorContainer actorContainer;

    public AutoTargetingShotComponent(ActionComponent actionComponent) {
        super(actionComponent);
    }

    public void setActorContainer(ActorContainer actorContainer) {
        this.actorContainer = actorContainer;
    }

    private void aquareTarget() {
        assert (this.actorContainer != null);
        this.target = this.actorContainer .getMainChara();
    }

    private float clacDirection(final PointF position, final PointF targetPosition) {
        PointF normalize = PointFUtilities.normal(position, targetPosition);
        double rotateRadian = Math.atan2((double) (normalize.y), (double) (normalize.x));
        rotateRadian += MathUtilities.degreeToRadian(90);
        return (float)rotateRadian;
    }

    @Override
    public void execute(float deltaTime) {
        if(this.target == null){
            this.aquareTarget();
            return;
        } // if

        StopWatch stopWatch = super.getShotTime();
        Weapon weapon = super.getWeapon();

        if (stopWatch.tick(deltaTime)) {
            weapon.setRotation(MathUtilities.radianToDegree(this.clacDirection(
                    this.getOwner().getPosition(),
                    this.target.getPosition()
            )));

            weapon.shot(
                    this.getOwner().getPosition(),
                    this.getOwner().getRotation(),
                    this.getOwner().getTag()
            );
            stopWatch.reset();
        } // if
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.AutoTargetingShot;
    }
}