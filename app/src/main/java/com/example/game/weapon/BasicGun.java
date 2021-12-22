package com.example.game.weapon;

import android.graphics.PointF;

public class BasicGun extends Weapon {
    public void shot(PointF parentGlobalPosition, float parentGlobalRotation, String tag) {
        PointF pos = parentGlobalPosition;
        float rot = parentGlobalRotation;
        super.requestCreateBullet(pos, rot,tag);
    }
}