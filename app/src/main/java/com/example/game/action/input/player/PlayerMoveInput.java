package com.example.game.action.input.player;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.game.action.command.MoveCommand;
import com.example.game.action.action_component.MoveComponent;
import com.example.game.action.input.ActionInput;
import com.example.game.common.InputEvent;

public class PlayerMoveInput implements ActionInput {
    private PointF prevPosition = new PointF();
    private MoveComponent moveComponent;
    public PlayerMoveInput() {
    }
    public PlayerMoveInput(MoveComponent moveComponent) {
        this.setMoveComponent(moveComponent);
    }

    public void setMoveComponent(MoveComponent moveComponent) {
        this.moveComponent = moveComponent;
    }
    @Override
    public void execute(InputEvent input) {
        assert (this.moveComponent != null);

        MoveCommand command = new MoveCommand();

        if (!input.enable) {
            command.speed.x = 0.0f;
            command.speed.y = 0.0f;
        } // if

        float x = input.positionX;
        float y = input.positionY;

        switch (input.actionType) {
            case (MotionEvent.ACTION_DOWN):
                this.prevPosition.x = x;
                this.prevPosition.y = y;
                break;
            case (MotionEvent.ACTION_MOVE):
                this.inputCommand(x, y, command);
                break;
            case (MotionEvent.ACTION_UP):
                break;
            default:
        } // switch
        moveComponent.writeCommand(command);
    }

    void inputCommand(float x, float y, MoveCommand out) {
        float touchX = x;
        float touchY = y;

        float diffX = touchX - this.prevPosition.x;
        float diffY = touchY - this.prevPosition.y;

        float magnitude = (float) Math.sqrt(
                diffX * diffX + diffY * diffY);

        if(magnitude > 300){
            PointF normalize = new PointF(diffX / magnitude, diffY / magnitude);
            diffX = normalize.x * 20.0f;
            diffY = normalize.y * 20.0f;
        } // if


        out.speed.x = diffX;
        out.speed.y = diffY;

        this.prevPosition.x = touchX;
        this.prevPosition.y = touchY;
    }
}