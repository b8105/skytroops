package com.example.game.effect;

import android.graphics.PointF;

public class EffectInfoUpdateParam {
    public PointF translation = null;

    EffectInfoUpdateParam( ) {
        this.translation= new PointF();
    }
    EffectInfoUpdateParam(PointF translation) {
        this.translation= translation;
    }
}