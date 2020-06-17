package com.pacman_game;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StartHandler {
    private final String path = "maps/info.json";
    public String mapPath;
    private JSONParser parser = new JSONParser();
    private JSONObject map;
    private int[] pacCoordinates;
    private int[][] ghostCoordinates;
    private int[] readyCoordinates;
    private int[] gameOverCoordinates;
    private int[] spaceCoordinates;
    private int[] ghostDestination;
    private int borderWidth;
    private int borderHeight;

    public StartHandler(String mapName){
        mapPath = "maps/" + mapName + "/map.txt";
        try {
            FileReader reader = new FileReader(path);
            JSONObject obj = (JSONObject) parser.parse(reader);
            map = (JSONObject) obj.get(mapName);
        } catch (ParseException | IOException e){
            e.printStackTrace();
        }
        setPacCoordinates();
        setGhostCoordinates();
        setOtherCoordinates();
        setBorder();
    }

    private void setPacCoordinates(){
        JSONArray coordinates = (JSONArray) map.get("pacman coordinates");
        pacCoordinates =  new int[]{Math.toIntExact((Long) coordinates.get(0)), Math.toIntExact((Long) coordinates.get(1))};
    }

    private void setGhostCoordinates(){
        JSONArray ghosts = (JSONArray) map.get("ghost coordinates");
        int[][] ghostCoordinates = new int[ghosts.size()][((JSONArray)ghosts.get(0)).size()];
        for (int i = 0; i < ghosts.size(); i++){
            JSONArray coordinates = (JSONArray) ghosts.get(i);
            int x = Math.toIntExact((Long) coordinates.get(0));
            int y = Math.toIntExact((Long) coordinates.get(1));
            ghostCoordinates[i] = new int[]{x, y};
        }
        this.ghostCoordinates =  ghostCoordinates;
    }

    private void setBorder(){
        borderWidth = Math.toIntExact((Long) map.get("borderwidth"));
        borderHeight = Math.toIntExact((Long) map.get("borderheight"));
    }

    public int[] getBorder(){
        return new int[]{borderWidth, borderHeight};
    }
    private void setOtherCoordinates(){
        JSONArray coordinates = (JSONArray) map.get("READY");
        readyCoordinates = new int[]{Math.toIntExact((Long) coordinates.get(0)), Math.toIntExact((Long) coordinates.get(1))};
        coordinates = (JSONArray) map.get("GAME OVER");
        gameOverCoordinates = new int[]{Math.toIntExact((Long) coordinates.get(0)), Math.toIntExact((Long) coordinates.get(1))};
        coordinates = (JSONArray) map.get("SPACE");
        spaceCoordinates = new int[]{Math.toIntExact((Long) coordinates.get(0)), Math.toIntExact((Long) coordinates.get(1))};
        coordinates = (JSONArray) map.get("ghost destination");
        ghostDestination = new int[]{Math.toIntExact((Long) coordinates.get(0)), Math.toIntExact((Long) coordinates.get(1))};
    }

    public int[] getPacCoordinates() {
        return pacCoordinates;
    }

    public int[][] getGhostCoordinates() {
        return ghostCoordinates;
    }

    public int[] getReadyCoordinates() {
        return readyCoordinates;
    }

    public int[] getGameOverCoordinates() {
        return gameOverCoordinates;
    }

    public int[] getSpaceCoordinates() {
        return spaceCoordinates;
    }

    public int[] getGhostDestination(){
        return ghostDestination;
    }
}
