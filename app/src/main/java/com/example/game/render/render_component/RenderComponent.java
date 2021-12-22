package com.example.game.render.render_component;

import com.example.game.actor.Actor;
import com.example.game.component.Component;
import com.example.game.render.RenderLayer;
import com.example.game.render.Renderable;

abstract public class RenderComponent implements Renderable, Component {
    private RenderLayer layer = null;
    private Actor owner = null;

    public RenderComponent(RenderLayer layer) {
        this.layer = layer;
        this.layer.add(this);
    }

    @Override
    public void onRenderableDestroy() {
        this.layer.remove(this);
    }

    @Override
    public void setOwner(Actor owner) {
        this.owner = owner;
    }

    @Override
    public Actor getOwner() {
        return this.owner;
    }

    @Override
    public void onComponentInitialize(Actor owner) {
    }

    @Override
    public void onComponentDestory(Actor owner) {
        this.onRenderableDestroy();
    }
}