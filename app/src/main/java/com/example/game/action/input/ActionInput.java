package com.example.game.action.input;

import com.example.game.common.InputEvent;

public interface ActionInput {
    public void execute(InputEvent input);
    public void initialize();
}