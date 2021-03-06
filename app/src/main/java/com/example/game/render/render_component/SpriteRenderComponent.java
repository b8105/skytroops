package com.example.game.render.render_component;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;

import com.example.game.actor.Actor;
import com.example.game.collision.collision_component.CollisionComponent;
import com.example.game.common.Transform2D;
import com.example.game.component.ComponentType;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayer;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderRectangleInfo;
import com.example.game.render.info.RenderSpriteInfo;

public class SpriteRenderComponent extends RenderComponent {
    private Bitmap bitmap = null;
    private Rect sourceRect = null;
    private CollisionComponent collisionComponent = null;
    private boolean centerFlag = false;

    public SpriteRenderComponent(RenderLayer layer) {
        super(layer);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public PointF getBitmapSize() {
        assert (this.bitmap != null);
        return new PointF(this.bitmap.getWidth(), this.bitmap.getHeight());
    }

    public Rect getSourceRect() {
        if (this.sourceRect == null) {
            return new Rect(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());
        } // if
        return this.sourceRect;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.SpriteRender;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void activate() {

    }

    @Override
    public void inactivate() {

    }

    public boolean isCenterFlag() {
        return this.centerFlag;
    }

    public void onComponentInitialize(Actor owner) {
        this.collisionComponent = owner.getComponent(
                ComponentType.EnemyCollision,
                ComponentType.PlaneCollision);
        if (this.collisionComponent == null) {
            this.collisionComponent = owner.getComponent(
                    ComponentType.BulletCollision);
        } // if
    }

    @Override
    public void execute(RenderCommandQueue out) {
        Actor owner = super.getOwner();
        Transform2D transform = new Transform2D(
                owner.getPosition(),
                owner.getRotation(),
                owner.getScale()
        );
        {
            RenderCommandList list = out.getRenderCommandList(RenderLayerType.BasicActorDebug);
            if (this.collisionComponent != null) {
                RenderRectangleInfo info = new RenderRectangleInfo(Color.RED);
                list.drawRectangle(
                        this.collisionComponent.getCollisionRectangle(),
                        info
                );
            } // if
        }
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.BasicActor);
        RenderSpriteInfo info = new RenderSpriteInfo(this.getSourceRect());
        info.center = this.isCenterFlag();
        list.drawSprite(
                this.bitmap,
                transform,
                info
        );
    }
}