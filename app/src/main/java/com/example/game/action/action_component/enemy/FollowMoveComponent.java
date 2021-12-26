package com.example.game.action.action_component.enemy;

import android.graphics.PointF;

import com.example.game.action.action_component.ActionComponent;
import com.example.game.action.action_component.common.MoveComponent;
import com.example.game.actor.Actor;
import com.example.game.actor.enemy_plane.CommanderEnemyPlane;
import com.example.game.actor.enemy_plane.FollowEnemyPlane;
import com.example.game.component.ComponentType;
import com.example.game.game.ActorContainer;
import com.example.game.game.CommanderEnemyPlaneVisitor;
import com.example.game.utility.PointFUtilities;
import com.example.game.weapon.Weapon;

public class FollowMoveComponent extends MoveComponent {
    private CommanderEnemyPlane followTarget = null;
    private PointF commanderTransration = new PointF();
    private float speed = 8.0f;
    private float accele = 2.0f;
    private ActorContainer actorContainer = null;
    private PointF prevMove = new PointF(0.0f, this.speed * 5);
    private Weapon weapon;
    private float enemyDegree = 180.0f;


    public FollowMoveComponent(ActionComponent actionComponent) {
        super(actionComponent);
    }

    public void setActorContainer(ActorContainer actorContainer) {
        this.actorContainer = actorContainer;
    }

    @Override
    public void onComponentInitialize(Actor owner) {
        super.onComponentInitialize(owner);
        this.commanderTransration =  ((FollowEnemyPlane)(owner)).getCommanderTranslation();
        this.weapon =  ((FollowEnemyPlane)(owner)).getWeapon("BasicGun");
    }

    private void aquireTarget(){
        if(this.followTarget != null && this.followTarget.getCommanderId() == -1){
            this.followTarget = null;
        } // if

        if(this.followTarget == null){
            CommanderEnemyPlaneVisitor visitor = new CommanderEnemyPlaneVisitor(
                    ((FollowEnemyPlane)(this.getOwner())).getCommanderId()
            );
            this.actorContainer.visitorAccept(visitor);
            this.followTarget = visitor.find;
        } // if
    }


    private PointF clacMove() {
        if(this.followTarget == null){
            return this.prevMove;
        } // if

        PointF move = new PointF();
        PointF commanderPosition = this.followTarget.getPosition();


        PointF transration = new PointF(this.commanderTransration.x,this.commanderTransration.y);

        transration = PointFUtilities.rotate(transration.x,transration.y, this.followTarget.getRotation());

        float destinationX = commanderPosition.x + transration.x;
        float destinationY = commanderPosition.y + transration.y;
        PointF destination = new PointF(destinationX, destinationY);
        PointF source = this.getOwner().getPosition();

        float accele = 1.0f;
        if(PointFUtilities.magnitude(source, destination) < this.speed ){
            accele = accele * 0.25f;
        } // if
        else if(PointFUtilities.magnitude(source, destination) > this.speed * 2){
            accele = this.accele;
        } // if


        PointF normal = PointFUtilities.normal(source, destination);

        move.x = normal.x * this.speed * accele;
        move.y = normal.y * this.speed* accele;

        this.prevMove.x = move.x;
        this.prevMove.y = move.y;
        return move;

    }
    private float clacRotation() {
        if(this.followTarget == null){
            return this.getOwner().getRotation();
        } // if
        return this.followTarget.getRotation();
    }

    @Override
    public void execute(float deltaTime) {
        this.aquireTarget();

        PointF position = super.getOwner().getPosition();

        PointF move = this.clacMove();

        position.x += move.x;
        position.y += move.y;

        super.getOwner().setPosition(position);

        float rotation = this.clacRotation();
        // エネミーなので
        this.weapon.setRotation(this.getOwner().getRotation() + this.enemyDegree);
        super.getOwner().setRotation(rotation);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.WaveMove;
    }
}