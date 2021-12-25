package com.example.game.actor;

import android.graphics.PointF;

import com.example.game.game.ActorContainer;
import com.example.game.common.Transform2D;
import com.example.game.component.Component;
import com.example.game.component.ComponentType;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    private ActorType actorType;
    private ActorState actorState;
    private String tag = "";
    private Transform2D transform = null;
    private PointF initialPosition = null;

    private List<Component> components = new ArrayList<Component>();

    public Actor(ActorContainer actorContainer, String tag) {
        this.actorState = ActorState.Active;
        this.tag = tag;
        this.transform = new Transform2D();
        this.initialPosition = new PointF();
        actorContainer.addActor(this);
    }

    //! setter
    public void setActorType(ActorType type) {
        this.actorType = type;
    }

    private void setTag(String tag) {
        this.tag = tag;
    }

    public void setPosition(float positionX, float positionY) {
        assert (this.transform != null);
        this.transform.position.x = positionX;
        this.transform.position.y = positionY;
    }

    public void setPosition(PointF position) {
        this.setPosition(position.x, position.y);
    }

    public void setRotation(float rotation) {
        this.transform.rotation = rotation;
    }

    public ActorType getActorType() {
        return this.actorType;
    }

    public ActorState getActorState() {
        return this.actorState;
    }

    public String getTag() {
        return this.tag;
    }

    public PointF getPosition() {
        assert (this.transform != null);
        return new PointF(
                transform.position.x,
                transform.position.y);
    }

    public float getRotation() {
        assert (this.transform != null);
        return transform.rotation;
    }

    public PointF getScale() {
        assert (this.transform != null);
        return new PointF(
                transform.scale.x,
                transform.scale.y);
    }

    public PointF getInitialPosition() {
        return this.initialPosition;
    }

    public <T> T getComponent(ComponentType componentType) {
        for (Component component : this.components) {
            if (component.getComponentType() == componentType) {
                return (T) component;
            } // if
        } // for
        return null;
    }

    public <T> T getComponent(ComponentType componentType, ComponentType componentType2) {
        for (Component component : this.components) {
            if (component.getComponentType() == componentType) {
                return (T) component;
            } // if
            else if (component.getComponentType() == componentType2) {
                return (T) component;
            } // else if
        } // for
        return null;
    }

    public void addComponent(Component component) {
        component.setOwner(this);
        this.components.add(component);
    }

    public void end() {
        this.actorState = ActorState.End;
    }

    public void initialize() {
        for (Component component : this.components) {
            component.onComponentInitialize(this);
        } // for
        this.initialPosition.x = this.transform.position.x;
        this.initialPosition.y = this.transform.position.y;
    }

    public void update(float deltaTime){
    }


    public void release(ActorContainer actorContainer) {
        for (Component component : this.components) {
            component.onComponentDestory(this);
        } // for
        actorContainer.removeActor(this);
    }
}