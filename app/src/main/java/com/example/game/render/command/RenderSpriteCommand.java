package com.example.game.render.command;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.game.common.Transform2D;
import com.example.game.render.info.RenderSpriteInfo;


public class RenderSpriteCommand extends RenderCommand {
    private Bitmap bitmap = null;
    private Transform2D transform = null;
    private Paint paint = null;
    private RenderSpriteInfo info = null;

    public RenderSpriteCommand(Bitmap bitmap, final Transform2D transform, RenderSpriteInfo info) {
        this.bitmap = bitmap;
        this.transform = transform;
        this.paint = new Paint(info.paintFlag);
        this.info = info;
    }

    public void execute(Canvas canvas) {
        int destPosX = (int) transform.position.x;
        int destPosY = (int) transform.position.y;
        int sizeX = this.bitmap.getWidth();
        int sizeY = this.bitmap.getHeight();
        int halfSizeX = (int) (sizeX * 0.5f);
        int halfSizeY = (int) (sizeY * 0.5f);


        Rect source = new Rect(0, 0, sizeX, sizeY);
        if (this.info.rect != null) {
            sizeX = this.info.rect.width();
            sizeY = this.info.rect.height();
            source = this.info.rect;
        } // if

        // draw point is center center
        Rect dest = new Rect(destPosX, destPosY, destPosX + sizeX, destPosY + sizeY);
        if (info.center) {
            dest = new Rect(
                    destPosX - halfSizeX,
                    destPosY - halfSizeY,
                    destPosX + halfSizeX,
                    destPosY + halfSizeY);

        } // if

        if (Math.abs(this.transform.rotation) > 0.0005f) {
            canvas.save();
            canvas.rotate(this.transform.rotation,
                    destPosX + sizeX * 0.5f,
                    destPosY + sizeY * 0.5f);
        } // if
        canvas.drawBitmap(
                this.bitmap,
                source,
                dest,
                this.paint);
        if (Math.abs(this.transform.rotation) > 0.0005f) {
            canvas.restore();
        } // if
    }
}