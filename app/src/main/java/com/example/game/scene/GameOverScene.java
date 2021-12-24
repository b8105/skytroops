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

public class GameOverScene extends Scene {
    private SceneTransitionStateMachine transitionStateMachine = null;

    private Transform2D backgroundTransform;
    private Bitmap backgroundBitmap;
    private Point backgroundBitmapSize;

    private Transform2D leadrboardboxTransform;
    private Bitmap leadrboardboxBitmap;
    private Point leadrboardboxBitmapSize;

    private UIButton startButton;

    private Game game;
    private int gameScorer;

    public GameOverScene(Game game, ImageResource imageResource, Point screenSize) {
        super(game, screenSize);
        this.game = game;
        this.transitionStateMachine = new SceneTransitionStateMachine(game);

        startButton = new UIButton(
                imageResource,
                game.getResources(), R.drawable.restartbtn,
                new PointF(screenSize.x * 0.5f, screenSize.y * 0.5f),
                new Point(450, 150)
        );
        {
            backgroundTransform = new Transform2D();
            this.backgroundTransform.position.x = 0;
            this.backgroundTransform.position.y = 0;
            this.backgroundBitmapSize = new Point(screenSize.x, screenSize.y);
            this.backgroundBitmap = BitmapFactory.decodeResource(game.getResources(), R.drawable.gameover_background);
            this.backgroundBitmap = Bitmap.createScaledBitmap(
                    this.backgroundBitmap,
                    this.backgroundBitmapSize.x,
                    this.backgroundBitmapSize.y,
                    false);
        }

        leadrboardboxTransform = new Transform2D();
        this.leadrboardboxTransform.position.x = 100;
        this.leadrboardboxTransform.position.y = 100;
        this.leadrboardboxBitmapSize = new Point(316 * 3, 342 * 3);
        this.leadrboardboxBitmap = BitmapFactory.decodeResource(game.getResources(), R.drawable.leadrboardbox);
        this.leadrboardboxBitmap = Bitmap.createScaledBitmap(
                this.leadrboardboxBitmap,
                this.leadrboardboxBitmapSize.x,
                this.leadrboardboxBitmapSize.y,
                false);
    }

    public void setGameScorerValue(int gameScorer) {
        this.gameScorer = gameScorer;
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

        list.drawSprite(this.backgroundBitmap,
                this.backgroundTransform,
                new RenderSpriteInfo());
        list.drawSprite(this.leadrboardboxBitmap,
                this.leadrboardboxTransform,
                new RenderSpriteInfo());

        {
            Transform2D t = new Transform2D();
            t.position.x = 500.0f;
            t.position.y = 400.0f;
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
            list.drawText("Score = " + this.gameScorer, this.leadrboardboxTransform, paint);
        }
        this.startButton.draw(out);
    }

}