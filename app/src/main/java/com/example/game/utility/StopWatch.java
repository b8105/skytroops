package com.example.game.utility;


//! 指定時に到達しても止まることなくなり続ける(trueを返し続ける)
//! float型の時間を管理するクラスです
//! 主にゲームイベントの生存期間,武器の発射インターバルに使っています
public class StopWatch {
    private float timeMax;
    private float time;

    public StopWatch(float max) {
        this.timeMax = max;
        this.time = 0.0f;
    }

    public float getTime() {
        return this.time;
    }

    public float getTimeMax() {
        return this.timeMax;
    }

    public float getDevidedTime() {
        return this.time / this.timeMax;
    }

    public void reset() {
        this.time = 0.0f;
    }

    public void reset(float max) {
        this.time = 0.0f;
        this.timeMax = max;
    }

    public boolean tick(float deltaTime) {
        this.time += deltaTime;

        if (this.time >= this.timeMax) {
            this.time = this.timeMax;
            return true;
        } // if
        return false;
    }
}
