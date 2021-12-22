package com.example.game.render;

import android.graphics.Canvas;

public interface Renderable {
    public void onRenderableDestroy();

    public void execute(RenderCommandQueue out);
}