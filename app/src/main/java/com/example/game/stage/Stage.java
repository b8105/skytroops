package com.example.game.stage;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.example.game.collision.CollisionLayer;
import com.example.game.collision.collision_component.StageCollisionComponent;
import com.example.game.common.Transform2D;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;

import java.util.HashMap;

public class Stage {
    private Transform2D transform = null;
    private Bitmap background = null;
    private StageRenderer renderer = null;
    private float scrollSpeed = 0.0f;
    private float defaultScrollSpeed = 10.0f;
    private float acceleScrollSpeed = 100.0f;

    StageCollisionComponent component;
    HashMap<StageType, Bitmap> stageTypeBitmapHashMap = null;
    StageType currentType = StageType.Type00;
    StageType prevType = StageType.Type00;

    public Stage(Point screenSize, ImageResource resources, CollisionLayer layer) {
        this.constructTransform(screenSize);
        this.renderer = new StageRenderer(this);
        this.scrollSpeed = 10;
        this.stageTypeBitmapHashMap = new HashMap<>();

        this.stageTypeBitmapHashMap.put(StageType.Type00, resources.getImageResource(ImageResourceType.StageBackground0));
        this.stageTypeBitmapHashMap.put(StageType.Type01, resources.getImageResource(ImageResourceType.StageBackground1));
        this.stageTypeBitmapHashMap.put(StageType.Type02, resources.getImageResource(ImageResourceType.StageBackground2));
        this.stageTypeBitmapHashMap.put(StageType.Type03, resources.getImageResource(ImageResourceType.StageBackground3));

        this.changeType(StageType.Type01);

        component = new StageCollisionComponent(layer);
        component.setScreenSize(screenSize);
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

    public float getDefaultScrollSpeed() {
        return this.defaultScrollSpeed;
    }

    public float getAcceleScrollSpeed() {
        return this.acceleScrollSpeed;
    }

    public StageRenderer getRenderer() {
        assert (this.renderer != null);
        return this.renderer;
    }

    public void changeType(StageType type) {
        this.background = this.stageTypeBitmapHashMap.get(type);
        this.prevType = currentType;
        this.currentType = type;
    }

    public void changeTransitionStage() {
        this.background = this.stageTypeBitmapHashMap.get(StageType.Type00);
        this.prevType = currentType;
        this.currentType = StageType.Type00;
    }

    public StageType getCurrentType() {
        return this.currentType;
    }

    public StageType getNextType() {
        if(currentType != StageType.Type00){
            return StageType.Type00;
        } // if
        switch (this.prevType){
            case Type01:
                return StageType.Type02;
            case Type02:
                return StageType.Type03;
            case Type03:
                return StageType.Type00;
        } // switch
        return this.currentType;
    }

    private void constructTransform(Point screenSize) {
        this.transform = new Transform2D();
        transform.scale.x = screenSize.x;
        transform.scale.y = screenSize.y;
    }

    public void update() {
        transform.position.y += this.scrollSpeed;
    }
}