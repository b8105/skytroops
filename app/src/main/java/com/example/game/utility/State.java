package com.example.game.utility;


//! StageMachineに管理される状態クラスのインターフェース
//! StateTypeはenum
//! イベント時の状態固有の振る舞いはActionクラスに実装をされます
//! なのでステートマシンの現在の状態の更新をしたいときは
//! StateMachine::GetCurrentState().GetAction().Execute(Args...)
//! という風に使います
public interface State<StateType, Action> {
    public StateType getStateType();

    public Action getAction();

    public void enter();

    public void exit();
}