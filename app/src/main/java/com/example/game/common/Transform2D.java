package com.example.game.common;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;

public class Transform2D {
    public static final Transform2D defaultTransform = new Transform2D();

    public PointF position = new PointF();
    public float rotation = 0.0f; // degree で管理して 使用するときに適宜radianに変換する
    public PointF scale = new PointF(1.0f, 1.0f);

    public Transform2D() {
    }

    public Transform2D(Transform2D transform) {
        this.position.x = transform.position.x;
        this.position.y = transform.position.y;
        this.rotation = transform.rotation;
        this.scale.x = transform.scale.x;
        this.scale.y = transform.scale.y;
    }

    public Transform2D(PointF position,
                       float rotation,
                       PointF scale) {
        this.position.x = position.x;
        this.position.y = position.y;
        this.rotation = rotation;
        this.scale.x = scale.x;
        this.scale.y = scale.y;
    }
}