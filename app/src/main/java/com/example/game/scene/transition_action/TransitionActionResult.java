package com.example.game.scene.transition_action;

import com.example.game.scene.transition_state.TransitionState;
import com.example.game.scene.transition_state.TransitionStateType;

public class TransitionActionResult {
    public TransitionActionResultType resultType = TransitionActionResultType.None;
    public TransitionStateType transitionStateType = TransitionStateType.CountMax;

    public TransitionActionResult() {
    }
}