package com.example.game.action.action_component.common;

import com.example.game.action.action_component.ActionComponent;
import com.example.game.game.ActorFactory;
import com.example.game.weapon.Weapon;
import com.example.game.action.ActionLayer;
import com.example.game.action.command.ShotCommand;
import com.example.game.component.ComponentType;
import com.example.game.utility.StopWatch;

public class ShotComponent extends ActionComponent {
    private ShotCommand command = null;
    private StopWatch shotTime = new StopWatch(0.2f);
    private Weapon weapon;
    // 触れている間は弾がでる

    public ShotComponent(ActionLayer layer) {
        super(layer);
    }

    public ShotComponent(ActionComponent actionComponent) {
        super(actionComponent);
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void writeCommand(ShotCommand command) {
        this.command = new ShotCommand();
        this.command.fire = command.fire;
    }

    @Override
    public void execute(float deltaTime) {
        if (command == null) {
//            System.out.println("null");
            return;
        } // if
        if (!command.fire) {
//            System.out.println("!command.fire");
            return;
        } // if
//        System.out.println("fire");

        if (shotTime.tick(deltaTime)) {
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