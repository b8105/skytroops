package com.example.game.render.hp_renderer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;

import com.example.game.parameter.HpParameter;
import com.example.game.R;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.Transform2D;
import com.example.game.common.shape.Rectangle;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderSpriteInfo;
import com.example.game.render.render_component.PlaneHpBarRenderComponent;

public class PlayerPlaneHpBarRenderer extends PlaneHpBarRenderer {
    private PlaneHpBarRenderComponent owner;
    private Bitmap bar;
    private Transform2D transform = null;

    public PlayerPlaneHpBarRenderer(PlaneHpBarRenderComponent owner,
                                   Resources resources) {
        this.owner = owner;
        this.bar = super.constructBitmap(resources, R.drawable.playerhealthbar,
                BitmapSizeStatic.playerHpBar);
        this.transform = new Transform2D();
    }

    public Point getSize(){
        return new Point(
                BitmapSizeStatic.playerHpBar.x,
                BitmapSizeStatic.playerHpBar.y
        );
    }
    private void drawBar(HpParameter hpParameter, RenderCommandList out) {
        Rectangle rectangle = new Rectangle();
        rectangle.left = 0.0f;
        float barLenght = bar.getWidth() - BitmapSizeStatic.playerHpBarIcon.x;
        barLenght *= hpParameter.calcPercent();
        rectangle.right = BitmapSizeStatic.playerHpBarIcon.x + barLenght;


        rectangle.top = 0.0f;
        rectangle.bottom = BitmapSizeStatic.playerHpBarIcon.y;// bar.getHeight();
        RenderSpriteInfo info = new RenderSpriteInfo(rectangle);
        out.drawSprite(this.bar, this.transform,info);
    }

    public void clacPosition() {
        if (this.owner != null) {
            this.transform.position.x = 0.0f;
            this.transform.position.y = 200.0f;
        } // if
    }

    public void execute(HpParameter hpParameter, RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.UI);
        this.clacPosition();
        this.drawBar(hpParameter,list);
    }
}
