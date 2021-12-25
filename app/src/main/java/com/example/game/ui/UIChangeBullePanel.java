package com.example.game.ui;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.game.R;
import com.example.game.collision.detector.RectangleCollisionDetector;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.InputEvent;
import com.example.game.common.shape.Circle;
import com.example.game.game.resource.ImageResource;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.transition_state.TransitionStateType;
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
    static private int elementCount = 3;

    public UIChangeBullePanel(
            ImageResource imageResource,
            Resources resources,
            PointF position) {
        float x = position.x;
        float y = position.y;

        this.toBasicButton = new UIChangeBulletButton(imageResource, resources,
                R.drawable.bullet01,
                new PointF(x, y), BitmapSizeStatic.bulletButton);
        x += BitmapSizeStatic.bulletButton.x;
        this.toBasicButton.unlock();

        this.toHomingButton = new UIChangeBulletButton(imageResource, resources,
                R.drawable.bullet02,
                new PointF(x, y), BitmapSizeStatic.bulletButton);
        x += BitmapSizeStatic.bulletButton.x;
        this.toHomingButton.lock();

        this.toThreeWayButton = new UIChangeBulletButton(imageResource, resources,
                R.drawable.bullet03,
                new PointF(x, y), BitmapSizeStatic.bulletButton);
        this.toThreeWayButton.lock();

        bulletButtons.add(toBasicButton);
        bulletButtons.add(toThreeWayButton);
        bulletButtons.add(toHomingButton);
    }

    public void setEvent(Weapon weapon) {
        this.toBasicButton.setTarget(weapon, UIChangeBulletButtonEventType.ToBasic);
        this.toThreeWayButton.setTarget(weapon, UIChangeBulletButtonEventType.ToThreeWay);
        this.toHomingButton.setTarget(weapon, UIChangeBulletButtonEventType.ToHoming);
    }


    static public PointF getButtonHalfSizeStatic() {
        return new PointF(
                BitmapSizeStatic.bulletButton.x * 0.5f,
                BitmapSizeStatic.bulletButton.y * 0.5f);
    }

    static public PointF getSizeStatic() {
        return new PointF(
                BitmapSizeStatic.bulletButton.x * elementCount,
                BitmapSizeStatic.bulletButton.y);
    }



    public void unlockToHomingButton(){
        this.toHomingButton.unlock();
    }
    public void unlockToThreeWayButton(){
        this.toThreeWayButton.unlock();
    }

    public void input(InputEvent input) {
        if (!input.enable) {
            return;
        } // if

        float x = input.positionX;
        float y = input.positionY;
        RectangleCollisionDetector detector = new RectangleCollisionDetector();
        Circle touchCircle = new Circle(x, y, 16);


        switch (input.actionType) {
            case (MotionEvent.ACTION_DOWN):
                for (UIChangeBulletButton bulletButton : this.bulletButtons) {
                    if (bulletButton.isLock()) {
                        continue;
                    } // if
                    if (detector.CollisionCircle(bulletButton.getRectangle(), touchCircle)) {
                        bulletButton.onTouch();
                    } // if
                } // for
                break;
            default:
        } // switch

    }

    public void draw(RenderCommandQueue out) {
        for (UIChangeBulletButton bulletButton : this.bulletButtons) {
            bulletButton.draw(out);
        } // for
    }
}