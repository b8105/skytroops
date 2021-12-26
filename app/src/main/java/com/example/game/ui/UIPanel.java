package com.example.game.ui;

import com.example.game.common.InputEvent;
import com.example.game.render.RenderCommandQueue;

public interface UIPanel {
    public void input(InputEvent input);

    public void update(float deltaTime);

    public void draw(RenderCommandQueue out);
}