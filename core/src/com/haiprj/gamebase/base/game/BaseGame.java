package com.haiprj.gamebase.base.game;

import com.badlogic.gdx.Game;
import com.haiprj.gamebase.base.screen.BaseScreen;

public abstract class BaseGame extends Game {

    protected BaseScreen currentScreen;
    @Override
    public void create() {

    }

    public void setCurrentScreen(BaseScreen currentScreen) {
        this.currentScreen = currentScreen;
        this.setScreen(this.currentScreen);
        if (this.currentScreen.getGame() == null) {
            this.currentScreen.setGame(this);
        }
    }
}
