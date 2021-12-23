package com.example.game.action.action_component.enemy;

import android.graphics.PointF;

import com.example.game.action.ActionLayer;
import com.example.game.action.action_component.ActionComponent;
import com.example.game.action.action_component.MoveComponent;
import com.example.game.actor.Actor;
import com.example.game.actor.enemy_plane.FollowEnemyPlane;
import com.example.game.component.ComponentType;
import com.example.game.utility.PointFUtilities;

public class FollowMoveComponent extends MoveComponent {
    Actor followTarget = null;
    PointF commanderTransration = new PointF(100.0f, 0.0f);
    float speed = 10.0f;

    public FollowMoveComponent(ActionComponent actionComponent) {
        super(actionComponent);
    }

    public void setFollowTarget(Actor followTarget) {
        this.followTarget = followTarget;
    }

    @Override
    public void onComponentInitialize(Actor owner) {
        super.onComponentInitialize(owner);
        this.followTarget =  ((FollowEnemyPlane)(owner)).getCommanderEnemyPlane();
    }

    private PointF clacMove() {
        assert (this.followTarget != null);
        assert (this.commanderTransration != null);
        PointF move = new PointF();

        PointF commanderPosition = this.followTarget.getPosition();

        float destinationX = commanderPosition.x + this.commanderTransration.x;
        float destinationY = commanderPosition.y + this.commanderTransration.y;
        PointF destination = new PointF(destinationX, destinationY);
        PointF source = this.getOwner().getPosition();

        if(PointFUtilities.magnitude(source, destination) < 60.0f){
            return move;
        } // if


        PointF normal = PointFUtilities.normal(source, destination);

        move.x = normal.x * speed;
        move.y = normal.y * speed;
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