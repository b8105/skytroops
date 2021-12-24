package com.example.game.game;

import com.example.game.actor.ActorTagString;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.utility.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class EnemySpawnWave {
    private StopWatch enemySpawnRate = null;
    private int spawnCount = 0;
    private int spawnCountMax = 0;
    private boolean spawned = false;
    private EnemySpawnWaveType type;
    private int spawnIndex = 0;
    List<EnemySpawnData> spawnDataList = new ArrayList<>();


    public EnemySpawnWave(EnemySpawnWaveType type, float requiredTime, int loopCountMax) {
        this.type = type;
        this.enemySpawnRate = new StopWatch(requiredTime);
        this.spawnCountMax = loopCountMax;
    }

    public EnemySpawnWave(EnemySpawnWaveType type, float requiredTime, List<EnemySpawnData> spawnDataList,int loopCountMax) {
        this.type = type;
        this.enemySpawnRate = new StopWatch(requiredTime);
        this.spawnCountMax = loopCountMax;
        this.spawnDataList = spawnDataList;
    }


    public boolean update(float deltaTime, ActorFactory actorFactory) {
        if (!this.spawned) {

            switch (this.type) {
                case A:
                    this.spawnModeA(actorFactory);
                    this.spawned = true;
                    break;
                case B:
                    this.spawnModeB(actorFactory);
                    if(this.spawnIndex == spawnDataList.size()){
                        this.spawnIndex = 0;
                        this.spawned = true;
                    } // if
                    break;
            } // switch
        } // if

        if (this.enemySpawnRate.tick(deltaTime)) {
            this.spawned = false;
            this.spawnCount++;
            this.enemySpawnRate.reset();
        } // if
        if (spawnCount == spawnCountMax) {
            return true;
        } // if
        return false;
    }


    private void spawnModeA(ActorFactory actorFactory) {
        float x = 0.0f;
        float y = -BitmapSizeStatic.enemy.y;
        float addX = 1080 / 5;
        for (int i = 0; i < 5; i++) {
            actorFactory.createEnemy(x, y, ActorTagString.enemy, EnemyPlaneType.Basic);
            x += addX;
        } // for
    }

    private void spawnModeC(ActorFactory actorFactory) {
        float x = 0.0f;
        float y = -BitmapSizeStatic.enemy.y;
        float addX = 1080 / 5;
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                actorFactory.createEnemy(x, y, ActorTagString.enemy, EnemyPlaneType.Strong);
            } // if
            else {
                actorFactory.createEnemy(x, y, ActorTagString.enemy, EnemyPlaneType.Weak);
            } // else
            x += addX;
        } // for
    }

    private void spawnModeB(ActorFactory actorFactory) {
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