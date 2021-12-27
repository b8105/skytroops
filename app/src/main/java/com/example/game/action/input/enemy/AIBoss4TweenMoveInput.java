package com.example.game.action.input.enemy;

import android.graphics.PointF;

import com.example.game.action.action_component.common.MoveComponent;
import com.example.game.action.command.MoveCommand;
import com.example.game.action.input.ActionInput;
import com.example.game.actor.Actor;
import com.example.game.actor.enemy_plane.BossEnemyPlane;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.InputEvent;
import com.example.game.component.ComponentType;
import com.example.game.main.Game;
import com.example.game.render.render_component.PlaneHpBarRenderComponent;

import java.util.Random;

public class AIBoss4TweenMoveInput implements ActionInput {
    private MoveComponent moveComponent;
    private float speed = 9.0f;
    private int moveSequence = 0;
    private float moveThresholdY = Game.getDisplayRealSize().y * 0.3f;
    private BossEnemyPlane bossEnemyPlane = null;

    private AIShotInput shotInput = null;
    private AIShotInput subShotInput = null;

    private boolean shotFlag =false;

    private int tweenFrame = 0;
    private int tweenFrameMax = 120;
    private int backFrame = 0;
    private int backFrameMax = 6;
    private int frontFrame = 0;
    private int frontFrameMax = 6;


    public void setMoveComponent(MoveComponent moveComponent) {
        this.moveComponent = moveComponent;
    }

    public void setInputSpeed(float speed) {
        this.speed = speed;
    }

    public void setShotInput(AIShotInput shotInput) {
        this.shotInput = shotInput;
    }
    public void setSubShotInput(AIShotInput shotInput) {
        this.subShotInput = shotInput;
    }
    private PointF clacMove() {
        PointF move = new PointF();

        switch (this.moveSequence) {
            case 0:
                move = new PointF(0.0f, speed);
                if (this.bossEnemyPlane == null) {
                    this.bossEnemyPlane = ((BossEnemyPlane) (moveComponent.getOwner()));
                    this.bossEnemyPlane.getInvincibleParameter().activate();
                } // if
                break;
            case 1:
                move = new PointF(-speed,0.0f );
                break;
            case  2:
                move = new PointF(speed,0.0f );
                break;
            case  3:
                move = new PointF(0.0f, -speed * 3.0f);
                break;
            case  4:
                move = new PointF(0.0f, speed* 3.0f);
                break;
        } // switch
        return move;
    }

    private void updateSequence(InputEvent input) {
        Actor owner = this.moveComponent.getOwner();
        PointF position = owner.getPosition();
        switch (this.moveSequence) {
            case 0:
                if (position.y > moveThresholdY) {
                    this.moveSequence++;
                    ((BossEnemyPlane) (moveComponent.getOwner())).getInvincibleParameter().inactivate();
                    ((PlaneHpBarRenderComponent)(this.bossEnemyPlane).getComponent(ComponentType.HpBarRender)).activate();
                    this.subShotInput.activate();
                } // if
                break;
            case 1:
                this.tweenFrame++;
                if(this.tweenFrame > this.tweenFrameMax){
                    this.tweenFrame = 0;
                    this.moveSequence = 3;
                } // if
                else if (position.x < 0.0f) {
                    this.moveSequence++;
                } // else if
                break;
            case 2:
                this.tweenFrame++;
                if(this.tweenFrame > this.tweenFrameMax){
                    this.tweenFrame = 0;
                    this.moveSequence = 3;
                } // if
                else if (position.x > Game.getDisplayRealSize().x- BitmapSizeStatic.boss.x)  {
                    this.moveSequence--;
                } // if
                break;
            case 3:
                this.backFrame++;
                if(this.backFrame > this.backFrameMax){
                    this.shotFlag = false;
                    this.moveSequence = 4;
                    this.backFrame = 0;
                } // if
                break;
            case  4:
                this.frontFrame++;
                if(!this.shotFlag){
                    this.shotInput.forceExecute();
                    this.shotFlag = true;
                } // if
                else {
                    this.shotInput.forceReset();
                } // else
                if(this.frontFrame > this.frontFrameMax){
                    this.moveSequence =  new Random().nextInt(2) + 1;
                    this.frontFrame = 0;
                } // if
                break;
        } // switch
    }

    @Override
    public void execute(InputEvent input) {
        assert (this.moveComponent != null);
        this.updateSequence(input);
        MoveCommand command = new MoveCommand();

        PointF speed = this.clacMove();

        command.speed.x = speed.x;
        command.speed.y = speed.y;

        moveComponent.writeCommand(command);
    }

    @Override
    public void initialize() {
        this.shotInput.inactivate();
        this.subShotInput.inactivate();
    }
}
