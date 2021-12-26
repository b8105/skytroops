package com.example.game.game_event;

import com.example.game.game_event.GameEvent;
import com.example.game.render.RenderCommandQueue;

import java.util.ArrayList;
import java.util.List;

public class GameEventContainer {
    private List<GameEvent> events = null;
    private List<GameEvent> createdEvents = null;
    private List<GameEvent> deleteEvents = null;

    public GameEventContainer() {
        this.events = new ArrayList<>();
        this.createdEvents = new ArrayList<>();
        this.deleteEvents = new ArrayList<>();
    }

    public void addEvent(GameEvent event) {
        this.createdEvents.add(event);
    }

    public void clear(){
        this.events.clear();
    }

    public void update(float deltaTime) {
        for (GameEvent event : this.createdEvents) {
            this.events.add(event);
        } // for
        this.createdEvents.clear();

        for (GameEvent event : this.deleteEvents) {
            this.events.remove(event);
        } // for
        this.deleteEvents.clear();

        for (GameEvent event : this.events) {
            if (event.update(deltaTime)) {
                deleteEvents.add(event);
            } // if
        } // for
    }

    public void draw(RenderCommandQueue out) {
        for (GameEvent event : this.events) {
            event.draw(out);
        } // for
    }

}