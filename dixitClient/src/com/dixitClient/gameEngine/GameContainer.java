package com.dixitClient.gameEngine;

import com.dixitClient.dixit.DixitGame;
import com.dixitClient.dixit.serverClient.ServerResponder;
import com.dixitClient.dixit.serverClient.ServerClientManager;
import com.dixitClient.dixit.login.LoginManager;
import com.dixitClient.dixit.login.LoginWindow;
import com.dixitClient.dixit.serverClient.ServerListener;

import javax.swing.*;
import java.io.IOException;

public class GameContainer implements Runnable
{
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;

	private ServerClientManager serverClientManager;
	private ServerListener serverListener;
	private ServerResponder serverResponder;

	public static boolean connected= false;
	public static boolean submitted = false;

	private boolean running = false;
	private final double UPDATE_CAP = 1.0/20.0;
	private int width = 1280;
	private int height = 864;
	private float scale = 1f;

	private String title= "Dixit Card Game";

	public GameContainer(AbstractGame game){
		this.game = game;
	}

	public void start(){
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);

		thread = new Thread(this);
		DixitGame.es.execute(thread);
	}

	public void stop(){

	}


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

			if(LoginManager.finish && !connected) {
				boolean result = createSocket();
				if(result)
					connected=true;
				else {
					LoginWindow window = new LoginWindow();
					window.getFrame().setVisible(true);
				}
			}

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

	public boolean createSocket(){
		try {
			serverClientManager = new ServerClientManager(LoginManager.getIp(), LoginManager.getPort());
			serverListener = new ServerListener(serverClientManager);
			serverResponder = new ServerResponder(serverClientManager);

			Thread serverListenerThread = new Thread(serverListener);
			DixitGame.es.execute(serverListenerThread);
			Thread serverResponderThread = new Thread(serverResponder);
			DixitGame.es.execute(serverResponderThread);
			submitted =true;

			return true;
		} catch (IOException e){
			JOptionPane.showMessageDialog(null, "Problem z połączeniem");
			return false;
		}
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

	public ServerClientManager getServerClientManager() {
		return serverClientManager;
	}

	public ServerListener getServerListener() {
		return serverListener;
	}

	public ServerResponder getClientResponder() {
		return serverResponder;
	}
}
