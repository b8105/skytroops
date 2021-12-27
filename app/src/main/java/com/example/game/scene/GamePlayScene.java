package com.example.game.scene;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;

import com.example.game.actor.enemy_plane.EnemyPlaneType;
import com.example.game.actor.player.PlayerPlane;
import com.example.game.common.BitmapSizeStatic;
import com.example.game.effect.EffectEmitter;
import com.example.game.effect.EffectInfo;
import com.example.game.effect.EffectType;
import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.game_event.EnemyDestroyedEvent;
import com.example.game.game_event.GameEventContainer;
import com.example.game.game_event.GameOver.GameOverInfoDrawEvent;
import com.example.game.game_event.GameOver.GameOverSlideEvent;
import com.example.game.game_event.GameOver.GameOverStartEvent;
import com.example.game.game_event.MissionSuccessEvent;
import com.example.game.game_event.Plane.PlaneMoveToCenterEvent;
import com.example.game.game_event.StageClearInfoDrawEvent;
import com.example.game.game_event.Plane.ToNextStageEvent;
import com.example.game.game_event.StageTransition.TransitionStageEnterEvent;
import com.example.game.game_event.StageTransition.TransitionStageExitEvent;
import com.example.game.game_event.TutorialEvent;
import com.example.game.game_event.UpgradeEvent;
import com.example.game.observation.boss_enemy_dead.BossEnemyDeadListener;
import com.example.game.observation.boss_enemy_dead.BossEnemyDeadMessage;
import com.example.game.DebugRenderer;
import com.example.game.observation.player_dead.PlayerDeadListener;
import com.example.game.observation.player_dead.PlayerDeadMessage;
import com.example.game.observation.player_dead.PlayerDeadSubject;
import com.example.game.render.ScoreRenderer;
import com.example.game.actor.ActorTagString;
import com.example.game.effect.EffectSystem;
import com.example.game.game.ActorContainer;
import com.example.game.game.creational.ActorFactory;
import com.example.game.game.ComponentExecutor;
import com.example.game.game.GameSystem;
import com.example.game.actor.Actor;
import com.example.game.scene.transition_state.TransitionStateType;
import com.example.game.stage.Stage;
import com.example.game.common.InputEvent;
import com.example.game.main.Game;
import com.example.game.render.RenderCommandQueue;
import com.example.game.stage.StageType;
import com.example.game.ui.change_bullet.UIChangeBulletPanel;
import com.example.game.ui.UILabel;
import com.example.game.ui.pause.UIPausePanel;
import com.example.game.ui.to_title.UISceneExitPanel;
import com.example.game.ui.tutorial.UITutorialEndPanel;
import com.example.game.ui.plane_upgrade.UIUpgradePanel;

import java.util.HashMap;
import java.util.List;

