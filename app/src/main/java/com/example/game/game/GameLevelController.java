package com.example.game.game;

import com.example.game.game.EnemySpawnSystem;
import com.example.game.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameLevelController {
    private int gameLevel = 0;
    private List<Float> enemySpawnRateTimes = new ArrayList<>();
    private List<Float> stageScrollThreshold = new ArrayList<>();

    public GameLevelController() {
        enemySpawnRateTimes.add(3.0f);
        enemySpawnRateTimes.add(1.5f);

        stageScrollThreshold.add(2000.0f);
        stageScrollThreshold.add(4000.0f);
    }

    public void update(
            Stage stage, EnemySpawnSystem enemySpawnSystem
    ) {
        if (gameLevel < this.stageScrollThreshold.size() - 1) {
            float scrollThreshold = this.stageScrollThreshold.get(gameLevel);
            float scroll = stage.getScroll();
            if (scroll > scrollThreshold) {
                gameLevel++;
//                enemySpawnSystem.resetRate(
  //                      this.enemySpawnRateTimes.get(gameLevel)
    //            );
            } // if
        } // if
    }
}