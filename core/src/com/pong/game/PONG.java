package com.pong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import javafx.scene.shape.Rectangle;

import java.awt.*;

//640 x 480

public class PONG extends ApplicationAdapter {
	private static Texture img;
	private static SpriteBatch batch;

	float bolaX , bolaY;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("bolaPrincipal.png");

		int bolaX = 220;
		int bolaY = 210;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, bolaX, bolaY);
		batch.end();
	}

	@Override
	public void mover(render){
		int dx=1;
		int dy=1;
		bolaX+=dx;
		bolaY+=dy;
		if(bolaX>render.getMaxX()){
			dx=-dx;
		}
		if(bolaY>render.getMaxY()){
			dy=-dy;
		}
		if(bolaX<0){
			dx=-dx;
		}
		if(bolaY<0){
			dy=-dy;
		}
	}
}
