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
    private BossEnemySpawner bossEnemySpawner = null;

    List<EnemySpawnData> spawnDataProto = new ArrayList<>();
    List<EnemySpawnData> spawnDataProtoA = new ArrayList<>();
    List<EnemySpawnData> spawnDataProtoB = new ArrayList<>();
    List<EnemySpawnData> spawnDataProtoC = new ArrayList<>();

    private void constructProtoType() {
        {
            spawnDataProtoA.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    0.0f, 0.0f));
            spawnDataProtoA.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    0.0f, 200.0f));
            spawnDataProtoA.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    0.0f, 400.0f));
            spawnDataProtoA.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    0.0f, 600.0f));
            spawnDataProtoA.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    0.0f, 800.0f));
        }
        {
            for(EnemySpawnData spawn : this.spawnDataProtoA ){
                spawnDataProtoC.add(spawn);
            } // for

            spawnDataProtoB.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 120.0f));
            spawnDataProtoB.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 400.0f));
            spawnDataProtoB.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 680.0f));
        }
        {
            for(EnemySpawnData spawn : this.spawnDataProtoA ){
                spawnDataProtoC.add(spawn);
            } // for
            spawnDataProtoC.add(new EnemySpawnData(EnemyPlaneType.Strong,
                    0.6f, 0.0f));
            spawnDataProtoC.add(new EnemySpawnData(EnemyPlaneType.Strong,
                    1.2f, 0.0f));
            spawnDataProtoC.add(new EnemySpawnData(EnemyPlaneType.Strong,
                    1.8f, 0.0f));
        }
    }

    public EnemySpawnSystem(StageType type) {
        this.bossEnemySpawner = new BossEnemySpawner();
        this.waves = new ArrayList<EnemySpawnWave>();
        this.constructProtoType();
        switch (type) {
            case Type01:
//                this.constructStage01();
                break;
            case Type02:
//                this.constructStage02();
                break;
            case Type03:
                this.constructStage03();
                break;
            case Type04:
                this.constructStage04();
                break;
            case Type05:
                this.constructStage05();
                break;
        } // switch
    }

    private void constructStage01() {
        this.waves.add(new EnemySpawnWave(1.0f, spawnDataProto, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 5));
    }

    private void constructStage02() {
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
    }

    private void constructStage03() {
        this.waves.add(new EnemySpawnWave(1.0f, spawnDataProto, 0));

        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));

    }

    private void constructStage04() {
//        this.waves.add(new EnemySpawnWave(1.0f, spawnDataProto, 0));
//        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 2));
    }
    private void constructStage05() {
//        this.waves.add(new EnemySpawnWave(1.0f, spawnDataProto, 0));
//        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 2));
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean update(float deltaTime, ActorFactory actorFactory,
                          StageType type) {
        if (this.currentWaveIndex != this.waves.size()) {
            EnemySpawnWave wave = this.waves.get(this.currentWaveIndex);
            if (wave.update(deltaTime, actorFactory,type)) {
                this.currentWaveIndex++;
            } // if
        } // if
        else {
            this.active = false;
            this.bossEnemySpawner.execute(type, actorFactory);
            return true;
        } // else
        return false;
    }
}