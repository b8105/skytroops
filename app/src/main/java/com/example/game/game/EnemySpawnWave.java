package com.example.game.game;

import com.example.game.actor.ActorTagString;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.utility.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class EnemySpawnWave {
    private StopWatch enemySpawnRate = null;
    private int spawnIndex = 0;
    private List<EnemySpawnData> spawnDataList = new ArrayList<>();
    private int loopCount = 0;
    private int loopCountMax = 0;

    public EnemySpawnWave(float requiredTime, List<EnemySpawnData> spawnDataList,int moreCount) {
        this.enemySpawnRate = new StopWatch(requiredTime);
        this.spawnDataList = spawnDataList;
        this.loopCountMax = moreCount;
    }


    public boolean update(float deltaTime, ActorFactory actorFactory) {
        this.spawn(actorFactory);

        if (this.enemySpawnRate.tick(deltaTime)) {
            if(this.loopCount == this.loopCountMax){
                return true;
            } // if
            else{
                this.loopCount++;
                this.spawnIndex = 0;
                this.enemySpawnRate.reset();
            } // else
        } // if
        return false;
    }

    private void spawn(ActorFactory actorFactory) {
        int index = this.spawnIndex;
        float y = -BitmapSizeStatic.enemy.y;

        for (index = this.spawnIndex; index < this.spawnDataList.size(); index++) {
            EnemySpawnData spawnData = this.spawnDataList.get(index);
            if (spawnData.time > this.enemySpawnRate.getTime()) {
                break;
            } // if
            actorFactory.createEnemy(spawnData.positionX, y,
                    ActorTagString.enemy, spawnData.type);
        } // for
        this.spawnIndex = index;
    }
}