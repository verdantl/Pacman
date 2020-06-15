package com.pacman_game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.lang.Math;

public class Pacman implements Collidable{

    private int lives;
    private int score; //Might want to move this to somewhere else.
    private final double SPEED = 1;
    private int x;
    private int y;
    private final int WIDTH = 15;
    private final int HEIGHT = 15;
    private int direction = 3; // 0 is up, 1, is left, 2, is down, 3 is right
    private int nextDirection = 3;
    private int thirdDirection = 3;

    private BufferedImage image;
    private Game game;
    protected Rectangle bounds;
    private Tile nextTile;
    private boolean moving;
    private CollisionDetector cd;
    private Animation animation;
    private boolean stop = false;
    public Animation deathAnimation;

    public Pacman(Game game, int x, int y) {
        this.game = game;
        this.cd = game.getCollisionDetector();
        this.x = x;
        this.y = y;
        lives = 3;
        score = 0;
        bounds = new Rectangle(x - 3, y - 4, Tile.SIZE, Tile.SIZE);
        animation = new Animation(50, new String[][]{{"res/pacman/pacman_up.png", "res/pacman/pacman_right2.png"},
                {"res/pacman/pacman_left.png", "res/pacman/pacman_right2.png"}, {"res/pacman/pacman_down.png",
                "res/pacman/pacman_right2.png"},
                {"res/pacman/pacman.png", "res/pacman/pacman_right2.png"}});
        deathAnimation = new Animation(30, new String[][]{{"res/death/pacman_up.png","res/death/pacman_up.png",
                "res/death/pacman_up.png", "res/death/pacman_up1.png", "res/death/pacman_up1-5.png",
                "res/death/pacman_up1-10.png", "res/death/pacman_up2.png", "res/death/pacman_up2-5.png",
                "res/death/pacman_up2-10.png", "res/death/pacman_up3-5.png", "res/death/pacman_up3-10.png",
                "res/death/pacman_up4.png", "res/death/pacman_up4-5.png", "res/death/pacman_up4-10.png",
                "res/death/pacman_up5.png", "res/death/pacman_up5-5.png", "res/death/pacman_up5-10.png",
                "res/death/pacman_up5-15.png", "res/death/pacman_up5-20.png", "res/death/pacman_up5-25.png",
                "res/death/pacman_up5-30.png", "res/death/pacman_up5-35.png", "res/death/pacman_up6.png",
                "res/death/pacman_up6.png", "res/death/pacman_up6.png", "res/death/pacman_up6.png"}});
        deathAnimation.setDirection(0);
        loadImage();
    }

    @Override
    public void setCollisionDetector(CollisionDetector cd) {
        this.cd = cd;
    }

    public void loadImage() {
        image = animation.getFrame();
    }

    public boolean getMoving(){
        return moving;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void reset(){
        stop = false;
        x = 338;
        y = 304;
        direction = 3;
        nextDirection = 3;
        thirdDirection = 3;
        deathAnimation.resetIndex();
        updateBounds();
        moving = false;
        animation.setDirection(direction);
        animation.resetIndex();
        loadImage();
        cd.reset();
        cd.update();
    }
    public void newGame(){
        lives = 3;
        score = 0;
    }
    private void setDirectionKey() {
        if (game.getKeyManager().up) {
            setThirdDirection(0);
        } else if (game.getKeyManager().left) {
            setThirdDirection(1);
        } else if (game.getKeyManager().down) {
            setThirdDirection(2);
        } else if (game.getKeyManager().right) {
            setThirdDirection(3);
        }
    }

    public int getNextDirection(){
        return nextDirection;
    }

    public int getDirection(){
        return direction;
    }

    private void setThirdDirection(int i){
        thirdDirection = i;
    }

    private void setSecondDirection(){
        if (nextDirection == direction || (!moving)){
            nextDirection = thirdDirection;
        }
    }

    private void updateMoving(){
        if (cd.canMove){
            moving = true;
            direction = nextDirection;
            nextTile = cd.getNextTile();
        }
    }
    private void checkOpposite(){
        if (Math.abs(direction - thirdDirection) == 2){
            moving = false;
            direction = nextDirection;
            nextTile = cd.getNextTile();
            reset(thirdDirection);
        }

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
            if (nextTile != null){
                int temp = nextTile.eatDot();
                score += temp;
                if (temp == 50){
                    game.getGhostManager().setScared();
                }
            }
        }
    }
    private void reset(int i){
        direction = i;
        nextDirection = i;
        setThirdDirection(i);
    }
    public void update() {
        if (!stop) {
            updateBounds();
            cd.update();
            updateMoving();
            checkOpposite();
            move();
            setDirectionKey();
            setSecondDirection();
            if (moving) {
                updateAnimation();
                animation.update();
            } else {
                animation.resetIndex();
            }
        }
        loadImage();
    }

    private void updateAnimation(){
        animation.setDirection(direction);
    }
    private void updateBounds(){
        bounds.x = x - 3;
        bounds.y = y - 4;
    }
    public void deathAnimation(){
        AffineTransform affineTransform = new AffineTransform();
        image = deathAnimation.getFrame();
        affineTransform.rotate(Math.toRadians(-90 * direction), image.getWidth()/2, image.getHeight()/2);
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
        image = affineTransformOp.filter(image, null);
        deathAnimation.update();
    }

    public void render(Graphics g) {
        g.drawImage(image, x, y, WIDTH, HEIGHT, null);
    }



    public int getScore(){
        return score;
    }

    public void reduceLives() {
        lives--;

    }

    public int getLives() {
        return lives;
    }

    public void stop(){
        stop = true;
    }
    public void addScore(int i){
        score += i;
    }
}
