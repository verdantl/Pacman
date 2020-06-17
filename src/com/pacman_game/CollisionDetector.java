package com.pacman_game;
import java.awt.*;

public class CollisionDetector{
    private final Game game;
    private final Collidable object;
    private Tile nextTile;
    public boolean canMove;
    private final int bufferDistance = Tile.SIZE;
    private final Map map;
    private int[] midPoint;

    public CollisionDetector(Game game, Collidable object){
        this.game = game;
        map = game.getMap();
        this.object = object;
        midPoint = map.getMidPoint();

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
        try {
            if (i == 0) {
                potNextTile = map.getTile(coordinates[0], coordinates[1] - bufferDistance);
            } else if (i == 1) {
                potNextTile = map.getTile(coordinates[0] - bufferDistance, coordinates[1]);
            } else if (i == 2) {
                potNextTile = map.getTile(coordinates[0], coordinates[1] + bufferDistance);
            } else {
                potNextTile = map.getTile(coordinates[0] + bufferDistance, coordinates[1]);
            }
            setCanMove(potNextTile);
            if (!canMove){
                continueMoving();
            }
        } catch (ArrayIndexOutOfBoundsException e){
            teleport();
        }



    }

    private void teleport(){
        if (map.teleportHorizontal){
            object.teleportX(midPoint[0]);
        }
        else {
            object.teleportY(midPoint[1]);
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
