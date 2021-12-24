package com.example.game.common;

import android.graphics.Point;

public class BitmapSizeStatic {
    public static Point player = new Point( 256,256);
    public static Point enemy = new Point(256,256);
    public static Point boss = new Point(512,512);
    public static Point enemyHpBar = new Point( (int)(175 * 0.6f),(int)(38 * 0.6f) );
    public static Point bossEnemyHpBar = new Point( (175 * 2),(38 * 2) );
    public static Point playerHpBar = new Point(274 * 2,41  * 2);
    public static Point playerHpBarIcon = new Point(41  * 2,41  * 2);
    public static Point bullet = new Point(86 * 3,86 * 3);
}