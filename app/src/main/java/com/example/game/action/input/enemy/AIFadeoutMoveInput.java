package com.example.game.action.input.enemy;

import android.graphics.PointF;

import com.example.game.action.command.MoveCommand;
import com.example.game.action.action_component.common.MoveComponent;
import com.example.game.action.input.ActionInput;
import com.example.game.actor.Actor;
import com.example.game.common.InputEvent;
import com.example.game.main.Game;
import com.example.game.utility.MathUtilities;
import com.example.game.utility.PointFUtilities;

public class AIFadeoutMoveInput implements ActionInput {
    private MoveComponent moveComponent;
    private float speed = 10.0f;
    private int moveSequence = 0;
    private float defaultFadeoutDirection = 45.0f;
    private float fadeoutDirection = 45.0f;
    private float moveThresholdY = Game.getDisplayRealSize().y * 0.5f;
    private AIShotInput shotInput = null;

    public void setMoveComponent(MoveComponent moveComponent) {
        this.moveComponent = moveComponent;
    }

    public void setShotInput(AIShotInput shotInput) {
        this.shotInput = shotInput;
    }

    private void clacDirection() {
        this.fadeoutDirection = this.defaultFadeoutDirection;
        if(this.moveComponent.getOwner().getInitialPosition().x < Game.getDisplayRealSize().x * 0.5f){
            this.fadeoutDirection =
                    this.defaultFadeoutDirection + (float)MathUtilities.radianToDegree( Math.PI * 0.5f);
        } // if
    }
    private PointF clacMove() {
        PointF move = new PointF();

        switch (this.moveSequence){
            case 0:
                move = new PointF(0.0f, speed);
                break;
            case 1:
                move = PointFUtilities.rotate(
                        this.speed, 0.0f, this.fadeoutDirection, 0.0f, 0.0f);
                move.y *= -1;
                break;
        } // switch
        return move;
    }
    private void updateSequence(){
        Actor owner = this.moveComponent.getOwner();
        PointF position =  owner.getPosition();
        switch (this.moveSequence){
            case 0:
                if(position.y > moveThresholdY){
                    this.moveSequence++;
                    this.shotInput.activate();
                } // if
                break;
            case 1:
                break;
        } // switch
    }

    @Override
    public void execute(InputEvent input) {
        assert (this.moveComponent != null);
        this.updateSequence();
        MoveCommand command = new MoveCommand();

        this.clacDirection();
        PointF speed = this.clacMove();

        command.speed.x = speed.x;
        command.speed.y = speed.y;

        moveComponent.writeCommand(command);
    }
}