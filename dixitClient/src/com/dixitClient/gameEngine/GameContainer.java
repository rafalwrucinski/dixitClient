package com.dixitClient.gameEngine;

import com.dixitClient.dixit.ServerClientManager;
import com.dixitClient.dixit.login.LoginManager;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameContainer implements Runnable
{
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;

	private static ExecutorService executorService;

	private boolean running = false;
	private final double UPDATE_CAP = 1.0/60.0;
	private int width = 1280;
	private int height = 864;
	private float scale = 1f;

	private String title= "Dixit Card Game";

	public GameContainer(AbstractGame game){
		this.game = game;
	}

	public void start(){
		executorService = Executors.newFixedThreadPool(5);
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);

		thread = new Thread(this);
		thread.run();
	}

	public void stop(){

	}

	@Override
	public void run(){
		running = true;

		boolean render = false;
		double firstTime = 0;
		double lastTime = Time.getTime();
		double passedTime =0;
		double unprocessedTime = 0;

		double frameTime = 0 ;
		int frames =0;
		int fps =0;

		while(running){
			render = false;

			firstTime = Time.getTime();
			passedTime = firstTime - lastTime;
			lastTime = firstTime;

			unprocessedTime += passedTime;
			frameTime += passedTime;

			while (unprocessedTime >= UPDATE_CAP)
			{
				unprocessedTime -= UPDATE_CAP;
				render = true;

				game.update(this,(float)UPDATE_CAP);

				input.update();

				if(frameTime >=1.0){
					frameTime = 0;
					fps = frames;
					frames =0;
				}
			}

			if(render){
				renderer.clear();
				game.render(this, renderer);
				renderer.process();
				renderer.drawText("FPS:"+fps, 0,0,0xff00ffff,0);
				window.update();
				frames++;
			}
			else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		dispose();
	}

	private void dispose(){

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}


	public float getScale() {
		return scale;
	}


	public String getTitle() {
		return title;
	}

	public Window getWindow() {
		return window;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setScale(float scale){this.scale= scale;}

	public Input getInput() {
		return input;
	}
}