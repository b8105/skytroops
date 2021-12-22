package com.example.game.effect;

import android.graphics.PointF;

import com.example.game.common.Transform2D;

public class EffectInfo {
    public EffectInfoBasicParam initParam = null;

    public EffectInfo() {
        this.initParam = new EffectInfoBasicParam();
    }
    public EffectInfo(
            EffectType type,
            PointF position,
            float duration) {
        this.initParam = new EffectInfoBasicParam(type,position,duration);
    }

    public EffectInfo(EffectInfoBasicParam initParam) {
        this.initParam =initParam;
    }

};