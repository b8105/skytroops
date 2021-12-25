package com.example.game.scene;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;

import com.example.game.actor.PlayerPlane;
import com.example.game.game.GameScorer;
import com.example.game.game.resource.ImageResource;
import com.example.game.game_event.EnemyDestroyedEvent;
import com.example.game.game_event.GameEventContainer;
import com.example.game.game_event.StageClearInfoDrawEvent;
import com.example.game.game_event.ToNextStageEvent;
import com.example.game.game_event.TransitionStageEnterEvent;
import com.example.game.game_event.TransitionStageExitEvent;
import com.example.game.observation.BossEnemyDeadListener;
import com.example.game.observation.BossEnemyDeadMessage;
import com.example.game.DebugRenderer;
import com.example.game.render.ScoreRenderer;
import com.example.game.component.ComponentType;
import com.example.game.actor.ActorTagString;
import com.example.game.effect.EffectSystem;
import com.example.game.game.ActorContainer;
import com.example.game.game.ActorFactory;
import com.example.game.game.ComponentExecutor;
import com.example.game.game.GameSystem;
import com.example.game.actor.Actor;
import com.example.game.scene.transition_state.TransitionStateType;
import com.example.game.stage.Stage;
import com.example.game.common.InputEvent;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.stage.StageType;
import com.example.game.ui.UIChangeBullePanel;

public class GamePlayScene extends Scene implements BossEnemyDeadListener {
    private SceneTransitionStateMachine transitionStateMachine = null;
    private ImageResource imageResource = null;
    private GameEventContainer gameEventContainer = null;
    private ActorContainer actorContainer = null;
    private GameSystem gameSystem = null;
    private ComponentExecutor componentExecutor = null;
    private EffectSystem effectSystem = null;
    private UIChangeBullePanel uiChangeBullePanel = null;
    private ActorFactory actorFactory = null;
    private Stage stage = null;

    public GamePlayScene(Game game, ImageResource imageResource, Point screenSize) {
        super(game, screenSize);
        Resources resources = game.getResources();
        this.transitionStateMachine = new SceneTransitionStateMachine(game);
        this.imageResource = imageResource;
        this.gameEventContainer = new GameEventContainer();
        this.actorContainer = new ActorContainer();
        this.gameSystem = new GameSystem(4.0f);
        this.componentExecutor = new ComponentExecutor();
        this.effectSystem = new EffectSystem(this.imageResource);
        PointF panelPosition = new PointF(
                UIChangeBullePanel.getButtonHalfSizeStatic().x,
                (UIChangeBullePanel.getButtonHalfSizeStatic().y * 4));
        this.uiChangeBullePanel = new UIChangeBullePanel(
                this.imageResource,
                resources,
                panelPosition);
        this.actorFactory = new ActorFactory(this,
                imageResource,
                this.actorContainer,
                this.componentExecutor,
                this.gameSystem,
                this.uiChangeBullePanel,
                this.effectSystem);


        this.gamePlayConstruct(game);
    }

    void gamePlayConstruct(Game game) {
        this.stage = new Stage(game.getDefaultDisplayRealSize(), this.imageResource,
                this.componentExecutor.getCollisionLayer());
        float x = game.getDefaultDisplayRealSize().x * 0.5f;
        float y = game.getDefaultDisplayRealSize().y * 0.85f;
        Actor plane = actorFactory.createPlayerPlane(x, y, ActorTagString.player);
    }

    public GameSystem getGameSystem() {
        return this.gameSystem;
    }

    public void sceneExit() {
        this.transitionStateMachine.transition(TransitionStateType.Exit);
    }


    @Override
    public void input(InputEvent input) {
        this.componentExecutor.input(input);
        this.uiChangeBullePanel.input(input);
    }

    void updateSystem(float deltaTime) {
        this.actorFactory.update();
        this.actorContainer.update();
        this.gameSystem.update(deltaTime, this.stage,
                this.actorContainer, this.actorFactory, this.stage.getCurrentType());
    }

    @Override
    public void update(float deltaTime) {
        this.transitionStateMachine.update(deltaTime);

        this.gameEventContainer.update(deltaTime);
        this.updateSystem(deltaTime);
        this.stage.update();
        this.componentExecutor.update(deltaTime);

        for(Actor actor : this.actorContainer.getActors()){
            actor.update(deltaTime);
        } // for

        this.effectSystem.update(deltaTime);
    }

    @Override
    public void draw(RenderCommandQueue out) {
        this.transitionStateMachine.drawTransitionEffect(out);
        this.gameEventContainer.draw(out);
        stage.getRenderer().execute(out);
        this.componentExecutor.draw(out);
        this.effectSystem.draw(out);
        this.uiChangeBullePanel.draw(out);
        new ScoreRenderer(this.imageResource).execute(this.getGameSystem(), out);
        new DebugRenderer().execute(this.actorContainer, this.effectSystem, out);
    }

    @Override
    public void onNotify(BossEnemyDeadMessage maeeage) {
        if (this.stage.getCurrentType() == StageType.Type05) {
            this.sceneExit();
        } // if
        else {
            this.gameEventContainer.addEvent(
                    new EnemyDestroyedEvent(this));
        } // else
    }
    public void createStageClearInfoDrawEvent() {
        this.gameEventContainer.addEvent(
                new StageClearInfoDrawEvent(this, this.gameSystem.getGameScorer(),this.imageResource)
        );
    }

    public void createToNextStageEvent() {
        this.gameEventContainer.addEvent(
                new ToNextStageEvent(this,
                        this.actorContainer,
                        this.stage,
                        this.uiChangeBullePanel));
    }

    public void createTransitionStageExitEvent(
            PlayerPlane player,
            PointF centerPosiotion) {
        this.gameEventContainer.addEvent(
                new TransitionStageExitEvent(
                        this,
                        player,centerPosiotion,
                        this.getGameSystem().getGameScorer()));
    }

    public void createTransitionStageEnterEvent() {
        this.gameEventContainer.addEvent(
                new TransitionStageEnterEvent(this.gameSystem, this.stage));
    }
}