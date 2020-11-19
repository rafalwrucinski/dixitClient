package com.dixitClient.dixit;

import com.dixitClient.dixit.login.LoginManager;
import com.dixitClient.dixit.login.LoginWindow;
import com.dixitClient.gameEngine.AbstractGame;
import com.dixitClient.gameEngine.GameContainer;
import com.dixitClient.gameEngine.Renderer;
import com.dixitClient.gameEngine.gfx.Image;
import com.dixitClient.gameEngine.gfx.ImageTile;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

enum State {
    LOGGING_STATE,
    WAITING_FOR_OTHERS,
    START_GAME;
}

public class DixitGame extends AbstractGame {
    private State state;
    private ServerClientManager serverClientManager;

    private String serverResponse;

    private Image startImage;
    private Image rectangleTransparent;
    private ImageTile textBox;

    public static GameContainer gc;

    float temp = 0;

    public DixitGame(){
        startImage = new Image("/dixitIcon.png");
        startImage.setAlpha(true);
        rectangleTransparent = new Image("/rectangleTransparent2.png");
        rectangleTransparent.setAlpha(true);
        textBox = new ImageTile("/textBox.png",800,100);
        textBox.setAlpha(true);
        state=State.LOGGING_STATE;
    }

    public static void main(String[] args) throws IOException {
        LoginWindow window = new LoginWindow();
        gc = new GameContainer(new DixitGame());

        window.getFrame().setVisible(true);
        window.getFrame().setAlwaysOnTop(true);

//        gc.setWidth(1280);
//        gc.setHeight(720);
//        gc.setScale(1.2f);
        gc.setWidth(1600);
        gc.setHeight(1080);
        gc.start();
    }

    public boolean createSocket(){
        try {
            serverClientManager = new ServerClientManager(LoginManager.getIp(), LoginManager.getPort());
            serverClientManager.setClientResponse("Connected as"+ LoginManager.getName());
            return true;
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Problem z połączeniem");
            return false;
        }
    }

    @Override
    public void update(GameContainer gc, float deltaTime) {

        //LOGIN STATE
        if(LoginManager.finish) {
            LoginManager.finish=false;
//            boolean result = createSocket();
//            if(result)
                state = State.WAITING_FOR_OTHERS;
//            else {
//                    LoginWindow window = new LoginWindow();
//                    window.getFrame().setVisible(true);
//            }
        }

        if(state==State.WAITING_FOR_OTHERS) {
            boolean isReady=false;
            if (gc.getInput().isMouseButtonDown(3)) {
                System.out.println("Left mouse clicked at:" + gc.getInput().getMouseX() + ", " + gc.getInput().getMouseY());
            }
//            if(!serverClientManager.getServerRequest().isEmpty()){

//            }
        }
        if(state==State.START_GAME){
            if (gc.getInput().isMouseButtonDown(3)) {
                System.out.println("Left mouse clicked at:" + gc.getInput().getMouseX() + ", " + gc.getInput().getMouseY());
            }
        }
    }


    @Override
    public void render(GameContainer gc, Renderer renderer) {
        if (this.getState() == State.LOGGING_STATE) {
            renderer.setzDepth(1);
            renderer.drawImage(startImage, (int) ((gc.getWidth() - startImage.getW()) / 2.0), (int) ((gc.getHeight() - startImage.getH()) / 2.0));
            renderer.setzDepth(2);
            renderer.drawImage(rectangleTransparent, (int) ((gc.getWidth() - rectangleTransparent.getW()) / 2.0f), (int) (((gc.getHeight() - rectangleTransparent.getH())) / 2.0f));
        }
        if (this.getState() == State.WAITING_FOR_OTHERS) {
            renderer.setzDepth(1);
            renderer.drawImage(startImage, (int) ((gc.getWidth() - startImage.getW()) / 2.0), (int) ((gc.getHeight() - startImage.getH()) / 2.0));
            renderer.setzDepth(2);
            renderer.drawImage(rectangleTransparent, (int) ((gc.getWidth() - rectangleTransparent.getW()) / 2.0f), (int) (((gc.getHeight() - rectangleTransparent.getH())) / 2.0f));
            renderer.setzDepth(3);
            renderer.drawText("Witaj: "+ LoginManager.getName(), (int) (gc.getWidth() / 2.0f), (int) (((gc.getHeight() - rectangleTransparent.getH())) / 2.0f) + 10, 0xff000000, 1, true);
            renderer.drawText("Oczekiwanie na pozostalych graczy",(int) (gc.getWidth() / 2.0f),(int) (gc.getHeight() / 2.0f),0xff555555,1,true);
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
