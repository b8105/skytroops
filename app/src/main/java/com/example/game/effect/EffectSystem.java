package com.example.game.effect;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.game.R;
import com.example.game.common.EffectBitmapSizeStatic;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderSpriteInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EffectSystem {

    HashMap<EffectType, Bitmap> effectBitmap;
    HashMap<EffectType, EffectPoolAndEmitTarget> effectPair;
    HashMap<EffectType, EffectEmitter> effectEmitter;
    Resources resources;

    public EffectSystem(Resources resources) {
        this.resources = resources;
        this.effectBitmap = new HashMap<>();
        this.effectPair = new HashMap<>();
        this.effectEmitter = new HashMap<>();

        this.generateEffectStruct(EffectType.Score, 10,
                R.drawable.scores100, EffectBitmapSizeStatic.score);
        this.generateEffectStruct(EffectType.Explosion, 10,
                R.drawable.explosion, EffectBitmapSizeStatic.explosion);
    }

    public List<Effect> getEffectList(EffectType effectType){
        return this.effectPair.get(effectType).effects;
    }

    public RenderLayerType getRenderLayer(EffectType effectType){
        switch (effectType){
            case Score:
                return RenderLayerType.UIEffect;
            case Explosion:
                return RenderLayerType.Effect;
        } // switch
        return null;
    }

    private void generateEffectStruct(EffectType effectType, int poolSize,
                                      int resouceId, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resouceId);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

        this.effectBitmap.put(effectType, bitmap);
        this.effectPair.put(effectType,
                new EffectPoolAndEmitTarget(poolSize, effectType));
        this.effectEmitter.put(effectType, new EffectEmitter(
                this.effectPair.get(effectType).effectPool,
                this.effectPair.get(effectType).effects
        ));
    }

    private void generateEffectStruct(EffectType effectType, int poolSize,
                                      int resouceId, Point size) {
        this.generateEffectStruct(effectType, poolSize, resouceId, size.x, size.y);
    }

    public EffectEmitter getSharedEmitter(EffectType effectType) {
        return this.effectEmitter.get(effectType);
    }

    public void update(float deltaTime) {
        // erase
        for (EffectType key : this.effectPair.keySet()) {
            List<Effect> currentTarget = this.effectPair.get(key).effects;
            List<Effect> removeList = new ArrayList<>();

            for (Effect effect : currentTarget) {
                if (!effect.isEnable()) {
                    removeList.add(effect);
                } // if
            } // if
            for (Effect effect : removeList) {
                currentTarget.remove(effect);
            } // for
        } // for


        // update
        for (EffectType key : this.effectPair.keySet()) {
            List<Effect> effects = this.effectPair.get(key).effects;
            for (Effect effect : effects) {
                if (effect.update(deltaTime)) {
                } // if
            } // for
        } // for
    }

    //! 描画
    public void draw(RenderCommandQueue out) {
        for (EffectType key : this.effectPair.keySet()) {
            List<Effect> effects = this.effectPair.get(key).effects;
            for (Effect effect : effects) {
                if (!effect.isEnable()) {
                    continue;
                } // if
                if (effect.isAnimationEnd()) {
                    continue;
                } // if
                RenderCommandList list = out.getRenderCommandList(this.getRenderLayer(key));

                RenderSpriteInfo info = new RenderSpriteInfo(effect.getAnimationRect());
                list.drawSprite(
                        this.effectBitmap.get(effect.getEffectType()),
                        effect.getTransform(),
                        info);
            } // for
        } // for
    }

    ;

}
