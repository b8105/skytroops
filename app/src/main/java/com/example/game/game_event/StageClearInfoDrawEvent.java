package com.example.game.game_event;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.example.game.common.Transform2D;
import com.example.game.game.GameScorer;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.scene.GamePlayScene;
import com.example.game.ui.UILabel;
import com.example.game.utility.StopWatch;

public class StageClearInfoDrawEvent extends GameEvent {
    private float time = 1.6f;
    private StopWatch existTimer;

    private int textSize = 96;

    private Transform2D transform;
    private Transform2D transformDestroyedCount;
    private Transform2D transformScoreText;
    private Paint paint;

    private GamePlayScene gamePlayScene;

    private String text;
    private String destroyedCountText;
    private String scoreText;

    private UILabel background = null;


    public StageClearInfoDrawEvent(GamePlayScene gamePlayScene,
                                   GameScorer gameScorer,
                                   ImageResource imageResource) {
        this.existTimer = new StopWatch(time);
        this.transform = new Transform2D();
        this.transformDestroyedCount = new Transform2D();
        this.transformScoreText = new Transform2D();
        this.paint = new Paint();
        this.gamePlayScene = gamePlayScene;

        this.text = "CLEAR!";
        this.destroyedCountText = "倒した数 : " +  gameScorer .getEnemyDestoryCountOnStage();
        this.scoreText = "スコア : "+ gameScorer .getEnemyDestoryScoreOnStage();

        this.transform.position.x = Game.getDisplayRealSize().x * 0.25f;
        this.transform.position.y = Game.getDisplayRealSize().x * 0.5f;
        this.transformDestroyedCount.position.x = this.transform.position.x;
        this.transformDestroyedCount.position.y = this.transform.position.y + (this.textSize * 2);
        this.transformScoreText.position.x = this.transform.position.x;
        this.transformScoreText.position.y = this.transformDestroyedCount.position.y + (this.textSize * 2);

        this.paint.setColor(Color.BLACK);
        this.paint.setTextSize(this.textSize);



        background = new UILabel(
                imageResource, ImageResourceType.ClearInfoBackground,
                new PointF(
                        (Game.getDisplayRealSize().x * 0.5f) ,
                        this.transform.position.y + 160
                ));

    }

    @Override
    public boolean update(float deltaTime) {
        if (existTimer.tick(deltaTime)) {
            this.gamePlayScene.createToNextStageEvent();
            return true;
        } // if
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.UI);

        background.draw(out);
        list.drawText(this.text, this.transform, this.paint);
        list.drawText(this.destroyedCountText, this.transformDestroyedCount, this.paint);
        list.drawText(this.scoreText, this.transformScoreText, this.paint);
    }
}