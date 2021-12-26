package com.example.game.weapon;

import android.graphics.PointF;

public class BasicGun extends Weapon {
    public void shot(PointF parentGlobalPosition, float parentGlobalRotation, String tag) {
        if(super.isReady()){
            super.setReady(false);
            super.resetShotInterval();

            PointF pos = parentGlobalPosition;
            float rot = 0.0f;
            super.requestCreateBullet(pos, rot,tag);
        } // if
    }
}