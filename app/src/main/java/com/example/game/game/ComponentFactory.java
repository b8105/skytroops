package com.example.game.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.game.render.RenderLayer;
import com.example.game.render.render_component.PlaneSpriteRenderComponent;
import com.example.game.render.render_component.SpriteRenderComponent;

public class ComponentFactory {
private     Resources resources = null;
    private     RenderLayer renderLayer= null;

    public ComponentFactory(
            Resources resources,
            RenderLayer renderLayer
    ){
        this.resources = resources;
        this.renderLayer = renderLayer;
    }
    public SpriteRenderComponent createSpriteRenderComponent(int bitmapSize, int drawableId) {
        SpriteRenderComponent spriteRenderComponent = new SpriteRenderComponent(renderLayer);

        Bitmap bitmap = BitmapFactory.decodeResource(this.resources, drawableId);
        bitmap = Bitmap.createScaledBitmap(
                bitmap,
                bitmapSize,
                bitmapSize,
                false);
        spriteRenderComponent.setBitmap(bitmap);

        return spriteRenderComponent;
    }
    public PlaneSpriteRenderComponent createPlaneSpriteRenderComponent(int bitmapSize, int drawableId) {
        PlaneSpriteRenderComponent spriteRenderComponent = new PlaneSpriteRenderComponent(renderLayer);

        Bitmap bitmap = BitmapFactory.decodeResource(this.resources, drawableId);
        bitmap = Bitmap.createScaledBitmap(
                bitmap,
                bitmapSize,
                bitmapSize,
                false);
        spriteRenderComponent.setBitmap(bitmap);

        return spriteRenderComponent;
    }

}
