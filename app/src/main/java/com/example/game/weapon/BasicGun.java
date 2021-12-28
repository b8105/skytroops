package com.example.game.weapon;

import android.graphics.PointF;

//! シンプルな武器クラスです
//! 自分の正面に対して１発撃ちます
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