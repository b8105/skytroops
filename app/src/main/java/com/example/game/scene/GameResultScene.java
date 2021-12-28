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
import com.example.game.main.LeaderBoard;
import com.example.game.main.LeaderBoardData;
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
import com.example.game.ui.UIText;

public class GameResultScene extends Scene {
    private SceneTransitionStateMachine transitionStateMachine = null;
    private UILabel background;
    private UILabel leadrboard;
    private UIButton restartButton;
    private Game game;

    private Transform2D transform = new Transform2D();
    private Paint paint = new Paint();

    public GameResultScene(Game game, ImageResource imageResource, Point screenSize) {
        super(game, screenSize);
        this.game = game;
        this.transitionStateMachine = new SceneTransitionStateMachine(game);

        PointF center = new PointF(Game.getDisplayRealSize().x * 0.5f,
                Game.getDisplayRealSize().y * 0.5f);
        this.restartButton = new UIButton(imageResource,ImageResourceType.RestartButton,
                new PointF(screenSize.x * 0.5f, screenSize.y * 0.8f)
        );
        this.background = new UILabel(imageResource, ImageResourceType.GameOverBackground, center);
        this.leadrboard = new UILabel(imageResource, ImageResourceType.GameResultBackground,
                new PointF(screenSize.x * 0.5f, screenSize.y * 0.35f));
        this.transform.position.x = Game.getDisplayRealSize().x * 0.175f;
        this.transform.position.y = Game.getDisplayRealSize().x * 0.35f;
        this.paint.setTextSize(96);
        this.paint.setColor(Color.BLACK);
    }

    public void setGameScorerValue(int gameScore) {
        LeaderBoard leaderBoard = Game.getLeaderBoard();
        leaderBoard.update(new LeaderBoardData("YOU", gameScore));
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
                if (this.restartButton.containCircle(touchCircle)) {
                    this.game.toTitleScene();
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

        this.drawLeaderBoard(out);
        this.restartButton.draw(out);
    }

    private void drawLeaderBoard(RenderCommandQueue out){
        int nameLength = Game.getLeaderBoard().getNameLength();
        int index = 1;

        PointF position = new PointF(this.transform.position.x,this.transform.position.y + this.paint.getTextSize() ) ;
        for(LeaderBoardData leaderBoardData : Game.getLeaderBoard().getLeaderBoardDataList()){
            UIText name = new UIText(
                    index + ". " + leaderBoardData.name, new PointF(position.x, position.y),
                    (int)this.paint.getTextSize() ,
                    Color.BLACK, super.GetGame().getResources(),
                    R.font.luckiest_guy_regular);
            UIText score = new UIText(
                     "" + leaderBoardData.score, new PointF(
                    position.x + (this.paint.getTextSize() * nameLength * 0.5f) , position.y),
                    (int)this.paint.getTextSize(),
                    Color.BLACK, super.GetGame().getResources(),
                    R.font.luckiest_guy_regular);
            score.setRightFlag(true, nameLength);

            position.y += this.paint.getTextSize() * 1.5f;
            name.draw(out);
            score.draw(out);
            index++;
        } // for

    }
}