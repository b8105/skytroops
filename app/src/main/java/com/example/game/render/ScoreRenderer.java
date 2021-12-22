package com.example.game.render;

import android.graphics.Paint;

import com.example.game.common.Transform2D;
import com.example.game.game.GameSystem;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;

public class ScoreRenderer {
    public ScoreRenderer(){}

    public void execute(GameSystem gameSystem, RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.UI);
        Paint p = new Paint();
        p.setTextSize(60);

        String s = "score = " + ( (Integer)(gameSystem.getGameScorer().getGameScore())) .toString();
        Transform2D t = new Transform2D();
        t.position.x = 700;
        t.position.y = 200;
        list.drawText(s, t, p);
    }
}