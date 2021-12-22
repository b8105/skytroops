package com.example.game.utility;

public interface State<StateType, Action> {
    public StateType getStateType();

    public Action getAction();

    public void enter();

    public void exit();
}