package com.example.game.render.info;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class RenderRectangleInfo {
    public int paintFlag = Paint.ANTI_ALIAS_FLAG;
    public int color = Color.GREEN;
    public boolean changeAlpha = false;
    public int alpha = 255;

    public RenderRectangleInfo() {
    }

    public RenderRectangleInfo(int color) {
        this.color = color;
    }

    public RenderRectangleInfo(int color, int alpha) {
        this.alpha = alpha;
        this.color = color;
        this.changeAlpha = true;
    }
}