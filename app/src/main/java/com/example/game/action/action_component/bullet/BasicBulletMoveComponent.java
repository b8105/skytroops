package com.example.game.action.action_component.bullet;

import android.graphics.PointF;

import com.example.game.action.ActionLayer;
import com.example.game.action.action_component.ActionComponent;
import com.example.game.actor.Actor;
import com.example.game.component.ComponentType;
import com.example.game.utility.PointFUtilities;

public class BasicBulletMoveComponent extends ActionComponent {
    private float speed;

    public BasicBulletMoveComponent(ActionLayer layer) {
        super(layer);
        this.speed = 28.0f;
    }

    @Override
    public void execute(float deltaTime) {
        Actor owner = super.getOwner();
        float rotation = owner.getRotation();
        PointF position = super.getOwner().getPosition();

        PointF move = PointFUtilities.rotate(0.0f, -this.speed, rotation, 0.0f, 0.0f);

        position.x += move.x;
        position.y += move.y;

        super.getOwner().setPosition(position);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.BasicBulletMove;
    }
}