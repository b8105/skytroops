package com.example.game.scene.transition_state;

import com.example.game.utility.State;
import com.example.game.scene.transition_action.TransitionAction;

public abstract class TransitionState implements
        State<TransitionStateType, TransitionAction> {
    private TransitionAction action;

    public void setAction(TransitionAction action) {
        this.action = action;
    }

    @Override
    final public TransitionAction getAction() {
        return this.action;
    }

    @Override
    public void enter() {
    }

    @Override
    public void exit() {
    }
}