package com.example.game.common.shape;

import android.graphics.PointF;

public class Circle {
    public PointF position = new PointF();
    public float radius = 0.0f;

    public Circle() {
    }

    public Circle(Circle circle) {
        this.position.x = circle.position.x;
        this.position.y = circle.position.y;
        this.radius = circle.radius;
    }

    public Circle(PointF position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    public Circle(float positionX, float positionY, float radius) {
        this.position.x = positionX;
        this.position.y = positionY;
        this.radius = radius;
    }
}
