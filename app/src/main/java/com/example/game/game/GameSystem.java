package com.example.game.game;

import com.example.game.game.ActorContainer;
import com.example.game.game.ActorFactory;
import com.example.game.game.EnemySpawnSystem;
import com.example.game.game.GameLevelController;
import com.example.game.stage.Stage;
import com.example.game.stage.StageType;

public class GameSystem {
    private EnemySpawnSystem enemySpawnSystem = null;
    private GameLevelController gameLevelController = null;
    private GameScorer gameScorer = null;

    private boolean spawnEnd = false;

    public GameSystem(float enemySpawnRate) {
        this.gameLevelController = new GameLevelController();
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
                       ActorFactory actorFactory) {
        this.gameLevelController.update(stage, this.enemySpawnSystem);

        if(this.enemySpawnSystem.isActive()){
            //this.spawnEnd =
            this.enemySpawnSystem.update(deltaTime, actorFactory);
        } // if
    }

}