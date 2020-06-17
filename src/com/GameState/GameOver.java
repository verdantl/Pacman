package com.GameState;

import com.pacman_game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameOver extends GameState implements KeyListener {
    int[] gameOverCoordinates;
    int[] spaceCoordinates;
    public GameOver(Game game){
        super(game);
        game.startOverListener(this);
        gameOverCoordinates = game.getStartHandler().getGameOverCoordinates();
        spaceCoordinates = game.getStartHandler().getSpaceCoordinates();

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        if (gameOver) {
            game.getPlayState().render(g);
        }
        Font gameOver = new Font("Goudy Stout", Font.PLAIN, 11);
        g.setFont(gameOver);
        g.setColor(Color.RED);
        g.drawString("GAME OVER", gameOverCoordinates[0], gameOverCoordinates[1]);
        g.setColor(Color.WHITE);
        g.drawString("Press SPACE to go back to the Menu screen", spaceCoordinates[0], spaceCoordinates[1]);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            GameState.gameOver = false;
            MenuState menuState = new MenuState(game);
            GameState.setCurrentState(menuState);
            game.setMenuState(menuState);
            game.resetMouseListener();
            game.reset();

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
