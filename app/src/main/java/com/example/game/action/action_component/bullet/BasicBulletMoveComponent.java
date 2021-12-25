package com.example.game.action.action_component.bullet;

import android.graphics.PointF;

import com.example.game.action.ActionLayer;
import com.example.game.action.action_component.ActionComponent;
import com.example.game.actor.Actor;
import com.example.game.actor.bullet.Bullet;
import com.example.game.component.ComponentType;
import com.example.game.utility.PointFUtilities;


//　1度射出された弾は止まらないので
// 入力コマンドも受け付けず向ている方向に動き続ける
// Weaponの撃つ力を速さとして受け取る
public class BasicBulletMoveComponent extends ActionComponent {
    private float speed;

    public BasicBulletMoveComponent(ActionLayer layer) {
        super(layer);
    }

    @Override
    public void onComponentInitialize(Actor owner) {
        super.onComponentInitialize(owner);
        this.speed = ((Bullet)(super.getOwner())).getAppliedShotSpeed();
    }

    @Override
    public void execute(float deltaTime) {
        Actor owner = super.getOwner();
        float rotation = owner.getRotation();
        PointF position = super.getOwner().getPosition();

        PointF move = PointFUtilities.rotate(0.0f, -this.speed, rotation, 0.0f, 0.0f);

        position.x += move.x;
        position.y += move.y;

        super.getOwner().setPosition(position);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.BasicBulletMove;
    }
}