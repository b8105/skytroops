package com.example.game.action.input.enemy;

import android.graphics.PointF;

import com.example.game.action.action_component.common.ShotComponent;
import com.example.game.action.command.ShotCommand;
import com.example.game.action.input.ActionInput;
import com.example.game.actor.Actor;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.InputEvent;
import com.example.game.game.ActorContainer;
import com.example.game.utility.MathUtilities;
import com.example.game.utility.PointFUtilities;

public class AIShotInput implements ActionInput {
    private boolean active = false;
    private ShotComponent shotComponent;
    private ActorContainer actorContainer;
    private boolean targeting = true;
    private float defaultAngle = 180.0f;
    private float playerHalfSizeX = 0.0f;

    public AIShotInput(ShotComponent shotComponent) {
        this.playerHalfSizeX = BitmapSizeStatic.player.x * 0.5f;
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
    public void inactivate(){
         this.active = false;
    }

    public void setTargeting(boolean targeting) {
        this.targeting = targeting;
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

    public void forceExecute(){
        ShotCommand command = new ShotCommand();
        this.inputCommand(command);
        this.shotComponent.writeCommand(command);
    }
    public void forceReset(){
        ShotCommand command = new ShotCommand();
        this.shotComponent.writeCommand(command);
    }

    @Override
    public void initialize() {
    }

    private void inputCommand(ShotCommand command) {
        Actor target = this.actorContainer.getMainChara();
        if(target != null){
            command.fire = true;
            if(this.targeting){
                PointF targetPosition = new PointF(
                        target.getPosition().x  - this.playerHalfSizeX,
                        target.getPosition().y
                );
                command.angle = MathUtilities.radianToDegree(this.clacDirection(
                        this.shotComponent.getOwner().getPosition(),
                        targetPosition));
            } // if
            else{
                command.angle = defaultAngle;
            } // else
        } // if
    }
}