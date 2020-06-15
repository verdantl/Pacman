package com.GameState;

import com.pacman_game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuState extends GameState implements KeyListener {
    public MenuState(Game game){
        super(game);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        game.getPlayState().render(g);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S){
            game.getPlayState().resetTimer();
            GameState.setCurrentState(game.getPlayState());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
