package com.example.game.game;

import com.example.game.actor.ActorTagString;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.utility.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class EnemySpawnSystem {
    private List<EnemySpawnWave> waves = null;
    private int currentWaveIndex = 0;
    private boolean active = true;

    public EnemySpawnSystem(float rate) {
        this.waves = new ArrayList<EnemySpawnWave>();
        EnemySpawnWave wave = new EnemySpawnWave(4.0f, 3);

        this.waves.add(wave);
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean update(float deltaTime, ActorFactory actorFactory) {
        if(this.currentWaveIndex != this.waves.size()){
            EnemySpawnWave wave = this.waves.get(this.currentWaveIndex);
            if(wave.update(deltaTime, actorFactory)){
                this.currentWaveIndex++;
            } // if
        } // if
        else {
            this.active = false;
            actorFactory.createEnemy(400, 500, ActorTagString.enemy, EnemyPlaneType.Boss);
            return true;
        } // else
        return false;
    }
}