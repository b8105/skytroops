package com.example.game.main;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Surface;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import com.example.game.R;


public class MainActivity extends AppCompatActivity {
    //private Debugger debugger = new Debugger();
    //! エントリポイント
    private Game game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //! ゲームアプリケーション実行開始
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