package com.example.game.actor;

import com.example.game.parameter.damage.Damage;
import com.example.game.parameter.HpParameter;
import com.example.game.parameter.invincible.PlaneInvincibleParameter;
import com.example.game.game.ActorContainer;

public class PlayerPlane extends Plane {
    private PlaneInvincibleParameter invincibleParameter = new PlaneInvincibleParameter();

    public PlayerPlane(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
        assert (super.getTag().equals(ActorTagString.player));
        actorContainer.setMainChara(this);
    }

    public void release(ActorContainer actorContainer) {
        super.release(actorContainer);
        assert (super.getTag().equals(ActorTagString.player));
        actorContainer.setMainChara(null);
    }


    public PlaneInvincibleParameter getInvincibleParameter() {
        return this.invincibleParameter;
    }

    public void resetInvincibleTime(float time) {
        this.invincibleParameter.setInvincibleTime(time);
    }


    public void applyDamage(Damage damage) {
        HpParameter hpParameter = super.getHpParameter();

        hpParameter.decrease(damage.value);
        this.invincibleParameter.activate();
        if (hpParameter.isLessEqualZero()) {
            super.end();
        } // if
    }
}
