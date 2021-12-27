package com.example.game.utility;

import android.graphics.PointF;

//! PointFを使った汎用関数を定義
//! PointFを二次元ベクトルに見立てて
//! ベクトルを回転させる関数などを定義しています
public class PointFUtilities {
    //! sqrtと割り算を使っているので呼びすぎないように注意
    public static PointF normal(PointF p, PointF q) {
        float directionX = q.x - p.x;
        float directionY = q.y - p.y;
        float magnitude = (float) Math.sqrt((directionX * directionX) + (directionY * directionY));
        return new PointF(directionX / magnitude, directionY / magnitude);
    }

    public static float magnitude(PointF p, PointF q) {
        float directionX = q.x - p.x;
        float directionY = q.y - p.y;
        return (float) Math.sqrt((directionX * directionX) + (directionY * directionY));
    }

    public static float length(PointF p, PointF q) {
        return magnitude(p, q);
    }

    //! 二次元の回転行列
    //! 回転軸が原点の場合は axisX,axisY はそれぞれ 0.0f,0.0f
    public static PointF rotate(float x, float y,
                                float degree,
                                float axisX, float axisY) {
        float radian = MathUtilities.degreeToRadian(degree);
        float originX = x - axisX;
        float originY = y - axisY;

        float transrationX = (float) ((originX) * Math.cos(radian) - (originY) * Math.sin(radian));
        float transrationY = (float) ((originX) * Math.sin(radian) + (originY) * Math.cos(radian));

        x = axisX + transrationX;
        y = axisY + transrationY;
        return new PointF(x, y);
    }

    public static PointF rotate(float x, float y,
                                float degree) {
        return rotate(x, y, degree, 0.0f, 0.0f);
    }


    public static PointF directionalVector(float scala, final PointF position, final PointF targetPosition) {
        PointF normalize = PointFUtilities.normal(position,
                new PointF(
                        targetPosition.x,
                        targetPosition.y));
        return new PointF(
                normalize.x * scala,
                normalize.y * scala);
    }

    //! 返す角度はラジアン角で
    //! 使いやすいように角度を90足しているので注意する
    public static float clacDirection(final PointF position, final PointF targetPosition) {
        PointF normalize = PointFUtilities.normal(position, targetPosition);
        double rotateRadian = Math.atan2((double) (normalize.y), (double) (normalize.x));
        rotateRadian += MathUtilities.degreeToRadian(90);
        return (float)rotateRadian;
    }

}