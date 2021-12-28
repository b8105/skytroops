package com.example.game.weapon;

import android.graphics.PointF;

//! 2Way,3Way,5Way Gunです
public class AnyWayGun extends Weapon {
    //! 射出角度をどれだけずらすか
    int wayAngle = 5;
    //! Shotで正面に１発撃っているので
    //! それを有効にするかどうか
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
            float rot = 0.0f;

            // 正面に１発
            if (this.frontShotFlag) {
                super.requestCreateBullet(pos, rot, tag);
            } // if
            //! 角度をずらしながら発射
            for (int i = 0; i < super.getShotCount(); i++) {
                rot += this.wayAngle;
                super.requestCreateBullet(pos, rot, tag);
                super.requestCreateBullet(pos, -rot, tag);
            } // for
        } // if
    }
}