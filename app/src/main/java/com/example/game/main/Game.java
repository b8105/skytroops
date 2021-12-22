package com.example.game.main;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.core.view.MotionEventCompat;

import com.example.game.common.InputEvent;
import com.example.game.common.InputTouchType;
import com.example.game.common.Transform2D;
import com.example.game.game.GameScorer;
import com.example.game.render.RenderCommandQueue;
import com.example.game.scene.GameOverScene;
import com.example.game.scene.GamePlayScene;
import com.example.game.scene.Scene;
import com.example.game.scene.TitleScene;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;

public class Game extends SurfaceView implements Runnable {
    //!
    public MainActivity activity;
    //! thread
    private Thread thread;
    private boolean running = true;
    float frameTime = 1.0f / 60.0f * 0.5f;
    float frameTimeCoefficientForGame = 3.0f;
    //! system
    private InputEvent inputEvent = new InputEvent();
    private RenderCommandQueue renderCommandQueue;

    //! scene
    private Scene currentScene = null;
    int scene = 0;
    boolean changeSceneFlag = false;


    boolean enableTouch = false;
    boolean prevTouch = false;
    boolean currentTouch = false;

    private JSONObject parseJson(String fileName) throws JSONException, IOException {
        InputStream inputStream = activity.getAssets().open(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        JSONObject json = null;

        String data = "";
        String str = bufferedReader.readLine();

        while (str != null) {
            data += str;
            str = bufferedReader.readLine();
        } // while

        json = new JSONObject(data);

        inputStream.close();
        bufferedReader.close();
        return json;
    }

    void load() throws JSONException, IOException {
        try {
            JSONObject json = this.parseJson(new String("test.json"));

            //JSONObject sample = json.getJSONObject("sample");
            JSONArray sample = json.getJSONArray("sample");
            for (int i = 0; i < sample.length(); ++i) {
                JSONObject data = sample.getJSONObject(i);
                int age = data.getInt("age");
                String name = data.getString("name");

                System.out.println("age = " + age);
                System.out.println("name  = " + name);
            } // for
        } // try
        catch (JSONException e) {
            e.printStackTrace();
        } // catch
    }

    // constructor
    public Game(MainActivity activity) {
        super(activity);
        this.activity = activity;
        this.start();
        renderCommandQueue = new RenderCommandQueue();
        currentScene = new TitleScene(this, this.getDefaultDisplayRealSize());
//        currentScene = new GameOverScene(this, this.getDefaultDisplayRealSize());

        try {
            this.load();
        } // try
        catch (JSONException e) {
            e.printStackTrace();
        } // catch
        catch (IOException e) {
            e.printStackTrace();
        } // catch
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
            this.currentScene = new TitleScene(this, this.getDefaultDisplayRealSize());
        } // if
        else if (this.scene == 1) {
            this.currentScene = null;
            this.currentScene = new GamePlayScene(this, this.getDefaultDisplayRealSize());
        } // if
        else if (this.scene == 2) {
            int score = ((GamePlayScene) this.currentScene).getGameSystem().getGameScorer().getGameScore();
            GameOverScene gameOverScene = new GameOverScene(this, this.getDefaultDisplayRealSize());
            gameOverScene.setGameScorerValue(score);
            this.currentScene = null;
            this.currentScene = gameOverScene;
        } // else if
    }

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

    //! 60FPSに保つ為にメインスレッドを休止させる
    private void sleep() {
        long time = (long) (this.frameTime * 1000.0f);
        try {
            Thread.sleep(time);
        } // try
        catch (InterruptedException e) {
            e.printStackTrace();
        } // catch
    }

    //! 描画関数
    private void executeDrawCommand(Canvas canvas) {
        canvas.drawColor(Color.BLUE);

        renderCommandQueue.resetCommandList();

        if (currentScene != null) {
            currentScene.draw(renderCommandQueue);
        } // if

        renderCommandQueue.executeCommandList(canvas);
    }

    public void IncremenntSceneNo() {
        this.scene++;
        changeSceneFlag = true;
    }

    public void toTitleScene() {
        changeSceneFlag = true;
        this.scene = 0;
    }
}