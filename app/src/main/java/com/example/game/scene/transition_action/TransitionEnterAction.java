package com.example.game.scene.transition_action;

import android.graphics.Color;

import com.example.game.common.shape.Rectangle;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderRectangleInfo;
import com.example.game.scene.transition_state.TransitionState;
import com.example.game.scene.transition_state.TransitionStateType;
import com.example.game.utility.StopWatch;

public class TransitionEnterAction extends TransitionAction {
    public TransitionEnterAction(float time, Game game) {
        super(time, game);
    }

    @Override
    public TransitionActionResult execute(float deltaTime) {
        TransitionActionResult result = new TransitionActionResult();
        StopWatch time = super.getTime();
        if (time.tick(deltaTime * super.getTimeCoefficient())) {
            result.resultType = TransitionActionResultType.Transition;
            result.transitionStateType = TransitionStateType.None;
        } // if
        return result;
    }

    @Override
    public void drawFadeTransition(RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.Blackout);
        Rectangle rectangle = new Rectangle();

        StopWatch time = super.getTime();
        float alpha = 255 - (time.getDevidedTime() * 255.0f);
        RenderRectangleInfo info = new RenderRectangleInfo(
                Color.BLACK,
                (int) alpha);

        rectangle.right = super.getScreenSize().x;
        rectangle.bottom = super.getScreenSize().y;
        list.drawRectangle(
                rectangle,
                info
        );
    }
}
