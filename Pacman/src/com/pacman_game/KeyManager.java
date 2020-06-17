package com.pacman_game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    public boolean up, down, left, right;
    private boolean[] keys;
    private boolean change = false;
    private Game game;


    public KeyManager(Game game){
        this.game = game;
        keys = new boolean[256];
        right = true;
    }

    public void update(){
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
    }
    public void reset(){
        keys[KeyEvent.VK_UP] = false;
        keys[KeyEvent.VK_DOWN] = false;
        keys[KeyEvent.VK_LEFT] = false;
        keys[KeyEvent.VK_RIGHT] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        reset();
        keys[e.getKeyCode()] = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        reset();
    }
}
