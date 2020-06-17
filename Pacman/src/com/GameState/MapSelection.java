package com.GameState;

import com.pacman_game.Map;

import java.awt.*;
import java.util.ArrayList;

public class MapSelection {
    private String name;
    private Map map;
    public int[] coordinates;
    public int[] size;
    private final Font FONT = new Font("Goudy Stout", Font.PLAIN, 30);
    private boolean hover;
    private boolean selected;

    public MapSelection(String name, Map map, int[] coordinates){
        this.name = name;
        this.map = map.getTransparentClone();
        this.coordinates = coordinates;
        size = new int[]{40 * name.length(), 40};
        hover = false;
        selected = false;
    }

    public void resetSelected(){
        selected = false;
    }

    public void resetHover(){
        hover = false;
    }
    public void setHover(){
        hover = true;
    }

    public void setSelected(){
        selected = true;
    }

    public void reset(){
        hover = false;
        selected = false;
    }

    public void render(Graphics2D g){
        if (hover){
            g.setColor(Color.WHITE);
        }
        if (selected){
            g.setColor(Color.YELLOW);
        }
        if (hover || selected) {
            g.fillRect(coordinates[0], coordinates[1], size[0], size[1]);
            g.setColor(Color.BLACK);
            g.fillRect(coordinates[0] + 3, coordinates[1] + 3, size[0] - 6, size[1] - 6);
            map.render(g);
        }

        g.setColor(Color.MAGENTA);
        g.setFont(FONT);
        g.drawString(name, coordinates[0] + 10, coordinates[1] + FONT.getSize());
    }
}
