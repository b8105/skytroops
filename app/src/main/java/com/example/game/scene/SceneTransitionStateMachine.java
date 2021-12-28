package com.example.game.scene;

import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.transition_action.TransitionAction;
import com.example.game.scene.transition_action.TransitionActionResult;
import com.example.game.scene.transition_action.TransitionActionResultType;
import com.example.game.scene.transition_action.TransitionEnterAction;
import com.example.game.scene.transition_action.TransitionExitAction;
import com.example.game.scene.transition_action.TransitionNoneAction;
import com.example.game.scene.transition_state.TransitionEnterState;
import com.example.game.scene.transition_state.TransitionExitState;
import com.example.game.scene.transition_state.TransitionNoneState;
import com.example.game.scene.transition_state.TransitionState;
import com.example.game.scene.transition_state.TransitionStateType;
import com.example.game.utility.StateMachine;

//! シーンの遷移の時に必要な機能だけを公開します
public class SceneTransitionStateMachine {
    private StateMachine<TransitionStateType> stateMachine = new StateMachine<TransitionStateType>();
    public  SceneTransitionStateMachine(Game game){
        TransitionState enter = new TransitionEnterState();
        TransitionState none = new TransitionNoneState();
        TransitionState exit = new TransitionExitState();

        enter.setAction(new TransitionEnterAction(1.0f, game));
        none.setAction(new TransitionNoneAction(1.0f, game));
        exit.setAction(new TransitionExitAction(1.0f, game
        ));
        this.stateMachine.register(enter);
        this.stateMachine.register(none);
        this.stateMachine.register(exit);
        this.stateMachine.start(enter.getStateType());
    }

    public TransitionState getState(TransitionStateType transitionStateType){
        return (TransitionState)this.stateMachine.getStatus().get(transitionStateType);
    }
    public void update(float deltaTime){
        TransitionState state = this.stateMachine.getCurrentState();
        TransitionAction action = state.getAction();
        TransitionActionResult result = action.execute(deltaTime);
        if (result.resultType == TransitionActionResultType.Transition) {
            this.stateMachine.transition(result.transitionStateType);
        } // if
    }
    public void drawTransitionEffect(RenderCommandQueue out){
        TransitionState state = this.stateMachine.getCurrentState();
        TransitionAction action = state.getAction();
        action.drawFadeTransition(out);
    }
    public void transition(TransitionStateType type){
        this.stateMachine.transition(type);
    }
}