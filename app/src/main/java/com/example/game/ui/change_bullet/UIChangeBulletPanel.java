package com.example.game.ui.change_bullet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PointF;

import com.example.game.R;
import com.example.game.actor.bullet.BulletType;
import com.example.game.actor.player.PlayerPlane;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.InputEvent;
import com.example.game.common.InputTouchType;
import com.example.game.effect.EffectEmitter;
import com.example.game.effect.EffectInfo;
import com.example.game.effect.EffectSystem;
import com.example.game.effect.EffectType;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.render.RenderCommandQueue;
import com.example.game.ui.UIPanel;
import com.example.game.ui.change_bullet.UIChangeBulletButton;
import com.example.game.ui.change_bullet.UIChangeBulletButtonEventType;
import com.example.game.weapon.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//! UIChangeBulletButtonのコンテナ
//! ボタンのイベント実行時の処理対象となるWeaponは
//! プレイヤーのWeaponであり,
//! そのWeapon生成時にButtonにセットする
public class UIChangeBulletPanel implements UIPanel {
    static private int elementCount = 3;

    //! 各ボタンです
    private List<UIChangeBulletButton> bulletButtons = new ArrayList<>();
    private UIChangeBulletButton toBasicButton;
    private UIChangeBulletButton toThreeWayButton;
    private UIChangeBulletButton toHomingButton;
    private UIChangeBulletButton currentButton = null;
    private float positionMarginX = 6.0f;

    //! ダブルタップ受付データ
    private int doubleTapFrame = 0;
    private int doubleTapFrameMax = 10;
    private boolean singleTap = false;
    private EffectEmitter bulletUpgradeEffect;

    HashMap<BulletType, Bitmap> requiredImage =null;

    public UIChangeBulletPanel(
            PlayerPlane playerPlane,
            ImageResource imageResource,
            Resources resources,
            EffectSystem effectSystem,
            PointF position) {
        this.requiredImage =  new HashMap<>();
        this.requiredImage.put(BulletType.Rapid,imageResource.getImageResource(ImageResourceType.RapidBullet));

        float x = position.x;
        float y = position.y;
        x += BitmapSizeStatic.bulletButton.x + this.positionMarginX;

        this.bulletUpgradeEffect = effectSystem.getSharedEmitter(EffectType.BulletUpgrade);
        this.toBasicButton = new UIChangeBulletButton(
                imageResource, resources,
                R.drawable.bullet01,
                new PointF(x, y), BitmapSizeStatic.bulletButton);
        x += BitmapSizeStatic.bulletButton.x + this.positionMarginX;
        this.toBasicButton.unlock();
        this.toHomingButton = new UIChangeBulletButton(
                imageResource, resources,
                R.drawable.bullet02,
                new PointF(x, y), BitmapSizeStatic.bulletButton);
        x += BitmapSizeStatic.bulletButton.x + this.positionMarginX;
        this.toHomingButton.lock();

        this.toThreeWayButton = new UIChangeBulletButton(
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

    private void effectEmit(UIChangeBulletButton uiChangeBulletButton) {
        PointF emitPos = uiChangeBulletButton.getPosition();
        emitPos.x -= BitmapSizeStatic.bulletUpgradeUnit.x * 0.5f;
        emitPos.y -= BitmapSizeStatic.bulletUpgradeUnit.y * 0.5f;
        EffectInfo info = new EffectInfo(
                EffectType.BulletUpgrade,
                emitPos,
                1.0f);
        this.bulletUpgradeEffect.emit(info);
        uiChangeBulletButton.unlock();
    }

    public void unlockToHomingButton() {
        this.effectEmit(this.toHomingButton);
        this.toHomingButton.unlock();
    }

    public void unlockToThreeWayButton() {
        this.effectEmit(this.toThreeWayButton);
        this.toThreeWayButton.unlock();
    }
    public void unlockToRapidButton() {
        this.toBasicButton.setType(UIChangeBulletButtonEventType.ToRapid);
        this.effectEmit(this.toBasicButton);
        this.toBasicButton.unlock();
        toBasicButton.setBitmap(this.requiredImage.get(BulletType.Rapid));
        this.toBasicButton.onTouch();
    }

    public void change(UIChangeBulletButton bulletButton){
        currentButton.setSelectFlag(false);
        bulletButton.onTouch();
        currentButton = bulletButton;
        currentButton.setSelectFlag(true);
    }

    public void changeToHoming(){
        this.change(this.toHomingButton);
    }

    public void changeToThreeway(){
        this.change(this.toThreeWayButton);
    }

    public void input(InputEvent input) {
        if (!input.enable) {
            return;
        } // if


        //! ダブルタップで弾種類を変更します
        if (input.touchType == InputTouchType.Touch) {
            if (this.doubleTapFrame > 0) {
                UIChangeBulletButton bulletButton = this.getNextButton();
                this.change(bulletButton);
            } // if
            else if (!this.singleTap) {
                this.singleTap = true;
                this.doubleTapFrame = this.doubleTapFrameMax;
            } // if
        } // if
    }

    public void update(float deltaTime) {
        this.doubleTapFrame--;
        if (this.doubleTapFrame <= 0) {
            this.doubleTapFrame = 0;
            this.singleTap = false;
        } // if
    }

    public void draw(RenderCommandQueue out) {
        for (UIChangeBulletButton bulletButton : this.bulletButtons) {
            bulletButton.draw(out);
        } // for
    }
}