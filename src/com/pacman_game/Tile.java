package com.pacman_game;

import java.awt.*;

public abstract class Tile {
    public final static int SIZE = 20;

    protected Tile() {}

    public abstract void update();

    public abstract void render(Graphics g, int x, int y);
    public abstract boolean isSolid();

    public abstract void setCoordinates(int[] ints);
    public abstract int[] getCoordinates();

    public abstract int eatDot();
    public abstract void changeColor();
}


