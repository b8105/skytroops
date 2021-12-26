package com.example.game.parameter.invincible;

import com.example.game.parameter.invincible.InvincibleParameter;
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
        if(this.planeSpriteRenderComponent != null){
            this.planeSpriteRenderComponent.activate();
        } // if
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        if (super.isActive() && this.planeSpriteRenderComponent != null) {
            if (this.planeSpriteRenderComponent.isActive()) {
                this.planeSpriteRenderComponent.inactivate();
            } // if
            else {
                this.planeSpriteRenderComponent.activate();
            } // else
        } // if
    }
}