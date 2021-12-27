package com.example.game.game_event;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;

import com.example.game.R;
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
import com.example.game.ui.plane_upgrade.UIUpgradePanel;
import com.example.game.utility.StopWatch;

public class MissionSuccessEvent extends GameEvent {
    private StopWatch existTimer = new StopWatch(2.0f);
    private float time = 1.6f;
    private Typeface font;
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
    private boolean endFlag = false;
    private UILabel enemyImage = null;

    public MissionSuccessEvent(GamePlayScene gamePlayScene,
                                   GameScorer gameScorer,
                                   Resources resource,
                                   ImageResource imageResource) {
        this.transform = new Transform2D();
        this.transformDestroyedCount = new Transform2D();
        this.transformScoreText = new Transform2D();
        this.paint = new Paint();
        this.gamePlayScene = gamePlayScene;

        this.text = "";
        this.destroyedCountText = "    x    " +  gameScorer .getEnemyDestoryCountOnStage();
        this.scoreText = gameScorer .getEnemyDestoryScoreOnStage() + " Pt";

        this.transform.position.x = Game.getDisplayRealSize().x * 0.25f;
        this.transform.position.y = Game.getDisplayRealSize().x * 0.65f;
        this.transformDestroyedCount.position.x = this.transform.position.x;
        this.transformDestroyedCount.position.y = this.transform.position.y + (this.textSize * 2);

        this.enemyImage = new UILabel(
                imageResource, ImageResourceType.BasicEnemyPlane, new PointF(
                this.transformDestroyedCount.position.x,
                this.transformDestroyedCount.position.y
        )
        );

//        this.activateUpgradePanel(uiUpgradePanel);

        this.transformScoreText.position.x = this.transform.position.x;
        this.transformScoreText.position.y = this.transformDestroyedCount.position.y + (this.textSize * 2);

        this.paint.setColor(Color.BLACK);
        this.paint.setTextSize(this.textSize);
        this.font = resource.getFont(R.font.luckiest_guy_regular);
        this.paint.setTypeface(this.font);


        background = new UILabel(
                imageResource, ImageResourceType.ClearInfoBackground,
                new PointF(
                        (Game.getDisplayRealSize().x * 0.5f) ,
                        this.transform.position.y + 160
                ));
    }
    @Override
    public boolean update(float deltaTime) {
        if(this.existTimer.tick(deltaTime)){
            this.gamePlayScene.sceneExit();
            return true;
        } // if
        return false;
    }

    @Override
    public void draw(RenderCommandQueue out) {
        RenderCommandList list = out.getRenderCommandList(RenderLayerType.FrontEvent);

        background.draw( out.getRenderCommandList(RenderLayerType.FrontEvent));

        list.drawText(this.text, this.transform, this.paint);
        list.drawText(this.destroyedCountText, this.transformDestroyedCount, this.paint);
        this.enemyImage.draw(list);

        int scoreTextLength = this.scoreText.length();
        Transform2D transform = new Transform2D();
        float offsetX = ((float)this.textSize * 0.5f) * ((float)scoreTextLength * 0.5f);
        transform.position.x = Game.getDisplayRealSize().x * 0.5f - offsetX;
        transform.position.y = this.transformScoreText.position.y;
        list.drawText(this.scoreText, transform, this.paint);
    }
}