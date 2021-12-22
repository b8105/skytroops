package com.example.game.render;

import android.graphics.Canvas;

import com.example.game.render.command.RenderCommand;

import java.util.LinkedList;
import java.util.List;

public class RenderCommandQueue {
    List<RenderCommandList> lists = new LinkedList<RenderCommandList>();

    public RenderCommandQueue() {
        int count = RenderLayerType.CountMax.ordinal();
        for (int i = 0; i < count; i++) {
            lists.add(new RenderCommandList());
        } // for
    }

    public void resetCommandList() {
        for (RenderCommandList list : this.lists) {
            list.reset();
        } // for
    }

    public void clear() {
        for (RenderCommandList list : this.lists) {
            list.reset();
        } // for
        lists.clear();
    }

    public RenderCommandList getRenderCommandList(RenderLayerType layer) {
        return this.lists.get(layer.ordinal());
    }

    public void executeCommandList(Canvas canvas) {
        for (RenderCommandList list : this.lists) {
            for (RenderCommand command : list.commands) {
                command.execute(canvas);
            } // for
        } // for
    }
}