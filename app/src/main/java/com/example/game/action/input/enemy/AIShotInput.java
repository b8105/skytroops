package com.example.game.action.input.enemy;

import android.graphics.PointF;

import com.example.game.action.action_component.common.ShotComponent;
import com.example.game.action.command.ShotCommand;
import com.example.game.action.input.ActionInput;
import com.example.game.actor.Actor;
import com.example.game.common.InputEvent;
import com.example.game.game.ActorContainer;
import com.example.game.utility.MathUtilities;
import com.example.game.utility.PointFUtilities;

public class AIShotInput implements ActionInput {
    private ShotComponent shotComponent;
    private boolean active = false;
    private ActorContainer actorContainer;

    public AIShotInput(ShotComponent shotComponent) {
        this.setShotComponent(shotComponent);
    }

    public void setShotComponent(ShotComponent shotComponent) {
        this.shotComponent = shotComponent;
    }

    public void setActorContainer(ActorContainer actorContainer) {
        this.actorContainer = actorContainer;
    }

    private boolean isActive(){
        return this.active;
    }
    public void activate(){
         this.active = true;
    }


    private float clacDirection(final PointF position, final PointF targetPosition) {
        PointF normalize = PointFUtilities.normal(position, targetPosition);
        double rotateRadian = Math.atan2((double) (normalize.y), (double) (normalize.x));
        rotateRadian += MathUtilities.degreeToRadian(90);
        return (float)rotateRadian;
    }

    @Override
    public void execute(InputEvent input) {
        assert (this.shotComponent != null);
        if(!this.isActive()){
            return;
        } // if

        ShotCommand command = new ShotCommand();

        this.inputCommand(command);

        this.shotComponent.writeCommand(command);
    }

    private void inputCommand(ShotCommand command) {
        Actor target = this.actorContainer.getMainChara();
        if(target != null){
            command.fire = true;
            command.angle = MathUtilities.radianToDegree(this.clacDirection(
                    this.shotComponent.getOwner().getPosition(),
                    target.getPosition()));
        } // if

    }
}