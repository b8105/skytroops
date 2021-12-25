package com.example.game.game.enemy_spawn;

import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.game.creational.EnemyCreateConfig;

public class EnemySpawnData {
        public EnemyPlaneType type;
        public float time;
        public float positionX;
        public EnemyCreateConfig config;

    public EnemySpawnData(EnemyPlaneType type,
                          float time, float positionX) {
        this.type = type;
        this.time = time;
        this.positionX = positionX;
        this.config = new EnemyCreateConfig();
    }
    public EnemySpawnData(EnemyPlaneType type,
                          float time, float positionX, EnemyCreateConfig config) {
        this.type = type;
        this.time = time;
        this.positionX = positionX;
        this.config = config;
    }

}
