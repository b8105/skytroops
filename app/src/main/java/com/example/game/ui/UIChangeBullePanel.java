package com.example.game.ui;

import android.content.res.Resources;
import android.graphics.PointF;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.example.game.R;
import com.example.game.actor.player.PlayerPlane;
import com.example.game.collision.detector.RectangleCollisionDetector;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.InputEvent;
import com.example.game.common.InputTouchType;
import com.example.game.common.shape.Circle;
import com.example.game.game.resource.ImageResource;
import com.example.game.render.RenderCommandQueue;
import com.example.game.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;

//! UIChangeBulletButtonのコンテナ
//! ボタンのイベント実行時の処理対象となるWeaponは
//! プレイヤーのWeaponであり,
//! そのWeapon生成時にButtonにセットする
public class UIChangeBullePanel {
    private List<UIChangeBulletButton> bulletButtons = new ArrayList<>();
    private UIChangeBulletButton toBasicButton;
    private UIChangeBulletButton toThreeWayButton;
    private UIChangeBulletButton toHomingButton;
    private UIChangeBulletButton currentButton = null;
    private float positionMarginX = 6.0f;
    static private int elementCount = 3;

    private int doubleTapFrame = 0;
    private int doubleTapFrameMax = 10;
    private boolean singleTap = false;

    public void update(float deltaTime) {
        this.doubleTapFrame--;
        if (this.doubleTapFrame <= 0) {
            this.doubleTapFrame = 0;
            this.singleTap = false;
        } // if
    }
    public UIChangeBullePanel(
            PlayerPlane playerPlane,
            ImageResource imageResource,
            Resources resources,
            PointF position) {
        float x = position.x;
        float y = position.y;
        x += BitmapSizeStatic.bulletButton.x + this.positionMarginX;

        this.toBasicButton = new UIChangeBulletButton(
                playerPlane,
                imageResource, resources,
                R.drawable.bullet01,
                new PointF(x, y), BitmapSizeStatic.bulletButton);
        x += BitmapSizeStatic.bulletButton.x + this.positionMarginX;
        this.toBasicButton.unlock();

        this.toHomingButton = new UIChangeBulletButton(
                playerPlane,
                imageResource, resources,
                R.drawable.bullet02,
                new PointF(x, y), BitmapSizeStatic.bulletButton);
        x += BitmapSizeStatic.bulletButton.x + this.positionMarginX;
        this.toHomingButton.lock();

        this.toThreeWayButton = new UIChangeBulletButton(
                playerPlane,
                imageResource, resources,
                R.drawable.bullet03,
                new PointF(x, y), BitmapSizeStatic.bulletButton);
        this.toThreeWayButton.lock();

        bulletButtons.add(toBasicButton);
        bulletButtons.add(toThreeWayButton);
        bulletButtons.add(toHomingButton);

        this.currentButton = toBasicButton;
        this.currentButton.setSelectFlag(true);
    }

    public void setEvent(Weapon weapon) {
        this.toBasicButton.setTarget(weapon, UIChangeBulletButtonEventType.ToBasic);
        this.toThreeWayButton.setTarget(weapon, UIChangeBulletButtonEventType.ToThreeWay);
        this.toHomingButton.setTarget(weapon, UIChangeBulletButtonEventType.ToHoming);
    }

    private UIChangeBulletButton getNextButton() {
        if (currentButton == this.toBasicButton) {
            if (!this.toHomingButton.isLock()) {
                return this.toHomingButton;
            } // if
        } // if
        else if (currentButton == this.toHomingButton) {
            if (!this.toThreeWayButton.isLock()) {
                return this.toThreeWayButton;
            } // if
            else {
                return this.toBasicButton;
            } // else
        } // else if
        return this.toBasicButton;
    }

    static public PointF getButtonHalfSizeStatic() {
        return new PointF(
                BitmapSizeStatic.bulletButton.x * 0.5f,
                BitmapSizeStatic.bulletButton.y * 0.5f);
    }

    public void unlockToHomingButton() {
        this.toHomingButton.unlock();
    }

    public void unlockToThreeWayButton() {
        this.toThreeWayButton.unlock();
    }

    public void input(InputEvent input) {
        if (!input.enable) {
            return;
        } // if

        if (input.touchType == InputTouchType.Touch) {
            if(this.doubleTapFrame > 0){
                UIChangeBulletButton bulletButton = this.getNextButton();
                currentButton.setSelectFlag(false);
                bulletButton.onTouch();
                currentButton = bulletButton;
                currentButton.setSelectFlag(true);
            } // if
            else if(!this.singleTap){
                this.singleTap = true;
                this.doubleTapFrame = this.doubleTapFrameMax;
            } // if
        } // if
    }

    public void draw(RenderCommandQueue out) {
        for (UIChangeBulletButton bulletButton : this.bulletButtons) {
            bulletButton.draw(out);
        } // for
    }
}