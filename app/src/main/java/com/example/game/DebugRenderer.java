package com.example.game;

import android.graphics.Paint;

import com.example.game.common.Transform2D;
import com.example.game.effect.EffectSystem;
import com.example.game.effect.EffectType;
import com.example.game.game.ActorContainer;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;

public class DebugRenderer {
    public void execute(ActorContainer actorContainer,
                          EffectSystem effectSystem, RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.UIDebug);
        Paint p = new Paint();
        p.setTextSize(60);

        if(actorContainer.getMainChara() == null){
            //fail.draw(out);
        } // if

        {
            Integer size = actorContainer.getActors().size();
            String s = "all actor count = " + size.toString();
            Transform2D t = new Transform2D();
            t.position.x = 300;
            t.position.y = 400;
            list.drawText(s, t, p);
        }
        {
            Integer size = actorContainer.getEnemies().size();
            String s = "enemy count = " + size.toString();
            Transform2D t = new Transform2D();
            t.position.x = 300;
            t.position.y = 500;
            list.drawText(s, t, p);
        }

        {
            Integer size = actorContainer.getBullets().size();
            String s = "all bullet count = " + size.toString();
            Transform2D t = new Transform2D();
            t.position.x = 300;
            t.position.y = 600;
            list.drawText(s, t, p);
        }
        {
            Integer size = actorContainer.getPlayerBullets().size();
            String s = "player bullet count = " + size.toString();
            Transform2D t = new Transform2D();
            t.position.x = 300;
            t.position.y = 700;
            list.drawText(s, t, p);
        }
        {
            Integer size = actorContainer.getEnemyBullets().size();
            String s = "enemy bullet count = " + size.toString();
            Transform2D t = new Transform2D();
            t.position.x = 300;
            t.position.y = 800;
            list.drawText(s, t, p);
        }

        {
            Integer size = effectSystem.getEffectList(EffectType.Score).size();
            String s = "score effect count = " + size.toString();
            Transform2D t = new Transform2D();
            t.position.x = 300;
            t.position.y = 900;
            list.drawText(s, t, p);
        }
        {
            Integer size = effectSystem.getEffectList(EffectType.Explosion).size();
            String s = "explosion effect count = " + size.toString();
            Transform2D t = new Transform2D();
            t.position.x = 300;
            t.position.y = 1000;
            list.drawText(s, t, p);
        }

        {
            Integer size = effectSystem.getEffectList(EffectType.Explosion).size();
            String s = "";

            if(actorContainer.getMainChara() != null && actorContainer.getMainChara().getInvincibleParameter().isActive()){
                s = "invincible is active";
            } // if
            else {
                s = "invincible is inactive";
            } // else
            Transform2D t = new Transform2D();
            t.position.x = 300;
            t.position.y = 1200;
            list.drawText(s, t, p);
        }
    }
}