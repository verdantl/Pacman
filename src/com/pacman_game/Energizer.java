package com.pacman_game;

import java.awt.*;

public class Energizer extends Tile {
    private final boolean isSolid = false;
    private boolean hasEnergizer;
    private boolean animate;
    private int timer;
    private long lastTime;
    private final int SPEED = 200;


    protected Energizer(boolean animate) {
        super();
        this.animate = animate;
        this.hasEnergizer = true;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (animate){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
            if (timer > SPEED) {
                timer = 0;
                hasEnergizer = !hasEnergizer;
            }
        }
    }
    @Override
    public void render(Graphics g, int x, int y) {
        if (hasEnergizer){
            g.setColor(Color.orange);
            g.drawOval(x + Tile.SIZE / 3, y + Tile.SIZE / 3, 10, 10);
            g.fillOval(x + Tile.SIZE / 3, y + Tile.SIZE / 3, 10, 10);

        }
    }

    @Override
    public boolean isSolid() {
        return isSolid;
    }

    @Override
    public int eatDot() {
        if (animate){
            animate = false;
            hasEnergizer = false;
            Map.eaten++;
            return 50;
        }
        else{
            return 0;
        }
    }

    @Override
    public void changeColor() {

    }


}
