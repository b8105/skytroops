package com.example.game.render.render_component;

import com.example.game.component.ComponentType;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayer;

public class PlaneSpriteRenderComponent extends SpriteRenderComponent {
    private boolean active = true;
    public PlaneSpriteRenderComponent(RenderLayer layer) {
        super(layer);
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.PlaneSpriteRender;
    }

    public boolean isActive() {
        return this.active;
    }
    public void activate() {
        this.active = true;
    }
    public void inactivate() {
        this.active = false;
    }

    public void execute(RenderCommandQueue out) {
        if(this.isActive()){
            super.execute(out);
        } // if
    }
}