package com.GameState;

import com.pacman_game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameOver extends GameState implements KeyListener {

    public GameOver(Game game){
        super(game);
        game.startOverListener(this);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        game.getPlayState().render(g);
        Font gameOver = new Font("Goudy Stout", Font.PLAIN, 11);
        g.setFont(gameOver);
        g.setColor(Color.RED);
        g.drawString("GAME OVER", 296, 259);
        g.setColor(Color.WHITE);
        g.drawString("Press SPACE to go back to the Menu screen", 121, 425);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            game.reset();
            game.getMap().reset("plan.txt");
            MenuState menuState = new MenuState(game);
            GameState.setCurrentState(menuState);
            PlayState playState = new PlayState(game);
            GameState.gameOver = false;
            game.setPlayState(playState);
            game.getPacman().newGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
