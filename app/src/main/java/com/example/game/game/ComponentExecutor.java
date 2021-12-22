package com.example.game.game;

import com.example.game.action.ActionLayer;
import com.example.game.collision.CollisionLayer;
import com.example.game.common.InputEvent;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayer;

public class ComponentExecutor {
    private ActionLayer actionLayer = null;
    private CollisionLayer collisionLayer = null;
    private RenderLayer renderLayer = null;

    public ComponentExecutor() {
        this.actionLayer = new ActionLayer();
        this.collisionLayer = new CollisionLayer();
        this.renderLayer = new RenderLayer();
    }

    public ActionLayer getActionLayer() {
        return this.actionLayer;
    }

    public CollisionLayer getCollisionLayer() {
        return this.collisionLayer;
    }

    public RenderLayer getRenderLayer() {
        return this.renderLayer;
    }

    public void input(InputEvent input) {
        actionLayer.input(input);
    }

    public void update(float deltaTime) {
        actionLayer.excute(deltaTime);
        collisionLayer.excute();
    }

    public void draw(RenderCommandQueue out) {
        renderLayer.excute(out);
    }
}