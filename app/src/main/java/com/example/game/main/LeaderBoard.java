package com.example.game.main;

import java.util.ArrayList;
import java.util.List;

//! ゲーム全体のスコアを保持します
//! Gameにstaticに持たれます
public class LeaderBoard {
    private List<LeaderBoardData> leaderBoardDataList = new ArrayList<>();
    private int capacity = 5;
    private int nameLength = 7;

    public LeaderBoard() {
        leaderBoardDataList.add(new LeaderBoardData("TOM", 99999));
        leaderBoardDataList.add(new LeaderBoardData("JOHN", 80000));
        leaderBoardDataList.add(new LeaderBoardData("HARY", 50000));
    }

    public List<LeaderBoardData> getLeaderBoardDataList() {
        return this.leaderBoardDataList;
    }

    public int getNameLength() {
        return this.nameLength;
    }

    public void update(LeaderBoardData newData) {
        if (this.leaderBoardDataList.size() < capacity) {
            this.leaderBoardDataList.add(newData);
            return;
        } // if

        int index = -1;
        for (int i = 0; i < this.capacity; i++) {
            if(this.leaderBoardDataList.get(i).score < newData.score){
                index = i;
                break;
            } // if
        } // for

        if(index != -1){
            this.leaderBoardDataList.remove(this.leaderBoardDataList.size() - 1);
            this.leaderBoardDataList.add(index, newData);
        } // if
    }
}