package com.example.game.action.action_component.bullet;

import android.graphics.PointF;

import com.example.game.action.ActionLayer;
import com.example.game.action.action_component.ActionComponent;
import com.example.game.actor.Actor;
import com.example.game.actor.ActorTagString;
import com.example.game.actor.Plane;
import com.example.game.actor.PlaneType;
import com.example.game.actor.bullet.Bullet;
import com.example.game.actor.enemy_plane.EnemyPlane;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.component.ComponentType;
import com.example.game.game.ActorContainer;
import com.example.game.game.FindNearestEnemyVisitor;
import com.example.game.utility.MathUtilities;
import com.example.game.utility.PointFUtilities;

public class BulletFrontMoveComponent extends ActionComponent {
    private float speed = 0.0f;
    private ActorContainer actorContainer;
    private Plane target;
//    private PointF previsousMove;
    //private PointF targetSize;
    private float angluarSpeedDegree = 1.0f;

    public BulletFrontMoveComponent(ActionLayer layer) {
        super(layer);
//        this.previsousMove = new PointF(0.0f, -10.0f);
  //      this.targetSize = new PointF();
    }

    public void setOwner(Actor owner) {
        super.setOwner(owner);
    }

    public void setActorContainer(ActorContainer actorContainer) {
        this.actorContainer = actorContainer;
    }
//
//    private void clacTargetSize() {
//        if (this.target == null) {
//            return;
//        } // if
//        assert (this.target.getPlaneType() == PlaneType.Player);
//       // this.targetSize.x = BitmapSizeStatic.player.x;
//        //this.targetSize.y = BitmapSizeStatic.player.y;
//    }

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

        float direcion = PointFUtilities.clacDirection(owner.getCenterPosition(), target.getCenterPosition()) - MathUtilities.degreeToRadian(90);
        float angluarSpeed = this.angluarSpeedDegree;

        float direcionDegree = MathUtilities.radianToDegree(direcion) + 90;
        float rotationDegree = rotation;

        if (Math.abs(direcionDegree - rotationDegree) > 1.0f) {
            float diffDegree = direcionDegree - rotationDegree;
            if (diffDegree < 0.0f) {
                angluarSpeed *= -1.0f;
            } // if

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