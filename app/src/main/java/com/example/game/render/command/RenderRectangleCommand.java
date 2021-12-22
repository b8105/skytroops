package com.example.game.render.command;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.game.render.info.RenderRectangleInfo;
import com.example.game.common.shape.Rectangle;

public class RenderRectangleCommand extends RenderCommand {
    private Rect rectangle;
    private Paint paint;

    public RenderRectangleCommand(Rectangle rectangle, RenderRectangleInfo info) {
        this.rectangle = new Rect();
        this.rectangle.set(
                (int) rectangle.left,
                (int) rectangle.top,
                (int) rectangle.right,
                (int) rectangle.bottom);
        this.paint = new Paint(info.paintFlag);
        this.paint.setColor(info.color);
        if (info.changeAlpha) {
            this.paint.setAlpha(info.alpha);
        } // if
    }

    public RenderRectangleCommand(Rect rectangle, RenderRectangleInfo info) {
        this.rectangle = new Rect();
        this.rectangle.set(
                (int) rectangle.left,
                (int) rectangle.top,
                (int) rectangle.right,
                (int) rectangle.bottom);
        this.paint = new Paint(info.paintFlag);
        this.paint.setColor(info.color);
    }

    public void execute(Canvas canvas) {
        canvas.drawRect(
                this.rectangle,
                this.paint);
    }
}