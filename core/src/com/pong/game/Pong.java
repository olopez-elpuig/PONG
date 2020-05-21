package com.pong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pong.game.Bola;

public class Pong extends ApplicationAdapter {
	SpriteBatch batch;
	Texture pelota, pantallaStart, pantallaGanador1, pantallaGanador2, pala1, pala2;

	BitmapFont fuente;

	Bola bola = new Bola();
	Jugador1 jugador1 = new Jugador1();
	Jugador2 jugador2 = new Jugador2();
	Pantalla pantalla = new Pantalla();
	Palas palas = new Palas();
	boolean first;

	@Override
	public void create () {
		batch = new SpriteBatch();
		pelota = new Texture("bolaPrincipal.png");
		pantallaStart = new Texture("Start.png");
		pantallaGanador1 = new Texture("Player1win.png");
		pantallaGanador2 = new Texture("Player2win.png");
		pala1 = new Texture("rectangulo.png");
		pala2 = new Texture("rectangulo.png");
		fuente= new BitmapFont(Gdx.files.internal("mono.fnt"), Gdx.files.internal("mono.png"), false);
		bola.velocidadPelota = 3;
		bola.dx = bola.velocidadPelota;
		bola.dy = bola.velocidadPelota;
		pantalla.pantalla = 0;  // 0 = start / 1 = game /  2 = ganador 1 / 3 = ganador 2
		pantalla.pantallaWidth = 640;
		pantalla.pantallaHeight = 480;
		palas.subeBaja = 10;
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
			bola.bolaX += bola.dx;
			bola.bolaY += bola.dy;

			// Punto jugador 2
			if (bola.bolaX + 20 > pantalla.pantallaWidth) {
				jugador2.contadorJugador2++;
				if (jugador2.contadorJugador2 == 3 ) {
					pantalla.pantalla = 2;
				}
				resetGame();
			}

			// Punto jugador 1
			else if (bola.bolaX < 0) {
				jugador1.contadorJugador1++;
				if (jugador1.contadorJugador1 == 3){
					pantalla.pantalla = 3;
				}
				resetGame();
			}

			// Cuando toca la pala 1
			if (bola.bolaY > palas.pala1Y && bola.bolaY < palas.pala1Y + 60) {
				if (bola.bolaX + 20 > pantalla.pantallaWidth - 20) {
					bola.dx = -bola.velocidadPelota;
				}
			}

			// Cuando toca la pala 2
			else if (bola.bolaY > palas.pala2Y && bola.bolaY < palas.pala2Y + 60) {
				if (bola.bolaX < 20) {
					bola.dx = bola.velocidadPelota;
				}
			}

			// Si llega al final a lo alto
			if (bola.bolaY + 20 > pantalla.pantallaHeight) {
				bola.dy = -bola.velocidadPelota;
			} else if (bola.bolaY < 0) {
				bola.dy = bola.velocidadPelota;
			}

			// Subir pala 1
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				if (palas.pala1Y + 60 + palas.subeBaja <= pantalla.pantallaHeight) {
					palas.pala1Y += palas.subeBaja;
				}
			}
			// Bajar pala 1
			else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				if (palas.pala1Y - palas.subeBaja >= 0) {
					palas.pala1Y -= palas.subeBaja;
				}
			}

			// Subir pala 2
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				if (palas.pala2Y + 60 + palas.subeBaja <= pantalla.pantallaHeight) {
					palas.pala2Y += palas.subeBaja;
				}
			}
			// Bajar pala 2
			else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				if (palas.pala2Y - palas.subeBaja >= 0) {
					palas.pala2Y -= palas.subeBaja;
				}
			}

			batch.begin();
			batch.draw(pelota, bola.bolaX, bola.bolaY, 20,20);
			batch.draw(pala1, pantalla.pantallaWidth - 20, palas.pala1Y, 20,60);
			batch.draw(pala2, 0,palas.pala2Y, 20,60);
			fuente.draw(batch, Integer.toString(jugador1.contadorJugador1), pantalla.pantallaWidth / 2 + 50, pantalla.pantallaHeight - 30);
			fuente.draw(batch, Integer.toString(jugador2.contadorJugador2), pantalla.pantallaWidth / 2 - 50, pantalla.pantallaHeight - 30);
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
		palas.pala1Y = pantalla.pantallaHeight / 2;
		palas.pala2Y = pantalla.pantallaHeight / 2;
		bola.bolaX = pantalla.pantallaWidth / 2;
		bola.bolaY = pantalla.pantallaHeight / 2;
	}

	private void resetPoints() {
		jugador1.contadorJugador1 = 0;
		jugador2.contadorJugador2 = 0;
	}
}
