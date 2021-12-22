package com.example.game.main;

import android.util.Log;
import android.view.MotionEvent;

import androidx.core.view.MotionEventCompat;

//!
public class Debugger {
    private String debugTag = "DebugTag";

    private void consoleoutTouchPosition(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        //取得した内容をログに表示
        Log.d("TouchEvent",
                "X:" + pointX + ",Y:" + pointY);
    }

    private void consoleoutMotionEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(debugTag, "Action was DOWN");
                break;
            case (MotionEvent.ACTION_MOVE):
                Log.d(debugTag, "Action was MOVE");
                break;
            case (MotionEvent.ACTION_UP):
                Log.d(debugTag, "Action was UP");
                break;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(debugTag, "Action was CANCEL");
                break;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(debugTag, "Movement occurred outside bounds " +
                        "of current screen element");
                break;
            default:
        } // switch
    }

    public boolean onTouchEvent(MotionEvent event) {
        //this.consoleoutTouchPosition(event);
        this.consoleoutMotionEvent(event);
        return true;
    }
}