package com.pacman_game;

import com.GameState.PlayState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Ghost implements Collidable {
    public Game game;
    private int id;

    private int x;
    private int y;
    private double SPEED = 1;
    private final int WIDTH = 15;
    private final int HEIGHT = 15;
    private BufferedImage image;
    private Rectangle bounds;
    private ArrayList<Tile> nextTiles;
    private boolean moving;
    private int direction;
    private int nextDirection;
    private CollisionDetector cd;
    private Random random = new Random();
    private Tile nextTile;
    private ArrayList<Integer> possibleDirections = new ArrayList<>();
    private Pacman pacman;
    private String[] images = {"res/ghosts/green_ghost.png", "res/ghosts/pink_ghost.png", "res/ghosts/red_ghost.png",
            "res/ghosts/teal_ghost.png", "res/ghosts/scared_ghost.png", "res/ghosts/flashing_ghost.png",
            "res/ghosts/eyes.png"};
    public static int[][] starting;
    private boolean stop = false;
    private boolean chase, scared, flashing, scatter, slow, chaseCenter;
    public boolean ate;
    private double dx = 0.5;
    public static Rectangle destination;
    private BufferedImage deathImage;

    public Ghost(Game game, int id){
        this.game = game;
        pacman = game.getPacman();
        this.id = id;
        x = starting[id][0];
        y = starting[id][1];
        direction = Math.abs(id - 3);
        nextDirection = direction;
        moving = false;
        bounds = new Rectangle(x - 3, y - 4, Tile.SIZE, Tile.SIZE);
        nextTiles = new ArrayList<>();
        loadImage();
        try {
            deathImage = ImageIO.read(new File("res/death/pacman_up6.png"));
        } catch (IOException ignored){
        }
    }

    public void reset(){
        x = starting[id][0];
        y = starting[id][1];
        stop = false;
        moving = false;
        setMode(0);
        updateBounds();
        loadImage();
        cd.reset();
        cd.update();
    }

    private void slowDown(){
        SPEED += dx;
        if (SPEED > 1){
            SPEED = 0.5;
        }
    }
    public static void setStarting(int[][] start){
        starting = start;
    }

    public static void setDestination(int[] destinationCoordinates){
        destination = new Rectangle(destinationCoordinates[0], destinationCoordinates[1], Tile.SIZE, Tile.SIZE);
    }
    public void setCollisionDetector(CollisionDetector cd){
        this.cd = cd;
    }

    public void loadImage(){
        if (ate){
            imageReader(6);
        }
        else if (flashing && scared){
            imageReader(5);
        }
        else if (scared) {
            imageReader(4);
        }
        else {
            imageReader(id);
        }
    }
    private void imageReader(int i){
        try {
            image = ImageIO.read(new File(images[i]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void teleportX(int x){
        this.x = 2 * (x - this.x) + this.x;
        updateBounds();
        moving = false;
    }

    public void teleportY(int y){
        this.y = 2 * (y - this.y) + this.y;
        updateBounds();
        moving = false;
    }
    public Rectangle getBounds(){
        return bounds;
    }

    private void updateMoving(){
        if (cd.canMove){
            moving = true;
            direction = nextDirection;
            nextTile = cd.getNextTile();
        }
    }
       //Here we assume moving is false, aka the ghost has stopped moving.
    private void setNextTiles(){
        nextTiles.clear();
        possibleDirections.clear();
        for (int i = 0; i < 4; i++){
            if (Math.abs(i - direction) != 2){
                nextDirection = i;
                cd.update();

                if (cd.canMove){
                nextTiles.add(cd.getNextTile());
                possibleDirections.add(i);
                }
            }

        }
    }
    //Here moving is still false, and we pick a random integer to be the direction

    private void chooseRandomDirection(){
        int randint;
        try {
            randint = random.nextInt(nextTiles.size());
            nextDirection = possibleDirections.get(randint);
            nextTile = nextTiles.get(randint);
        } catch(IllegalArgumentException e){
            int[] temp = {2, 3, 0, 1};
            nextDirection = temp[direction];
            cd.update();
            nextTile = cd.getNextTile();
        }

    }
    private void directionalMove(Rectangle pacmanBounds){
        if (nextTiles.size() == 0){
            int[] temp = {2, 3, 0, 1};
            nextDirection = temp[direction];
            cd.update();
            nextTile = cd.getNextTile();
        }
        else {
            int pacX = pacmanBounds.x;
            int pacY = pacmanBounds.y;
            ArrayList<Integer> temp = new ArrayList<>();
            if (pacX < bounds.x) {
                chaseOrScared(temp, 1, 3);
            }
            else if (pacX > bounds.x) {
                chaseOrScared(temp, 3, 1);
            }
            if (pacY < bounds.y) {
                chaseOrScared(temp, 0, 2);
            } else if (pacY > bounds.y) {
                chaseOrScared(temp, 2, 0);
            }
            int counter = 0;
            //Here we are positive that temp has at most 2 items
            Collections.shuffle(temp);
            for (int i : temp) {
                if (possibleDirections.contains(i)) {
                    nextDirection = i;
//                    nextTile = nextTiles.get(possibleDirections.indexOf(i));
                    cd.update();
                    break;
                }
                else{
                    counter++;
                }
            }
            if (counter == temp.size()) {
                chooseRandomDirection();
            }
        }
    }

    private void chaseOrScared(ArrayList<Integer> temp, int chaseInt, int scaredInt){
        if (chase || ate || chaseCenter) {
            temp.add(chaseInt);
        }
        else{
            temp.add(scaredInt);
        }
    }

    //after setting moving to true, we want the pacman to move in the direction that is set until the
    //collision detector detects a solid wall, in which case we set moving to false

    public void changeFlashing(){
        flashing = !flashing;
        loadImage();
    }

    private void move(){
        int[] coordinates = nextTile.getCoordinates();
        if (bounds.x < coordinates[0]){
            x += SPEED ;
            direction = 3;
        }
        else if (bounds.x > coordinates[0]){
            x -= SPEED;
            direction = 1;
        }
        else if (bounds.y > coordinates[1]){
            y -= SPEED;
            direction = 0;
        }
        else if(bounds.y < coordinates[1]){
            y += SPEED;
            direction = 2;
        }
        else{
            moving = false;
        }
        updateBounds();
    }

    private void offByOne(){
        if (SPEED == 2) {
            if (bounds.x % 2 == 0) {
                x -= 1;
            }
            if (bounds.y % 2 == 1) {
                y -= 1;
            }
        }
        updateBounds();


    }

    public void update() {
        if (!stop) {
            updateBounds();
            cd.update();
            updateMoving();
            if (slow){
                slowDown();
            }

            if (SPEED >= 1) {
                move();
            }
            updateDirection();
            checkPacman();
        }
    }

    private void updateDirection() {
        if (!moving) {
            setNextTiles();
            if (scatter) {
                chooseRandomDirection();
            } else if (ate && !scared) {

                SPEED = 2;
                if (bounds.intersects(destination)) {
                    setMode(0);

                } else {
                    chaseCenter = true;

                    directionalMove(destination);
                }

            } else {
                directionalMove(pacman.getBounds());
            }
        }
        else if (SPEED == 2){
            offByOne();
        }
    }
    private void checkPacman(){
        if (Math.abs(pacman.getBounds().x - bounds.x) < 7 &&
                Math.abs(pacman.getBounds().y - bounds.y) < 7){
            if (scared && !ate){
                ate = true;
                scared = false;
                slow = false;
                pacman.addScore(PlayState.addScore());
                game.drawGhostEat();
                SPEED = 2;
                loadImage();
            }
            else if (ate){
            }
            else {
                pacman.reduceLives();
                game.getPlayState().death();
            }
        }
    }

    public void scaredReverseDirection(){
        Rectangle pacBounds = pacman.getBounds();
        if (pacBounds.y == bounds.y){
            if (pacBounds.x < bounds.x){
                moving = false;
                direction = 3;
                nextDirection = direction;
            }
            else if (pacBounds.x > bounds.x) {
                moving = false;
                direction = 1;
                nextDirection = direction;
            }
        }
        if (pacBounds.x == bounds.x){
            if (pacBounds.y < bounds.y){
                moving = false;
                direction = 2;
                nextDirection = direction;
            }
            else if (pacBounds.y > bounds.y) {
                moving = false;
                direction = 0;
                nextDirection = direction;
            }
        }
    }

    public void render(Graphics g){
        g.drawImage(image, x, y, WIDTH, HEIGHT,null);
        if (ate){
        }
    }

    public boolean getMoving() {
        return moving;
    }

    public int getNextDirection() {
        return nextDirection;
    }

    public int getDirection(){
        return direction;
    }
    private void updateBounds(){
        bounds.x = x - 3;
        bounds.y = y - 4;
    }

    public void stop(){
        stop = true;
    }
    
    public void setMode(int i){
        if (i == 0){
            scatter = true;
            chase = false;
            scared = false;
            ate = false;
            flashing = false;
            SPEED = 1;
            slow = false;
            chaseCenter = false;
            loadImage();
        }
        else if (i == 1){
            scatter = false;
            chase = true;
            scared = false;
            ate = false;
            flashing = false;
            SPEED = 1;
            slow = false;
            chaseCenter = false;
            loadImage();
        }
        else{
            scatter = false;
            chase = false;
            scared = true;
            ate = false;
            flashing = false;
            slow = true;
            loadImage();
        }
    }

    public void disappear(){
        image = deathImage;
    }
}
