package com.example.game.effect;

import com.example.game.common.EffectBitmapSizeStatic;
import com.example.game.common.Transform2D;
import com.example.game.common.shape.Rectangle;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderLayer;
import com.example.game.render.RenderLayerType;
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
    private SpriteAnimation animation = null;

    public SpriteAnimation createExplosionAnimation() {
            SpriteAnimation animation = new SpriteAnimation();

            List<SpriteAnimationPart> anime = new ArrayList<>();
            SpriteAnimationPart anim =  new SpriteAnimationPart();
            anim.width = EffectBitmapSizeStatic.explosionUnit.x;
            anim.height =  EffectBitmapSizeStatic.explosionUnit.y;
            anim.name = "none";
            anim.offsetX = 0;
            anim.offsetY = 0;
            anim.pattern.add(new SpriteAnimationPartPattern(10, 0,0));
            anim.pattern.add(new SpriteAnimationPartPattern(10, 1,0));
            anim.pattern.add(new SpriteAnimationPartPattern(10, 2,0));
            anim.pattern.add(new SpriteAnimationPartPattern(10, 3,0));
            anime.add(anim);
            animation.create(anime);
            animation.changeMotion(0);

            return animation;
    }

    public SpriteAnimation createScoreAnimation() {
        SpriteAnimation animation = new SpriteAnimation();

        List<SpriteAnimationPart> anime = new ArrayList<>();
        SpriteAnimationPart anim =  new SpriteAnimationPart();
        anim.width = EffectBitmapSizeStatic.score.x;
        anim.height = EffectBitmapSizeStatic.score.y;
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
        } // switch
        return null;
    }

    public Effect(EffectType effectType) {
        this.animation = this.createAnimation(effectType);
        this.lifeDuration = new StopWatch(0.0f);
    }


    public Transform2D getTransform() {
        return this.effectInfoBasicParam.transform;
    }

    public float getAlpha() {
        return this.effectInfoBasicParam.alpha;
    }

    public EffectInfoBasicParam getBasicParam() {
        return this.effectInfoBasicParam;
    }

    //! ゲッター
    public EffectType getEffectType() {
        return this.effectInfoBasicParam.type;
    }

    //! ゲッター
    public Rectangle getAnimationRect() {
        return this.animation.getSourceRectangle();
    }

    //! 判定
    public boolean isAnimationEnd() {
        return this.animation.isEndMotion();
    }

    //! 判定
    public boolean isEnable() {
        return this.enable;
    }

    //! 更新
    public boolean update(float deltaTime) {
        this.animation.addTimer(deltaTime);
        if (this.lifeDuration.tick(deltaTime) || this.animation.isEndMotion()) {
            this.enable = false;
            return true;
        } // if
        return false;
    }

    //! 開始
    public void start(EffectInfo info) {
        this.effectInfoBasicParam = info.initParam;
        this.enable = true;
        this.lifeDuration.reset(this.effectInfoBasicParam.lifeDuration);
        this.animation.changeMotion(0);
    }
};
