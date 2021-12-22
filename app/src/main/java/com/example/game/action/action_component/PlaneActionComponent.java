package com.example.game.action.action_component;

import android.graphics.PointF;

import com.example.game.action.command.MoveCommand;
import com.example.game.action.ActionLayer;
import com.example.game.component.ComponentType;

//! ルートアクション ActionLayerから実行される
public class PlaneActionComponent extends ActionComponent {
    public PlaneActionComponent(ActionLayer layer) {
        super(layer);
    }

    @Override
    public void execute(float deltaTime) {
        super.childrenExecute(deltaTime);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.PlaneAction;
    }
}