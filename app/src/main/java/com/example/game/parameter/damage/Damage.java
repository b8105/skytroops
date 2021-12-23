package com.example.game.parameter.damage;

import com.example.game.collision.CollisionInfo;

public class Damage {
    public int value;

    public Damage(CollisionInfo collisionInfo){
        this.value = collisionInfo.force;
    }
}