package com.example.game.scene;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.game.R;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.ui.UIButton;
import com.example.game.collision.detector.RectangleCollisionDetector;
import com.example.game.common.InputEvent;
import com.example.game.common.Transform2D;
import com.example.game.common.shape.Circle;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderSpriteInfo;
import com.example.game.ui.UILabel;

public class GameOverScene extends Scene {
    private SceneTransitionStateMachine transitionStateMachine = null;
    private UILabel background;
    private UILabel leadrboard;
    private UIButton startButton;
    private Game game;
    private int gameScore;


    Transform2D transform = new Transform2D();
    Paint paint = new Paint();

    public GameOverScene(Game game, ImageResource imageResource, Point screenSize) {
        super(game, screenSize);
        this.game = game;
        this.transitionStateMachine = new SceneTransitionStateMachine(game);

        PointF center = new PointF(Game.getDisplayRealSize().x * 0.5f,
                Game.getDisplayRealSize().y * 0.5f);
        this.startButton = new UIButton(imageResource,ImageResourceType.RestartButton,
                new PointF(screenSize.x * 0.5f, screenSize.y * 0.8f)
        );
        this.background = new UILabel(imageResource, ImageResourceType.GameOverBackground, center);
        this.leadrboard = new UILabel(imageResource, ImageResourceType.GameResultBackground,center);

        transform.position.x = 140.0f;
        transform.position.y = 700.0f;
        paint.setTextSize(90);
        paint.setColor(Color.BLACK);

    }

    public void setGameScorerValue(int gameScorer) {
        this.gameScore = gameScorer;

        if(Game.getHighScore() < this.gameScore){
            Game.setHighScore(this.gameScore);
        } // if

    }

    @Override
    public void input(InputEvent input) {
        if (!input.enable) {
            return;
        } // if

        float x = input.positionX;
        float y = input.positionY;
        RectangleCollisionDetector detector = new RectangleCollisionDetector();
        Circle touchCircle = new Circle(x, y, 16);

        switch (input.actionType) {
            case (MotionEvent.ACTION_DOWN):
                if (this.startButton.containCircle(touchCircle)) {
                    this.game.toTitleScene();
                    //this.transitionStateMachine.transition(TransitionStateType.Exit);
                } // if
                break;
            default:
        } // switch
    }

    @Override
    public void update(float deltaTime) {
        this.transitionStateMachine.update(deltaTime);
    }

    @Override
    public void draw(RenderCommandQueue out) {
        this.transitionStateMachine.drawTransitionEffect(out);
        RenderCommandList list = out.getRenderCommandList(
                RenderLayerType.Background2D);
        this.background.draw(list);
        this.leadrboard.draw(list);

        list.drawText("Score : " + this.gameScore, this.transform, paint);
        this.startButton.draw(out);
    }

}