package com.GameState;

import com.pacman_game.*;

import javax.imageio.ImageIO;
import java.awt.*;

import java.io.File;
import java.io.IOException;

public class PlayState extends GameState{
    private Pacman pacman;
    private Ghost[] ghosts;
    private Map map;
    private int SCOREX;
    private int SCOREY;
    private int[] lives;
    private boolean starting;
    private final int SPEED = 1000;
    public static int scoreCounter = 0;
    private boolean pause;
    private int[] readyCoordinates;

    public PlayState(Game game) {
        super(game);

        pacman = game.getPacman();
        ghosts = game.getGhosts();
        map = game.getMap();
        starting = false;
        pause = false;
        readyCoordinates = game.getStartHandler().getReadyCoordinates();
        int[] border = game.getStartHandler().getBorder();
        SCOREX = border[0] + 40;
        SCOREY = border[1];
        lives = new int[]{border[0] + 30, border[1] + map.mapHeight * Tile.SIZE + 10};

    }
    public static void resetScoreCounter(){
        scoreCounter = 0;
    }
    public static int addScore(){
        scoreCounter += 200;
        return scoreCounter;
    }


    @Override
    public void update() {
        if (!starting || pause) {
            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            if (timer > SPEED && !starting) {
                starting = true;
                game.getGhostManager().reset();
            }
            else if (pause && timer > 600){
                pause = false;
            }
        }
        else if (Map.eaten == Map.DOTS){
            Map.eaten = 0;
            GameState.rounds++;
            GameState.setCurrentState(new ClearedMap(game));
        }
        else{
            pacman.update();
            updateGhosts();
            map.update();
        }

    }

    @Override
    public void render(Graphics2D g) {
        map.render(g);
        drawAddOn(g);

        renderGhosts(g);
        if (pause){
            drawGhostEat(g);
        }
        pacman.render(g);
//        map.renderTunnels(g);
    }

    public void pause(){
        resetTimer();
        pause = true;
    }

    private void drawAddOn(Graphics2D g) {
        Font scoreFont = new Font("Goudy Stout", Font.PLAIN, 14);
        g.setFont(scoreFont);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(pacman.getScore()), SCOREX, SCOREY);
        try{
            Image liveImage = ImageIO.read(new File("res/pacman/pacman.png"));
            for (int i = 0; i < pacman.getLives(); i++) {
                g.drawImage(liveImage, lives[0] + 25 * i, lives[1], 15, 15, null);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        if (!starting && GameState.getCurrentState() == this){
            g.drawString("READY", readyCoordinates[0], readyCoordinates[1]);
        }

    }

    public void removeGhosts(){
        for (Ghost g: ghosts){
            g.disappear();
        }
    }
    public void drawGhostEat(Graphics2D g){
        Font ghostFont = new Font("Garamond", Font.PLAIN, 12);
        g.setFont(ghostFont);
        g.setColor(Color.MAGENTA);
        Rectangle pacmanBounds = pacman.getBounds();
        g.drawString(String.valueOf(scoreCounter), pacmanBounds.x + Tile.SIZE - 5, pacmanBounds.y + 5);
    }

    private void updateGhosts(){
        for (Ghost ghost : ghosts) {
            ghost.update();
        }
    }

    private void renderGhosts(Graphics2D g){
        for (Ghost ghost : ghosts) {
            ghost.render(g);
        }
    }
    public void death(){
        if (pacman.getLives() == 0) {
            GameState.gameOver = true;
        }
            GameState.setCurrentState(new DeathState(game));
    }
}
