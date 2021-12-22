package com.example.game.game;

import com.example.game.game.ActorContainer;
import com.example.game.game.ActorFactory;
import com.example.game.game.EnemySpawnSystem;
import com.example.game.game.GameLevelController;
import com.example.game.stage.Stage;

public class GameSystem {
    private EnemySpawnSystem enemySpawnSystem = null;
    private GameLevelController gameLevelController = null;
    private GameScorer gameScorer = null;

    public GameSystem(float enemySpawnRate) {
        this.gameLevelController = new GameLevelController();
        this.enemySpawnSystem = new EnemySpawnSystem(enemySpawnRate);
        this.gameScorer = new GameScorer();
    }

    public GameScorer getGameScorer() {
        return this.gameScorer;
    }

    public void update(float deltaTime,
                       Stage stage,
                       ActorContainer actorContainer,
                       ActorFactory actorFactory) {
        this.gameLevelController.update(stage, this.enemySpawnSystem);
        this.enemySpawnSystem.update(deltaTime, actorFactory, actorContainer);
    }

}