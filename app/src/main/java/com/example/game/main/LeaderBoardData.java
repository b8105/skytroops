package com.example.game.main;


//! LeaderBoardの基本パラメータ
public class LeaderBoardData {
    public String name;
    public int score;

    public LeaderBoardData() {
    }

    public LeaderBoardData(String name, int score) {
        this.name = name;
        this.score = score;
    }
}