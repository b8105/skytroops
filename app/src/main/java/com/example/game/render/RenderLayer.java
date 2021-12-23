package com.example.game.render;


import java.util.ArrayList;
import java.util.List;

public class RenderLayer {
    private List<Renderable> renderables = new ArrayList<Renderable>();

    public void add(Renderable renderable) {
        assert (renderable != null);
        this.renderables.add(renderable);
    }

    public void remove(Renderable renderable) {
        assert (renderable != null);
        this.renderables.remove(renderable);
    }

    public void excute(RenderCommandQueue out) {
        for (Renderable renderable : this.renderables) {
            renderable.execute(out);
        } // for
    }

    ;
}