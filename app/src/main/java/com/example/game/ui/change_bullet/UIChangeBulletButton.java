package com.example.game.ui.change_bullet;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;

import com.example.game.R;
import com.example.game.actor.player.PlayerPlane;
import com.example.game.actor.bullet.BulletType;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.game.resource.ImageResource;
import com.example.game.render.RenderCommandQueue;
import com.example.game.ui.UIButton;
import com.example.game.ui.UILabel;
import com.example.game.weapon.Weapon;


//! ボタンに背景を設定したいだけなのでLabelを持たせる
//! draw関数では背景を描画してから基底クラスを実行する
//! ボタンでより細かい制御をする場合は
//! Labelを継承してButtonを持たせ抽象クラスにする
public class UIChangeBulletButton extends UIButton {
    private Weapon weapon;
    private UIChangeBulletButtonEventType type;
    private final int singleShotCount = 0;
    private final int threeWayCount = 1;
    private UILabel background;
    private UILabel lockBitmap;
    private UILabel selectBitmap;
    private boolean lockFlag = false;
    private boolean selectFlag = false;

    //! 対象(Weapon)変更するパラメータです
    private float rapidInterval = 0.09f;
    private float basicInterval = 0.16f;
    private float homingInterval = 0.35f;
    private float threewayInterval = 0.26f;

    public UIChangeBulletButton(
            ImageResource imageResource, Resources resources, int id, PointF position, Point size) {
        super(imageResource, resources, id, position, size);

        this.background = new UILabel(imageResource,
                resources, R.drawable.boxbackground, position, size);
        this.lockBitmap = new UILabel(imageResource,
                resources, R.drawable.lock, position, BitmapSizeStatic.buttonLock);
        this.selectBitmap = new UILabel(imageResource,
                resources, R.drawable.bullet_button_frame, position, BitmapSizeStatic.bulletButton);
    }

    public UIChangeBulletButton(ImageResource imageResource, Resources resources, int id, PointF position, Point size,
                                Weapon weapon, UIChangeBulletButtonEventType type) {
        super(imageResource, resources, id, position, size);
        this.setTarget(weapon, type);
    }

    public void setType(UIChangeBulletButtonEventType type) {
        this.type = type;
    }

    public void setTarget(Weapon weapon, UIChangeBulletButtonEventType type) {
        this.weapon = weapon;
        this.type = type;
    }

    public void setSelectFlag(boolean selectFlag) {
        this.selectFlag = selectFlag;
    }

    public boolean isLock() {
        return this.lockFlag;
    }

    public void lock() {
        this.lockFlag = true;
    }

    public void unlock() {
        this.lockFlag = false;
    }

    @Override
    public void onTouch() {
        switch (this.type) {
            case ToBasic:
                this.weapon.resetShotInterval(this.basicInterval);
                this.weapon.setShotCount(this.singleShotCount);
                this.weapon.setBulletType(BulletType.Basic);
                break;
            case ToRapid:
                this.weapon.resetShotInterval(this.rapidInterval);
                this.weapon.setShotCount(this.singleShotCount);
                this.weapon.setBulletType(BulletType.Rapid);
                break;
            case ToHoming:
                this.weapon.resetShotInterval(this.homingInterval);
                this.weapon.setShotCount(this.singleShotCount);
                this.weapon.setBulletType(BulletType.Homing);
                break;
            case ToThreeWay:
                this.weapon.resetShotInterval(this.threewayInterval);
                this.weapon.setBulletType(BulletType.Homing);
                this.weapon.setBulletType(BulletType.Basic);
                this.weapon.setShotCount(this.threeWayCount);
                break;

        } // switch
    }

    @Override
    public void draw(RenderCommandQueue out) {
        this.background.draw(out);
        if (this.selectFlag) {
            this.selectBitmap.draw(out);
        } // if
        super.draw(out);


        if (this.lockFlag) {
            this.lockBitmap.draw(out);
        } // if
    }
}