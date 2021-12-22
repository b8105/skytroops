package com.example.game.action.input;

import com.example.game.action.command.MoveCommand;
import com.example.game.action.action_component.MoveComponent;
import com.example.game.common.InputEvent;

public class AIStraightMoveInput implements ActionInput {
    private MoveComponent moveComponent;

    public void setMoveComponent(MoveComponent moveComponent) {
        this.moveComponent = moveComponent;
    }

    @Override
    public void execute(InputEvent input) {
        assert (this.moveComponent != null);
        MoveCommand command = new MoveCommand();

        command.speed.x = 0.0f;
        command.speed.y = 16.0f;

        moveComponent.writeCommand(command);
    }
}