public class GamePlayScene extends Scene
        implements PlayerDeadListener, BossEnemyDeadListener {
    private SceneTransitionStateMachine transitionStateMachine = null;
    private GamePlaySceneStateType gamePlaySceneStateType = GamePlaySceneStateType.Play;
    private ImageResource imageResource = null;
    private GameEventContainer gameEventContainer = null;
    private ActorContainer actorContainer = null;
    private GameSystem gameSystem = null;
    private ComponentExecutor componentExecutor = null;
    private EffectSystem effectSystem = null;
    private UIChangeBulletPanel uiChangeBullePanel = null;
    private UIPausePanel uiPausePanel = null;
    private UIUpgradePanel uIUpgradePanel = null;
    private UITutorialEndPanel uiTutorialEndPanel = null;
    private UISceneExitPanel uiToTitlePanel = null;

    private ActorFactory actorFactory = null;
    private Stage stage = null;

    private ScoreRenderer scoreRenderer = null;


    private HashMap<StageType, Integer> scoreTable = new HashMap<>();
    private EffectEmitter bossScoreEffect;


    public GamePlayScene(Game game, ImageResource imageResource, Point screenSize) {
        super(game, screenSize);
        Resources resources = game.getResources();
        this.transitionStateMachine = new SceneTransitionStateMachine(game);
        this.imageResource = imageResource;
        this.gameEventContainer = new GameEventContainer();
        this.actorContainer = new ActorContainer();
        this.gameSystem = new GameSystem();
        this.componentExecutor = new ComponentExecutor();
        this.effectSystem = new EffectSystem(this.imageResource);
        PointF panelPosition = new PointF(
                UIChangeBulletPanel.getButtonHalfSizeStatic().x,
                (UIChangeBulletPanel.getButtonHalfSizeStatic().y * 4.5f));
        this.uiChangeBullePanel = new UIChangeBulletPanel(
                null,
                this.imageResource,
                resources,
                this.effectSystem,
                panelPosition);
        this.uiPausePanel = new UIPausePanel(
                this.imageResource,
                this);
        this.uIUpgradePanel = new UIUpgradePanel(
                this.imageResource,
                this);
        this.uiTutorialEndPanel = new UITutorialEndPanel(
                this.imageResource,
                this);
        this.uiToTitlePanel = new UISceneExitPanel(
                this.imageResource,
                this);

        this.bossScoreEffect = this.effectSystem.getSharedEmitter(EffectType.Score500);

        this.scoreRenderer = new ScoreRenderer(imageResource);
        this.actorFactory = new ActorFactory(this,
                imageResource,
                this.actorContainer,
                this.componentExecutor,
                this.gameSystem,
                this.uiChangeBullePanel,
                this.effectSystem);


        this.gamePlayConstruct(game);

        this.scoreTable.put(StageType.Type01, 500);
        this.scoreTable.put(StageType.Type02, 1000);
        this.scoreTable.put(StageType.Type03, 1500);
        this.scoreTable.put(StageType.Type04, 2000);
        this.scoreTable.put(StageType.Type05, 2500);
    }

    void gamePlayConstruct(Game game) {
        this.stage = new Stage(game.getDefaultDisplayRealSize(), this.imageResource,
                this.componentExecutor.getCollisionLayer());
        this.createTutorialEvent(ImageResourceType.HowtoPlay);

        float x = game.getDefaultDisplayRealSize().x * 0.5f;
        float y = game.getDefaultDisplayRealSize().y * 0.85f;
        PlayerPlane plane = actorFactory.createPlayerPlane(x, y, ActorTagString.player);
        PlayerDeadSubject deadSubject = new PlayerDeadSubject();
        deadSubject.addObserver(this);
        plane.setPlayerDeadSubject(deadSubject);
    }

    public GameSystem getGameSystem() {
        return this.gameSystem;
    }

    public GamePlaySceneStateType getGamePlaySceneStateType() {
        return this.gamePlaySceneStateType;
    }

    public void sceneExitToTitle() {
        this.transitionStateMachine.getState(TransitionStateType.Exit).getAction().setToTitle(true);
        this.transitionStateMachine.transition(TransitionStateType.Exit);
        this.gameEventContainer.clear();
    }

    public void sceneExit() {
        this.transitionStateMachine.transition(TransitionStateType.Exit);
        this.gameEventContainer.clear();
    }

    public void gamePlay() {
        this.gamePlaySceneStateType = GamePlaySceneStateType.Play;
    }

    public void gamePause() {
        this.gamePlaySceneStateType = GamePlaySceneStateType.Pause;
    }


    @Override
    public void input(InputEvent input) {
        this.componentExecutor.input(input);
        if (this.uiChangeBullePanel != null) {
            this.uiChangeBullePanel.input(input);
        } // if
        if (this.uiPausePanel != null) {
            this.uiPausePanel.input(input);
        } // if
        if (this.uIUpgradePanel != null) {
            if (this.uIUpgradePanel.isActive()) {
                this.uIUpgradePanel.input(input);
            } // if
        } // if
        if (this.uiTutorialEndPanel != null) {
            if (this.uiTutorialEndPanel.isActive()) {
                this.uiTutorialEndPanel.input(input);
            } // if
        } // if
        if (this.uiToTitlePanel != null) {
            if (this.uiToTitlePanel.isActive()) {
                this.uiToTitlePanel.input(input);
            } // if
        } // if

    }

    void updateSystem(float deltaTime) {
        this.actorFactory.update();
        this.actorContainer.update();
        this.gameSystem.update(deltaTime,
                this.actorContainer, this.actorFactory, this.stage.getCurrentType());
        if (this.uiChangeBullePanel != null) {
            this.uiChangeBullePanel.update(deltaTime);
        } // if
        if (this.uiPausePanel != null) {
            this.uiPausePanel.update(deltaTime);
        } // if
        if (this.uIUpgradePanel != null) {
            if (this.uIUpgradePanel.isActive()) {
                this.uIUpgradePanel.update(deltaTime);
            } // if
        } // if
        if (this.uiTutorialEndPanel != null) {
            if (this.uiTutorialEndPanel.isActive()) {
                this.uiTutorialEndPanel.update(deltaTime);
            } // if
        } // if
        if (this.uiToTitlePanel != null) {
            if (this.uiToTitlePanel.isActive()) {
                this.uiToTitlePanel.update(deltaTime);
            } // if
        } // if

    }

    @Override
    public void update(float deltaTime) {
        this.transitionStateMachine.update(deltaTime);

        this.gameEventContainer.update(deltaTime);
        this.updateSystem(deltaTime);
        if (this.gamePlaySceneStateType == GamePlaySceneStateType.Play) {
            this.stage.update();
            this.componentExecutor.update(deltaTime);
            for (Actor actor : this.actorContainer.getActors()) {
                actor.update(deltaTime);
            } // for
        } // if

        this.effectSystem.update(deltaTime);
    }

    @Override
    public void draw(RenderCommandQueue out) {
        this.transitionStateMachine.drawTransitionEffect(out);
        this.gameEventContainer.draw(out);
        stage.getRenderer().execute(out);
        this.componentExecutor.draw(out);
        this.effectSystem.draw(out);
        if (this.uiChangeBullePanel != null) {
            this.uiChangeBullePanel.draw(out);
        } // if
        if (this.uiPausePanel != null) {
            if (this.uiPausePanel.isActive()) {
                this.uiPausePanel.draw(out);
            } // if
        } // if
        if (this.uIUpgradePanel != null) {
            if (this.uIUpgradePanel.isActive()) {
                this.uIUpgradePanel.draw(out);
            } // if
        } // if
        if (this.uiTutorialEndPanel != null) {
            if (this.uiTutorialEndPanel.isActive()) {
                this.uiTutorialEndPanel.draw(out);
            } // if
        } // if
        if (this.uiToTitlePanel != null) {
            if (this.uiToTitlePanel.isActive()) {
                this.uiToTitlePanel.draw(out);
            } // if
        } // if
        if (this.scoreRenderer.isActive()) {
            this.scoreRenderer.execute(this.getGameSystem(), out);
        } // if
        new DebugRenderer().execute(this.actorContainer, this.effectSystem, out);
    }

    @Override
    public void onNotify(BossEnemyDeadMessage maeeage) {
        PlayerPlane playerPlane = this.actorContainer.getMainChara();
        if (playerPlane == null) {
            return;
        } // if
        playerPlane.getInvincibleParameter().activate();

        this.gameSystem.getGameScorer().addScore(
                this.scoreTable.get(this.stage.getCurrentType()).intValue());

        {
            PointF emitPos = new PointF(Game.getDisplayRealSize().x * 0.5f ,
                    Game.getDisplayRealSize().y * 0.5f);
            emitPos.y -= BitmapSizeStatic.score.y * 0.5f;
            EffectInfo info = new EffectInfo(
                    EffectType.Score500,
                    emitPos,
                    0.5f,
                    new PointF(0.0f, -5.0f)
            );
            this.bossScoreEffect.emit(info);
        }


        if (this.stage.getCurrentType() == StageType.Type05) {
            this.gameEventContainer.addEvent(
                    new EnemyDestroyedEvent(this));
        } // if
        else {
            this.gameEventContainer.addEvent(
                    new EnemyDestroyedEvent(this));
        } // else
    }

    @Override
    public void onNotify(PlayerDeadMessage maeeage) {
        this.uiPausePanel.inactivate();
        this.scoreRenderer.inactivate();

        this.gameEventContainer.addEvent(
                new GameOverStartEvent(this, this.imageResource)
        );
        this.uiChangeBullePanel = null;
    }

    public void createStageClearInfoDrawEvent() {
        if(this.stage.getCurrentType() == StageType.Type05){
            this.gameEventContainer.addEvent(
                    new MissionSuccessEvent(this,  this.gameSystem.getGameScorer(), super.GetGame().getResources(), this.imageResource)
            );
        } // if
        else{

        this.gameEventContainer.addEvent(
                new StageClearInfoDrawEvent(this, this.uIUpgradePanel, this.gameSystem.getGameScorer(), super.GetGame().getResources(), this.imageResource)
        );
        } // else
    }

    public void createUpgradeEvent() {
        this.gameEventContainer.addEvent(
                new UpgradeEvent(this,
                        this.actorContainer,
                        this.stage,
                        this.effectSystem,
                        this.uiChangeBullePanel,
                        this.imageResource)
        );
    }

    public void createGameOverEvent() {
        this.gameEventContainer.addEvent(
                new GameOverInfoDrawEvent(this
                        , this.uiToTitlePanel, this.gameSystem.getGameScorer(), super.GetGame().getResources(), this.imageResource)
        );
    }

    public void createGameOverSlideEvent(List<UILabel> uiLabels) {
        this.gameEventContainer.addEvent(
                new GameOverSlideEvent(
                        uiLabels, this
                ));
    }

    public void createToNextStageEvent() {
        this.gameEventContainer.addEvent(
                new ToNextStageEvent(this,
                        this.actorContainer,
                        this.stage,
                        this.uiChangeBullePanel,
                        this.imageResource));
    }

    public void createTutorialEvent(ImageResourceType imageResourceType) {
        this.gameEventContainer.addEvent(
                new TutorialEvent(this.imageResource, imageResourceType, this.uiTutorialEndPanel, this.gameSystem.getEnemySpawnSystem())
        );
    }

    public void createPlaneMoveToCenterEvent() {
        PlayerPlane playerPlane = this.actorContainer.getMainChara();
        if (playerPlane == null) {
            return;
        } // if
        this.gameEventContainer.addEvent(
                new PlaneMoveToCenterEvent(this,
                        this.actorContainer,
                        this.stage,
                        this.uiChangeBullePanel,
                        this.imageResource));
    }

    public void createToGameOverScene() {
        this.sceneExit();
    }

    public void createTransitionStageExitEvent(
            PlayerPlane player,
            PointF centerPosiotion) {
        this.gameEventContainer.addEvent(
                new TransitionStageExitEvent(
                        this,
                        player, centerPosiotion,
                        this.getGameSystem().getGameScorer()));
    }

    public void createTransitionStageEnterEvent() {
        this.gameEventContainer.addEvent(
                new TransitionStageEnterEvent(this.gameSystem, this.stage, this, this.uiChangeBullePanel));
    }

}