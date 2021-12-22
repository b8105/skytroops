package com.example.game.component;

import com.example.game.actor.Actor;

public interface Component {
    public ComponentType getComponentType();

    public void setOwner(Actor owner);

    public Actor getOwner();

    public void onComponentInitialize(Actor owner);

    public void onComponentDestory(Actor owner);
}