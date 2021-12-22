package com.example.game.render.command;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.game.common.shape.Circle;

public class RenderCircleCommand extends RenderCommand {
    private Circle circle;
    private Paint paint;

    public RenderCircleCommand(Circle circle, Paint paint) {
        this.circle = new Circle();
        this.circle.position.x = circle.position.x;
        this.circle.position.y = circle.position.y;
        this.circle.radius = circle.radius;
        this.paint = new Paint(paint);
    }

    public RenderCircleCommand(float positionX, float positionY, float radius, Paint paint) {
        this.circle = new Circle(positionX, positionY, radius);
        this.paint = new Paint();
        this.paint.setColor(paint.getColor());
    }

    public void execute(Canvas canvas) {
        canvas.drawCircle(
                this.circle.position.x,
                this.circle.position.y,
                this.circle.radius,
                this.paint);
    }
}