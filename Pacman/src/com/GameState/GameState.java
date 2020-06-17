package com.GameState;

import com.pacman_game.Game;

public abstract class GameState {
    protected static int rounds;
    //The following static variables/methods are referred to as the Game State Manager
    private static GameState currentState = null;
    public static boolean gameOver = false;
    public static void setCurrentState(GameState state){
        currentState = state;
    }
    protected int timer;
    protected long lastTime;
    public static GameState getCurrentState(){
        return currentState;
    }

    protected Game game;
    public GameState(Game game){
        this.game = game;
        timer = 0;
        lastTime = System.currentTimeMillis();
        rounds = 0;
    }

    public abstract void update();
    public abstract void render(java.awt.Graphics2D g);

    public void resetTimer(){
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

}
