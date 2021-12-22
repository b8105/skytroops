package com.example.game.actor;

import com.example.game.parameter.PlaneInvincibleParameter;
import com.example.game.game.ActorContainer;

public class PlayerPlane extends Plane {
    private PlaneInvincibleParameter invincibleParameter = new PlaneInvincibleParameter();

    public PlayerPlane(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
        if (super.getTag().equals(ActorTagString.player)) {
            actorContainer.setMainChara(this);
        } // if
    }

    public PlaneInvincibleParameter getInvincibleParameter() {
        return this.invincibleParameter;
    }

    public void resetInvincibleTime(float time) {
        this.invincibleParameter.setInvincibleTime(time);
    }

    public void damege(int value) {
        this.hpParameter.decrease(value);
        this.invincibleParameter.activate();
        if (this.hpParameter.isLessEqualZero()) {

            super.end();
        } // if
    } // if
}
