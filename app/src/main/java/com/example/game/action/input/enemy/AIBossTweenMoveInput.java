package com.example.game.action.input.enemy;

import android.graphics.PointF;

import com.example.game.action.action_component.ActionComponent;
import com.example.game.action.action_component.common.MoveComponent;
import com.example.game.action.command.MoveCommand;
import com.example.game.action.input.ActionInput;
import com.example.game.actor.Actor;
import com.example.game.actor.enemy_plane.BossEnemyPlane;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.InputEvent;
import com.example.game.main.Game;

import java.util.AbstractCollection;

public class AIBossTweenMoveInput implements ActionInput {
    private MoveComponent moveComponent;
    private float speed = 9.0f;
    private int moveSequence = 0;
    private float moveThresholdY = Game.getDisplayRealSize().y * 0.3f;
    private BossEnemyPlane bossEnemyPlane = null;
    private AIShotInput shotInput = null;

    public void setMoveComponent(MoveComponent moveComponent) {
        this.moveComponent = moveComponent;
    }

    public void setShotInput(AIShotInput shotInput) {
        this.shotInput = shotInput;
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
        } // switch
        return move;
    }

    private void updateSequence() {
        Actor owner = this.moveComponent.getOwner();
        PointF position = owner.getPosition();
        switch (this.moveSequence) {
            case 0:
                if (position.y > moveThresholdY) {
                    this.moveSequence++;
                    ((BossEnemyPlane) (moveComponent.getOwner())).getInvincibleParameter().inactivate();
                    this.shotInput.activate();
                } // if
                break;
            case 1:
                if (position.x < 0.0f) {
                    this.moveSequence++;
                } // if
                break;
            case 2:
                if (position.x > Game.getDisplayRealSize().x-BitmapSizeStatic.boss.x)  {
                    this.moveSequence--;
                } // if
                break;
        } // switch
    }

    @Override
    public void execute(InputEvent input) {
        assert (this.moveComponent != null);
        this.updateSequence();
        MoveCommand command = new MoveCommand();

        PointF speed = this.clacMove();

        command.speed.x = speed.x;
        command.speed.y = speed.y;

        moveComponent.writeCommand(command);
    }
}
