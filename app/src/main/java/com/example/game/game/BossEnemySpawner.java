package com.example.game.game;

import com.example.game.actor.ActorTagString;
import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.stage.StageType;


//! 小さなコードなので派生させずswitchで処理分けします
public class BossEnemySpawner {
    public void spawn(StageType stageType, ActorFactory actorFactory) {
        this.execute(stageType, actorFactory);
    }
    public void execute(StageType stageType, ActorFactory actorFactory){
        switch (stageType) {
            case Type01:
                actorFactory.createEnemy(300,
                        0 - BitmapSizeStatic.boss.y,
                        ActorTagString.enemy, EnemyPlaneType.Stage01Boss,stageType);
                break;
            case Type02:
                actorFactory.createEnemy(300,
                        0 - BitmapSizeStatic.boss.y,
                        ActorTagString.enemy, EnemyPlaneType.Stage02Boss,stageType);
                break;
            case Type03:
                actorFactory.createEnemy(300,
                        0 - BitmapSizeStatic.boss.y,
                        ActorTagString.enemy, EnemyPlaneType.Stage03Boss,stageType);
                break;
            case Type04:
                actorFactory.createEnemy(300,
                        0 - BitmapSizeStatic.boss.y,
                        ActorTagString.enemy, EnemyPlaneType.Stage04Boss,stageType);
                break;
            case Type05:
                actorFactory.createEnemy(300,
                        0 - BitmapSizeStatic.boss.y,
                        ActorTagString.enemy, EnemyPlaneType.Stage05Boss,stageType);
                break;
        } // switch
    }
}