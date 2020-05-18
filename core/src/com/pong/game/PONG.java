package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PONG extends ApplicationAdapter {
	SpriteBatch batch;
	Texture pelota, pantallaStart, pantallaGanador1, pantallaGanador2, pala1, pala2;
	int velocidadPelota = 3;
	int dx = velocidadPelota;
	int dy = velocidadPelota;
	float bolaX, bolaY;

	int contadorJugador1 = 0;
	int contadorJugador2 = 0;

	int pantalla = 0; // 0 = start / 1 = game /  2 = ganador 1 / 3 = ganador 2

	int pantallaWidth = 640;
	int pantallaHeight = 480;

	int pala1Y, pala2Y;
	int subeBaja = 10;
	BitmapFont fuente;

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
		resetGame();
	}

	@Override
	public void render () {
		// Start
		if (pantalla == 0) {
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(pantallaStart, 0, 0, pantallaWidth,pantallaHeight);
			batch.end();
			if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
				pantalla = 1;
				resetGame();
			}
		}

		if (pantalla == 2) {
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(pantallaGanador1, 0, 0, pantallaWidth,pantallaHeight);
			batch.end();
		}


		if (pantalla == 3) {
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(pantallaGanador2, 0, 0, pantallaWidth,pantallaHeight);
			batch.end();
		}

		// Juego
		if (pantalla == 1) {
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			bolaX += dx;
			bolaY += dy;

			// Punto jugador 2
			if (bolaX + 20 > pantallaWidth) {
				contadorJugador2++;
				if (contadorJugador2 == 3 ){
					pantalla = 2;
				}
				resetGame();
			}

			// Punto jugador 1
			else if (bolaX < 0) {
				contadorJugador1++;
				if (contadorJugador1 == 3){
					pantalla = 3;
				}
				resetGame();
			}

			// Cuando toca la pala 1
			if (bolaY > pala1Y && bolaY < pala1Y + 60) {
				if (bolaX + 20 > pantallaWidth - 20) {
					dx = -velocidadPelota;
				}
			}

			// Cuando toca la pala 2
			else if (bolaY > pala2Y && bolaY < pala2Y + 60) {
				if (bolaX < 20) {
					dx = velocidadPelota;
				}
			}

			// Si llega al final a lo alto
			if (bolaY + 20 > pantallaHeight) {
				dy = -velocidadPelota;
			} else if (bolaY < 0) {
				dy = velocidadPelota;
			}

			// Subir pala 1
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				if (pala1Y + 60 + subeBaja <= pantallaHeight) {
					pala1Y += subeBaja;
				}
			}
			// Bajar pala 1
			else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				if (pala1Y - subeBaja >= 0) {
					pala1Y -= subeBaja;
				}
			}

			// Subir pala 2
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				if (pala2Y + 60 + subeBaja <= pantallaHeight) {
					pala2Y += subeBaja;
				}
			}
			// Bajar pala 2
			else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				if (pala2Y - subeBaja >= 0) {
					pala2Y -= subeBaja;
				}
			}

			batch.begin();
			batch.draw(pelota, bolaX, bolaY, 20,20);
			batch.draw(pala1, pantallaWidth - 20, pala1Y, 20,60);
			batch.draw(pala2, 0,pala2Y, 20,60);
			fuente.draw(batch, Integer.toString(contadorJugador1), pantallaWidth / 2 + 50, pantallaHeight - 30);
			fuente.draw(batch, Integer.toString(contadorJugador2), pantallaWidth / 2 - 50, pantallaHeight - 30);
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
		pala1Y = pantallaHeight / 2;
		pala2Y = pantallaHeight / 2;
		bolaX = pantallaWidth / 2;
		bolaY = pantallaHeight / 2;
	}
}
