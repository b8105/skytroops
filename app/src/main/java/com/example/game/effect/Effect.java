package com.example.game.effect;

import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.Transform2D;
import com.example.game.common.shape.Rectangle;
import com.example.game.utility.StopWatch;
import com.example.game.utility.animation.SpriteAnimation;
import com.example.game.utility.animation.SpriteAnimationPart;
import com.example.game.utility.animation.SpriteAnimationPartPattern;

import java.util.ArrayList;
import java.util.List;

public class Effect {
    private boolean enable = false;
    private StopWatch lifeDuration = null;
    private EffectInfoBasicParam effectInfoBasicParam = new EffectInfoBasicParam();
    private EffectInfoUpdateParam effectInfoUpdateParam = new EffectInfoUpdateParam();
    private SpriteAnimation animation = null;

    public SpriteAnimation createExplosionAnimation() {
        SpriteAnimation animation = new SpriteAnimation();

        List<SpriteAnimationPart> anime = new ArrayList<>();
        SpriteAnimationPart anim =  new SpriteAnimationPart();
        anim.width = BitmapSizeStatic.explosionUnit.x;
        anim.height =  BitmapSizeStatic.explosionUnit.y;
        anim.name = "none";
        anim.offsetX = 0;
        anim.offsetY = 0;
        anim.pattern.add(new SpriteAnimationPartPattern(3, 0,0));
        anim.pattern.add(new SpriteAnimationPartPattern(3, 1,0));
        anim.pattern.add(new SpriteAnimationPartPattern(3, 2,0));
        anim.pattern.add(new SpriteAnimationPartPattern(3, 3,0));
        anime.add(anim);
        animation.create(anime);
        animation.changeMotion(0);

        return animation;
    }


    public SpriteAnimation createBulletUpgradeAnimation() {
        SpriteAnimation animation = new SpriteAnimation();

        List<SpriteAnimationPart> anime = new ArrayList<>();
        SpriteAnimationPart anim =  new SpriteAnimationPart();
        anim.width = BitmapSizeStatic.bulletUpgradeUnit.x;
        anim.height =  BitmapSizeStatic.bulletUpgradeUnit.y;
        anim.name = "none";
        anim.offsetX = 0;
        anim.offsetY = 0;

        int frame = 3;
        anim.pattern.add(new SpriteAnimationPartPattern(frame, 0,0));
        anim.pattern.add(new SpriteAnimationPartPattern(frame, 1,0));
        anim.pattern.add(new SpriteAnimationPartPattern(frame, 2,0));
        anim.pattern.add(new SpriteAnimationPartPattern(frame, 3,0));
        anim.pattern.add(new SpriteAnimationPartPattern(frame, 4,0));
        anim.pattern.add(new SpriteAnimationPartPattern(frame, 5,0));
        anim.pattern.add(new SpriteAnimationPartPattern(frame, 6,0));
        anim.pattern.add(new SpriteAnimationPartPattern(frame, 7,0));
        anime.add(anim);
        animation.create(anime);
        animation.changeMotion(0);

        return animation;
    }

    public SpriteAnimation createScoreAnimation() {
        SpriteAnimation animation = new SpriteAnimation();

        List<SpriteAnimationPart> anime = new ArrayList<>();
        SpriteAnimationPart anim =  new SpriteAnimationPart();
        anim.width = BitmapSizeStatic.score.x;
        anim.height = BitmapSizeStatic.score.y;
        anim.name = "none";
        anim.offsetX = 0;
        anim.offsetY = 0;
        anim.pattern.add(new SpriteAnimationPartPattern(100, 0,0));
        anime.add(anim);
        animation.create(anime);
        animation.changeMotion(0);

        return animation;
    }

    public SpriteAnimation createHpUpgrade() {
        SpriteAnimation animation = new SpriteAnimation();

        List<SpriteAnimationPart> anime = new ArrayList<>();
        SpriteAnimationPart anim =  new SpriteAnimationPart();
        anim.width = BitmapSizeStatic.hpUpgrade.x;
        anim.height = BitmapSizeStatic.hpUpgrade.y;
        anim.name = "none";
        anim.offsetX = 0;
        anim.offsetY = 0;
        anim.pattern.add(new SpriteAnimationPartPattern(50, 0,0));
        anime.add(anim);
        animation.create(anime);
        animation.changeMotion(0);

        return animation;
    }

    public SpriteAnimation createPlaneUpgrade() {
        SpriteAnimation animation = new SpriteAnimation();

        List<SpriteAnimationPart> anime = new ArrayList<>();
        SpriteAnimationPart anim =  new SpriteAnimationPart();
        anim.width = BitmapSizeStatic.planeUpgrade.x;
        anim.height = BitmapSizeStatic.planeUpgrade.y;
        anim.name = "none";
        anim.offsetX = 0;
        anim.offsetY = 0;
        anim.pattern.add(new SpriteAnimationPartPattern(100, 0,0));
        anime.add(anim);
        animation.create(anime);
        animation.changeMotion(0);

        return animation;
    }

    public SpriteAnimation createAnimation(EffectType effectType){
        switch (effectType){
            case Score:
                return this.createScoreAnimation();
            case Explosion:
                return this.createExplosionAnimation();
            case BulletUpgrade:
                return this.createBulletUpgradeAnimation();
            case PlaneUpgrade:
                return this.createPlaneUpgrade();
            case HPUpgrade:
                return this.createHpUpgrade();
        } // switch
        return null;
    }

    public Effect(EffectType effectType) {
        this.animation = this.createAnimation(effectType);
        this.lifeDuration = new StopWatch(0.0f);
    }

    public Effect(EffectType effectType, SpriteAnimation animation) {
        this.animation = animation;
        this.lifeDuration = new StopWatch(0.0f);
    }

    public Transform2D getTransform() {
        return this.effectInfoBasicParam.transform;
    }

    public float getAlpha() {
        return this.effectInfoBasicParam.alpha;
    }

    public EffectType getEffectType() {
        return this.effectInfoBasicParam.type;
    }

    public Rectangle getAnimationRect() {
        if(this.isExistAnimation()){
            return this.animation.getSourceRectangle();
        } // if
        return new Rectangle();
    }

    public boolean isExistAnimation(){
        return this.animation != null;
    }


    public boolean isAnimationEnd() {
        if(this.isExistAnimation()){
            return this.animation.isEndMotion();
        } // if
        return false;
    }

    //! 判定
    public boolean isEnable() {
        return this.enable;
    }

    //! 更新
    public boolean update(float deltaTime) {
        this.effectInfoBasicParam.update(this.effectInfoUpdateParam);

        if(this.isExistAnimation()){
            this.animation.addTimer(deltaTime);
        } // if
        if (this.lifeDuration.tick(deltaTime) || (this.isExistAnimation() && this.animation.isEndMotion())) {
            this.enable = false;
            return true;
        } // if
        return false;
    }

    //! 開始
    public void start(EffectInfo info) {
        this.effectInfoBasicParam = info.initParam;
        this.effectInfoUpdateParam = info.updateParam;
        this.enable = true;
        this.lifeDuration.reset(this.effectInfoBasicParam.lifeDuration);
        if(this.isExistAnimation()){
            this.animation.changeMotion(0);
        } // if
    }
};
