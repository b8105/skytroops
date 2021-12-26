package com.example.game.collision.collision_component;

import android.graphics.PointF;
import android.graphics.Rect;

import com.example.game.actor.Actor;
import com.example.game.actor.ActorState;
import com.example.game.collision.CollisionInfo;
import com.example.game.collision.detector.RectangleCollisionDetector;
import com.example.game.collision.visitor.BulletCollisionComponentVisitor;
import com.example.game.collision.CollisionLayer;
import com.example.game.collision.Collisionable;
import com.example.game.collision.visitor.EnemyCollisionComponentVisitor;
import com.example.game.collision.visitor.PlaneCollisionComponentVisitor;
import com.example.game.collision.visitor.StageCollisionComponentVisitor;
import com.example.game.common.shape.Rectangle;
import com.example.game.component.Component;
import com.example.game.component.ComponentType;
import com.example.game.render.render_component.SpriteRenderComponent;

import java.util.ArrayList;
import java.util.List;

abstract public class CollisionComponent implements Collisionable, Component {
    private List<Collisionable> collisioned = new ArrayList<Collisionable>();
    private CollisionLayer layer = null;
    private Actor owner = null;
    private SpriteRenderComponent spriteRenderComponent = null;
    private PointF collisionRectSizeOffset = new PointF();
    private boolean active = true;

    public CollisionComponent(CollisionLayer layer) {
        this.layer = layer;
        this.layer.add(this);
    }

    @Override
    public void onCollisionableDestroy() {
        this.layer.remove(this);
    }

    public void setCollisionRectSizeOffset(float sizeX, float sizeY) {
        this.collisionRectSizeOffset.x = sizeX;
        this.collisionRectSizeOffset.y = sizeY;
    }

    public void setCollisionRectSizeOffset(float size) {
        this.setCollisionRectSizeOffset(size, size);
    }

    public void setSpriteRenderComponent(SpriteRenderComponent spriteRenderComponent) {
        this.spriteRenderComponent = spriteRenderComponent;
    }

    public Rectangle getCollisionRectangle() {
        Rect sourceRect = this.getSpriteRenderComponent().getSourceRect();
        PointF position = this.getOwner().getPosition();
        PointF expansion = this.getCollisionRectSizeOffset();
        Rectangle rect = new Rectangle(0, 0, sourceRect.width(), sourceRect.height());
        rect.offset((int) position.x, (int) position.y);
        if (this.spriteRenderComponent.isCenterFlag()) {
            rect.offset(-rect.getSize().x *0.5f,
                    -rect.getSize().y * 0.5f);
        } // if
        rect.expansion(expansion.x, expansion.y);
        return rect;
    }

    public boolean isActive() {
        return this.active;
    }

    protected boolean isCollisionRect(
            Collisionable my,
            Collisionable target,
            Actor myOwner,
            Actor targetOwner,
            CollisionInfo info) {
        // Actorのどちらかが終了状態であればぶつからない
        if (myOwner.getActorState() == ActorState.End || targetOwner.getActorState() == ActorState.End) {
            return false;
        } // if
        // 同じチームに所属しているActor同士はぶつからない
        else if (myOwner.getTag().equals(targetOwner.getTag())) {
            return false;
        } // if

        // 矩形での衝突判定をとる
        Rectangle myRect = my.getCollisionRectangle();
        Rectangle targetRect = target.getCollisionRectangle();
        RectangleCollisionDetector d = new RectangleCollisionDetector();
        if (d.CollisionRectangle(myRect, targetRect)) {
            info.targetTag = targetOwner.getTag();
            return true;
        } // if
        return false;
    }

    protected PointF getCollisionRectSizeOffset() {
        return this.collisionRectSizeOffset;
    }


    public void activate(){
        this.active = true;
    }
    public void inactivate(){
        this.active = false;
    }


    @Override
    public void visitorAccept(BulletCollisionComponentVisitor visitor) {
        visitor.visit((BulletCollisionComponent) this);
    }

    @Override
    public void visitorAccept(EnemyCollisionComponentVisitor visitor) {
        visitor.visit((EnemyCollisionComponent) this);
    }

    @Override
    public void visitorAccept(PlaneCollisionComponentVisitor visitor) {
        visitor.visit((PlaneCollisionComponent) this);
    }

    @Override
    public void visitorAccept(StageCollisionComponentVisitor visitor) {
        visitor.visit((StageCollisionComponent) this);
    }

    @Override
    final public boolean existCollisioned(Collisionable target) {
        // 線形走査
        for (Collisionable com : this.collisioned) {
            if (com == target) {
                return true;
            } // if
        } // for
        return false;
    }

    @Override
    final public void addCollisioned(Collisionable target) {
        this.collisioned.add(target);
    }

    @Override
    final public void removeCollisioned(Collisionable target) {
        this.collisioned.remove(target);
    }

    @Override
    public void setOwner(Actor owner) {
        this.owner = owner;
    }

    @Override
    public Actor getOwner() {
        return this.owner;
    }

    public SpriteRenderComponent getSpriteRenderComponent() {
        return this.spriteRenderComponent;
    }

    @Override
    public void onComponentInitialize(Actor owner) {
        this.spriteRenderComponent = owner.getComponent(ComponentType.SpriteRender);
    }

    @Override
    public void onComponentDestory(Actor owner) {
        this.onCollisionableDestroy();
    }
}
