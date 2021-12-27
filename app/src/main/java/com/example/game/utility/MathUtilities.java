package com.example.game.utility;


//! 数値関連の汎用関数を定義
//! radian <-> degreeの変換
//! だけです

public class MathUtilities {
    static public double radianToDegree(double radians) {
        return radians * (180.0 / Math.PI);
    }
    static public float radianToDegree(float radians) {
        return (float) radianToDegree((double) radians);
    }

    static public double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }

    static public float degreeToRadian(float degree) {
        return (float) degreeToRadian((double) degree);
    }

}
