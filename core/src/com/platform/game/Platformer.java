package com.platform.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Platformer extends Game {

	public static Platformer INSTANCE;
	public SpriteBatch batch;
	public Texture img;
	public BitmapFont font;

	public Platformer(){
		INSTANCE = this;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		ScreenUtils.clear(0,0,0,1);
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}
}
