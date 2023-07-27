package base.game;

import base.screen.BaseScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

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
