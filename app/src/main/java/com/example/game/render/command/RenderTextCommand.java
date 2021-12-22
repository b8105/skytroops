package com.example.game.render.command;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.game.common.Transform2D;
import com.example.game.render.command.RenderCommand;

public class RenderTextCommand extends RenderCommand {
    private String text;
    private Transform2D transform;
    private Paint paint;

    public RenderTextCommand(String text, Transform2D transform, Paint paint) {
        this.text = text;
        this.transform = transform;
        this.paint = new Paint(paint);
    }

    public void execute(Canvas canvas) {
        canvas.drawText(
                this.text,
                this.transform.position.x,
                this.transform.position.y,
                this.paint);
    }
}