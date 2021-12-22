package com.example.game.scene.transition_action;

import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.transition_state.TransitionStateType;
import com.example.game.utility.StopWatch;

public class TransitionNoneAction extends TransitionAction {
    public TransitionNoneAction(float time, Game game) {
        super(time, game);
    }

    @Override
    public TransitionActionResult execute(float deltaTime) {
        TransitionActionResult result = new TransitionActionResult();
        return result;
    }

    @Override
    public void drawFadeTransition(RenderCommandQueue out) {

    }
}
