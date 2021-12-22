package com.example.game.action;

import com.example.game.action.input.ActionInput;
import com.example.game.common.InputEvent;

import java.util.ArrayList;
import java.util.List;

public class ActionLayer {
    private List<Actionable> actionables = new ArrayList<Actionable>();

    public void add(Actionable actionable) {
        assert (actionable != null);
        this.actionables.add(actionable);
    }

    public void remove(Actionable actionable) {
        assert (actionable != null);
        this.actionables.remove(actionable);
    }

    public void input(InputEvent input) {
        for (Actionable actionable : this.actionables) {
            ActionInput actionInput = actionable.getActionInput();
            if (actionInput != null) {
                actionInput.execute(input);
            } // if
        } // for
    }

    public void excute(float deltaTime) {
        for (Actionable actionable : this.actionables) {
            actionable.execute(deltaTime);
        } // for
    }
}