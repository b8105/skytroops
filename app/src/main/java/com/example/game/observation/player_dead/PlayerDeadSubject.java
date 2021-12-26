package com.example.game.observation.player_dead;

import com.example.game.observation.boss_enemy_dead.BossEnemyDeadListener;
import com.example.game.observation.boss_enemy_dead.BossEnemyDeadMessage;

import java.util.ArrayList;
import java.util.List;

public class PlayerDeadSubject {
    private List<PlayerDeadListener> observers = new ArrayList<PlayerDeadListener>();

    public void addObserver(PlayerDeadListener playerDeadListener) {
        this.observers.add(playerDeadListener);
    }
    public void removeObserver(PlayerDeadListener playerDeadListener) {
        this.observers.remove(playerDeadListener);
    }
    public void notify(PlayerDeadMessage message) {
        for(PlayerDeadListener listener : this.observers) {
            listener.onNotify(message);
        } // for
    }
    public void clear() {
        observers.clear();
    }
}