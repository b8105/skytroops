package com.example.game.action.action_component.enemy;

import android.graphics.PointF;

import com.example.game.action.action_component.ActionComponent;
import com.example.game.action.command.MoveCommand;
import com.example.game.action.ActionLayer;
import com.example.game.component.ComponentType;

public class WaveMoveComponent extends ActionComponent {
    //! 周期
    private float count = 0.0f;
    //! 周期加算値
    private float countIncrement = 12.0f;
    //! 周期閾値
    private float countLimit = 360.0f;
    //! 振幅
    private float waveAmplitude = 15.0f;
    //! Y方向の移動
    private float speedY = 10.0f;

    public WaveMoveComponent(ActionComponent actionComponent) {
        super(actionComponent);
    }

    private PointF clacMove(){
        PointF move = new PointF();
        move.x = (float) Math.cos( (double)( count * Math.PI / 180.0f)) * waveAmplitude;
        move.y = this.speedY;

        count += countIncrement;
        if (count > countLimit) {
            count = 0.0f;
        } // if
        return move;
    }

    @Override
    public void execute(float deltaTime) {
        PointF position = super.getOwner().getPosition();

        PointF move = this.clacMove();

        position.x += move.x;
        position.y += move.y;

        super.getOwner().setPosition(position);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.WaveMove;
    }
}