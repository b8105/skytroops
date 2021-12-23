package com.example.game.action.input.enemy;

import com.example.game.action.input.ActionInput;
import com.example.game.common.InputEvent;

import java.util.ArrayList;
import java.util.List;

//! アクションの入力機能のルート
public class EnemyPlaneActionInput implements ActionInput {
    private List<ActionInput> children = new ArrayList<>();

    public void addActionInput(ActionInput input) {
        this.children.add(input);
    }

    @Override
    public void execute(InputEvent input) {
        for (ActionInput actionInput : this.children) {
            actionInput.execute(input);
        } // for
    }
}