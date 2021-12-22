package com.example.game.stage;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.game.common.Transform2D;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderSpriteInfo;

public class StageRenderer {
    private Stage stage = null;

    public StageRenderer(Stage stage) {
        this.stage = stage;
    }

    private void drawScroll(RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(
                RenderLayerType.UI);
        Float scroll = this.stage.getScroll();

        Transform2D drawTransform = new Transform2D();
        drawTransform.position.x = 700;
        drawTransform.position.y = 100;

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(64);
        list.drawText(scroll.toString(), drawTransform, paint);
    }

    public void execute(RenderCommandQueue out) {
        assert (this.stage != null);

        RenderCommandList list = out.getRenderCommandList(
                RenderLayerType.BasicActor);

        Bitmap bitmap = this.stage.getBitmap();
//        Transform2D transform = this.stage.getTransform();
        Transform2D transform = new Transform2D();
        transform.position.y = this.stage.getScroll();
        //this.stage.getTransform();

        int h = bitmap.getHeight();
        int screenHeight = h;
        for (float y = ((int) transform.position.y % h) - h; y < screenHeight; y += h) {
            Transform2D t = new Transform2D();
            t.position.x = 0;
            t.position.y = y;
            list.drawSprite(
                    bitmap,
                    t,
                    new RenderSpriteInfo());

        } // for
        this.drawScroll(out);

    }
}