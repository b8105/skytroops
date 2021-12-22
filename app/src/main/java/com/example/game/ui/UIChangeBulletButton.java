package com.example.game.ui;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;

import com.example.game.R;
import com.example.game.actor.bullet.BulletType;
import com.example.game.render.RenderCommandQueue;
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

    public UIChangeBulletButton(Resources resources, int id, PointF position, Point size) {
        super(resources, id, position, size);

        this.background = new UILabel(
                resources, R.drawable.boxbackground, position, size
        );
    }

    public UIChangeBulletButton(Resources resources, int id, PointF position, Point size,
                                Weapon weapon, UIChangeBulletButtonEventType type) {
        super(resources, id, position, size);
        this.setTarget(weapon, type);
    }

    public void setTarget(Weapon weapon, UIChangeBulletButtonEventType type) {
        this.weapon = weapon;
        this.type = type;
    }

    @Override
    public void onTouch() {
        switch (this.type) {
            case ToBasic:
                this.weapon.setShotCount(singleShotCount);
                this.weapon.setBulletType(BulletType.Basic);
                break;
            case ToHoming:
                this.weapon.setShotCount(singleShotCount);
                this.weapon.setBulletType(BulletType.Homing);
                break;
            case ToThreeWay:
                this.weapon.setBulletType(BulletType.Homing);
                this.weapon.setBulletType(BulletType.Basic);
                this.weapon.setShotCount(threeWayCount);
                break;
        } // switch
    }

    @Override
    public void draw(RenderCommandQueue out) {
        this.background.draw(out);
        super.draw(out);
    }
}