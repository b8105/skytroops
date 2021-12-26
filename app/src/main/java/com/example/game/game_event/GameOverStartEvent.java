package com.example.game.game_event;

import android.graphics.PointF;

import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.ui.UILabel;

public class GameOverStartEvent extends GameEvent{
    private UILabel mainImage = null;
    private PointF position = new PointF(
            Game.getDisplayRealSize().x * 0.5f,
            Game.getDisplayRealSize().y * 0.5f);

    public GameOverStartEvent(ImageResource imageResource){
        this.mainImage = new UILabel(
                imageResource,
                ImageResourceType.GameOverG,
                position);
    }

    @Override
    public boolean update(float deltaTime) {
//        this.mainImage.setPosition();
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
        this.mainImage.draw(out);
    }
}