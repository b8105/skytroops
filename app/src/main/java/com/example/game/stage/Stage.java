package com.example.game.stage;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.game.R;
import com.example.game.collision.CollisionLayer;
import com.example.game.collision.collision_component.StageCollisionComponent;
import com.example.game.common.Transform2D;

public class Stage {
    private Transform2D transform = null;
    private Bitmap background = null;
    private StageRenderer renderer = null;
    private float scrollSpeed = 0.0f;

    StageCollisionComponent component;

    public Stage(Point screenSize, Resources resources, CollisionLayer layer) {
        this.constructTransform(screenSize);
        this.constructBackground(resources);
        this.renderer = new StageRenderer(this);
        this.scrollSpeed = 10;

        component = new StageCollisionComponent(layer);
        component.setScreenSize(screenSize);
    }

    private void constructTransform(Point screenSize) {
        this.transform = new Transform2D();
        transform.scale.x = screenSize.x;
        transform.scale.y = screenSize.y;
    }

    private void constructBackground(Resources resources) {
        this.background = BitmapFactory.decodeResource(resources, R.drawable.background02);
        this.background = Bitmap.createScaledBitmap(
                background,
                (int) this.transform.scale.x,
                (int) this.transform.scale.y,
                false);
    }

    public void setScrollSpeed(float scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

    public Bitmap getBitmap() {
        assert (this.background != null);
        return this.background;
    }

    private Transform2D getTransform() {
        assert (this.transform != null);
        return this.transform;
    }

    public float getScrollSpeed() {
        return this.scrollSpeed;
    }

    public float getScroll() {
        return this.getTransform().position.y;
    }

    public StageRenderer getRenderer() {
        assert (this.renderer != null);
        return this.renderer;
    }

    public void update() {
        transform.position.y += this.scrollSpeed;
    }
}