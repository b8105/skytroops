package com.example.game.utility;

import com.example.game.scene.transition_state.TransitionState;

import java.util.HashMap;

//! 単純なステートマシンです
//! update関数が無いのは持つStateがアクションのクラスを持つことを想定しているからです
//! ステートのポップや遷移条件は現状不要なので入れてません
public class StateMachine<StateType> {
    private State currentState;
    private HashMap<StateType, State> status;

    public StateMachine() {
        this.currentState = null;
        this.status = new HashMap<>();
    }

    public <AnyState> AnyState getCurrentState() {
        return (AnyState) this.currentState;
    }

    public StateType getCurrentStateType() {
        return (StateType) this.currentState.getStateType();
    }

    public HashMap<StateType, State> getStatus() {
        return this.status;
    }

    public void start(StateType key) {
        this.currentState = status.get(key);
    }

    public void register(State state) {
        this.register((StateType) state.getStateType(), state);
    }

    public void register(StateType key, State state) {
        status.put(key, state);
    }

    public void transition(StateType key) {
        if (this.currentState != null) {
            this.currentState.exit();
        } // if

        State state = this.status.get(key);
        if (state != null) {
            this.currentState = state;
            this.currentState.enter();
        } // if
        else {
            this.currentState = null;
        } // else
    }

    public void reset() {
        this.status.clear();
    }
}