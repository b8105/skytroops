package com.example.game.action.input.enemy;

import android.graphics.PointF;

import com.example.game.action.action_component.common.MoveComponent;
import com.example.game.action.command.MoveCommand;
import com.example.game.action.input.ActionInput;
import com.example.game.actor.Actor;
import com.example.game.common.InputEvent;
import com.example.game.main.Game;
import com.example.game.utility.PointFUtilities;

import java.util.Random;


public class AICornerMoveInput implements ActionInput {
    private MoveComponent moveComponent;
    private float speed = 0.0f;
    private int moveDirection = new Random().nextInt(3) + -1;
    private PointF targetPosition = new PointF(
            Game.getDisplayRealSize().x * 0.5f,
            Game.getDisplayRealSize().y * 0.5f) ;
    private float moveThresholdY = Game.getDisplayRealSize().y * 0.4f;
    private float angleThresholdY = 50.0f;
    private float movedAngle = 0.0f;
    private int moveSequence = 0;

    public void setMoveComponent(MoveComponent moveComponent) {
        this.moveComponent = moveComponent;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    private PointF clacMove() {
        PointF move = new PointF();

        switch (this.moveSequence) {
            case 0:
                move =this.moveHoming(this.speed, this.moveComponent.getOwner().getCenterPosition(),targetPosition);
                break;
            case 1:
                break;
            case 2:
                move = new PointF(this.speed * -moveDirection, speed);
                break;
        } // switch
        return move;
    }
    private float clacAngle() {
        float angle = 0.0f;

        switch (this.moveSequence) {
            case 0:
                break;
            case 1:
                angle = moveDirection;
                break;
            case 2:
                break;
        } // switch
        return angle;
    }

    private void updateSequence() {
        Actor owner = this.moveComponent.getOwner();
        PointF position = owner.getPosition();
        switch (this.moveSequence) {
            case 0:
                if (position.y > moveThresholdY) {
                    this.moveSequence++;
                } // if
                break;
            case 1:
                if(this.moveDirection == 0){
                    this.moveSequence++;
                } // if
                else if (this.movedAngle > angleThresholdY) {
                    this.moveSequence++;
                } // else if
                break;
        } // switch
    }

    @Override
    public void execute(InputEvent input) {
        assert (this.moveComponent != null);
        this.updateSequence();
        MoveCommand command = new MoveCommand();

        PointF speed = this.clacMove();
        float angluarSpeed = this.clacAngle();

        command.speed.x = speed.x;
        command.speed.y = speed.y;
        command.angluarSpeed = angluarSpeed;

        this.movedAngle += Math.abs(angluarSpeed);
        this.moveComponent.writeCommand(command);
    }

    @Override
    public void initialize() {
    }
    private PointF moveHoming(float speed, final PointF position, final PointF targetPosition) {
        PointF normalize = PointFUtilities.normal(position,
                new PointF(
                        targetPosition.x ,
                        targetPosition.y ));
        return new PointF(
                normalize.x * speed,
                normalize.y * speed);
    }
}