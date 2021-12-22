package com.example.game.parameter;

import com.example.game.utility.StopWatch;

public class InvincibleParameter {
    private boolean active = false;
    private float time;
    private StopWatch invincibleTimer;

    public InvincibleParameter() {
        this.active = false;
        this.time = 1.0f;
        this.invincibleTimer = new StopWatch(this.time );
    }
    public void setInvincibleTime(float time){
        this.invincibleTimer.reset(time);
    }

    public boolean isActive() {
        return this.active;
    }

    public void activate() {
        if (this.isActive()) {
            return;
        } // if
        this.active = true;
        this.invincibleTimer.reset(this.time);
    }
    public void inactivate() {
         this.active = false;
    }

    public void update(float deltaTime) {
        if (this.invincibleTimer.tick(deltaTime)) {
            this.inactivate();
            this.invincibleTimer.reset();
        } // if
    }
}