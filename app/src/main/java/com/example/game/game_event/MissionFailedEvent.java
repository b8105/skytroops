package com.example.game.game_event;

import com.example.game.render.RenderCommandQueue;
import com.example.game.utility.StopWatch;

public class MissionFailedEvent extends GameEvent {
    private StopWatch existTimer;

    public MissionFailedEvent(float time) {
        existTimer = new StopWatch(time);
    }

    public void initialize() {
    }

    @Override
    public boolean update(float deltaIime) {
        if (existTimer.tick(deltaIime)) {
            return true;
        } // if
        return false;
    }
    @Override
    public void draw(RenderCommandQueue out) {
    }
}