package com.example.game.render.command;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.game.render.info.RenderRectangleInfo;
import com.example.game.common.shape.Rectangle;

public class RenderLineRectangleCommand extends RenderCommand {
    private Rect rectangle;
    private Paint paint;

    public RenderLineRectangleCommand(Rectangle rectangle, RenderRectangleInfo info) {
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

    public RenderLineRectangleCommand(Rect rectangle, RenderRectangleInfo info) {
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
        float[] points = {
                this.rectangle.left, this.rectangle.top,
                this.rectangle.left, this.rectangle.bottom,
                this.rectangle.right, this.rectangle.bottom,
                this.rectangle.right, this.rectangle.top,
        };
        canvas.drawLines(points, paint);
    }
}