package com.pong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pong extends ApplicationAdapter {
	SpriteBatch batch;
	Texture pelota, pantallaStart, pantallaGanador1, pantallaGanador2, pala;

	BitmapFont fuente;

	Bola bola = new Bola();
	Jugador jugador1 = new Jugador();
	Jugador jugador2 = new Jugador();
	Pantalla pantalla = new Pantalla();
	boolean first;

	@Override
	public void create () {
		batch = new SpriteBatch();
		pelota = new Texture("bolaPrincipal.png");
		pantallaStart = new Texture("Start.png");
		pantallaGanador1 = new Texture("Player1win.png");
		pantallaGanador2 = new Texture("Player2win.png");
		pala = new Texture("rectangulo.png");
		fuente= new BitmapFont(Gdx.files.internal("mono.fnt"), Gdx.files.internal("mono.png"), false);
		bola.velocidad = 3;
		bola.dx = bola.velocidad;
		bola.dy = bola.velocidad;
		pantalla.pantalla = 0;  // 0 = start / 1 = game /  2 = ganador 1 / 3 = ganador 2
		pantalla.pantallaWidth = 640;
		pantalla.pantallaHeight = 480;
		jugador1.pala.subeBaja = 10;
		jugador2.pala.subeBaja = 10;
		resetGame();
		resetPoints();
	}

	@Override
	public void render () {
		// Start
		if (pantalla.pantalla == 0) {
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(pantallaStart, 0, 0, pantalla.pantallaWidth,pantalla.pantallaHeight);
			batch.end();
			if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
				pantalla.pantalla = 1;
				resetGame();
			}
		}

		if (pantalla.pantalla == 2) {
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(pantallaGanador1, 0, 0, pantalla.pantallaWidth,pantalla.pantallaHeight);
			batch.end();
			if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
				resetGame();
				resetPoints();
				pantalla.pantalla = 1;
			}
		}


		if (pantalla.pantalla == 3) {
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(pantallaGanador2, 0, 0, pantalla.pantallaWidth,pantalla.pantallaHeight);
			batch.end();
			if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
				resetGame();
				resetPoints();
				pantalla.pantalla = 1;
			}
		}

		// Juego
		if (pantalla.pantalla == 1) {
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			bola.x += bola.dx;
			bola.y += bola.dy;

			// Punto jugador 2
			if (bola.x + 20 > pantalla.pantallaWidth) {
				jugador2.puntos++;
				if (jugador2.puntos == 3 ) {
					pantalla.pantalla = 2;
				}
				resetGame();
			}

			// Punto jugador 1
			else if (bola.x < 0) {
				jugador1.puntos++;
				if (jugador1.puntos == 3){
					pantalla.pantalla = 3;
				}
				resetGame();
			}

			// Cuando toca la pala 1
			if (bola.y > jugador1.pala.y && bola.y < jugador1.pala.y + 60) {
				if (bola.x + 20 > pantalla.pantallaWidth - 20) {
					bola.dx = -bola.velocidad;
				}
			}

			// Cuando toca la pala 2
			else if (bola.y > jugador2.pala.y && bola.y < jugador2.pala.y + 60) {
				if (bola.x < 20) {
					bola.dx = bola.velocidad;
				}
			}

			// Si llega al final a lo alto
			if (bola.y + 20 > pantalla.pantallaHeight) {
				bola.dy = -bola.velocidad;
			} else if (bola.y < 0) {
				bola.dy = bola.velocidad;
			}

			// Subir pala 1
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				if (jugador1.pala.y + 60 + jugador1.pala.subeBaja <= pantalla.pantallaHeight) {
					jugador1.pala.y += jugador1.pala.subeBaja;
				}
			}
			// Bajar pala 1
			else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				if (jugador1.pala.y - jugador1.pala.subeBaja >= 0) {
					jugador1.pala.y -= jugador1.pala.subeBaja;
				}
			}

			// Subir pala 2
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				if (jugador2.pala.y + 60 + jugador2.pala.subeBaja <= pantalla.pantallaHeight) {
					jugador2.pala.y += jugador2.pala.subeBaja;
				}
			}
			// Bajar pala 2
			else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				if (jugador2.pala.y - jugador2.pala.subeBaja >= 0) {
					jugador2.pala.y -= jugador2.pala.subeBaja;
				}
			}

			batch.begin();
			batch.draw(pelota, bola.x, bola.y, 20,20);
			batch.draw(pala, pantalla.pantallaWidth - 20, jugador1.pala.y, 20,60);
			batch.draw(pala, 0,jugador2.pala.y, 20,60);
			fuente.draw(batch, Integer.toString(jugador1.puntos), pantalla.pantallaWidth / 2 + 50, pantalla.pantallaHeight - 30);
			fuente.draw(batch, Integer.toString(jugador2.puntos), pantalla.pantallaWidth / 2 - 50, pantalla.pantallaHeight - 30);
			batch.end();
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		pelota.dispose();
		pantallaStart.dispose();
		pantallaGanador1.dispose();
		pantallaGanador2.dispose();
	}

	private void resetGame() {
		jugador1.pala.y = pantalla.pantallaHeight / 2;
		jugador2.pala.y = pantalla.pantallaHeight / 2;
		bola.x = pantalla.pantallaWidth / 2;
		bola.y = pantalla.pantallaHeight / 2;
	}

	private void resetPoints() {
		jugador1.puntos = 0;
		jugador2.puntos = 0;
	}
}

