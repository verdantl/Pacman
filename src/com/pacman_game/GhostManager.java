package com.pacman_game;

import com.GameState.PlayState;

import java.util.Random;

public class GhostManager {
    private Ghost[] ghosts;
    private int[] modes;
    protected int timer;
    protected long lastTime;
    private Random random;
    private final int TIME = 10000;
    private final int FLASHSPEED = 400;
    private final int FLASHTIME = 7600;
    private boolean flashing;
    private int tempTimer = 0;

    public GhostManager(Ghost[] ghosts){
        this.ghosts = ghosts;
        timer = 0;
        lastTime = System.currentTimeMillis();
        random = new Random();
        modes = new int[]{0, 0, 0, 0};
        for (int i = 0; i < modes.length; i++){
            modes[i] = 1;
            if (!ghosts[i].ate) {
                ghosts[i].setMode(modes[i]);
                flashing = false;
            }
        }
    }

    public void update(){
        keepTime();
    }

    public void switchModes(){
        for (int i = 0; i < modes.length; i++){
            modes[i] = random.nextInt(2);
            if (!ghosts[i].ate) {
                ghosts[i].setMode(modes[i]);
                flashing = false;
            }
        }
    }

    public void keepTime(){
        timer += System.currentTimeMillis() - lastTime;
        tempTimer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (timer > TIME){
            switchModes();
            timer = 0;
        }
        if (flashing && timer > FLASHTIME) {
            trackFlash();
        }
    }
    private void trackFlash(){
        if (tempTimer > FLASHSPEED) {
            tempTimer = 0;
            for (int i = 0; i < modes.length; i++) {
                if (!ghosts[i].ate) {
                    ghosts[i].changeFlashing();
                }
            }
        }
    }
    public void reset(){
        for (int i = 0; i < modes.length; i++){
            modes[i] = 0;
            ghosts[i].setMode(modes[i]);
        }
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public void setScared(){
        PlayState.resetScoreCounter();
        for (int i = 0; i < modes.length; i++){
            modes[i] = 2;
            if (!ghosts[i].ate) {
                ghosts[i].setMode(modes[i]);
                ghosts[i].scaredReverseDirection();
            }

        }
        timer = 0;
        lastTime = System.currentTimeMillis();
        flashing = true;
        tempTimer = 0;
    }
}
