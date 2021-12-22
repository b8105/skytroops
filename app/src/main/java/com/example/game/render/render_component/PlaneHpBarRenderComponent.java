package com.example.game.render.render_component;

import com.example.game.parameter.HpParameter;
import com.example.game.actor.Actor;
import com.example.game.actor.Plane;
import com.example.game.component.ComponentType;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayer;
import com.example.game.render.hp_renderer.PlaneHpBarRenderer;

public class PlaneHpBarRenderComponent extends RenderComponent{
    PlaneHpBarRenderer hpBarRenderer= null;;
    HpParameter hpParameter = null;

    public PlaneHpBarRenderComponent(RenderLayer layer) {
        super(layer);
    }

    public void setHpBarRenderer(PlaneHpBarRenderer hpBarRenderer) {
        this.hpBarRenderer = hpBarRenderer;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.HpBarRender;
    }

    public void onComponentInitialize(Actor owner) {
        super.onComponentInitialize(owner);
        this.hpParameter = ((Plane)(owner)).getHp();
    }
    @Override
    public void execute(RenderCommandQueue out) {
        this.hpBarRenderer.execute(this.hpParameter,out);
    }
}