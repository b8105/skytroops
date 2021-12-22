package com.example.game.common;

import android.view.MotionEvent;

import androidx.core.view.MotionEventCompat;


public class InputEvent {
    //public MotionEvent motionEvent;
    public boolean enable = false;
    public float positionX;
    public float positionY;
    public int actionType = -1;
    public InputTouchType touchType;

    public InputEvent() {
    }

    public InputEvent(MotionEvent motionEvent,InputTouchType touchType) {
        this.touchType = touchType;
        this.positionX = motionEvent.getX();
        this.positionY = motionEvent.getY();
        this.actionType = MotionEventCompat.getActionMasked(motionEvent);
    }
}