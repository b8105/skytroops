package com.example.game.actor;

import android.graphics.PointF;

import com.example.game.common.BitmapSizeStatic;
import com.example.game.component.ComponentType;
import com.example.game.parameter.damage.Damage;
import com.example.game.parameter.HpParameter;
import com.example.game.parameter.invincible.PlaneInvincibleParameter;
import com.example.game.game.ActorContainer;
import com.example.game.parameter.recovery.Recovery;
import com.example.game.parameter.recovery.RecoveryApplicable;
import com.example.game.weapon.AnyWayGun;

public class PlayerPlane extends Plane implements RecoveryApplicable {
    private PlaneInvincibleParameter invincibleParameter = new PlaneInvincibleParameter();
    private float defaultShotInterval = 0.2f;
    public PlayerPlane(ActorContainer actorContainer, String tag) {
        super(actorContainer, tag);
        assert (super.getTag().equals(ActorTagString.player));
        actorContainer.setMainChara(this);

        AnyWayGun anyWayGun = new AnyWayGun();
        super.addWeapon("MainWeapon",anyWayGun);
        anyWayGun.resetShotInterval(defaultShotInterval);
    }

    @Override
    public PointF getCenterPosition() {
        PointF position = super.getPosition();

        position.x += BitmapSizeStatic.player.x * 0.5f;
        position.y += BitmapSizeStatic.player.y * 0.5f;
        return position;
    }

    @Override
    public PlaneType getPlaneType() {
        return PlaneType.Player;
    }

    public void update(float deltaTime){
        super.update(deltaTime);
        if(this.invincibleParameter != null){
            invincibleParameter.setPlaneSpriteRenderComponent(
                    super.getComponent(ComponentType.PlaneSpriteRender));
            this.invincibleParameter.update(deltaTime);
        } // if
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
        hpParameter.clampZeroMax();
        this.invincibleParameter.activate();
        if (hpParameter.isLessEqualZero()) {
            super.end();
        } // if
    }

    @Override
    public void applyRecovery(Recovery recovery) {
        HpParameter hpParameter = super.getHpParameter();
        hpParameter.increase(recovery.value);
        hpParameter.clampZeroMax();
    }
}
