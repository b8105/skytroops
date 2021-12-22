package com.example.game.ui;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.game.R;
import com.example.game.collision.detector.RectangleCollisionDetector;
import com.example.game.common.InputEvent;
import com.example.game.common.shape.Circle;
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
    static private Point buttonSize = new Point(86 * 2,86 * 2) ;
    static private int elementCount = 3;

    public UIChangeBullePanel(
            Resources resources,
            PointF position     ) {
        float x = position.x;
        float y = position.y;

        this. toBasicButton = new UIChangeBulletButton(resources,
                R.drawable.bullet01,
                new PointF(x, y), buttonSize);
        x += buttonSize.x;

        this.toHomingButton= new UIChangeBulletButton (resources,
                R.drawable.bullet02,
                new PointF(x, y), buttonSize);
        x += buttonSize.x;

        this. toThreeWayButton= new UIChangeBulletButton(resources,
                R.drawable.bullet03,
                new PointF(x, y), buttonSize);


        bulletButtons.add(toBasicButton);
        bulletButtons.add(toThreeWayButton);
        bulletButtons.add(toHomingButton);
    }

    public void setEvent(Weapon weapon){
        this.toBasicButton.setTarget(weapon, UIChangeBulletButtonEventType.ToBasic);
        this.toHomingButton.setTarget(weapon, UIChangeBulletButtonEventType.ToHoming);
        this.toThreeWayButton.setTarget(weapon, UIChangeBulletButtonEventType.ToThreeWay);
    }


    static public PointF getButtonHalfSizeStatic(){
        return new PointF(
                buttonSize.x * 0.5f,
                buttonSize.y * 0.5f);
    }
    static public PointF getSizeStatic(){
        return new PointF(
                buttonSize.x * elementCount,
                buttonSize.y);
    }

    public void input(InputEvent input){
        if (!input.enable) {
            return;
        } // if

        float x = input.positionX;
        float y = input.positionY;
        RectangleCollisionDetector detector = new RectangleCollisionDetector();
        Circle touchCircle = new Circle(x, y, 16);


        switch (input.actionType) {
            case (MotionEvent.ACTION_DOWN):
                for(UIChangeBulletButton bulletButton : this.bulletButtons){
                    if (detector.CollisionCircle(bulletButton.getRectangle(), touchCircle)) {
                        bulletButton.onTouch();
                    } // if
                } // for
                break;
            default:
        } // switch

    }
    public void draw (RenderCommandQueue out){
        for(UIChangeBulletButton bulletButton : this.bulletButtons){
            bulletButton.draw(out);
        } // for
    }
}