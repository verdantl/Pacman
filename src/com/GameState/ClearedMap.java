package com.GameState;

import com.pacman_game.Game;

import java.awt.*;

public class ClearedMap extends GameState {
    private boolean newMap;
    private int timer;
    private int flashtimer;
    private long lastTime;
    private final int TIME = 3000;
    private final int FLASHSPEED = 500;
    private boolean flash = false;

    public ClearedMap(Game game) {
        super(game);
        newMap = false;
        timer = 0;
        flashtimer = 0;
        lastTime = System.currentTimeMillis();
        game.freeze();
    }

    @Override
    public void update() {
        if (newMap) {
            game.reset();
            game.getMap().reset("plan.txt");
            PlayState playState = new PlayState(game);
            GameState.setCurrentState(playState);
            game.setPlayState(playState);

        }
        else if (flashtimer > TIME){
            newMap = true;

        }
        else if(timer > FLASHSPEED){
            game.getMap().flashing = !game.getMap().flashing;
            timer = 0;
            flashtimer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
        }

        else{
            timer += System.currentTimeMillis() - lastTime;
            flashtimer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (!newMap) {
            game.getPlayState().update();
            game.getPlayState().render(g);
        }
    }
}
