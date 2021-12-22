package com.example.game.collision.detector;

import com.example.game.common.shape.Circle;
import com.example.game.common.shape.Rectangle;

public class RectangleCollisionDetector {
    public RectangleCollisionDetector() {
    }

    public boolean CollisionRectangle(Rectangle a, Rectangle b) {
        return (a.left < b.right) && (b.left < a.right) && (a.top < b.bottom) && (b.top < a.bottom);
    }

    public boolean CollisionCircle(Rectangle rect, Circle circle) {
        double d;
        float circleX = circle.position.x;
        float circleY = circle.position.y;

        if (rect.left <= circleX && circleX <= rect.right &&
                rect.top <= circleY && circleY <= rect.bottom) {
            return true;
        } // if

        if (Math.abs(circleX - rect.left) <= circle.radius) {
            d = Math.sqrt(circle.radius * circle.radius - ((circleX - rect.left) * (circleX - rect.left)));
            if ((rect.top <= circleY + d && circleY + d <= rect.bottom) || (rect.top <= circleY - d && circleY - d <= rect.bottom)) {
                return true;
            } // if
        } // if
        if (Math.abs(circleX - rect.right) <= circle.radius) {
            d = Math.sqrt(circle.radius * circle.radius - ((circleX - rect.right) * (circleX - rect.right)));
            if ((rect.top <= circleY + d && circleY + d <= rect.bottom) || (rect.top <= circleY - d && circleY - d <= rect.bottom)) {
                return true;
            } // if
        } // if
        if (Math.abs(circleY - rect.top) <= circle.radius) {
            d = Math.sqrt(circle.radius * circle.radius - ((circleY - rect.top) * (circleY - rect.top)));
            if ((rect.left <= circleX + d && circleX + d <= rect.right) || (rect.left <= circleX - d && circleX - d <= rect.right)) {
                return true;
            } // if
        } // if
        if (Math.abs(circleY - rect.bottom) <= circle.radius) {
            d = Math.sqrt(circle.radius * circle.radius - ((circleY - rect.bottom) * (circleY - rect.bottom)));
            if ((rect.left <= circleX + d && circleX + d <= rect.right) || (rect.left <= circleX - d && circleX - d <= rect.right)) {
                return true;
            } // if
        } // if
        return false;
    }
}
