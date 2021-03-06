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
    private boolean targetingFlag = true;
    public AutoTargetingShotComponent(ActionComponent actionComponent) {
        super(actionComponent);
    }

    public void setActorContainer(ActorContainer actorContainer) {
        this.actorContainer = actorContainer;
    }

    public void setTargetingFlag(boolean targetingFlag) {
        this.targetingFlag = targetingFlag;
    }

    private void aquareTarget() {
        assert (this.actorContainer != null);
        this.target = this.actorContainer .getMainChara();
    }
    @Override
    public void execute(float deltaTime) {
        if(this.target == null){
            this.aquareTarget();
            return;
        } // if

        Weapon weapon = super.getWeapon();
        if(weapon.isReady()){
            if(this.targetingFlag){
                weapon.setRotation(MathUtilities.radianToDegree(PointFUtilities.clacDirection(
                        this.getOwner().getPosition(),
                        this.target.getPosition()
                )));
            } // if

            weapon.shot(
                    this.getOwner().getPosition(),
                    this.getOwner().getRotation(),
                    this.getOwner().getTag()
            );
        } // if

    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.AutoTargetingShot;
    }
}