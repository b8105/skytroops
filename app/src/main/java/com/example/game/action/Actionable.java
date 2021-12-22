package com.example.game.action;

import com.example.game.action.input.ActionInput;

public interface Actionable {
    public void onActionableDestroy();

    public void setActionInput(ActionInput input);

    public ActionInput getActionInput();

    public void execute(float deltaTime);
}