package com.example.game.effect;

import java.util.List;

public class EffectEmitter {
    private EffectPool effectPool;
    private List<Effect> outTarget;

    public EffectEmitter(EffectPool effectPool, List<Effect> outTarget) {
        this.effectPool = effectPool;
        this.outTarget = outTarget;
    }
    public boolean emit(EffectInfo effectInfo) {
        Effect effect = this.effectPool.fetch();

        if (effect == null) {
            return false;
        } // if
        effect.start(effectInfo);
        this.outTarget.add(effect);
        return true;
    }
};
