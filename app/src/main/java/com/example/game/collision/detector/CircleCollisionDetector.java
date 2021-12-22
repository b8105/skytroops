package com.example.game.collision.detector;

import com.example.game.common.shape.Circle;

public class CircleCollisionDetector {
    public CircleCollisionDetector() {
    }

    public boolean detection(Circle a, Circle b) {
        return
                Math.pow(a.position.x - b.position.x, 2.0f) +
                        Math.pow(a.position.y - b.position.y, 2.0f) <=
                        Math.pow(a.radius + b.radius, 2.0f);
    }
}