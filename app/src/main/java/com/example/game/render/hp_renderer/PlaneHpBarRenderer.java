package com.example.game.render.hp_renderer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.parameter.HpParameter;
import com.example.game.render.RenderCommandQueue;

public class PlaneHpBarRenderer {
    protected Bitmap constructBitmap(
            ImageResource imsgeResources, ImageResourceType imageResourceType) {
        Bitmap bitmap = imsgeResources.getImageResource(imageResourceType);
        //BitmapFactory.decodeResource(resources, id);
        //bitmap = Bitmap.createScaledBitmap(
        //      bitmap, size.x, size.y,
        //      false);
        return bitmap;
    }

    public Point getSize(){
        return new Point();
    }
    public Point getHalfSize(){
        Point size = this.getSize();
        return new Point(
                (int)(size.x * 0.5f),
                (int)(size.y * 0.5f)
        );
    }
    public void execute(HpParameter hpParameter, RenderCommandQueue out) {

    }
}