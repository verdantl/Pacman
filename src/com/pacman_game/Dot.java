package com.pacman_game;

import java.awt.*;

public class Dot extends Tile{

    private final boolean isSolid = false;
    private boolean hasDot;
    private int[] coordinates;

    protected Dot(boolean hasDot) {
        super();
        this.hasDot = hasDot;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g, int x, int y) {
        if (hasDot){
            g.setColor(Color.yellow);
            g.drawOval(x + Tile.SIZE / 2 - 1, y + Tile.SIZE / 2 - 1, 5, 5);
            g.fillOval(x + Tile.SIZE / 2 - 1, y + Tile.SIZE / 2 - 1, 5, 5);
            coordinates = new int[]{x, y};

        }
    }

    @Override
    public boolean isSolid() {
        return isSolid;
    }

    public int eatDot(){
        if (hasDot){
            hasDot = false;
            Map.eaten++;
            return 10;
        }
        return 0;
    }

    @Override
    public void changeColor() {

    }

    public void setCoordinates(int[] coordinates){
        this.coordinates = coordinates;
    }

    public int[] getCoordinates(){
        return coordinates;
    }



}
