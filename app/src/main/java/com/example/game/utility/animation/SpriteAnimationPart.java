package com.example.game.utility.animation;

import java.util.ArrayList;
import java.util.List;

//! スプライトアニメーションの１つ分のアニメーションデータです
//! 画像のUVを指定して表示するためのデータ
//! １枚の画像をサイズで区切り(width,height)
//! 指定の位置(offset)から x列 y行 tフレーム...表示します
public class SpriteAnimationPart {
    public String name = "";
    public int offsetX = 0;
    public int offsetY = 0;
    public int width = 0;
    public int height = 0;
    public boolean loop = false;
    public List<SpriteAnimationPartPattern> pattern = null;

    public SpriteAnimationPart() {
        this.name = "";
        this.offsetX = 0;
        this.offsetY = 0;
        this.width = 0;
        this.height = 0;
        this.loop = false;
        this.pattern = new ArrayList<>();
    }

    public void setParameter(
            String name,
            int offsetX,
            int offsetY,
            int width,
            int height,
            boolean loop,
            List<SpriteAnimationPartPattern> pattern) {
        this.name = name;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.loop = loop;
        this.pattern = pattern;
    }
}