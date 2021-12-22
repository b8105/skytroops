package com.example.game.parameter;

import com.example.game.parameter.InvincibleParameter;
import com.example.game.render.render_component.PlaneSpriteRenderComponent;

public class PlaneInvincibleParameter extends InvincibleParameter {
    private PlaneSpriteRenderComponent planeSpriteRenderComponent = null;

    public PlaneInvincibleParameter() {
        super();
    }

    public void setPlaneSpriteRenderComponent(PlaneSpriteRenderComponent planeSpriteRenderComponent) {
        this.planeSpriteRenderComponent = planeSpriteRenderComponent;
    }

    public void inactivate() {
        super.inactivate();
        this.planeSpriteRenderComponent.activate();
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        if (super.isActive()) {
            if (this.planeSpriteRenderComponent.isActive()) {
                this.planeSpriteRenderComponent.inactivate();
            } // if
            else {
                this.planeSpriteRenderComponent.activate();
            } // else
        } // if
    }
}