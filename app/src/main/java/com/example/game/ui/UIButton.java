package com.example.game.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;

import com.example.game.collision.detector.RectangleCollisionDetector;
import com.example.game.common.Transform2D;
import com.example.game.common.shape.Circle;
import com.example.game.common.shape.Rectangle;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderRectangleInfo;
import com.example.game.render.info.RenderSpriteInfo;

public class UIButton extends UILabel{
    public UIButton(
            ImageResource imageResource,
            Resources resources,int id,
            PointF position, Point size) {
        super(imageResource,resources, id,
         position, size);
    }

    public UIButton(ImageResource imageResource,
                   ImageResourceType imageResourceType,
                   PointF position) {
        super(imageResource, imageResourceType,position);
    }

    public void onTouch(){}

    public boolean containCircle(Circle circle){
        RectangleCollisionDetector detector = new RectangleCollisionDetector();
        if (detector.CollisionCircle(this.getRectangle(),circle)) {
            return true;
        } // if
        return false;
    }
}