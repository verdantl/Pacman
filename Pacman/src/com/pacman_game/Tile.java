package com.pacman_game;

import java.awt.*;

public abstract class Tile {
    public final static int SIZE = 20;
    protected int[] coordinates = new int[2];
    protected Tile() {}

    public abstract void update();

    public abstract void render(Graphics g, int x, int y);
    public abstract boolean isSolid();

    public void setCoordinates(int[] coordinates){
        this.coordinates = coordinates;
    }
    public int[] getCoordinates(){
        return coordinates;
    }

    public abstract int eatDot();
    public abstract void changeColor();

}


