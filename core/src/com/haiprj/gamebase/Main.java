package com.haiprj.gamebase;

import base.game.BaseGame;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends BaseGame {

	public Main() {

	}

	@Override
	public void create () {
		super.create();
		this.setCurrentScreen(new MainScreen(this));
	}
}
