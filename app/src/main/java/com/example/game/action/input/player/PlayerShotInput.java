package com.example.game.action.input.player;

import com.example.game.action.action_component.common.ShotComponent;
import com.example.game.action.command.ShotCommand;
import com.example.game.action.input.ActionInput;
import com.example.game.common.InputEvent;
import com.example.game.common.InputTouchType;

public class PlayerShotInput implements ActionInput {
    private ShotComponent shotComponent;

    public PlayerShotInput(ShotComponent shotComponent) {
        this.setShotComponent(shotComponent);
    }

    public void setShotComponent(ShotComponent shotComponent) {
        this.shotComponent = shotComponent;
    }

    @Override
    public void execute(InputEvent input) {
        assert (this.shotComponent != null);
        ShotCommand command = new ShotCommand();

        if(input.touchType == InputTouchType.Hold){
            this.inputCommand(command);
        } // if

        this.shotComponent.writeCommand(command);
    }

    void inputCommand(ShotCommand command) {
        command.fire = true;
    }
}