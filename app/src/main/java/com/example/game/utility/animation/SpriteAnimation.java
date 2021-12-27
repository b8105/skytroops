package com.example.game.utility.animation;

import android.graphics.Point;

import com.example.game.common.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

//! 60fpsを想定したスプライトアニメーションデータのコンテナです
//! 表示UV矩形の取得が主な役割です
public class SpriteAnimation {
    float idealFrameTime = 1.0f / 60.0f;
    boolean motionEnd = false;
    float time = 0.0f;
    int currentMotionNo = -1;
    int currentPatternNo = -1;
    SpriteAnimationPart currentAniamtion = null;
    List<SpriteAnimationPart> animations;

    public SpriteAnimation() {
        this.idealFrameTime = 1.0f / 60.0f;
        this.motionEnd = false;
        this.time = 0.0f;
        this.currentMotionNo = -1;
        this.currentPatternNo = -1;
        this.currentAniamtion = null;
        this.animations = new ArrayList<>();
    }

//    public void setMotionLoop(boolean loop) {
//        this.currentAniamtion.loop = loop;
//    }
//
//    public int getMotionNo() {
//        return this.currentMotionNo;
//    }
//
//    public String getMotionName() {
//        return this.currentAniamtion.name;
//    }
//
//    public int getAnimationSize() {
//        return this.animations.size();
//    }

    //! 描画矩形の取得
    public Rectangle getSourceRectangle() {
        Point offset = new Point(
                this.currentAniamtion.offsetX,
                this.currentAniamtion.offsetY);
        Point size = new Point(
                this.currentAniamtion.width,
                this.currentAniamtion.height);
        Rectangle rect = new Rectangle(
                offset.x + this.currentAniamtion.pattern.get(this.currentPatternNo).row * size.x,
                offset.y + this.currentAniamtion.pattern.get(this.currentPatternNo).col * size.y,
                offset.x + this.currentAniamtion.pattern.get(this.currentPatternNo).row * size.x + size.x,
                offset.y + this.currentAniamtion.pattern.get(this.currentPatternNo).col * size.y + size.y);
        return rect;
    }

    public boolean isEndMotion() {
        if (this.currentAniamtion.loop) {
            return false;
        } // if
        return this.motionEnd;
    }

    public boolean addTimer(float time) {
        if (this.motionEnd) {
            return false;
        } // if
        if (this.animations.size() == 0) {
            return false;
        } // if
        this.time += time;

        //! 指定された秒数が経ったら次のパターンに移行します
        int wait = this.currentAniamtion.pattern.get(this.currentPatternNo).wait;
        if (wait * this.idealFrameTime < this.time) {
            int pattern_size = this.currentAniamtion.pattern.size();
            this.currentPatternNo++;
            if (this.currentPatternNo > pattern_size - 1) {
                if (this.currentAniamtion.loop) {
                    this.currentPatternNo = 0;
                } // if
                else {
                    this.currentPatternNo = pattern_size - 1;
                    this.motionEnd = true;
                } // else
            } // if
            this.time = 0.0f;
        } // if
        return true;
    }

    public boolean changeMotion(int no) {
        this.motionEnd = false;
        this.currentPatternNo = 0;
        this.currentMotionNo = no;
        this.currentAniamtion = this.animations.get(no);
        return true;
    }

    public boolean create(List<SpriteAnimationPart> data) {
        this.animations = data;
        this.currentAniamtion = this.animations.get(0);
        return true;
    }
}