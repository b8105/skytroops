package com.example.game.ui;

import com.example.game.common.InputEvent;
import com.example.game.render.RenderCommandQueue;

//! サブクラスがUI基本オブジェクトのコンテナの役目を果たします
public interface UIPanel {
    public void input(InputEvent input);

    public void update(float deltaTime);

    public void draw(RenderCommandQueue out);
}