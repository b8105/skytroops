package com.example.game.utility.animation;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class BezierCurveAnimation {
    List<Float> animX = new ArrayList();
    List<Float> animY = new ArrayList();
    float rate = 0.0f;

    public BezierCurveAnimation(float rate) {
        this.rate = rate;
    }
    //! 追加
    public void addControlPoint(PointF point) {
        this.animX.add(point.x);
        this.animY.add(point.y);
    }
    public PointF calculatePointPosition(float time) {
        BezierCurve bezier_curve = new BezierCurve();

        PointF ret = new PointF();
        ret.x = bezier_curve.interpolationAnimData(time / this.rate, this.animX);
        ret.y = bezier_curve.interpolationAnimData(time / this.rate, this.animY);
        return ret;
    }
}