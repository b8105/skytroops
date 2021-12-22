package com.example.game.game;

public class GameScorer {
    private int gameScore;
    public GameScorer(){
        gameScore = 0;
    }

    public int getGameScore() {
        return this.gameScore;
    }

    public void addScore(int score){
        this.gameScore += score;
    }
}