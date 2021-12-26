package com.example.game.scene;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.game.R;
import com.example.game.collision.detector.RectangleCollisionDetector;
import com.example.game.common.InputEvent;
import com.example.game.common.Transform2D;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandList;
import com.example.game.render.RenderCommandQueue;
import com.example.game.render.RenderLayerType;
import com.example.game.render.info.RenderRectangleInfo;
import com.example.game.render.info.RenderSpriteInfo;
import com.example.game.scene.transition_action.TransitionAction;
import com.example.game.scene.transition_action.TransitionActionResult;
import com.example.game.scene.transition_action.TransitionActionResultType;
import com.example.game.scene.transition_action.TransitionEnterAction;
import com.example.game.scene.transition_action.TransitionExitAction;
import com.example.game.scene.transition_action.TransitionNoneAction;
import com.example.game.scene.transition_state.TransitionEnterState;
import com.example.game.scene.transition_state.TransitionExitState;
import com.example.game.scene.transition_state.TransitionNoneState;
import com.example.game.scene.transition_state.TransitionState;
import com.example.game.scene.transition_state.TransitionStateType;
import com.example.game.common.shape.Circle;
import com.example.game.common.shape.Rectangle;
import com.example.game.utility.StateMachine;
import com.example.game.utility.StopWatch;
import com.example.game.utility.animation.SpriteAnimation;
import com.example.game.utility.animation.SpriteAnimationPart;
import com.example.game.utility.animation.SpriteAnimationPartPattern;

import java.util.ArrayList;
import java.util.List;

public class TitleScene extends Scene {
    private SceneTransitionStateMachine transitionStateMachine = null;
    private Transform2D transform = new Transform2D();
    private Bitmap background;

    private Transform2D startButtonTransform;
    private Bitmap startButtonBitmap;
    private Point startButtonBitmapSize;

    Transform2D scoreTransform = new Transform2D();
    Paint scorePaint = new Paint();

    void constructStartButton(Game game) {
        int screenX = super.getScreenSize().x;
        int screenY = super.getScreenSize().y;

        this.scoreTransform .position.x = 640.0f;
        this.scoreTransform .position.y = 50.0f;
        this.scorePaint.setTextSize(50);
        this.scorePaint.setColor(Color.BLACK);

        this.transitionStateMachine = new SceneTransitionStateMachine(game);


        this.startButtonTransform = new Transform2D();
        this.startButtonTransform.position.x = screenX * 0.5f;
        this.startButtonTransform.position.y = screenY * 0.5f;
        this.startButtonBitmapSize = new Point(450, 150);
        this.startButtonBitmap = BitmapFactory.decodeResource(game.getResources(), R.drawable.start_flybtn);
        this.startButtonBitmap = Bitmap.createScaledBitmap(
                this.startButtonBitmap,
                this.startButtonBitmapSize.x,
                this.startButtonBitmapSize.y,
                false);
    }

    public TitleScene(Game game, Point screenSize) {
        super(game, screenSize);
        this.constructStartButton(game);

        this.background = BitmapFactory.decodeResource(game.getResources(), R.drawable.title_background);
        this.background = Bitmap.createScaledBitmap(
                background,
                this.getScreenSize().x,
                this.getScreenSize().y,
                false);
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
                if (detector.CollisionCircle(this.getStartButtonRectangle(), touchCircle)) {
                    this.transitionStateMachine.transition(TransitionStateType.Exit);
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

        {
            RenderCommandList list = out.getRenderCommandList(
                    RenderLayerType.Background2D);
            list.drawSprite(
                    this.background,
                    this.transform,
                    new RenderSpriteInfo(Color.RED));
        }
        RenderCommandList list = out.getRenderCommandList(
                RenderLayerType.BasicActor);

        this.drawStartButton(out.getRenderCommandList(RenderLayerType.BasicActor));

        if(Game.getHighScore() != 0){
            list.drawText("Score : " + Game.getHighScore(), this.scoreTransform, this.scorePaint);
        } // if

    }

    Rectangle getStartButtonRectangle() {
        Transform2D transform = new Transform2D(this.startButtonTransform);
        transform.position.x -= this.startButtonBitmap.getWidth() * 0.5f;
        transform.position.y -= this.startButtonBitmap.getHeight() * 0.5f;

        Rectangle rect = new Rectangle(
                transform.position.x,
                transform.position.y,
                transform.position.x + this.startButtonBitmap.getWidth(),
                transform.position.y + this.startButtonBitmap.getHeight()
        );
        return rect;
    }

    void drawStartButton(RenderCommandList list) {
        Transform2D transform = new Transform2D(this.startButtonTransform);
        transform.position.x -= this.startButtonBitmap.getWidth() * 0.5f;
        transform.position.y -= this.startButtonBitmap.getHeight() * 0.15f;

        //list.drawRectangle(this.getStartButtonRectangle(), new RenderRectangleInfo());
        list.drawSprite(
                this.startButtonBitmap,
                transform,
                new RenderSpriteInfo());
    }
}