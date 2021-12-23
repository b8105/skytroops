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

    public EnemySpawnWave(float requiredTime,int loopCountMax) {
        this.enemySpawnRate = new StopWatch(requiredTime);
        this.spawnCountMax = loopCountMax;
    }

    public boolean update(float deltaTime, ActorFactory actorFactory) {
        if(!this.spawned){
            this.spawnModeA(actorFactory);
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
}