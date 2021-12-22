package com.example.game.common.shape;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

public class Rectangle {
    public float left = 0.0f;
    public float top = 0.0f;
    public float right = 0.0f;
    public float bottom = 0.0f;

    public Rectangle() {
    }

    public Rectangle(Rect rect) {
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public Rectangle(
            float left,
            float top,
            float right,
            float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public Rectangle(
            float left,
            float top,
            int right,
            int bottom) {
        this.left = left;
        this.top = top;
        this.right = (float) right;
        this.bottom = (float) bottom;
    }

    public void set(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void set(int left, int top, int right, int bottom) {
        this.set((float) left, (float) top, (float) right, (float) bottom);
    }

    public void set(Rect rect) {
        this.set(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void offset(float transitionX, float transitionY) {
        this.left += transitionX;
        this.top += transitionY;
        this.right += transitionX;
        this.bottom += transitionY;
    }

    public void offset(int transitionX, int transitionY) {
        this.offset((float) transitionX, (float) transitionY);
    }

    public void offset(PointF transition) {
        this.offset(transition.x, transition.y);
    }

    public void offset(Point transition) {
        this.offset(transition.x, transition.y);
    }

    public void transrate(Point transition) {
        this.offset(transition);
    }

    public void transrate(PointF transition) {
        this.offset(transition);
    }

    public void expansion(float sizeX, float sizeY) {
        this.left -= sizeX;
        this.top -= sizeY;
        this.right += sizeX;
        this.bottom += sizeY;
    }

    public PointF getSize() {
        return new PointF(
                this.right - this.left,
                this.bottom - this.top
        );
    }
}