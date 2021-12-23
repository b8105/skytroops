package com.example.game.action.input.enemy;

import android.graphics.PointF;

import com.example.game.action.action_component.common.MoveComponent;
import com.example.game.action.command.MoveCommand;
import com.example.game.action.input.ActionInput;
import com.example.game.actor.Actor;
import com.example.game.common.InputEvent;
import com.example.game.main.Game;
import com.example.game.utility.PointFUtilities;

public class AIBossMoveInput implements ActionInput {
    private MoveComponent moveComponent;
    private float speed = 10.0f;
    private int moveSequence = 0;
    private float moveThresholdY = Game.getDisplayRealSize().y * 0.3f;

    public void setMoveComponent(MoveComponent moveComponent) {
        this.moveComponent = moveComponent;
    }

    private PointF clacMove() {
        PointF move = new PointF();

        switch (this.moveSequence){
            case 0:
                move = new PointF(0.0f, speed);
                break;
            case 1:
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

        PointF speed = this.clacMove();

        command.speed.x = speed.x;
        command.speed.y = speed.y;

        moveComponent.writeCommand(command);
    }
}
