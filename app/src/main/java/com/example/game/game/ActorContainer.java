package com.example.game.game;

import com.example.game.actor.Actor;
import com.example.game.actor.ActorState;
import com.example.game.actor.player.PlayerPlane;
import com.example.game.actor.bullet.Bullet;
import com.example.game.actor.enemy_plane.BossEnemyPlane;
import com.example.game.actor.enemy_plane.CommanderEnemyPlane;
import com.example.game.actor.enemy_plane.EnemyPlane;
import com.example.game.game.container_visitor.CommanderEnemyPlaneVisitor;
import com.example.game.game.container_visitor.FindNearestEnemyVisitor;

import java.util.ArrayList;
import java.util.List;

public class ActorContainer {
    List<Actor> actors = new ArrayList<Actor>();
    PlayerPlane mainChara = null;
    List<EnemyPlane> enemyPlanes = new ArrayList<EnemyPlane>();
    List<CommanderEnemyPlane> commanderEnemyPlane = new ArrayList<CommanderEnemyPlane>();
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Bullet> playerBullets = new ArrayList<Bullet>();
    List<Bullet> enemyBullets = new ArrayList<Bullet>();
    BossEnemyPlane bossEnemy = null;

    public void setMainChara(PlayerPlane actor) {
        this.mainChara = actor;
    }

    public void setBossEnemy(BossEnemyPlane bossEnemy) {
        this.bossEnemy = bossEnemy;
    }

    public PlayerPlane getMainChara() {
        return this.mainChara;
    }

    public BossEnemyPlane getBossEnemy() {
        return this.bossEnemy;
    }

    public List<Actor> getActors() {
        return this.actors;
    }

    public List<EnemyPlane> getEnemies() {
        return this.enemyPlanes;
    }
    public List<CommanderEnemyPlane> getCommanderEnemies() {
        return this.commanderEnemyPlane;
    }

    public List<Bullet> getBullets() {
        return this.bullets;
    }
    public List<Bullet> getPlayerBullets() {
        return this.playerBullets;
    }
    public List<Bullet> getEnemyBullets() {
        return this.enemyBullets;
    }

    public void addActor(Actor actor) {
        this.actors.add(actor);
    }

    public void addEnemyPlane(EnemyPlane plane) {
        this.enemyPlanes.add(plane);
    }

    public void addCommanderEnemyPlane(CommanderEnemyPlane plane) {
        this.commanderEnemyPlane.add(plane);
    }


    public void addBullet(Bullet bullet) {
        this.bullets.add(bullet);
    }
    public void addPlayerBullet(Bullet bullet) {
        this.playerBullets.add(bullet);
    }
    public void addEnemyBullet(Bullet bullet) {
        this.enemyBullets.add(bullet);
    }

    public void removeActor(Actor actor) {
        this.actors.remove(actor);
    }

    public void removeEnemyPlane(EnemyPlane plane) {
        this.enemyPlanes.remove(plane);
    }
    public void removeCommanderEnemyPlane(CommanderEnemyPlane plane) {
        this.commanderEnemyPlane.remove(plane);
    }


    public void removeBullet(Bullet bullet) {
        this.bullets.remove(bullet);
    }

    public void removePlayerBullet(Bullet bullet) {
        this.playerBullets.remove(bullet);
    }

    public void removeEnemyBullet(Bullet bullet) {
        this.enemyBullets.remove(bullet);
    }

    public void visitorAccept(FindNearestEnemyVisitor visitor) {
        visitor.visit(this);
    }
    public void visitorAccept(CommanderEnemyPlaneVisitor visitor) {
        visitor.visit(this);
    }

    public void update() {
        List<Actor> removeList = new ArrayList<>();
        for (Actor actor : this.actors) {
            if (actor.getActorState() == ActorState.End) {
                removeList.add(actor);
            } // if
        } // for
        for (Actor actor : removeList) {
            actor.release(this);
            actor = null;
        } // for
    }
}