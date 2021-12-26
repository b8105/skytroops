package com.example.game.action.action_component.common;

import android.graphics.PointF;

import com.example.game.action.action_component.ActionComponent;
import com.example.game.action.command.MoveCommand;
import com.example.game.action.ActionLayer;
import com.example.game.actor.ActorTagString;
import com.example.game.actor.ActorType;
import com.example.game.actor.Plane;
import com.example.game.actor.enemy_plane.CommanderEnemyPlane;
import com.example.game.actor.enemy_plane.EnemyPlane;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.actor.enemy_plane.FollowEnemyPlane;
import com.example.game.collision.CollisionableType;
import com.example.game.component.ComponentType;
import com.example.game.utility.MathUtilities;

public class MoveComponent extends ActionComponent {
    private MoveCommand command = null;

    public MoveComponent(ActionComponent actionComponent) {
        super(actionComponent);
    }

    public void writeCommand(MoveCommand command) {
        this.command = new MoveCommand();
        this.command.speed.x = command.speed.x;
        this.command.speed.y = command.speed.y;
        this.command.angluarSpeed = command.angluarSpeed;
    }

    @Override
    public void execute(float deltaTime) {
        if (command == null) {
            return;
        } // if
        PointF position = super.getOwner().getPosition();

        position.x += this.command.speed.x;
        position.y += this.command.speed.y;

        super.getOwner().setPosition(position);

        float rotation = super.getOwner().getRotation();

        if(Math.abs(this.command.angluarSpeed ) > 0.1f){
            if(rotation + this.command.angluarSpeed < 0.0f){
                rotation += MathUtilities.radianToDegree(Math.PI * 2.0f);
            } // if
            rotation += this.command.angluarSpeed;
        } // if

        super.getOwner().setRotation(rotation);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.Move;
    }
}