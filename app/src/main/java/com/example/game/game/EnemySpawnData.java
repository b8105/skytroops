package com.example.game.game;

import com.example.game.actor.enemy_plane.EnemyPlaneType;

public class EnemySpawnData {
        public EnemyPlaneType type;
        public float time;
        public float positionX;

        public  EnemySpawnData(EnemyPlaneType type,
                         float time, float positionX) {
            this.type = type;
            this.time = time;
            this.positionX = positionX;
        }

}
