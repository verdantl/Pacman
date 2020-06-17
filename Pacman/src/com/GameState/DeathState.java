package com.GameState;

import com.pacman_game.Animation;
import com.pacman_game.Game;
import com.pacman_game.Ghost;

import java.awt.*;

public class DeathState extends GameState {
    private boolean original;
    private int timer;
    private long lastTime;

    public DeathState(Game game) {
        super(game);
        original = true;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (original) {
            if (timer > 2500) {
                timer = 0;
                original = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (original) {
            if (500 < timer) {
                for (Ghost ghost: game.getGhosts()){
                    ghost.disappear();
                }
                if (timer <= 1450) {
                    game.getPacman().deathAnimation();
                }
            }

            game.getPlayState().render(g);
        } else {
            if (GameState.gameOver) {
                GameState.setCurrentState(new GameOver(game));
                for (Ghost ghost: game.getGhosts()){
                    ghost.disappear();
                }
            }
            else {
                game.reset();
                PlayState playState = new PlayState(game);
                GameState.setCurrentState(playState);
                game.setPlayState(playState);
            }
        }

    }
}
