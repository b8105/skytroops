package com.example.game.weapon;

import android.graphics.PointF;

public class AnyWayGun extends Weapon {
    int wayAngle = 5;

    public void setWayAngle(int wayAngle) {
        this.wayAngle = wayAngle;
    }

    public void shot(PointF parentGlobalPosition, float parentGlobalRotation, String tag) {
        PointF pos = parentGlobalPosition;
        float rot = parentGlobalRotation;

        // 正面に１発
        super.requestCreateBullet(pos, rot,tag);

        for (int i = 0; i < super.getShotCount(); i++) {
            rot += this.wayAngle;
            super.requestCreateBullet(pos, rot,tag);
            super.requestCreateBullet(pos, -rot,tag);
        } // for
    }
}