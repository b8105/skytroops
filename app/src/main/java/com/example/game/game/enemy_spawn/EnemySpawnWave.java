package com.example.game.game.enemy_spawn;

import com.example.game.actor.ActorTagString;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.game.creational.ActorFactory;
import com.example.game.game.enemy_spawn.EnemySpawnData;
import com.example.game.main.Game;
import com.example.game.stage.StageType;
import com.example.game.utility.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemySpawnWave {
    private StopWatch enemySpawnRate = null;
    private int spawnIndex = 0;
    private List<EnemySpawnData> spawnDataList = new ArrayList<>();
    private int loopCount = 0;
    private int loopCountMax = 0;


    private int randomRangeWeak = 140;
    private int randomRangeStrong = (int)(Game.getDisplayRealSize().x -  BitmapSizeStatic.enemy.x);

    public EnemySpawnWave(float requiredTime, List<EnemySpawnData> spawnDataList,int moreCount) {
        this.enemySpawnRate = new StopWatch(requiredTime);
        this.spawnDataList = spawnDataList;
        this.loopCountMax = moreCount;
    }


    public boolean update(float deltaTime, ActorFactory actorFactory, StageType stageType) {
        this.spawn(actorFactory,stageType);

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

    private void spawn(ActorFactory actorFactory, StageType stageType) {
        int index = this.spawnIndex;
        float y = -BitmapSizeStatic.enemy.y;

        for (index = this.spawnIndex; index < this.spawnDataList.size(); index++) {
            EnemySpawnData spawnData = this.spawnDataList.get(index);
            if (spawnData.time > this.enemySpawnRate.getTime()) {
                break;
            } // if
            float offset = 0.0f;
            if(spawnData.type == EnemyPlaneType.Weak ){
                offset = new Random().nextInt(this.randomRangeWeak);
            } // if
            else if(spawnData.type == EnemyPlaneType.Strong){
                offset = new Random().nextInt(this.randomRangeStrong);
            } // if

            actorFactory.createEnemy(spawnData.positionX + offset, y,
                    ActorTagString.enemy, spawnData.type,stageType,spawnData.config);
        } // for
        this.spawnIndex = index;
    }
}