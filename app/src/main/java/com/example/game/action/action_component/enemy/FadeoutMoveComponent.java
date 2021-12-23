package com.example.game.action.action_component.enemy;

import android.graphics.PointF;

import com.example.game.action.action_component.ActionComponent;
import com.example.game.actor.Actor;
import com.example.game.component.ComponentType;

public class FadeoutMoveComponent extends ActionComponent {
    private float speed = 10.0f;
    private int moveSequence = 0;
    private float fadeoutDirection = 90.0f;
    private float moveThreshold = 500.0f;

    public FadeoutMoveComponent(ActionComponent actionComponent) {
        super(actionComponent);
    }

    private PointF clacMove() {
        PointF move = new PointF();

        switch (this.moveSequence){
            case 0:
                move = new PointF(0.0f, speed);
                break;
            case 1:
                move = new PointF(speed, 0.0f);
                break;
        } // switch
        return move;
    }
    private void updateSequence(){
        Actor owner = super.getOwner();
        PointF position =  owner.getPosition();
        switch (this.moveSequence){
            case 0:
                if(position.y > moveThreshold){
                    this.moveSequence++;
                } // if
                break;
            case 1:
                break;
        } // switch
    }


    @Override
    public void execute(float deltaTime) {
        this.updateSequence();

        PointF position = super.getOwner().getPosition();

        PointF move = this.clacMove();

        position.x += move.x;
        position.y += move.y;

        super.getOwner().setPosition(position);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.FadeoutMove;
    }
}