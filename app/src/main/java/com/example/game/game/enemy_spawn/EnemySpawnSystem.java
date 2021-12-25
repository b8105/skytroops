package com.example.game.game.enemy_spawn;

import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.game.creational.ActorFactory;
import com.example.game.game.creational.BossEnemySpawner;
import com.example.game.stage.StageType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnemySpawnSystem {
    private List<EnemySpawnWave> waves = null;
    private int currentWaveIndex = 0;
    private boolean active = true;
    private BossEnemySpawner bossEnemySpawner = null;

    List<EnemySpawnData> spawnDataProto = new ArrayList<>();
    List<EnemySpawnData> spawnDataProtoA = new ArrayList<>();
    List<EnemySpawnData> spawnDataProtoB1 = new ArrayList<>();
    List<EnemySpawnData> spawnDataProtoB2 = new ArrayList<>();
    List<EnemySpawnData> spawnDataProtoB3 = new ArrayList<>();
    List<EnemySpawnData> spawnDataProtoB = new ArrayList<>();
    List<EnemySpawnData> spawnDataProtoC = new ArrayList<>();
    List<EnemySpawnData> spawnDataProtoD = new ArrayList<>();

    List<EnemySpawnData> spawnDataProtoE = new ArrayList<>();

    HashMap<StageType, List<EnemySpawnWave>> bossWave = new HashMap<>();

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
            for (EnemySpawnData spawn : this.spawnDataProtoA) {
                spawnDataProtoC.add(spawn);
            } // for

            spawnDataProtoB1.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 400.0f));

            spawnDataProtoB2.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 120.0f));
            spawnDataProtoB2.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 680.0f));


            spawnDataProtoB3.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 120.0f));
            spawnDataProtoB3.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 400.0f));
            spawnDataProtoB3.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 680.0f));


            spawnDataProtoB.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 120.0f));
            spawnDataProtoB.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 400.0f));
            spawnDataProtoB.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    1.0f, 680.0f));
        }
        {
            for (EnemySpawnData spawn : this.spawnDataProtoA) {
                spawnDataProtoC.add(spawn);
            } // for
            spawnDataProtoC.add(new EnemySpawnData(EnemyPlaneType.Strong,
                    0.6f, 0.0f));
            spawnDataProtoC.add(new EnemySpawnData(EnemyPlaneType.Strong,
                    1.2f, 0.0f));
            spawnDataProtoC.add(new EnemySpawnData(EnemyPlaneType.Strong,
                    1.8f, 0.0f));
        }


        {
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    0.0f, 0.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    0.0f, 200.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    0.0f, 400.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    0.0f, 600.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    0.0f, 800.0f));


            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    3.0f, 0.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    3.0f, 200.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    3.0f, 400.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    3.0f, 600.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    3.0f, 800.0f));


            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Strong,
                    4.6f, 0.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Strong,
                    4.2f, 0.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Strong,
                    4.8f, 0.0f));


            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    7.0f, 120.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    7.0f, 400.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Weak,
                    7.0f, 680.0f));

            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    9.0f, 0.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    9.0f, 200.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    9.0f, 400.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    9.0f, 600.0f));
            spawnDataProtoD.add(new EnemySpawnData(EnemyPlaneType.Basic,
                    9.0f, 800.0f));
        }

        spawnDataProtoE.add(new EnemySpawnData(EnemyPlaneType.Commander,
                0.0f, 500.0f));
        spawnDataProtoE.add(new EnemySpawnData(EnemyPlaneType.Follow,
                0.0f, 600.0f));
    }

    public EnemySpawnSystem(StageType type) {
        this.bossEnemySpawner = new BossEnemySpawner();
        this.waves = new ArrayList<EnemySpawnWave>();
        this.constructProtoType();

        {
            this.bossWave.put(
                    StageType.Type04, new ArrayList<>()
            );
            this.bossWave.get(StageType.Type04).add(new EnemySpawnWave(2.0f, spawnDataProtoA, 1000));
        }
        {
            this.bossWave.put(
                    StageType.Type05, new ArrayList<>()
            );
            this.bossWave.get(StageType.Type05).add(new EnemySpawnWave(10.0f, spawnDataProtoD, 1000));
        }

        switch (type) {
            case Type01:
//                this.constructStage01();
                break;
            case Type02:
//                this.constructStage02();
               break;
            case Type03:
//                this.constructStage03();
                break;
            case Type04:
//                this.constructStage04();
               break;
            case Type05:
//               this.constructStage05();
                break;
        } // switch

        this.waves.add(new EnemySpawnWave(10.0f, spawnDataProtoE, 0));
    }

    private void constructStage01() {
        this.waves.add(new EnemySpawnWave(4.0f, spawnDataProto, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 5));
    }

    private void constructStage02() {
        this.waves.add(new EnemySpawnWave(1.0f, spawnDataProto, 0));

        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB1, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB2, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB1, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB2, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB3, 0));
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
        this.waves.add(new EnemySpawnWave(1.0f, spawnDataProto, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));

    }

    private void constructStage05() {
        this.waves.add(new EnemySpawnWave(1.0f, spawnDataProto, 0));

        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 4));


        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoC, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoB, 0));
        this.waves.add(new EnemySpawnWave(2.0f, spawnDataProtoA, 0));
    }

    public boolean isActive() {
        return this.active;
    }

    public void updateExistBoss(float deltaTime, ActorFactory actorFactory, StageType type) {
        List<EnemySpawnWave> current = this.bossWave.get(type);
        if (current != null) {

            if (this.currentWaveIndex != current.size()) {
                EnemySpawnWave wave = current.get(this.currentWaveIndex);
                if (wave.update(deltaTime, actorFactory, type)) {
                    this.currentWaveIndex++;
                } // if
            } // if

        } // if

    }

    public boolean update(float deltaTime, ActorFactory actorFactory,
                          StageType type) {
        if (this.currentWaveIndex != this.waves.size()) {
            EnemySpawnWave wave = this.waves.get(this.currentWaveIndex);
            if (wave.update(deltaTime, actorFactory, type)) {
                this.currentWaveIndex++;
            } // if
        } // if
        else {
            this.active = false;
            this.currentWaveIndex = 0;
            this.bossEnemySpawner.execute(type, actorFactory);
            return true;
        } // else
        return false;
    }
}