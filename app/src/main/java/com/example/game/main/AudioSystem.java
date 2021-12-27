package com.example.game.main;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.game.R;

import java.util.HashMap;

public class AudioSystem {
    HashMap<SEType, SoundPool> soundPoolHashMap = null;
    HashMap<BGMType, MediaPlayer> mediaPlayerHashMap = null;

    public AudioSystem(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(
                context, R.raw.bgm_title);
        mediaPlayer.start();
        mediaPlayer.setVolume(0.0f,1.0f);

        this.soundPoolHashMap = new HashMap<>();
        this.mediaPlayerHashMap = new HashMap<>();

//        MediaPlayer mediaPlayer = new MediaPlayer();
////        mediaPlayer.setDataSource(R.raw.bgm_result);
//        mediaPlayer.start();
//        mediaPlayer.stop();
//        mediaPlayer.reset();
//        mediaPlayer.release();
    }

    public void release() {

    }
}