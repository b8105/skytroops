package com.example.game.game;

import com.example.game.game.creational.ActorFactory;
import com.example.game.game.enemy_spawn.EnemySpawnSystem;
import com.example.game.stage.Stage;
import com.example.game.stage.StageType;

public class GameSystem {
    private EnemySpawnSystem enemySpawnSystem = null;
    private GameScorer gameScorer = null;
    private boolean spawnEnd = false;

    public GameSystem() {
        this.enemySpawnSystem = new EnemySpawnSystem(StageType.Type01);
        this.gameScorer = new GameScorer();
    }

    public GameScorer getGameScorer() {
        return this.gameScorer;
    }

    public void resetSpawnSystem(StageType stageType){
        this.enemySpawnSystem = new EnemySpawnSystem(stageType);
    }


    public boolean isSpawnEnd() {
        return this.spawnEnd;
    }

    public void update(float deltaTime,
                       Stage stage,
                       ActorContainer actorContainer,
                       ActorFactory actorFactory,
                       StageType stageType) {
//        this.gameLevelController.update(stage, this.enemySpawnSystem);

        if(this.enemySpawnSystem.isActive()){
            this.enemySpawnSystem.update(deltaTime, actorFactory,stageType);
        } // if
        else{
            if(actorContainer.getBossEnemy() != null){
//                this.enemySpawnSystem.updateExistBoss(deltaTime, actorFactory,stageType);
            } // if
        } // else
    }

}