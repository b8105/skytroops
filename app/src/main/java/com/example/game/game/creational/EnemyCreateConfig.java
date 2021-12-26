package com.example.game.game.creational;

import android.graphics.PointF;

public class EnemyCreateConfig {
    public int commanderId = -1;
    public PointF commanderTranslation = new PointF();

    public EnemyCreateConfig() {
    }
    public EnemyCreateConfig(int commanderId) {
        this.commanderId = commanderId;
    }
    public EnemyCreateConfig(int commanderId,
                             PointF commanderTranslation) {
        this.commanderId = commanderId;
        this.commanderTranslation = commanderTranslation;
    }

}