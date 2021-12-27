package com.example.game.ui;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;

import com.example.game.common.Transform2D;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;

public class UIText {
    private String text;
    private Transform2D transform;
    private int textSize = 256;
    private Paint paint;
    private Typeface font;
    private boolean centerFlag = false;
    private boolean rightFlag = false;
    private int maxSize = 0;

    public UIText(String text, PointF position) {
        this.text = text;
        this.transform = new Transform2D();
        this.paint = new Paint();

        this.transform.position.x = position.x;
        this.transform.position.y = position.y;
    }
    public UIText(String text, PointF position, int size) {
        this.text = text;
        this.transform = new Transform2D();
        this.paint = new Paint();
        this.transform.position.x = position.x;
        this.transform.position.y = position.y;
        this.textSize = size;

        this.paint.setTextSize(this.textSize);
    }

    public UIText(String text, PointF position, int size, Color color) {
        this.text = text;
        this.transform = new Transform2D();
        this.paint = new Paint();
        this.transform.position.x = position.x;
        this.transform.position.y = position.y;
        this.textSize = size;

        this.paint.setTextSize(this.textSize);
        this.paint.setColor(Color.BLACK);
    }

    public UIText(String text, PointF position, int size, int color, Resources resources, int fontId) {
        this.text = text;
        this.transform = new Transform2D();
        this.paint = new Paint();
        this.transform.position.x = position.x;
        this.transform.position.y = position.y;
        this.textSize = size;

        this.paint.setTextSize(this.textSize);
        this.paint.setColor(color);
        this.font = resources.getFont(fontId);
        this.paint.setTypeface(this.font);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCenterFlag(boolean centerFlag) {
        this.centerFlag = centerFlag;
    }

    public void setRightFlag(boolean rightFlag, int maxSize) {
        this.rightFlag = rightFlag;
        this.maxSize = maxSize;
    }

    public void draw(RenderCommandQueue out) {
        if(this.rightFlag){
            Transform2D transform = new Transform2D();
            transform.position.x = this.transform.position.x;
            transform.position.y = this.transform.position.y;

            transform.position.x += (this.maxSize - this.text.length())  * (textSize * 0.5f);

            RenderCommandList list = out.getRenderCommandList(RenderLayerType.UI);
            list.drawText(this.text, transform, this.paint);
        } // if
        else{
            RenderCommandList list = out.getRenderCommandList(RenderLayerType.UI);
            list.drawText(this.text, this.transform, this.paint);
        } // else
    }
    public void draw(RenderCommandList out) {
        out.drawText(this.text, this.transform, this.paint);
    }
}