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
    private Paint paint;
    private int textSize = 32;
    Transform2D textTransform = new Transform2D();

    public StageRenderer(Stage stage) {
        this.stage = stage;
        this.paint = new Paint();
        Transform2D textTransform = new Transform2D();
        this.textTransform.position.x = this.textSize;
        this.textTransform.position.y = this.textSize;
    }

    private void drawStageType(RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(
                RenderLayerType.UI);
        this.paint.setTextSize(this.textSize);
        {
            String text = "stage no : " + stage.getCurrentType().ordinal();
            list.drawText(text, this.textTransform, this.paint);
        }
    }

    public void execute(RenderCommandQueue out) {
        assert (this.stage != null);

        RenderCommandList list = out.getRenderCommandList(
                RenderLayerType.BasicActor);

        Bitmap bitmap = this.stage.getBitmap();
        Transform2D transform = new Transform2D();
        transform.position.y = this.stage.getScroll();

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

        this.drawStageType(out);
    }
}