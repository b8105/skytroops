package com.example.game.effect;

import android.graphics.PointF;

import com.example.game.common.Transform2D;

public class EffectInfo {
    public EffectInfoBasicParam initParam = null;
    public EffectInfoUpdateParam updateParam = null;

    public EffectInfo() {
        this.initParam = new EffectInfoBasicParam();
        this.updateParam = new EffectInfoUpdateParam();
    }
    public EffectInfo(
            EffectType type,
            PointF position,
            float duration) {
        this.initParam = new EffectInfoBasicParam(type,position,duration);
        this.updateParam = new EffectInfoUpdateParam();
    }
    public EffectInfo(
            EffectType type,
            PointF position,
            float duration,
            PointF translation) {
        this.initParam = new EffectInfoBasicParam(type,position,duration);
        this.updateParam = new EffectInfoUpdateParam(translation);
    }

    public EffectInfo(EffectInfoBasicParam initParam) {
        this.initParam =initParam;
    }

};