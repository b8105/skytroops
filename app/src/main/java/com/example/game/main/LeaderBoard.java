package com.example.game.main;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {
    private List<LeaderBoardData> leaderBoardDataList = new ArrayList<>();

    public LeaderBoard(){
        leaderBoardDataList.add(new LeaderBoardData("TOM", 99999));
        leaderBoardDataList.add(new LeaderBoardData("JOHN", 80000));
        leaderBoardDataList.add(new LeaderBoardData("HARY", 50000));
    }
    public List<LeaderBoardData> getLeaderBoardDataList() {
        return this.leaderBoardDataList;
    }
}