package com.example.game.scene;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.game.common.InputEvent;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;

public class Scene {
    private Game game;
    private Point screenSize = new Point();

    Scene(Game game, Point screenSize) {
        this.game = game;
        this.screenSize.x = screenSize.x;
        this.screenSize.y = screenSize.y;
    }

    protected Game GetGame() {
        return this.game;
    }

    protected Point getScreenSize() {
        return this.screenSize;
    }



    //! 入力
    public void input(InputEvent input) {
    }

    //! 更新
    public void update(float deltaTime) {
    }

    //! 描画
    public void draw(RenderCommandQueue out) {
    }
}