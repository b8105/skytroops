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

public class EnemyPlaneHpBarRenderer extends PlaneHpBarRenderer {
    private PlaneHpBarRenderComponent owner;
    private Bitmap frame;
    private Bitmap bar;
    private Transform2D transform = null;

    public EnemyPlaneHpBarRenderer(PlaneHpBarRenderComponent owner,
                                   Resources resources) {
        this.owner = owner;
        this.bar = super.constructBitmap(resources, R.drawable.redhealthbar,BitmapSizeStatic.enemyHpBar);
        this.frame = super.constructBitmap(resources, R.drawable.healthframe,BitmapSizeStatic.enemyHpBar);
        this.transform = new Transform2D();
    }

    public Point getSize(){
        return new Point(
                BitmapSizeStatic.enemyHpBar.x,
                BitmapSizeStatic.enemyHpBar.y
        );
    }
    private void drawFrame(RenderCommandList out) {
        out.drawSprite(frame, this.transform, new RenderSpriteInfo());
    }
    private void drawBar(HpParameter hpParameter,RenderCommandList out) {
        Rectangle rectangle = new Rectangle();
        rectangle.left = 0.0f;
        rectangle.right = BitmapSizeStatic.enemyHpBar.x * hpParameter.calcPercent();
        rectangle.top = 0.0f;
        rectangle.bottom = BitmapSizeStatic.enemyHpBar.y;

        RenderSpriteInfo info = new RenderSpriteInfo(rectangle);
        out.drawSprite(this.bar, this.transform, info);
    }

    public void clacPosition() {
        if (this.owner != null) {
            this.transform.position = this.owner.getOwner().getPosition();
            this.transform.position.x += BitmapSizeStatic.player.x * 0.5f;
            this.transform.position.y += BitmapSizeStatic.player.y;
            this.transform.position.x -= BitmapSizeStatic.enemyHpBar.x * 0.5f;
            this.transform.position.y -= BitmapSizeStatic.enemyHpBar.y * 0.5f;
        } // if
    }

    public void execute(HpParameter hpParameter,RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.UI);
        this.clacPosition();
        this.drawFrame(list);
        this.drawBar(hpParameter,list);
    }
}