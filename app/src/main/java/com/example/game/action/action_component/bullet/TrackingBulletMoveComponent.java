package com.example.game.action.action_component.bullet;

import android.graphics.PointF;

import com.example.game.action.ActionLayer;
import com.example.game.action.action_component.ActionComponent;
import com.example.game.actor.Actor;
import com.example.game.actor.ActorTagString;
import com.example.game.actor.Plane;
import com.example.game.actor.bullet.Bullet;
import com.example.game.component.ComponentType;
import com.example.game.game.ActorContainer;
import com.example.game.utility.MathUtilities;
import com.example.game.utility.PointFUtilities;

//! 現在BossEnemy4専用です
//! HomingBulletMoveComponentよりもゆるく追いかけます
public class TrackingBulletMoveComponent extends ActionComponent {
    private float speed = 0.0f;
    private ActorContainer actorContainer;
    private Plane target;
    private float angluarSpeedDegree = 1.0f;

    public TrackingBulletMoveComponent(ActionLayer layer) {
        super(layer);
    }

    public void setOwner(Actor owner) {
        super.setOwner(owner);
    }

    public void setActorContainer(ActorContainer actorContainer) {
        this.actorContainer = actorContainer;
    }

    private void clacTarget() {
        assert (this.actorContainer != null);
        String tag = super.getOwner().getTag();
        assert (tag == ActorTagString.enemy);
        this.target = this.actorContainer.getMainChara();
    }

    @Override
    public void onComponentInitialize(Actor owner) {
        super.onComponentInitialize(owner);
        this.speed = ((Bullet) (super.getOwner())).getAppliedShotSpeed();
    }


    private void updateRotation(Actor owner,Actor target){
        float rotation = owner.getRotation();

        // 見て計算しやすいようにDegreeの変数を作ります
        float direcion = PointFUtilities.clacDirection(owner.getCenterPosition(), target.getCenterPosition()) - MathUtilities.degreeToRadian(90);
        float angluarSpeed = this.angluarSpeedDegree;
        float direcionDegree = MathUtilities.radianToDegree(direcion) + 90;
        float rotationDegree = rotation;

        if (Math.abs(direcionDegree - rotationDegree) > 1.0f) {
            float diffDegree = direcionDegree - rotationDegree;
            // 近い方へ振り向く
            if (diffDegree < 0.0f) {
                angluarSpeed *= -1.0f;
            } // if

            //　計算結果が0.0度(degree)をを下回るなら正規化する
            if (rotation + angluarSpeed < 0.0f) {
                rotation += MathUtilities.radianToDegree(Math.PI * 2.0f);
            } // if
            rotation += angluarSpeed;
        } // if
        super.getOwner().setRotation(rotation);
    }

    @Override
    public void execute(float deltaTime) {
        Actor owner = super.getOwner();

        this.clacTarget();

        // オブジェクトの回転量を更新します
        // ターゲットがいるならその方向を緩やかに向きます
        if(this.target != null){
            updateRotation(owner,this.target);
        } // if

        // translate
        PointF position = super.getOwner().getPosition();
        PointF move = PointFUtilities.rotate(0.0f, -this.speed, owner.getRotation(), 0.0f, 0.0f);
        position.x += move.x;
        position.y += move.y;
        super.getOwner().setPosition(position);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.BasicBulletMove;
    }
}