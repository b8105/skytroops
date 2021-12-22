package com.example.game.game;

import com.example.game.actor.ActorTagString;
import com.example.game.utility.StopWatch;

public class EnemySpawnSystem {
    StopWatch enemySpawnRate = null;

    public EnemySpawnSystem(float rate) {
        this.enemySpawnRate = new StopWatch(rate);
    }

    public void resetRate(float rate) {
        this.enemySpawnRate.reset(rate);
    }

    public void update(float deltaTime, ActorFactory actorFactory, ActorContainer actorContainer) {
        if (this.enemySpawnRate.tick(deltaTime)) {
            float x = 0.0f;
            //float y = -actorFactory.getEnemyBitmapSize();
            float y = 0.0f;
            float addX = 1080 / 5;
            for (int i = 0; i < 5; i++) {
                if(i == 3){
                    actorFactory.createWeakEnemy(x, y, ActorTagString.enemy);
                } // if
                else {
                    actorFactory.createBasicEnemy(x, y, ActorTagString.enemy);
                } // else
                x += addX;
            } // for
            enemySpawnRate.reset();
        } // if
    }
}