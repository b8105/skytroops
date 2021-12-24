package com.example.game.game;

import android.graphics.PointF;

import com.example.game.actor.Actor;
import com.example.game.actor.enemy_plane.EnemyPlane;

import java.util.List;

public class FindNearestEnemyVisitor {
    public Actor find;
    public PointF position;

    public FindNearestEnemyVisitor(PointF position) {
        this.position = position;
    }

    public void visit(ActorContainer actorContainer) {
        List<EnemyPlane> enemies = actorContainer.getEnemies();
        assert (enemies != null);
        if(enemies.isEmpty()){
            this.find = null;
            return;
        } // if

        float distanceMin = Float.POSITIVE_INFINITY;
        for(Actor actor : enemies){
             PointF targetPos = actor.getPosition();
            float diffX = targetPos.x - this.position.x;
            float diffY = targetPos.y - this.position.y;

            float distance = (diffX * diffX) + (diffY * diffY);
            if(distanceMin > distance){
                distanceMin = distance;
                this.find = actor;
            } // if
        } // for
    }
}