package com.haiprj.gamebase.base.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haiprj.gamebase.base.game.BaseGame;

import static com.haiprj.gamebase.utils.GameUtils.VIEWPORT_HEIGHT;
import static com.haiprj.gamebase.utils.GameUtils.VIEWPORT_WIDTH;


public abstract class BaseScreen implements Screen {

    protected BaseGame game;
    protected Viewport viewport;
    protected PerspectiveCamera camera;
    public BaseScreen(BaseGame game) {
        this.game = game;
        this.camera = new PerspectiveCamera(67f, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        this.viewport = new FillViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f,  0f);
    }

    @Override
    public void resize(int width, int height) {
        VIEWPORT_WIDTH = width / 2f;
        VIEWPORT_HEIGHT = height / 2f;
        this.camera.viewportWidth = VIEWPORT_WIDTH;
        this.camera.viewportHeight = VIEWPORT_HEIGHT;
        this.camera.update(false);
        this.viewport.update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void setGame(BaseGame game) {
        this.game = game;
    }

    public BaseGame getGame() {
        return game;
    }
}
