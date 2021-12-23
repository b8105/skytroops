package com.example.game.render.hp_renderer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;

import com.example.game.R;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.common.Transform2D;
import com.example.game.common.shape.Rectangle;
import com.example.game.parameter.HpParameter;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderSpriteInfo;
import com.example.game.render.render_component.PlaneHpBarRenderComponent;

public class BossEnemyPlaneHpBarRenderer extends PlaneHpBarRenderer {
    private PlaneHpBarRenderComponent owner;
    private Bitmap bar;
    private Transform2D transform = null;

    public BossEnemyPlaneHpBarRenderer(PlaneHpBarRenderComponent owner,
                                   Resources resources) {
        this.owner = owner;
        this.bar = super.constructBitmap(resources, R.drawable.redhealthbar, BitmapSizeStatic.bossEnemyHpBar);
        this.transform = new Transform2D();
    }

    public Point getSize(){
        return new Point(
                BitmapSizeStatic.bossEnemyHpBar.x,
                BitmapSizeStatic.bossEnemyHpBar.y
        );
    }
    private void drawBar(HpParameter hpParameter, RenderCommandList out) {
        Point size = this.getSize();

        Rectangle rectangle = new Rectangle();
        rectangle.left = 0.0f;
        rectangle.right = size.x * hpParameter.calcPercent();
        rectangle.top = 0.0f;
        rectangle.bottom = size.y;

        RenderSpriteInfo info = new RenderSpriteInfo(rectangle);
        out.drawSprite(this.bar, this.transform, info);
    }

    public void clacPosition() {
        if (this.owner != null) {
            Point size = this.getSize();

            this.transform.position = this.owner.getOwner().getPosition();
            this.transform.position.x += BitmapSizeStatic.boss.x * 0.5f;
            this.transform.position.x -= size.x * 0.5f;
            this.transform.position.y += BitmapSizeStatic.boss.y * 0.65f;
        } // if
    }

    public void execute(HpParameter hpParameter, RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.UI);
        this.clacPosition();
        this.drawBar(hpParameter,list);
    }
}
