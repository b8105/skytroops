package com.example.game.utility.animation;

//! スプライトアニメーションの
//! UV座標を指定するときのパターンデータです
//! 例えば　wait = 6 row 1 col 0 なら
//! 画像を指定のサイズで区切った時に
//! 1列目 0行目の位置から指定の範囲(サイズ)を 6フレーム(0.01667 * 6　秒)　表示します
//! サイズ指定についてはSpriteAnimationPartが担います
public class SpriteAnimationPartPattern {
    public int wait = 0;
    public int row = 0;
    public int col = 0;

    public SpriteAnimationPartPattern(int wait, int row, int col) {
        this.wait = wait;
        this.row = row;
        this.col = col;
    }
}