package com.pacman_game;

import java.awt.*;

public class Tunnel extends Tile{
    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.fillRect(x + 1, y, Tile.SIZE - 1, Tile.SIZE);
    }

    @Override
    public boolean isSolid() {
        return false;
    }


    @Override
    public int eatDot() {
        return 0;
    }

    @Override
    public void changeColor() {

    }
}
