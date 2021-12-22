package com.example.game.action.action_component.bullet;

import android.graphics.PointF;

import com.example.game.action.ActionLayer;
import com.example.game.action.action_component.ActionComponent;
import com.example.game.actor.Actor;
import com.example.game.component.ComponentType;

public class BasicBulletMoveComponent extends ActionComponent {
    private float speed;

    public BasicBulletMoveComponent(ActionLayer layer) {
        super(layer);
        this.speed = 28.0f;
    }

    double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }

    float degreeToRadian(float degree) {
        return (float) this.degreeToRadian((double) degree);
    }

    PointF rotateOnScreenCoordinate(float x, float y,
                                    float degree,
                                    float axisX, float axisY) {
        // sprite front is Y direction
        degree += 90;

        // rotate
        float radian = this.degreeToRadian(degree);
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

    PointF rotate(float x, float y,
                  float degree,
                  float axisX, float axisY) {
        // rotate
        float radian = this.degreeToRadian(degree);
        float originX = x - axisX;
        float originY = y - axisY;

        float transrationX = (float) ((originX) * Math.cos(radian) - (originY) * Math.sin(radian));
        float transrationY = (float) ((originX) * Math.sin(radian) + (originY) * Math.cos(radian));

        x = axisX + transrationX;
        y = axisY + transrationY;
        return new PointF(x, y);
    }
    
    @Override
    public void execute(float deltaTime) {
        Actor owner = super.getOwner();
        float rotation = owner.getRotation();
        PointF position = super.getOwner().getPosition();

        PointF move = this.rotate(0.0f, -this.speed, rotation, 0.0f, 0.0f);

        position.x += move.x;
        position.y += move.y;

        super.getOwner().setPosition(position);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.BasicBulletMove;
    }
}