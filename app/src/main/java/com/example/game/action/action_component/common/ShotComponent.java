package com.example.game.action.action_component.common;

import com.example.game.action.action_component.ActionComponent;
import com.example.game.weapon.Weapon;
import com.example.game.action.command.ShotCommand;
import com.example.game.component.ComponentType;
import com.example.game.utility.StopWatch;


// Weaponのshotを呼ぶ為のクラスです
// Actorは入力機能を持たないのでこのクラスを介して発射します
public class ShotComponent extends ActionComponent {
    private ShotCommand command = null;
    private Weapon weapon;
    private boolean instanceFlag = false;
    private boolean fixedRotation = false;

    public ShotComponent(ActionComponent actionComponent) {
        super(actionComponent);
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setInstanceFlag(boolean instance) {
        this.instanceFlag = instance;
    }

    public void setFixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
    }

    protected ShotCommand getCommand() {
        return this.command;
    }

    protected Weapon getWeapon() {
        return this.weapon;
    }

    public void writeCommand(ShotCommand command) {
        this.command = new ShotCommand();
        this.command.fire = command.fire;
        this.command.angle = command.angle;
    }

    @Override
    public void execute(float deltaTime) {
        if (command == null) {
            return;
        } // if
        if (!command.fire) {
            return;
        } // if
        if (!this.isActive()) {
            return;
        } // if


        if (this.weapon.isReady()) {
            if (this.instanceFlag) {
                super.inactivate();
            } // if

            if (!this.fixedRotation) {
                this.weapon.setRotation(command.angle);
            } // if
            this.weapon.shot(
                    this.getOwner().getPosition(),
                    this.getOwner().getRotation(),
                    this.getOwner().getTag());
        } // if
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.Shot;
    }
}