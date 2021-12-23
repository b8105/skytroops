package com.example.game.action.action_component.common;

import com.example.game.action.action_component.ActionComponent;
import com.example.game.game.ActorFactory;
import com.example.game.utility.MathUtilities;
import com.example.game.weapon.Weapon;
import com.example.game.action.ActionLayer;
import com.example.game.action.command.ShotCommand;
import com.example.game.component.ComponentType;
import com.example.game.utility.StopWatch;

public class ShotComponent extends ActionComponent {
    private ShotCommand command = null;
    private StopWatch shotTime = new StopWatch(0.2f);
    private Weapon weapon;

    public ShotComponent(ActionComponent actionComponent) {
        super(actionComponent);
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setShotInterval(float time) {
        this.shotTime.reset(time);
    }

    protected ShotCommand getCommand() {
        return this.command;
    }

    protected StopWatch getShotTime() {
        return this.shotTime;
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

        if (shotTime.tick(deltaTime)) {
            weapon.setRotation(command.angle);

            this.weapon.shot(
                    this.getOwner().getPosition(),
                    this.getOwner().getRotation(),
                    this.getOwner().getTag()
            );
            shotTime.reset();
        } // if
    }
    @Override
    public ComponentType getComponentType() {
        return ComponentType.Shot;
    }
}