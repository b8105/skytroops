package com.example.game.main;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Surface;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import com.example.game.R;


// ご覧いただいてありがとうございます
// 仕様に対してどんなふうに作ったのかを書きます
// クラス設計は作るゲームを考えながらしました
// クラスが持つ役割が１機能になるように意識して
// 仕様にある動きをプログラム組み合わせで実現できるような構造を目指しました
// Enemyの生成周りは外部ファイルに移すなどしたかったのですが間に合いませんでしたすみません


// 必須仕様 【タイトル画面】
// ・ゲーム画面へ遷移する事が出来る。
// シーンの遷移はGame.javaでしています

//【ゲーム画面】
//・自機の弾種が３種類以上の実装が確認できる。
//３種類のみ下記の仕様で実装すること。
//・直線上に発射される弾
//・３方向に発射される弾
//・最も近い敵を追尾する弾
//※ 弾種は切替式でもパワーアップ式でもどちらでもOK

// 弾の発射はWeaponクラスからFactoryへ生成リクエストを投げて生成しています
// 発射される弾の動きは ***BulletMoveComponent.javaに定義しています
// 弾は発射される時に武器(砲台となるクラス Weapon.java)から位置と方向(回転量) 弾のタイプを渡され設定されます
// 直線上に発射される弾と３方向に発射される弾は
// BasicBulletMoveComponent.javaで弾クラスの方向を参照して位置を移動させています
// 最も近い敵を追尾するは
// HomingBulletMoveComponent.javaで定義しており
// ゲームオブジェクト(Actor)のコンテナのをメンバとしてもってしまっているので
// ビジタークラスを介して最も近い敵を取り出して
// 対象の方向に 回転せて移動させています
// 弾の切り替えは UIChangeBulletPanel.javaしています
// 画面をダブルタップすることで
// UIChangeBulletPanelが持つUIChangeBulletButtonが持っている
// プレイヤーのWeaponが発射する弾のタイプを変更します


//・敵が２種類以上の実装が確認できる。
//２種類のみ下記の仕様で実装すること。
//・プレイヤーに向かって弾を撃ちながら直線状に進む敵
//・正面に向かって弾を撃ちながらジグザグにに進む敵
//・画面中央まで直進した後、プレイヤーに向かって弾を撃ちながら画
//・スコアが確認出来る。
//・ゲームオーバー画面へ遷移出来る。 (ゲームクリア時でもゲームオーバー画面へ遷移してよい)
//面外へ退場する敵
//・４機編成で行動する敵 ※ 攻撃方法は任意


// ゲームオブジェクトの移動や弾の発射は１つアクションとして扱っています
// ２つしかないのでステートマシンなどで管理せずにルートアクションの子要素として動かしています
// 正面に向かって弾を撃ちながらジグザグにに進む敵であれば
// PlaneActionComponentが持つActionComponentの配列に
// ジグザグに動くWaveMoveComponentと弾を発射するAutoTargetingShotComponentを追加することで
// 敵の動きを作っていますランダムで正面でなくプレイヤーに撃つこともします
// ActionComponentが入力される情報が必要なら対応するInputクラスからコマンド(命令)を受け取り
// アクションを実行します　
// 敵の動きについてはgame.action.action_componentパッケージに
// ActionComponentが受け取るコマンド(*Command)はgame.action.commandパッケージに
// *Commandを生成する入力クラスはgame.action.inputパッケージに入っています
// それぞれがActionLayerの関数内で実行されます

// 退場する敵と編成で行動する敵についてはそれぞれ
// AIFadeoutMoveInput.java と
// FollowMoveComponent.javaで実装しています


// スコは右上に表示
// プレイヤーがやられたらリザルトシーンに移動します

//【ゲームオーバー画面】
//・タイトルへ戻る事が出来る。
// GameResultScene.javaで実装してます
// ボタンでタイトルに戻ります



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