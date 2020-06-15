package com.pacman_game;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    private int width, height;
    private int borderWidth = 35;
    private int borderHeight = 80;
    private Tile[][] map;
    public static int DOTS;
    public static int eaten;
    public boolean flashing;
    private Tunnel[] tunnels;
    private int tunnelIndex = 0;
    private int[] midPoint = new int[2];

    public Map(String path){
        DOTS = 0;
        eaten = 0;
        tunnels = new Tunnel[2];
        loadMap(path);

    }

    private void loadMap(String path){

        ArrayList<String> mapBuild = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null){
                mapBuild.add(line.replaceAll("\\s+",""));
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        width = mapBuild.get(0).length();
        height = mapBuild.size();

        map = new Tile[height][width] ;
        midPoint[0] = borderWidth + (width * Tile.SIZE / 2);
        midPoint[1] = borderHeight + (height * Tile.SIZE / 2);

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                char character = mapBuild.get(y).charAt(x);
                map[y][x] = parser(character);

                assert map[y][x] != null;
                map[y][x].setCoordinates(new int[]{x * Tile.SIZE + borderWidth, y * Tile.SIZE + borderHeight});
                }
            }

        }

    public void reset(String path){
        eaten = 0;
        loadMap(path);
        flashing = false;
    }

    public int[] getMidPoint(){
        return midPoint;
    }

    private Tile parser(char character){
        if (character == '#'){
            return new Wall();

        }
        else if (character == '^'){
            DOTS++;
            return new Dot(true);

        }
        else if (character == '$'){
            return new Dot(false);
        }
        else if (character == '@'){
            DOTS++;
            return new Energizer(true);
        }

        else if (character == '*') {
            Tunnel tunnel = new Tunnel();
            tunnels[tunnelIndex] = tunnel;
            tunnelIndex += 1;
            return tunnel;
        }

        return null;
    }
    public void renderTunnels(Graphics2D g){
        for (Tunnel tunnel: tunnels){
            int[] coordinates = tunnel.getCoordinates();
            tunnel.render(g, coordinates[0], coordinates[1]);
        }
    }

    public Tile getTile(int x, int y) throws ArrayIndexOutOfBoundsException{
        int newX = (x - borderWidth) / Tile.SIZE;
        int newY = (y - borderHeight) / Tile.SIZE;
        return map[newY][newX];
    }

    public void update() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[y][x].update();
                if (flashing){
                    map[y][x].changeColor();
                }
            }
        }
        if (flashing){
            flashing = false;
        }
    }

    public void render(Graphics g){
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                int[] coordinates = map[y][x].getCoordinates();
                map[y][x].render(g, coordinates[0], coordinates[1]);
            }
        }
    }
}
