package com.example.game.stage;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.example.game.collision.CollisionLayer;
import com.example.game.collision.collision_component.StageCollisionComponent;
import com.example.game.common.Transform2D;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;

import java.util.HashMap;

//! ゲームステージです
//! 番号に対応する各背景をスクロールさせているだけです
//! Actorを継承させていませんが
//! Actorのサブクラス間で衝突判定をとりたいので
//! CollisionComponentをもっています
public class Stage {
    private Transform2D transform = null;
    private Bitmap background = null;
    private StageRenderer renderer = null;
    private float scrollSpeed = 0.0f;
    private float defaultScrollSpeed = 8.0f;

    //! CollisionLayer内でプレイヤーや弾クラスと当たり判定をとります
    StageCollisionComponent component;
    HashMap<StageType, Bitmap> stageTypeBitmapHashMap = null;
    StageType currentType = StageType.Type01;
    StageType prevType = StageType.Type01;

    public Stage(Point screenSize, ImageResource resources, CollisionLayer layer) {
        this.constructTransform(screenSize);
        this.renderer = new StageRenderer(this);
        this.scrollSpeed = this.defaultScrollSpeed;
        this.stageTypeBitmapHashMap = new HashMap<>();

        this.stageTypeBitmapHashMap.put(StageType.Type01, resources.getImageResource(ImageResourceType.StageBackground1));
        this.stageTypeBitmapHashMap.put(StageType.Type02, resources.getImageResource(ImageResourceType.StageBackground2));
        this.stageTypeBitmapHashMap.put(StageType.Type03, resources.getImageResource(ImageResourceType.StageBackground3));
        this.stageTypeBitmapHashMap.put(StageType.Type04, resources.getImageResource(ImageResourceType.StageBackground4));
        this.stageTypeBitmapHashMap.put(StageType.Type05, resources.getImageResource(ImageResourceType.StageBackground5));

        this.changeType(StageType.Type01);

        component = new StageCollisionComponent(layer);
        component.setScreenSize(screenSize);
    }

    public Bitmap getBitmap() {
        assert (this.background != null);
        return this.background;
    }

    private Transform2D getTransform() {
        assert (this.transform != null);
        return this.transform;
    }

    public float getScroll() {
        return this.getTransform().position.y;
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

    public StageType getCurrentType() {
        return this.currentType;
    }

    public StageType getNextType() {
        switch (this.currentType){
            case Type01:
                return StageType.Type02;
            case Type02:
                return StageType.Type03;
            case Type03:
                return StageType.Type04;
            case Type04:
                return StageType.Type05;
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