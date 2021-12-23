package com.example.game.scene;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;

import com.example.game.game_event.EnemyDestroyedEvent;
import com.example.game.game_event.GameEvent;
import com.example.game.game_event.GameEventContainer;
import com.example.game.game_event.ToNextStageEvent;
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
    private GameEventContainer gameEventContainer = null;
    private ActorContainer actorContainer = null;
    private GameSystem gameSystem = null;
    private ComponentExecutor componentExecutor = null;
    private EffectSystem effectSystem = null;
    private UIChangeBullePanel uiChangeBullePanel = null;
    private ActorFactory actorFactory = null;
    private Stage stage = null;

    private int stageCountMax = 2;
    private int stageCount = 0;

    private boolean bossDestroyed = false;

    public GamePlayScene(Game game, Point screenSize) {
        super(game, screenSize);
        Resources resources = game.getResources();
        this.transitionStateMachine = new SceneTransitionStateMachine(game);
        this.gameEventContainer = new GameEventContainer();
        this.actorContainer = new ActorContainer();
        this.gameSystem = new GameSystem(4.0f);
        this.componentExecutor = new ComponentExecutor();
        this.effectSystem = new EffectSystem(resources);
        PointF panelPosition = new PointF(
                screenSize.x - UIChangeBullePanel.getSizeStatic().x + UIChangeBullePanel.getButtonHalfSizeStatic().x,
                screenSize.y - UIChangeBullePanel.getSizeStatic().y + UIChangeBullePanel.getButtonHalfSizeStatic().y);
        this.uiChangeBullePanel = new UIChangeBullePanel(
                resources,
                panelPosition);
        this.actorFactory = new ActorFactory(this,
                resources,
                this.actorContainer,
                this.componentExecutor,
                this.gameSystem,
                this.uiChangeBullePanel,
                this.effectSystem);


        this.gamePlayConstruct(game);
    }

    void gamePlayConstruct(Game game) {
        this.stage = new Stage(game.getDefaultDisplayRealSize(), game.getResources(),
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
        this.gameSystem.update(deltaTime, this.stage, this.actorContainer, this.actorFactory, this.stageCount);
    }

    @Override
    public void update(float deltaTime) {
        this.transitionStateMachine.update(deltaTime);
        if (this.stageCount == this.stageCountMax) {
        } // if

        this.gameEventContainer.update(deltaTime);
        this.updateSystem(deltaTime);
        this.stage.update();
        this.componentExecutor.update(deltaTime);

        {
            if (actorContainer.getMainChara() != null) {
                actorContainer.getMainChara().getInvincibleParameter().setPlaneSpriteRenderComponent(
                        actorContainer.getMainChara().getComponent(ComponentType.PlaneSpriteRender)
                );
                actorContainer.getMainChara().getInvincibleParameter().update(deltaTime);
            } // if
        }

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
        new ScoreRenderer().execute(this.getGameSystem(), out);
        new DebugRenderer().execute(this.actorContainer, this.effectSystem, out);
    }

    @Override
    public void onNotify(BossEnemyDeadMessage maeeage) {
        this.gameEventContainer.addEvent(new EnemyDestroyedEvent(this));
    }

    public void createToNextStageEvent() {
        this.gameEventContainer.addEvent(new ToNextStageEvent(this));
    }


    public void toNextStage() {
        this.stageCount++;
        if (this.stageCount == 0) {
            this.stage.resetBitmap(this.GetGame().getResources(), StageType.Type01);
            this.gameSystem.resetSpawnSystem(StageType.Type01);
        } // if
        else if (this.stageCount == 1) {
            this.stage.resetBitmap(this.GetGame().getResources(), StageType.Type02);
            this.gameSystem.resetSpawnSystem(StageType.Type02);
        } // if
        else if (this.stageCount == 2) {
            this.stage.resetBitmap(this.GetGame().getResources(), StageType.Type03);
            this.gameSystem.resetSpawnSystem(StageType.Type03);
        } // if
    }

}