package com.example.game.action.action_component;

import com.example.game.action.input.ActionInput;
import com.example.game.action.ActionLayer;
import com.example.game.action.Actionable;
import com.example.game.actor.Actor;
import com.example.game.component.Component;
import com.example.game.component.ComponentType;

import java.util.ArrayList;
import java.util.List;

abstract public class ActionComponent implements Actionable, Component {
    private ActionLayer layer = null;
    private Actor owner = null;
    private ActionInput input = null;

    ActionComponent parent = null;
    List<ActionComponent> children = new ArrayList<>();
    boolean active = true;

    public ActionComponent(ActionComponent parent) {
        this.parent = parent;
        parent.children.add(this);
    }

    public ActionComponent(ActionLayer layer) {
        this.layer = layer;
        this.layer.add(this);
    }

    @Override
    public void setActionInput(ActionInput input) {
        this.input = input;
    }

    @Override
    public ActionInput getActionInput() {
        return this.input;
    }

    public boolean isActive() {
        return this.active;
    }


    public void activate() {
        this.active  = true;
    }
    public void inactivate() {
        this.active  = false;
    }



    @Override
    public void onActionableDestroy() {
        if (this.parent != null) {
            this.children.remove(this);
        } // if
        else {
            this.layer.remove(this);
        } // if
    }

    @Override
    public void setOwner(Actor owner) {
        this.owner = owner;
    }

    @Override
    public Actor getOwner() {
        if (this.parent == null) {
            return this.owner;
        } // if
        return this.parent.getOwner();
    }

    @Override
    public void onComponentInitialize(Actor owner) {
        for(ActionComponent actionComponent : this.children){
            actionComponent.onComponentInitialize(owner);
        } // for
    }

    @Override
    public void onComponentDestory(Actor owner) {
        this.onActionableDestroy();
    }

    protected void childrenExecute(float deltaTime) {
        if(!this.isActive()){
            return;
        } // if

        for (ActionComponent actionComponent : this.children) {
            actionComponent.execute(deltaTime);
        } // for
    }
}