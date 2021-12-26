package com.example.game.scene.transition_action;

import android.graphics.Point;

import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.utility.StopWatch;

public abstract class TransitionAction {
    private StopWatch time = null;
    private float timeCoefficient = 0.0f;
    private Game game;

    TransitionAction(float time, Game game) {
        this.time = new StopWatch(time);
        this.timeCoefficient = 2.0f;
        this.game = game;
    }

    protected void sceneTransition() {
        this.game.IncremenntSceneNo();
    }

    protected float getTimeCoefficient() {
        return this.timeCoefficient;
    }

    protected Point getScreenSize() {
        return new Point(
                this.game.getDefaultDisplayRealSize().x,
                this.game.getDefaultDisplayRealSize().y
        );
    }

    public StopWatch getTime() {
        return this.time;
    }

    public abstract TransitionActionResult execute(float deltaTime);

    public abstract void drawFadeTransition(RenderCommandQueue out);
}
