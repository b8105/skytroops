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

// コマンドを受け取り移動します
// 移動可能な速さは持たず
// Actorの位置、回転量に速度を足しこむだけのクラスです
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

        // 角速度が無視できるほど小さいなら回転しない
        if(Math.abs(this.command.angluarSpeed ) > 0.1f){
            // 回転して角度が0.0(degree)を下回るなら360.0足しこんで正規化する
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