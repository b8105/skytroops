package com.example.game.utility.animation;

import java.util.ArrayList;
import java.util.List;

class BezierCurve {
    //! 補間
    float interpolation(float time,float  prev,float  next) {
        float from = prev;
        float to = next;
        return from + (to - from) * time;
    }

    //! 補間計算
    float interpolationAnimData(float normalized, List<Float> control) {
        if (!(0.0 <= normalized)) {
            return 0.0f;
        } // if

        if (control.size() > 2) {
            List<Float> anim = new ArrayList<>();

            for (int i = 1; i < control.size(); i++) {
                float value = this.interpolation(normalized, control.get(i - 1), control.get(i));
                anim.add(value);
            } // for
            return this.interpolationAnimData(normalized, anim);
        } // if
        else if (control.size() == 2) {
            return this.interpolation(normalized, control.get(0), control.get(1));
        } // else if
        return 0.0f;
    }
};
