package com.example.game.render;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.example.game.common.Transform2D;
import com.example.game.game.GameSystem;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.ui.UILabel;

public class ScoreRenderer {
    private Transform2D transform;
    private Paint paint;
    private int textSize = 0;
    private int textMargin = 0;
    private float offsetX = 0.0f;
    private UILabel background = null;

    public ScoreRenderer(ImageResource imageResource) {
        this.transform = new Transform2D();
        transform.position.x = 700;
        transform.position.y = 120;

        this.paint = new Paint();
        this.paint.setColor(Color.BLACK);
        this.paint.setTextSize(72);

        this.textSize = 72;
        this.textMargin = 4;
        this.offsetX = this.textSize * 0.5f;

        background = new UILabel(
                imageResource, ImageResourceType.ScoreBackground,
                new PointF(820.0f, 90.0f));
    }

    private void clacPosition(String text){
        float posX = Game.getDisplayRealSize().x;
        posX -= (this.textSize + this.textMargin )  * 0.5f * text.length();
        posX -= this.offsetX;
        transform.position.x  = posX;
    }


    public void execute(GameSystem gameSystem, RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.UI);
        String text = ((Integer) (gameSystem.getGameScorer().getGameScore())).toString();
        this.clacPosition(text);
        background.draw(out);
        list.drawText(text, this.transform, this.paint);
    }
}