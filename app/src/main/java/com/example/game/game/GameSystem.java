package com.example.game.game;

import com.example.game.game.creational.ActorFactory;
import com.example.game.game.enemy_spawn.EnemySpawnSystem;
import com.example.game.stage.Stage;
import com.example.game.stage.StageType;

public class GameSystem {
    private EnemySpawnSystem enemySpawnSystem = null;
    private GameScorer gameScorer = null;

    public GameSystem() {
        this.enemySpawnSystem = new EnemySpawnSystem(StageType.Type01);
        this.gameScorer = new GameScorer();
    }

    public GameScorer getGameScorer() {
        return this.gameScorer;
    }

    public EnemySpawnSystem getEnemySpawnSystem() {
        return this.enemySpawnSystem;
    }

    public void resetSpawnSystem(StageType stageType) {
        this.enemySpawnSystem = new EnemySpawnSystem(stageType);
    }

    public void update(float deltaTime,
                       ActorContainer actorContainer,
                       ActorFactory actorFactory,
                       StageType stageType) {

        if (this.enemySpawnSystem.isActive()) {
            if (!this.enemySpawnSystem.isBossExist()) {
                this.enemySpawnSystem.update(deltaTime, actorFactory, stageType);
            } // if
            else {
                if (actorContainer.getBossEnemy() != null) {
                    this.enemySpawnSystem.updateExistBoss(deltaTime, actorFactory, stageType);
                } // if
            } // else
        } // if
    }
}