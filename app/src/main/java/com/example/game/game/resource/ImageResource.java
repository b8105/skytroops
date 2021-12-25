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
        this.constructPlane(screenSize);
        this.constructBullet(screenSize);
        this.constructStageBackground(screenSize);
        this.constructEffect(screenSize);
        this.constructHpRenderer(screenSize);

        HashMap<ImageResourceType, Bitmap> hash = this.imageResourceTypeBitmapHashMap;

        hash.put(ImageResourceType.BulletLock,
                this.createScaledBitmap(R.drawable.lock, BitmapSizeStatic. buttonLock.x, BitmapSizeStatic.buttonLock.y));
        hash.put(ImageResourceType.ScoreBackground,
                this.createScaledBitmap(R.drawable.leadrboardbox, BitmapSizeStatic.scoreBackground.x, BitmapSizeStatic.scoreBackground.y));
        hash.put(ImageResourceType.ClearInfoBackground,
                this.createScaledBitmap(R.drawable.leadrboardbox, BitmapSizeStatic.clearInfoBackground.x, BitmapSizeStatic.clearInfoBackground.y));

    }
    private void constructHpRenderer( Point screenSize){
        HashMap<ImageResourceType, Bitmap> hash = this.imageResourceTypeBitmapHashMap;

        hash.put(ImageResourceType.EnemyPlaneHpBar,
                this.createScaledBitmap(R.drawable.redhealthbar, BitmapSizeStatic.enemyHpBar.x, BitmapSizeStatic.enemyHpBar.y));
        hash.put(ImageResourceType.EnemyPlaneHpFrame,
                this.createScaledBitmap(R.drawable.healthframe, BitmapSizeStatic.enemyHpBar.x, BitmapSizeStatic.enemyHpBar.y));
        hash.put(ImageResourceType.PlayerPlaneHpBar,
                this.createScaledBitmap(R.drawable.playerhealthbar, BitmapSizeStatic.playerHpBar.x, BitmapSizeStatic.playerHpBar.y));
        hash.put(ImageResourceType.BossEnemyPlaneHpBar,
                this.createScaledBitmap(R.drawable.redhealthbar, BitmapSizeStatic.bossEnemyHpBar.x, BitmapSizeStatic.bossEnemyHpBar.y));
    }

    private void constructPlane( Point screenSize){
        HashMap<ImageResourceType, Bitmap> hash = this.imageResourceTypeBitmapHashMap;

        hash.put(ImageResourceType.PlayerPlane, this.createScaledBitmap(
                R.drawable.plane1up, BitmapSizeStatic.player.x, BitmapSizeStatic.player.y));
        hash.put(ImageResourceType.BasicEnemyPlane, this.createScaledBitmap(
                R.drawable.enemy10, BitmapSizeStatic.enemy.x, BitmapSizeStatic.enemy.y));
        hash.put(ImageResourceType.WeakEnemyPlane, this.createScaledBitmap(
                R.drawable.enemy02, BitmapSizeStatic.enemy.x, BitmapSizeStatic.enemy.y));
        hash.put(ImageResourceType.StrongEnemyPlane, this.createScaledBitmap(
                R.drawable.enemy06, BitmapSizeStatic.enemy.x, BitmapSizeStatic.enemy.y));
        hash.put(ImageResourceType.FollowEnemyPlane, this.createScaledBitmap(
                R.drawable.enemy03, BitmapSizeStatic.enemy.x, BitmapSizeStatic.enemy.y));
        hash.put(ImageResourceType.CommanderEnemyPlane, this.createScaledBitmap(
                R.drawable.enemy04, BitmapSizeStatic.enemy.x, BitmapSizeStatic.enemy.y));

        hash.put(ImageResourceType.Stage01BossEnemyPlane, this.createScaledBitmap(
                R.drawable.enemy08, BitmapSizeStatic.boss.x, BitmapSizeStatic.boss.y));
        hash.put(ImageResourceType.Stage02BossEnemyPlane, this.createScaledBitmap(
                R.drawable.enemy13, BitmapSizeStatic.boss.x, BitmapSizeStatic.boss.y));
        hash.put(ImageResourceType.Stage03BossEnemyPlane, this.createScaledBitmap(
                R.drawable.enemy14, BitmapSizeStatic.boss.x, BitmapSizeStatic.boss.y));
        hash.put(ImageResourceType.Stage04BossEnemyPlane, this.createScaledBitmap(
                R.drawable.enemy01, BitmapSizeStatic.boss.x, BitmapSizeStatic.boss.y));
        hash.put(ImageResourceType.Stage05BossEnemyPlane, this.createScaledBitmap(
                R.drawable.enemy05, BitmapSizeStatic.boss.x, BitmapSizeStatic.boss.y));

    }
    private void constructBullet( Point screenSize){
        HashMap<ImageResourceType, Bitmap> hash = this.imageResourceTypeBitmapHashMap;

        hash.put(ImageResourceType.BasicBullet, this.createScaledBitmap(
                R.drawable.bullet01, BitmapSizeStatic.bullet.x, BitmapSizeStatic.bullet.y));
        hash.put(ImageResourceType.HomingBullet, this.createScaledBitmap(
                R.drawable.bullet02, BitmapSizeStatic.bullet.x, BitmapSizeStatic.bullet.y));
        hash.put(ImageResourceType.Stage01BossBullet, this.createScaledBitmap(
                R.drawable.bullet05, BitmapSizeStatic.bullet.x, BitmapSizeStatic.bullet.y));
        hash.put(ImageResourceType.Stage02BossBullet, this.createScaledBitmap(
                R.drawable.bullet06, BitmapSizeStatic.bullet.x, BitmapSizeStatic.bullet.y));
        hash.put(ImageResourceType.Stage03BossBullet, this.createScaledBitmap(
                R.drawable.bullet08, BitmapSizeStatic.bullet.x, BitmapSizeStatic.bullet.y));
        hash.put(ImageResourceType.Stage04BossBullet, this.createScaledBitmap(
                R.drawable.bullet09, BitmapSizeStatic.bullet.x, BitmapSizeStatic.bullet.y));

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
        hash.put(ImageResourceType.StageBackground4,
                this.createScaledBitmap(R.drawable.background04, screenSize.x, screenSize.y));
        hash.put(ImageResourceType.StageBackground5,
                this.createScaledBitmap(R.drawable.background05, screenSize.x, screenSize.y));

    }




    private Bitmap createScaledBitmap(int id, int x, int y) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.resources, id);
        return Bitmap.createScaledBitmap(bitmap, x, y, false);
    }

    public Bitmap getImageResource(ImageResourceType imageResourceType) {
        return this.imageResourceTypeBitmapHashMap.get(imageResourceType);
    }
}