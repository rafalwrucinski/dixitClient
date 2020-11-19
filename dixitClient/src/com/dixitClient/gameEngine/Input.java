package com.dixitClient.gameEngine;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener
{
	private GameContainer gc;

	private final int NUM_MOUSE_BUTTONS = 4;
	private boolean[] mousePressed = new boolean[NUM_MOUSE_BUTTONS];
	private boolean[] mousePressedLast = new boolean[NUM_MOUSE_BUTTONS];
	private int[] mousePossition = new int[2];

	private final int NUM_KEYS = 256;
	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] keysLast = new boolean[NUM_KEYS];

	private final int NUM_BUTTONS = 256;
	private boolean[] buttons = new boolean[NUM_BUTTONS];
	private boolean[] buttonsLast = new boolean[NUM_BUTTONS];

	private int mouseX, mouseY;

	public boolean isKey(int keyCode){
		return keys[keyCode];
	}
	public boolean isKeyUp(int keyCode){
		return !keys[keyCode] && keysLast[keyCode];
	}
	public boolean isKeyDown(int keyCode){
		return keys[keyCode] && !keysLast[keyCode];
	}

	public boolean isMouseButton(int mouseButton){
		return mousePressed[mouseButton];
	}

	public boolean isMouseButtonUp(int mouseButton){
		return !mousePressed[mouseButton] && mousePressedLast[mouseButton];
	}

    public boolean isMouseButtonDown(int mouseButton){
        return mousePressed[mouseButton] && !mousePressedLast[mouseButton];
    }


    public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public Input(GameContainer gc){
		this.gc=gc;
		mouseX=0;
		mouseY=0;

		gc.getWindow().getCanvas().addKeyListener(this);
		gc.getWindow().getCanvas().addMouseListener(this);
		gc.getWindow().getCanvas().addMouseMotionListener(this);
	}

	public void update()
	{
		for (int i=0; i < NUM_KEYS; i++){
			keysLast[i]=keys[i];
		}

		for (int i= 0; i < NUM_BUTTONS; i++){
			buttonsLast[i] = buttons[i];
		}

		for (int i= 0; i< NUM_MOUSE_BUTTONS; i++){
			mousePressedLast[i]= mousePressed[i];
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed[e.getButton()]=true;
		mousePossition[0] = e.getX();
		mousePossition[1] = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed[e.getButton()]= false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = (int)(e.getX()/ gc.getScale());
		mouseY = (int)(e.getY()/ gc.getScale());
	}
}
