package com.example.game.render;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.example.game.common.Activatable;
import com.example.game.common.Transform2D;
import com.example.game.game.GameSystem;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.ui.UILabel;
import com.example.game.ui.UIText;

public class ScoreRenderer implements Activatable {
    private Transform2D transform;
    private Paint paint;
    private int textSize = 0;
    private int textMargin = 0;
    private float offsetX = 0.0f;
    private UILabel background = null;
    private boolean active = true;

    public ScoreRenderer(ImageResource imageResource) {
        float positionY = 140.0f;

        this.paint = new Paint();
        this.paint.setColor(Color.BLACK);
        this.textSize = 42;
        this.paint.setTextSize(this.textSize );
        this.textMargin = 4;
        this.offsetX = this.textSize * 0.5f;

        background = new UILabel(
                imageResource, ImageResourceType.ScoreBackground,
                new PointF(950.0f, positionY));

        this.transform = new Transform2D();
        transform.position.x = 620;
        transform.position.y = positionY + (this.textSize*0.5f);
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
        this.background.draw(out);
        list.drawText(text, this.transform, this.paint);
    }
    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void activate() {
        this.active = true;
    }

    @Override
    public void inactivate() {
        this.active = false;
    }
}