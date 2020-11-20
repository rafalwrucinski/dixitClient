package com.dixitClient.dixit;

import com.dixitClient.dixit.login.LoginManager;
import com.dixitClient.dixit.login.LoginWindow;
import com.dixitClient.gameEngine.AbstractGame;
import com.dixitClient.gameEngine.GameContainer;
import com.dixitClient.gameEngine.Renderer;
import com.dixitClient.gameEngine.gfx.CardImage;
import com.dixitClient.gameEngine.gfx.Image;
import com.dixitClient.gameEngine.gfx.ImageTile;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.dixitClient.gameEngine.GameContainer.submitted;

enum State {
    LOGGING_STATE,
    WAITING_FOR_OTHERS,
    START_GAME,
    SHUFLE_CARDS;
}

public class DixitGame extends AbstractGame {
    private State state;
    private boolean sended=false;
    private boolean temp= false;

    public static ExecutorService es;

    private Image startImage;
    private Image rectangleTransparent;
    private ImageTile textBox;
    private CardImage cardImage;

    public DixitGame(){
        startImage = new Image("/dixitIcon.png");
        startImage.setAlpha(true);
        rectangleTransparent = new Image("/rectangleTransparent2.png");
        rectangleTransparent.setAlpha(true);
        textBox = new ImageTile("/textBox.png",800,100);
        textBox.setAlpha(true);
//        state = State.LOGGING_STATE;
        state = State.SHUFLE_CARDS;
    }

    public static void main(String[] args) throws IOException {
        es = Executors.newFixedThreadPool(10);

        //LoginWindow window = new LoginWindow();
        GameContainer gc = new GameContainer(new DixitGame());

       // window.getFrame().setVisible(true);
        //window.getFrame().setAlwaysOnTop(true);

//        gc.setWidth(1280);
//        gc.setHeight(720);
//        gc.setScale(1.2f);
        gc.setWidth(1600);
        gc.setHeight(1080);
        gc.start();
    }



    @Override
    public void update(GameContainer gc, float deltaTime) {

        //LOGIN STATE
        if((LoginManager.finish) && (state==State.LOGGING_STATE)) {
            if(GameContainer.connected)
                state = State.WAITING_FOR_OTHERS;
        }

        if(state == State.WAITING_FOR_OTHERS) {
            if(submitted&&!sended){
                gc.getClientResponder().setServerResponse("Connected as " + LoginManager.getName());
                sended=true;
            }
            try {
                if (!gc.getServerListener().getServerRequestLast().isEmpty() && gc.getServerListener().getServerRequestLast() != null) {
                    if (gc.getServerListener().getServerRequestLast().equals("Server is Responding")) {
                        state = State.START_GAME;
                    }
                }
            } catch (NullPointerException e){
                return;
            }

            if (gc.getInput().isMouseButtonDown(3)) {
                gc.getClientResponder().setServerResponse("Left mouse clicked at:" + gc.getInput().getMouseX() + ", " + gc.getInput().getMouseY());
            }
        }
        if(state==State.START_GAME){
            if (gc.getInput().isMouseButtonDown(3)) {
                System.out.println("Left mouse clicked at:" + gc.getInput().getMouseX() + ", " + gc.getInput().getMouseY());
            }
        } if(state==State.SHUFLE_CARDS){
            if (gc.getInput().isMouseButtonDown(3)) {
                System.out.println("Left mouse clicked at:" + gc.getInput().getMouseX() + ", " + gc.getInput().getMouseY());
            }
            if(!temp){
                temp=true;
                cardImage=new CardImage(1, gc.getWidth(), gc.getHeight());
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
        if (this.getState() == State.START_GAME) {
            renderer.setzDepth(1);
            renderer.drawImage(startImage, (int) ((gc.getWidth() - startImage.getW()) / 2.0), (int) ((gc.getHeight() - startImage.getH()) / 2.0));
            renderer.setzDepth(2);
            renderer.drawImage(rectangleTransparent, (int) ((gc.getWidth() - rectangleTransparent.getW()) / 2.0f), (int) (((gc.getHeight() - rectangleTransparent.getH())) / 2.0f));
            renderer.setzDepth(3);
            renderer.drawText("Witaj: "+ LoginManager.getName(), (int) (gc.getWidth() / 2.0f), (int) (((gc.getHeight() - rectangleTransparent.getH())) / 2.0f) + 10, 0xff000000, 1, true);
            renderer.drawText(gc.getServerListener().getServerRequestLast(),(int) (gc.getWidth() / 2.0f),(int) (gc.getHeight() / 2.0f),0xff555555,1,true);
        }
        if (this.getState() == State.SHUFLE_CARDS) {
            renderer.setzDepth(1);
            renderer.drawImage(cardImage, (int) ((gc.getWidth() - startImage.getW()) / 2.0), (int) ((gc.getHeight() - startImage.getH()) / 2.0));
//            renderer.setzDepth(2);
//            renderer.drawImage(rectangleTransparent, (int) ((gc.getWidth() - rectangleTransparent.getW()) / 2.0f), (int) (((gc.getHeight() - rectangleTransparent.getH())) / 2.0f));
//            renderer.setzDepth(3);
//            renderer.drawText("Witaj: "+ LoginManager.getName(), (int) (gc.getWidth() / 2.0f), (int) (((gc.getHeight() - rectangleTransparent.getH())) / 2.0f) + 10, 0xff000000, 1, true);
//            renderer.drawText(gc.getServerListener().getServerRequestLast(),(int) (gc.getWidth() / 2.0f),(int) (gc.getHeight() / 2.0f),0xff555555,1,true);
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
