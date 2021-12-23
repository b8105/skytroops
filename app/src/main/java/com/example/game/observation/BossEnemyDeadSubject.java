package com.example.game.observation;


import java.util.ArrayList;
import java.util.List;

public class BossEnemyDeadSubject {
    private List<BossEnemyDeadListener> observers = new ArrayList<BossEnemyDeadListener>();

    public void addObserver(BossEnemyDeadListener bossEnemyDeadListener) {
        this.observers.add(bossEnemyDeadListener);
    }
    public void removeObserver(BossEnemyDeadListener bossEnemyDeadListener) {
        this.observers.remove(bossEnemyDeadListener);
    }
    public void notify(BossEnemyDeadMessage message) {
        for(BossEnemyDeadListener listener : this.observers) {
            listener.onNotify(message);
        } // for
    }
    public void clear() {
        observers.clear();
    }
}