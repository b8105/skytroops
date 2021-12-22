package com.example.game.game_event;

import com.example.game.game_event.GameEvent;

import java.util.ArrayList;
import java.util.List;

public class GameEventContainer {
    private List<GameEvent> events = null;
    private List<GameEvent> created_events = null;
    private List<GameEvent> delete_events = null;
    public GameEventContainer() {
        //! ゲームイベント
        this.events = new ArrayList<>();
        //! 追加
        this.created_events = new ArrayList<>();
        //! 削除
        this.delete_events = new ArrayList<>();
    }

//    public void OnNotifyEventRequestListener(message) {
//        let event = message.event;
//        let request = message.request;
//
//        if (request == "Add") {
//            this.AddEvent(event);
//        } // if
//        else if (request == "Delete") {
//            event.GetEventRequestSubject().RemoveObserver(this);
//            this.delete_events.Add(event);
//        } // else if
//    }
    public void addEvent(GameEvent event) {
//        let sub = event.GetEventRequestSubject();
//        let t = this;
 //       sub.AddObserver(t);
        this.created_events.add(event);
    }

    public void update(float deltaTime) {
        for (GameEvent event : this.created_events) {
            this.events.add(event);
        } // for
        this.created_events.clear();

        for (GameEvent event : this.delete_events) {
            this.events.remove(event);
        } // for
        this.delete_events.clear();

        for (GameEvent event : this.events) {
            event.update(deltaTime);
        } // for
    }
}