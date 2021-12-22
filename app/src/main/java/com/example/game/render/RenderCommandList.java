package com.example.game.render;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.game.common.Transform2D;
import com.example.game.render.command.RenderCircleCommand;
import com.example.game.render.command.RenderCommand;
import com.example.game.render.command.RenderLineRectangleCommand;
import com.example.game.render.command.RenderRectangleCommand;
import com.example.game.render.command.RenderSpriteCommand;
import com.example.game.render.command.RenderTextCommand;
import com.example.game.render.info.RenderRectangleInfo;
import com.example.game.render.info.RenderSpriteInfo;
import com.example.game.common.shape.Circle;
import com.example.game.common.shape.Rectangle;

import java.util.LinkedList;
import java.util.List;

public class RenderCommandList {
    List<RenderCommand> commands = new LinkedList<RenderCommand>();

    public RenderCommandList() {
    }

    public void reset() {
        this.commands.clear();
    }

    public boolean isEmpty() {
        return this.commands.isEmpty();
    }

    boolean executeCommand(Canvas canvas) {
        for (RenderCommand command : commands) {
            command.execute(canvas);
        } // for
        return true;
    }

    public void drawFillCircle(Circle circle, Paint paint) {
        commands.add(new RenderCircleCommand(circle, paint));
    }

    public void drawFillCircle(float positionX, float positionY, float radius, Paint paint) {
        commands.add(new RenderCircleCommand(
                positionX,
                positionY,
                radius,
                paint));
    }

    public void drawLineRectangle(Rectangle rectangle, RenderRectangleInfo info) {
        commands.add(new RenderLineRectangleCommand(
                rectangle,
                info));
    }

    public void drawRectangle(Rectangle rectangle, RenderRectangleInfo info) {
        commands.add(new RenderRectangleCommand(
                rectangle,
                info));
    }

    public void drawRectangle(Rect rectangle, RenderRectangleInfo info) {
        commands.add(new RenderRectangleCommand(
                rectangle,
                info));
    }

    public void drawSprite(Bitmap bitmap, Transform2D transform, RenderSpriteInfo info) {
        commands.add(new RenderSpriteCommand(bitmap, transform, info));
    }

    public void drawText(String text, Transform2D transform, Paint paint) {
        commands.add(new RenderTextCommand(text, transform, paint));
    }
}