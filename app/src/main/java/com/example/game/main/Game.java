package com.example.game.main;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;

import com.example.game.R;
import com.example.game.common.InputEvent;
import com.example.game.common.InputTouchType;
import com.example.game.common.shape.Circle;
import com.example.game.game.resource.ImageResource;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GameResultScene;
import com.example.game.scene.GamePlayScene;
import com.example.game.scene.Scene;
import com.example.game.scene.TitleScene;
import com.example.game.ui.UIButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//! ゲームのエントリポイントです
//! 実行スレッドの中で　入力->処理->出力 繰り返します
//! ゲームシーンの管理やその中で使用するクラスを保持します
public class Game extends SurfaceView implements Runnable {
    public static PointF displayRealSize = new PointF();
    //! ユーザのハイスコア
    public static int highScore = 0;
    //! 全体のスコアホルダー
    public static LeaderBoard leaderBoard = new LeaderBoard();
    //! リソース獲得のためにキャッシュします
    public MainActivity activity;
    //! 実行スレッドパラメータ
    private Thread thread;
    private boolean running = true;
    private float frameTime = 1.0f / 60.0f;
    private float frameTimeCoefficientForGame = 1.0f;
    //! ゲーム全体で使用するシステムのパラメータ
    private RenderCommandQueue renderCommandQueue;
    private ImageResource imageResource;
    private InputEvent inputEvent = new InputEvent();
    //! 今回のゲームはタッチ操作しかないのでboolean変数１つ
    private boolean enableTouch = false;
    private boolean prevTouch = false;
    private boolean currentTouch = false;

    //! シーンを複雑な管理はしないので番号で制御します
    private Scene currentScene = null;
    private int scene = 0;
    private int titleNo = 0;
    private boolean changeSceneFlag = false;


    private UIButton debugSwitch;
    private boolean debugFlag = true;

    public Game(MainActivity activity) {
        super(activity);
        this.activity = activity;
        displayRealSize.x = this.getDefaultDisplayRealSize().x;
        displayRealSize.y = this.getDefaultDisplayRealSize().y;
        this.start();
        this.renderCommandQueue = new RenderCommandQueue();
        this.imageResource = new ImageResource(this.getResources(), this.getDefaultDisplayRealSize());
        this.currentScene = new TitleScene(this,this.imageResource, this.getDefaultDisplayRealSize());

        debugSwitch = new UIButton(
                imageResource,
                super.getResources(),
                R.drawable.restartbtn,
                new PointF(900.0f, 30.0f),
                new Point(32, 32));
    }

    public static PointF getDisplayRealSize() {
        return displayRealSize;
    }

    public static PointF getDisplayHalfRealSize() {
        return new PointF(displayRealSize.x, displayRealSize.y);
    }

    public static LeaderBoard getLeaderBoard() {
        return Game.leaderBoard;
    }

    public static int getHighScore() {
        return highScore;
    }

    public Resources getResources() {
        assert (this.activity != null);
        return this.activity.getResources();
    }

    public Point getDefaultDisplayRealSize() {
        assert (this.activity != null);
        Point realSize = new Point();
        this.activity.getWindowManager().getDefaultDisplay().getRealSize(realSize);
        return realSize;
    }

    @Override
    public void run() {
        while (this.running) {
            this.input();
            this.update();
            this.draw();
            this.sleep();
        } // while
    }

    private void start() {
        this.running = true;
        thread = new Thread(this);
        thread.start();
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        this.inputEvent.enable = true;
        this.inputEvent.positionX = event.getX();
        this.inputEvent.positionY = event.getY();

        int action = MotionEventCompat.getActionMasked(event);
        this.inputEvent.actionType = action;

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                enableTouch = true;

                if (debugSwitch.containCircle(new Circle(event.getX(), event.getY(), 4))) {
                    this.debugFlag = !this.debugFlag;
                } // if
            case (MotionEvent.ACTION_MOVE):
                return true;
            case (MotionEvent.ACTION_UP):
                enableTouch = false;
                return true;
            case (MotionEvent.ACTION_CANCEL):
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                return true;
            default:
                return super.onTouchEvent(event);
        } // switch
    }

    void changeScene() {
        if (this.scene == 0) {
            this.currentScene = null;
            this.currentScene = new TitleScene(this, this.imageResource, this.getDefaultDisplayRealSize());
        } // if
        else if (this.scene == 1) {
            this.currentScene = null;
            this.currentScene = new GamePlayScene(this, this.imageResource, this.getDefaultDisplayRealSize());
        } // if
        else if (this.scene == 2) {
            int score = ((GamePlayScene) this.currentScene).getGameSystem().getGameScorer().getGameScore();
            GameResultScene gameOverScene = new GameResultScene(this, this.imageResource, this.getDefaultDisplayRealSize());
            gameOverScene.setGameScorerValue(score);
            this.currentScene = null;
            this.currentScene = gameOverScene;
        } // else if
    }

    //! 入力イベントの更新
    void refresh() {
        this.prevTouch = this.currentTouch;
        this.currentTouch = this.enableTouch;

        this.inputEvent.touchType = InputTouchType.None;
        if (!this.prevTouch && this.currentTouch) {
            this.inputEvent.touchType = InputTouchType.Touch;
        } // if
        else if (this.prevTouch && this.currentTouch) {
            this.inputEvent.touchType = InputTouchType.Hold;
        } // else if
        else if (this.prevTouch && !this.currentTouch) {
            this.inputEvent.touchType = InputTouchType.Release;
        } // else if
    }


    void input() {
        this.refresh();

        //! 前のフレームのイベントを実行してからにしたいので
        //! もしシーンを遷移させるならこのタイミングでさせます
        if (this.changeSceneFlag) {
            this.changeSceneFlag = false;
            this.changeScene();
        } // if

        if (currentScene != null) {
            currentScene.input(this.inputEvent);
        } // if
        this.inputEvent = new InputEvent();
    }

    private void update() {
        if (currentScene != null) {
            currentScene.update(this.frameTime * frameTimeCoefficientForGame);
        } // if
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            this.executeDrawCommand(canvas);

            getHolder().unlockCanvasAndPost(canvas);
        } // if
    }

    private void sleep() {
        long time = (long) (this.frameTime * 1000.0f);
        try {
            Thread.sleep(time);
        } // try
        catch (InterruptedException e) {
            e.printStackTrace();
        } // catch
    }

    private void executeDrawCommand(Canvas canvas) {
        canvas.drawColor(Color.BLUE);

        renderCommandQueue.resetCommandList();

        if (currentScene != null) {
            currentScene.draw(renderCommandQueue);
        } // if

        if (!this.debugFlag) {
            debugSwitch.draw(renderCommandQueue);
        } // if

        renderCommandQueue.executeCommandList(canvas, this.debugFlag);
    }

    //! changeSceneFlagがtrueであれば
    //! シーン番号の対応したSceneクラスが生成されます
    public void incremenntSceneNo() {
        this.scene++;
        changeSceneFlag = true;
    }

    //! タイトルに遷移します
    public void toTitleScene() {
        changeSceneFlag = true;
        this.scene = this.titleNo;
    }
}