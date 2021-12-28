package com.example.game.component;

import com.example.game.actor.Actor;

//! 派生先として
//! dctionableなActionComponent
//! collisionableなCollisionComponent
//! drawableなRenderComponent
//! があります
//! Actorが持つパラメータはComponent化せずに
//! Actorのupdate関数の中で更新しています
public interface Component {
    public ComponentType getComponentType();

    public boolean isActive() ;
    public void activate() ;
    public void inactivate() ;

    public void setOwner(Actor owner);

    public Actor getOwner();

    public void onComponentInitialize(Actor owner);

    public void onComponentDestory(Actor owner);
}