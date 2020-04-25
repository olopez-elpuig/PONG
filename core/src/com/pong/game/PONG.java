package com.pong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//640 x 480

public class PONG extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	float pressX , pressY;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("Start.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, pressX, pressY);
		batch.end();
	}
}
