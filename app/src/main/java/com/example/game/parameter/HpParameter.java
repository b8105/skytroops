package com.example.game.parameter;

public class HpParameter {
    int value = 0;
    int valueMax = 0;

    public HpParameter(int value) {
        this.value = value;
        this.valueMax = value;
    }

    public HpParameter(int value, int valueMax) {
        this.value = value;
        this.valueMax = valueMax;
    }

    public int getValue(){
        return this.value;
    }
    public int getValueMax(){
        return this.valueMax;
    }
    public float calcPercent(){
        return (float)this.value /  (float)this.valueMax;
    }

    public void resetHp(int value){
        this.value = value;
        this.valueMax = value;
    }

    public void decrease(int value){
        this.value -= value;
    }

    public boolean isLessEqualZero(){
        return this.value <= 0;
    }

}