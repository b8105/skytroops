package com.example.game.weapon;

import android.graphics.PointF;

public class AnyWayGun extends Weapon {
    int wayAngle = 5;
    boolean frontShotFlag = true;

    public void setWayAngle(int wayAngle) {
        this.wayAngle = wayAngle;
    }

    public void setFrontShotFlag(boolean frontShotFlag) {
        this.frontShotFlag = frontShotFlag;
    }

    public void shot(PointF parentGlobalPosition, float parentGlobalRotation, String tag) {
        if (super.isReady()) {
            super.setReady(false);
            super.resetShotInterval();

            PointF pos = parentGlobalPosition;
            float rot = parentGlobalRotation;

            // 正面に１発
            if (this.frontShotFlag) {
                super.requestCreateBullet(pos, rot, tag);
            } // if

            for (int i = 0; i < super.getShotCount(); i++) {
                rot += this.wayAngle;
                super.requestCreateBullet(pos, rot, tag);
                super.requestCreateBullet(pos, -rot, tag);
            } // for
        } // if
    }
}