package com.example.game.effect;

import android.graphics.Bitmap;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderSpriteInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EffectSystem {
    private HashMap<EffectType, Bitmap> effectBitmap;
    private HashMap<EffectType, EffectPoolAndEmitTarget> effectPair;
    private HashMap<EffectType, EffectEmitter> effectEmitter;
    private HashMap<EffectType, RenderLayerType> effectRenderLayer;
    private ImageResource resources;

    public EffectSystem(ImageResource resources) {
        this.resources = resources;
        this.effectBitmap = new HashMap<>();
        this.effectPair = new HashMap<>();
        this.effectEmitter = new HashMap<>();
        this.effectRenderLayer = new HashMap<>();

        this.generateEffectStruct(EffectType.Score, 10,
                ImageResourceType.ScoreEffect, RenderLayerType.UIEffect);
        this.generateEffectStruct(EffectType.Explosion, 10,
                ImageResourceType.ExplosionEffect, RenderLayerType.Effect);
        this.generateEffectStruct(EffectType.BulletUpgrade, 1,
                ImageResourceType.BulletUpgradeEffect, RenderLayerType.UIForground);
        this.generateEffectStruct(EffectType.PlaneUpgrade, 4,
                ImageResourceType.PlaneUpgradeEffect, RenderLayerType.UIEffect);
        this.generateEffectStruct(EffectType.HPUpgrade, 1,
                ImageResourceType.HPUpgradeEffect, RenderLayerType.UIEffect);

    }

    public List<Effect> getEffectList(EffectType effectType) {
        return this.effectPair.get(effectType).effects;
    }

    private void generateEffectStruct(
            EffectType effectType, int poolSize,
            ImageResourceType imageResourceType, RenderLayerType renderLayerType) {
        this.effectBitmap.put(effectType, this.resources.getImageResource(imageResourceType));
        this.effectPair.put(effectType,
                new EffectPoolAndEmitTarget(poolSize, effectType));
        this.effectEmitter.put(effectType, new EffectEmitter(
                this.effectPair.get(effectType).effectPool,
                this.effectPair.get(effectType).effects
        ));

        this.effectRenderLayer.put(effectType, renderLayerType);
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
                RenderCommandList list = out.getRenderCommandList(this.effectRenderLayer.get(key));

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
