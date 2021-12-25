package com.example.game.parameter;

import com.example.game.utility.StopWatch;

public class ShotInterval {
    private float time = 0.16f;
    private StopWatch shotTime = null;

    public ShotInterval() {
        this.shotTime = new StopWatch(this.time);
    }

    public void resetInterval() {
        this.shotTime.reset(this.time);
    }

    public void resetInterval(float time) {
        this.time = time;
        this.shotTime.reset(this.time);
    }

    public boolean update(float deltaTime) {
        if (this.shotTime.tick(deltaTime)) {
            return true;
        } // if
        return false;
    }
}