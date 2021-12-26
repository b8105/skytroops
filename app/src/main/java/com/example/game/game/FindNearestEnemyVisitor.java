package com.example.game.game;

import android.graphics.PointF;

import com.example.game.actor.Actor;
import com.example.game.actor.Plane;
import com.example.game.actor.PlaneType;
import com.example.game.actor.enemy_plane.EnemyPlane;

import java.util.List;


// ActorContainerから指定の位置に最も近いPlaneを抽出します
// もし範囲を指定する場合は
// float range を持たせて for分の中でdistanceと比較する
public class FindNearestEnemyVisitor {
    public Plane find;
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
        for(Plane actor : enemies){
             PointF targetPos = actor.getPosition();
            float diffX = targetPos.x - this.position.x;
            float diffY = targetPos.y - this.position.y;

            // 平方根をとる必要はない
            float distance = (diffX * diffX) + (diffY * diffY);
            if(distanceMin > distance){
                distanceMin = distance;
                this.find = actor;
            } // if
        } // for
    }
}