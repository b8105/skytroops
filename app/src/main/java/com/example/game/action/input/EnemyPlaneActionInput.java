package com.example.game.action.input;

import com.example.game.action.input.ActionInput;
import com.example.game.common.InputEvent;

import java.util.ArrayList;
import java.util.List;

//! アクションの入力機能のルート
public class EnemyPlaneActionInput implements ActionInput {
    //private AIStraightMoveInput aiStraightMoveInput = null;
    private List<ActionInput> children = new ArrayList<>();
    //
    // 一定周期で進むごとに
    // 触れている間は弾がでる
//    public void setPlayerMoveInput(PlayerMoveInput playerMoveInput) {
//        this.playerMoveInput = playerMoveInput;
//    }
//
//    public void setPlayerShotInput(PlayerShotInput playerShotInput) {
//        this.playerShotInput = playerShotInput;
//    }

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