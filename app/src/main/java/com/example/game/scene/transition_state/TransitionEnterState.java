package com.example.game.scene.transition_state;

import com.example.game.scene.transition_action.TransitionAction;

public class TransitionEnterState extends TransitionState {
    @Override
    public TransitionStateType getStateType() {
        return TransitionStateType.Enter;
    }
}
