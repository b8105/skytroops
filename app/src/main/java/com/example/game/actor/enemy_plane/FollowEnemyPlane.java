package com.example.game.actor.enemy_plane;

import android.graphics.PointF;

import com.example.game.game.ActorContainer;
import com.example.game.game.creational.EnemyCreateConfig;

public class FollowEnemyPlane extends EnemyPlane {
    private CommanderEnemyPlane commanderEnemyPlane;
    private int commanderId = -1;
    PointF commanderTranslation = new PointF();

    public FollowEnemyPlane(ActorContainer actorContainer, String tag, EnemyCreateConfig config) {
        super(actorContainer, tag);
        this.commanderId = config.commanderId;
        this.commanderTranslation.x = config.commanderTranslation.x;
        this.commanderTranslation.y = config.commanderTranslation.y;
    }

    public void setCommanderEnemyPlane(CommanderEnemyPlane commanderEnemyPlane) {
        this.commanderEnemyPlane = commanderEnemyPlane;
    }

    public void setCommanderTranslation(PointF commanderTranslation) {
        this.commanderTranslation = commanderTranslation;
    }

    public void setCommanderId(int commanderId) {
        this.commanderId = commanderId;
    }

    public int getCommanderId() {
        return this.commanderId;
    }

    public PointF getCommanderTranslation() {
        return this.commanderTranslation;
    }

    public EnemyPlaneType getEnemyPlaneType(){
        return EnemyPlaneType.Follow;
    }

    public CommanderEnemyPlane getCommanderEnemyPlane() {
        return this.commanderEnemyPlane;
    }
}