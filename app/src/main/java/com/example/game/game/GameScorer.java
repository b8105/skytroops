package com.example.game.game;

public class GameScorer {
    private int gameScore;
    private int enemyDestoryCountOnStage;
    private int enemyDestoryScoreOnStage;

    public GameScorer() {
        this.gameScore = 0;
        this.enemyDestoryCountOnStage = 0;
        this.enemyDestoryScoreOnStage = 0;
    }

    public int getGameScore() {
        return this.gameScore;
    }

    public int getEnemyDestoryCountOnStage() {
        return this.enemyDestoryCountOnStage;
    }

    public int getEnemyDestoryScoreOnStage() {
        return this.enemyDestoryScoreOnStage;
    }

    public void addScore(int score) {
        this.gameScore += score;
        this.enemyDestoryScoreOnStage += score;
        this.enemyDestoryCountOnStage++;
    }

    public void resetStageScore() {
        this.enemyDestoryCountOnStage = 0;
        this.enemyDestoryScoreOnStage = 0;
    }

}