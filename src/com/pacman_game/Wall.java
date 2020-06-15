package com.pacman_game;

import java.awt.*;
import java.util.ArrayList;

public class Wall extends Tile{
    private final boolean isSolid = true;
    public Color borderColor;
    public Color wallColor;
    private final ArrayList<Color> colors = new ArrayList<>();
    protected Wall() {
        super();
        borderColor = Color.BLUE;
        wallColor = Color.BLACK;
        colors.add(Color.BLACK);
        colors.add(Color.WHITE);
    }

    public void changeColor(){
        wallColor = colors.get(Math.abs(1 - colors.indexOf(wallColor)));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(borderColor);
        g.drawRect(x, y, Tile.SIZE, Tile.SIZE);
        g.setColor(wallColor);
        g.fillRect(x + 1, y + 1, Tile.SIZE - 1, Tile.SIZE - 1);
        coordinates = new int[]{x, y};
    }

    @Override
    public boolean isSolid() {
        return isSolid;
    }

    @Override
    public int eatDot() {
        return 0;
    }


}
