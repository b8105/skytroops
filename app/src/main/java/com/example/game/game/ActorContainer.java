package com.example.game.game;

import com.example.game.actor.Actor;
import com.example.game.actor.ActorState;
import com.example.game.actor.PlayerPlane;
import com.example.game.actor.bullet.Bullet;
import com.example.game.actor.Plane;
import com.example.game.actor.enemy_plane.EnemyPlane;

import java.util.ArrayList;
import java.util.List;

public class ActorContainer {
    List<Actor> actors = new ArrayList<Actor>();
    PlayerPlane mainChara = null;
    List<EnemyPlane> enemyPlanes = new ArrayList<EnemyPlane>();
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Bullet> playerBullets = new ArrayList<Bullet>();
    List<Bullet> enemyBullets = new ArrayList<Bullet>();

    public void setMainChara(PlayerPlane actor) {
        this.mainChara = actor;
    }

    public PlayerPlane getMainChara() {
        return this.mainChara;
    }

    public List<Actor> getActors() {
        return this.actors;
    }

    public List<EnemyPlane> getEnemies() {
        return this.enemyPlanes;
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