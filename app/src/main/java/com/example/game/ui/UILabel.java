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

//! UI基本オブジェクト
public class UILabel {
    private Transform2D transform;
    private Bitmap bitmap;
    private Point bitmapSize;

    public UILabel(ImageResource imageResource,
                   Resources resources,int id,
                   PointF position, Point size) {
        this.transform = new Transform2D();
        this.transform.position.x = position.x;
        this.transform.position.y = position.y;
        this.bitmapSize = new Point(size.x, size.y);
        this.bitmap = BitmapFactory.decodeResource(
                resources, id);
        this.bitmap = Bitmap.createScaledBitmap(
                this.bitmap,
                this.bitmapSize.x,
                this.bitmapSize.y,
                false);
    }

    public UILabel(ImageResource imageResource,
                   ImageResourceType imageResourceType,
                   PointF position) {
        this.transform = new Transform2D();
        this.transform.position.x = position.x;
        this.transform.position.y = position.y;

        this.bitmap = imageResource.getImageResource(imageResourceType);
    }

    public void setPosition(PointF position) {
        this.transform.position.x = position.x;
        this.transform.position.y = position.y;
    }
    public void setPositionX(float x) {
        this.transform.position.x = x;
    }
    public void setPositionY(float y) {
        this.transform.position.y = y;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public PointF getPosition(){
        return  new PointF(this.transform.position.x, this.transform.position.y);
    }
    public Rectangle getRectangle() {
        Transform2D transform = new Transform2D(this.transform);
        transform.position.x -= this.bitmap.getWidth() * 0.5f;
        transform.position.y -= this.bitmap.getHeight() * 0.5f;

        Rectangle rect = new Rectangle(
                transform.position.x,
                transform.position.y,
                transform.position.x + this.bitmap.getWidth(),
                transform.position.y + this.bitmap.getHeight()
        );
        return rect;
    }

    public void draw(RenderCommandQueue out){
        RenderCommandList list = out.getRenderCommandList(
                RenderLayerType.UI);

        Transform2D transform = new Transform2D(this.transform);
        transform.position.x -= this.bitmap.getWidth() * 0.5f;
        transform.position.y -= this.bitmap.getHeight() * 0.5f;

        list.drawSprite(
                this.bitmap,
                transform,
                new RenderSpriteInfo());
    }
    public void draw(RenderCommandList list){
        Transform2D transform = new Transform2D(this.transform);
        transform.position.x -= this.bitmap.getWidth() * 0.5f;
        transform.position.y -= this.bitmap.getHeight() * 0.5f;

        list.drawSprite(
                this.bitmap,
                transform,
                new RenderSpriteInfo());
    }
}