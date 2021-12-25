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

    public static Point bulletButton = new Point((int)(86 * 1.2f),(int)(86 * 1.2f));
    public static Point buttonLock = new Point((int)(76 * 1.2),(int)(33 * 1.2f));


    public static Point score = new Point( (int)(150 * 1.5), (int)(150 * 1.5));
    public static Point explosion = new Point(800, 200);
    public static Point explosionUnit = new Point(800 / 4, 200);
    public static Point bulletUpgrade = new Point(800 / 4, 200);


    public static Point scoreBackground = new Point(500, 140);
}