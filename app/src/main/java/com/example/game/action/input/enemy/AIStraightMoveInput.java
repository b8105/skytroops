package com.example.game.action.input.enemy;

import com.example.game.action.command.MoveCommand;
import com.example.game.action.action_component.common.MoveComponent;
import com.example.game.action.input.ActionInput;
import com.example.game.common.InputEvent;

public class AIStraightMoveInput implements ActionInput {
    private MoveComponent moveComponent;
    private float speed = 32.0f;

    public void setMoveComponent(MoveComponent moveComponent) {
        this.moveComponent = moveComponent;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void execute(InputEvent input) {
        assert (this.moveComponent != null);
        MoveCommand command = new MoveCommand();

        command.speed.x = 0.0f;
        command.speed.y = this.speed;

        moveComponent.writeCommand(command);
    }
}