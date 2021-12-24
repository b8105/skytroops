package com.example.game.game;

import com.example.game.actor.ActorTagString;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.stage.StageType;
import com.example.game.utility.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class EnemySpawnSystem {
    private List<EnemySpawnWave> waves = null;
    private int currentWaveIndex = 0;
    private boolean active = true;

    public EnemySpawnSystem(StageType type) {
        this.waves = new ArrayList<EnemySpawnWave>();

        List<EnemySpawnData> spawnDataList = new ArrayList<>();
        spawnDataList.add(new EnemySpawnData(EnemyPlaneType.Strong,
                0.0f, 200.0f));
        spawnDataList.add(new EnemySpawnData(EnemyPlaneType.Basic,
                1.0f, 300.0f));
        spawnDataList.add(new EnemySpawnData(EnemyPlaneType.Strong,
                3.0f, 600.0f));

        switch (type) {
            case Type01:
                this.waves.add(new EnemySpawnWave(EnemySpawnWaveType.B, 4.0f, spawnDataList, 2));
                break;
            case Type02:
                this.waves.add(new EnemySpawnWave(EnemySpawnWaveType.Dummy, 2.0f, 1));
                this.waves.add(new EnemySpawnWave(EnemySpawnWaveType.A, 2.0f, 2));
                this.waves.add(new EnemySpawnWave(EnemySpawnWaveType.B, 2.0f, 2));
                this.waves.add(new EnemySpawnWave(EnemySpawnWaveType.A, 2.0f, 2));
                break;
        } // switch
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean update(float deltaTime, ActorFactory actorFactory,
                          StageType type) {
        if (this.currentWaveIndex != this.waves.size()) {
            EnemySpawnWave wave = this.waves.get(this.currentWaveIndex);
            if (wave.update(deltaTime, actorFactory)) {
                this.currentWaveIndex++;
            } // if
        } // if
        else {
            this.active = false;

            switch (type) {
                case Type01:
                    actorFactory.createEnemy(300, 0 - BitmapSizeStatic.boss.y, ActorTagString.enemy, EnemyPlaneType.Boss);
                    break;
                case Type02:
                    actorFactory.createEnemy(300, 0 - BitmapSizeStatic.boss.y, ActorTagString.enemy, EnemyPlaneType.Boss2);
                    break;
            } // switch
            return true;
        } // else
        return false;
    }
}