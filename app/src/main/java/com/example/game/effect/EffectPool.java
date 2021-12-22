package com.example.game.effect;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EffectPool {
    private List<Effect> elements = new ArrayList<>();

    public EffectPool(int size, EffectType effectType) {
        //! 固定配列
        for (int i = 0; i < size; i++) {
            this.elements.add(new Effect(effectType));
        } // for
    }

    public Effect fetch() {
        for(Effect effect : this.elements){
            if (!effect.isEnable()) {
                return effect;
            } // if
        } // for
        return null;
    }
}