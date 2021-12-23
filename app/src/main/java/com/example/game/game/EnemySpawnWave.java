package com.example.game.game;

import com.example.game.actor.ActorTagString;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.utility.StopWatch;

public class EnemySpawnWave {
    private StopWatch enemySpawnRate = null;
    private int spawnCount = 0;
    private int spawnCountMax = 0;
    private boolean spawned = false;
    private EnemySpawnWaveType type;

    public EnemySpawnWave(EnemySpawnWaveType type, float requiredTime,int loopCountMax) {
        this.type = type;
        this.enemySpawnRate = new StopWatch(requiredTime);
        this.spawnCountMax = loopCountMax;
    }

    public boolean update(float deltaTime, ActorFactory actorFactory) {
        if(!this.spawned){

            switch (this.type){
                case A:
                    this.spawnModeA(actorFactory);
                    break;
                case B:
                    this.spawnModeB(actorFactory);
                    break;
            } // switch
            this.spawned = true;
        } // if

        if (this.enemySpawnRate.tick(deltaTime)) {
            this.spawned = false;
            this.spawnCount++;
            this.enemySpawnRate.reset();
        } // if
        if(spawnCount == spawnCountMax){
           return true;
        } // if
        return false;
    }


    private void spawnModeA(ActorFactory actorFactory){
        float x = 0.0f;
        float y = -BitmapSizeStatic.enemy.y;
        float addX = 1080 / 5;
        for (int i = 0; i < 5; i++) {
            actorFactory.createEnemy(x, y, ActorTagString.enemy, EnemyPlaneType.Basic);
            x += addX;
        } // for
    }
    private void spawnModeB(ActorFactory actorFactory){
        float x = 0.0f;
        float y = -BitmapSizeStatic.enemy.y;
        float addX = 1080 / 5;
        for (int i = 0; i < 5; i++) {
            if(i % 2 == 0){
                actorFactory.createEnemy(x, y, ActorTagString.enemy, EnemyPlaneType.Strong);
            } // if
            else {
                actorFactory.createEnemy(x, y, ActorTagString.enemy, EnemyPlaneType.Weak);
            } // else
            x += addX;
        } // for
    }
}