package com.example.game.action.action_component.bullet;

import android.graphics.PointF;

import com.example.game.actor.ActorTagString;
import com.example.game.actor.Plane;
import com.example.game.actor.PlaneType;
import com.example.game.actor.bullet.Bullet;
import com.example.game.actor.enemy_plane.EnemyPlane;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.game.ActorContainer;
import com.example.game.game.container_visitor.FindNearestEnemyVisitor;
import com.example.game.action.ActionLayer;
import com.example.game.action.action_component.ActionComponent;
import com.example.game.actor.Actor;
import com.example.game.component.ComponentType;
import com.example.game.utility.MathUtilities;
import com.example.game.utility.PointFUtilities;


// 敵キャラがこれを使うとバランスが壊れるので
// EnemyPlaneの系譜が持つWeaponから撃たれることは想定していない
// ActorContainerから最寄りのPlaneを探してきて追跡対象とする
// もし対象がなかったら前のフレームと同じ動きをする
public class HomingBulletMoveComponent extends ActionComponent {
    private float speed = 0.0f;
    private ActorContainer actorContainer;
    private Plane target;
    private PointF previsousMove;
    private PointF targetSize;

    public HomingBulletMoveComponent(ActionLayer layer) {
        super(layer);
        this.previsousMove = new PointF(0.0f, -12.0f);
        this.targetSize = new PointF();
    }

    public void setOwner(Actor owner) {
        super.setOwner(owner);
    }

    public void setActorContainer(ActorContainer actorContainer) {
        this.actorContainer = actorContainer;
    }

    private void clacTargetSize() {
        if (this.target == null) {
            return;
        } // if
        if (this.target.getPlaneType() == PlaneType.Enemy) {
            EnemyPlane enemy = ((EnemyPlane) (this.target));
            if (enemy.isBoss()) {
                this.targetSize.x = BitmapSizeStatic.boss.x;
                this.targetSize.y = BitmapSizeStatic.boss.y;
            } // if
            else {
                this.targetSize.x = BitmapSizeStatic.enemy.x;
                this.targetSize.y = BitmapSizeStatic.enemy.y;
            } // else
        } // if
        else if (this.target.getPlaneType() == PlaneType.Player) {
            this.targetSize.x = BitmapSizeStatic.player.x;
            this.targetSize.y = BitmapSizeStatic.player.y;
        } // else if
    }

    private void clacTarget() {
        assert (this.actorContainer != null);
        String tag = super.getOwner().getTag();
        if (tag == ActorTagString.player) {
            // 指定の範囲内で最寄りのActorが欲しいなどの場合は対応するVisitorを呼び出す
            FindNearestEnemyVisitor visitor = new FindNearestEnemyVisitor(
                    getOwner().getPosition()
            );
            this.actorContainer.visitorAccept(visitor);
            this.target = visitor.find;
        } // if
        else if (tag == ActorTagString.enemy) {
            this.target = this.actorContainer.getMainChara();
        } // else if
    }


    private PointF moveDefault(float speed) {
        return this.previsousMove;
    }

    @Override
    public void onComponentInitialize(Actor owner) {
        super.onComponentInitialize(owner);
        this.speed = ((Bullet) (super.getOwner())).getAppliedShotSpeed();
    }


    @Override
    public void execute(float deltaTime) {
        this.clacTarget();
        this.clacTargetSize();
        Actor owner = super.getOwner();

        PointF position = this.getOwner().getPosition();
        PointF move = null;
        float rotateRadian = 0.0f;

        // ターゲットに対して移動してその方向を向きます
        // もし居なかったら前の動きを続けます
        if (this.target != null) {
            PointF targetPosition = this.target.getCenterPosition();

            move = PointFUtilities.directionalVector(
                    this.speed,
                    this.getOwner().getCenterPosition(),
                    targetPosition);
            this.previsousMove.x = move.x;
            this.previsousMove.y = move.y;
            rotateRadian = (float) Math.atan2((double) (move.y), (double) (move.x));
            rotateRadian += MathUtilities.degreeToRadian(90);
        } // if
        else {
            move = this.moveDefault(this.speed);
            this.previsousMove.x = move.x;
            this.previsousMove.y = move.y;
            rotateRadian = (float) Math.atan2((double) (move.y), (double) (move.x));
            rotateRadian += MathUtilities.degreeToRadian(90);
        } // else
        position.x += move.x;
        position.y += move.y;


        owner.setRotation(MathUtilities.radianToDegree(rotateRadian));
        owner.setPosition(position);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.BasicBulletMove;
    }
}