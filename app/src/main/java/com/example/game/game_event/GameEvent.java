package com.example.game.game_event;

import com.example.game.render.RenderCommandQueue;

public abstract class GameEvent {
    public GameEvent() {
    }
    public void initialize() {
    }
    public abstract boolean update(float deltaTime);
    public abstract void draw(RenderCommandQueue out);
}