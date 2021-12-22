package com.example.game.action.input.player;

import com.example.game.action.input.ActionInput;
import com.example.game.common.InputEvent;

//! アクションの入力機能のルート
public class PlayerActionInput implements ActionInput {
    //! PlaneActionComponentが保持する各アクションのへのコマンドを発行する
    private PlayerMoveInput playerMoveInput = null;
    private PlayerShotInput playerShotInput = null;

    //
    // 一定周期で進むごとに
    // 触れている間は弾がでる
    public void setPlayerMoveInput(PlayerMoveInput playerMoveInput) {
        this.playerMoveInput = playerMoveInput;
    }

    public void setPlayerShotInput(PlayerShotInput playerShotInput) {
        this.playerShotInput = playerShotInput;
    }

    @Override
    public void execute(InputEvent input) {
        this.playerMoveInput.execute(input);
        this.playerShotInput.execute(input);
    }
}