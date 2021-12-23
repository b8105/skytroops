package com.example.game.utility;

import android.graphics.PointF;

public class PointFUtilities {
    static public PointF normal(PointF p, PointF q) {
        float directionX = q.x - p.x;
        float directionY = q.y - p.y;
        float magnitude = (float) Math.sqrt((directionX * directionX) + (directionY * directionY));
        return new PointF(directionX / magnitude, directionY / magnitude);
    }

    static public float magnitude(PointF p, PointF q) {
        float directionX = q.x - p.x;
        float directionY = q.y - p.y;
        return (float) Math.sqrt((directionX * directionX) + (directionY * directionY));
    }

    static public float length(PointF p, PointF q) {
        return magnitude(p, q);
    }

    static public PointF rotateOnScreenCoordinate(float x, float y,
                                                  float degree,
                                                  float axisX, float axisY) {
        // sprite front is Y direction
        degree += 90;

        // rotate
        float radian = MathUtilities.degreeToRadian(degree);
        float originX = x - axisX;
        float originY = y - axisY;

        float transrationX = (float) ((originX) * Math.cos(radian) - (originY) * Math.sin(radian));
        float transrationY = (float) ((originX) * Math.sin(radian) + (originY) * Math.cos(radian));

        x = axisX + transrationX;
        y = axisY + transrationY;

        // on screen coordinate
        y *= -1;
        return new PointF(x, y);
    }

    static public PointF rotate(float x, float y,
                                float degree,
                                float axisX, float axisY) {
        // rotate
        float radian = MathUtilities.degreeToRadian(degree);
        float originX = x - axisX;
        float originY = y - axisY;

        float transrationX = (float) ((originX) * Math.cos(radian) - (originY) * Math.sin(radian));
        float transrationY = (float) ((originX) * Math.sin(radian) + (originY) * Math.cos(radian));

        x = axisX + transrationX;
        y = axisY + transrationY;
        return new PointF(x, y);
    }

    static public PointF rotate(float x, float y,
                                float degree) {
        return rotate(x, y, degree, 0.0f, 0.0f);
    }
}