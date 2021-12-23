package com.example.game.main;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import com.example.game.R;


// どうしてこの関数を作ったのか
// どんなふうに使うのかをコメントを書く

//play時間は5くらい
//      lebel 0　HPは２くらい  boss
//        lebel 1　HPは３くらい てき種類ふやす  tween animation
//        lebel 0　HPは２くらい

public class MainActivity extends AppCompatActivity {
    private Debugger debugger = new Debugger();
    private Game game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game(this);
        setContentView(game);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}