package com.example.game.effect;

import java.util.ArrayList;
import java.util.List;

public class EffectPoolAndEmitTarget {
    public  EffectPool effectPool;
    public  List<Effect> effects;

    public EffectPoolAndEmitTarget(int poolSize, EffectType effectType) {
        this.effectPool = new EffectPool(poolSize, effectType);
        this.effects = new ArrayList<>();
    }
}