package com.example.game.render.info;

import android.graphics.Paint;
import android.graphics.Rect;

import com.example.game.common.shape.Rectangle;

public class RenderSpriteInfo {
    public Rect rect = null;
    public int paintFlag = Paint.ANTI_ALIAS_FLAG;
    public boolean changeAlpha = false;
    int alpha = 255;
    public boolean center = false;


    public RenderSpriteInfo() {
    }

    public RenderSpriteInfo(Rect rect) {
        this.rect = rect;
    }

    public RenderSpriteInfo(Rectangle rect) {
        this.rect = new Rect();
        this.rect.set(
                (int) rect.left,
                (int) rect.top,
                (int) rect.right,
                (int) rect.bottom);
    }

    public RenderSpriteInfo(int alpha) {
        this.changeAlpha = true;
        this.alpha = alpha;
    }
}