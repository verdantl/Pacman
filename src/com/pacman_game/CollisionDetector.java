package com.pacman_game;

import java.awt.*;

public class CollisionDetector{
    private Game game;
    private Collidable object;
    private Tile nextTile;
    public boolean canMove;
    private final int bufferDistance = Tile.SIZE;
    private Map map;

    public CollisionDetector(Game game, Collidable object){
        this.game = game;
        map = game.getMap();
        this.object = object;

    }

    public void reset(){
        nextTile = null;
        canMove = false;
    }

    private void setCanMove(Tile potNextTile){
        if (potNextTile.isSolid() || object.getMoving()){
            canMove = false;
        }
        else{
            canMove = true;
            nextTile = potNextTile;
        }
    }

    public Tile getNextTile(){
        return nextTile;
    }

    public void update(){
        int i = object.getNextDirection();
        Rectangle bounds = object.getBounds();
        int[] coordinates = {bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height};
        Tile potNextTile;
        if (i == 0){
            potNextTile = map.getTile(coordinates[0], coordinates[1] - bufferDistance);
        }
        else if (i == 1){
            potNextTile = map.getTile(coordinates[0] - bufferDistance, coordinates[1]);
        }
        else if (i == 2){
            potNextTile = map.getTile(coordinates[0], coordinates[1] + bufferDistance);
        }
        else{
            potNextTile = map.getTile(coordinates[0] + bufferDistance, coordinates[1]);
        }
        setCanMove(potNextTile);
        if (!canMove){
            continueMoving();
        }

    }
    public void continueMoving(){
        int i = object.getDirection();
        Rectangle bounds = object.getBounds();
        int[] coordinates = {bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height};
        Tile potNextTile;
        if (i == 0){
            potNextTile = map.getTile(coordinates[0], coordinates[1] - bufferDistance);
        }
        else if (i == 1){
            potNextTile = map.getTile(coordinates[0] - bufferDistance, coordinates[1]);
        }
        else if (i == 2){
            potNextTile = map.getTile(coordinates[0], coordinates[1] + bufferDistance);
        }
        else{
            potNextTile = map.getTile(coordinates[0] + bufferDistance, coordinates[1]);
        }
        setCanMove(potNextTile);
    }
}
