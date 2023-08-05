package com.haiprj.gamebase.utils.views;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ScrollPaneCustom extends ScrollPane {
    public ScrollPaneCustom(Actor actor) {
        super(actor);
        init();
    }

    public ScrollPaneCustom(Actor actor, Skin skin) {
        super(actor, skin);
        init();
    }

    public ScrollPaneCustom(Actor actor, Skin skin, String styleName) {
        super(actor, skin, styleName);
        init();
    }

    public ScrollPaneCustom(Actor actor, ScrollPaneStyle style) {
        super(actor, style);
        init();
    }

    protected void init() {
        setScrollbarsVisible(true);

    }
}
