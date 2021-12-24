package com.example.game.game.resource;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.game.R;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.game.resource.ImageResourceType;

import java.util.HashMap;

public class ImageResource {
    Resources resources;
    HashMap<ImageResourceType, Bitmap> imageResourceTypeBitmapHashMap = null;

    public ImageResource(Resources resources, Point screenSize) {
        this.resources = resources;
        this.imageResourceTypeBitmapHashMap = new HashMap<>();
        this.constructStageBackground(screenSize);
        this.constructEffect(screenSize);

        HashMap<ImageResourceType, Bitmap> hash = this.imageResourceTypeBitmapHashMap;

        hash.put(ImageResourceType.BulletLock,
                this.createScaledBitmap(R.drawable.lock, BitmapSizeStatic. buttonLock.x, BitmapSizeStatic.buttonLock.y));

    }
    private void constructEffect( Point screenSize){
        HashMap<ImageResourceType, Bitmap> hash = this.imageResourceTypeBitmapHashMap;

        hash.put(ImageResourceType.ScoreEffect, this.createScaledBitmap(
                R.drawable.scores100, BitmapSizeStatic.score.x, BitmapSizeStatic.score.y));
        hash.put(ImageResourceType.ExplosionEffect, this.createScaledBitmap(
                R.drawable.explosion, BitmapSizeStatic.explosion.x, BitmapSizeStatic.explosion.y));

        hash.put(ImageResourceType.BulletUpgradeEffect, this.createScaledBitmap(
                R.drawable.upgtext, BitmapSizeStatic.bulletUpgrade.x, BitmapSizeStatic.bulletUpgrade.y));
    }
    private void constructStageBackground( Point screenSize){
        HashMap<ImageResourceType, Bitmap> hash = this.imageResourceTypeBitmapHashMap;

        hash.put(ImageResourceType.StageBackground0,
                this.createScaledBitmap(R.drawable.background, screenSize.x, screenSize.y));
        hash.put(ImageResourceType.StageBackground1,
                this.createScaledBitmap(R.drawable.background01, screenSize.x, screenSize.y));
        hash.put(ImageResourceType.StageBackground2,
                this.createScaledBitmap(R.drawable.background02, screenSize.x, screenSize.y));
        hash.put(ImageResourceType.StageBackground3,
                this.createScaledBitmap(R.drawable.background03, screenSize.x, screenSize.y));
    }




    private Bitmap createScaledBitmap(int id, int x, int y) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.resources, id);
        return Bitmap.createScaledBitmap(bitmap, x, y, false);
    }

    public Bitmap getImageResource(ImageResourceType imageResourceType) {
        return this.imageResourceTypeBitmapHashMap.get(imageResourceType);
    }
}