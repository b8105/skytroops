package com.example.game.effect;

import android.graphics.PointF;

import com.example.game.common.Transform2D;

public class EffectInfoBasicParam {
    public EffectType type;
    public Transform2D transform;
    public float lifeDuration = 0.0f;
    public float alpha = 1.0f;

    EffectInfoBasicParam() {
        this.lifeDuration = 0.0f;
        this.transform = new Transform2D();
        this.alpha = 1.0f;
    }

    EffectInfoBasicParam(EffectType type,
                         PointF position,
                         float duration) {
        this.type = type;
        this.lifeDuration = duration;
        this.transform = new Transform2D();
        this.alpha = 1.0f;

        this.transform.position.x = position.x;
        this.transform.position.y = position.y;
    }
}