package com.pacman_game;

import com.GameState.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Game implements Runnable{

    private Display display;
    public String title;
    public int width, height;

    private Thread thread;
    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;
    private int pacX = 358;
    private int pacY = 324;
    private Pacman pacman = new Pacman(this, pacX, pacY);
    private final int numGhosts = 4;
    private Ghost[] ghosts = new Ghost[numGhosts];
    private GhostManager ghostManager;
    private MenuState menuState;
    private PlayState playState;
    private CollisionDetector collisionDetector;
    private CollisionDetector[] ghostCollision = new CollisionDetector[numGhosts];
    private final KeyManager keyManager;
    private final Map map;



    public Game(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        display = new Display(title, width, height);
        for (int i = 0; i < ghosts.length; i++){
            ghosts[i] = new Ghost(this, i);
        }
        keyManager = new KeyManager(this);
        map = new Map("new.txt");
        collisionDetector = new CollisionDetector(this, pacman);
        for (int i = 0; i < ghosts.length; i++){
            CollisionDetector temp = new CollisionDetector(this, ghosts[i]);
            ghostCollision[i] = temp;
            ghosts[i].setCollisionDetector(temp);
        }
        ghostManager = new GhostManager(ghosts);
        pacman.setCollisionDetector(collisionDetector);
    }

    private void init(){
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        menuState = new MenuState(this);
        playState = new PlayState(this);
        display.getFrame().addKeyListener(menuState);
        GameState.setCurrentState(menuState);

    }

    public void startOverListener(GameOver gameOver){
        display.getFrame().addKeyListener(gameOver);
    }

    @Override
    public void run() {
        init();
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running){
            now = System.nanoTime();

            timer += now - lastTime;
            delta += (now - lastTime) / timePerTick;
            lastTime = now;
            if (delta >= 1) {
                update();
                render();
                ticks++;
                delta--;
            }
            if (timer >= 1000000000){
                ticks = 0;
                timer = 0;
            }
//            try {
//                TimeUnit.MILLISECONDS.sleep(20);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        stop();
    }
    public Pacman getPacman(){
        return pacman;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public CollisionDetector getCollisionDetector(){
        return collisionDetector;
    }

    public Map getMap(){
        return map;
    }

    public Ghost[] getGhosts(){
        return ghosts;
    }

    public PlayState getPlayState(){
        return playState;
    }

    public void setPlayState(PlayState playState){
        this.playState = playState;
    }

    public GhostManager getGhostManager(){
        return ghostManager;
    }
    public void freeze(){
        pacman.stop();
        for (Ghost g: ghosts){
            g.stop();
        }
    }
    public void reset(){
        pacman.reset();
        for (Ghost g: ghosts){
            g.reset();
        }
        ghostManager.reset();

    }
    public synchronized void start(){
        if (running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if (!running){
            return;
        }
        running = false;
        try{
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void update(){
        keyManager.update();
        if (GameState.getCurrentState() != null){
            GameState.getCurrentState().update();
        }
        ghostManager.update();

    }
    public void drawGhostEat(){
        playState.pause();
    }

    public void drawDeath(BufferedImage image, int x, int y){
        BufferStrategy tempBS = display.getCanvas().getBufferStrategy();
        Graphics2D temp = (Graphics2D) tempBS.getDrawGraphics();
        System.out.println(Arrays.toString(new int[]{x, y}));
        g.clearRect(x, y, 15, 15);
        temp.drawImage(image, x, y, Tile.SIZE, Tile.SIZE, null);
        tempBS.show();
        g.dispose();

    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //Clear screen
        g.clearRect(0, 0, width, height);
        if (GameState.getCurrentState() != null) {
            GameState.getCurrentState().render((Graphics2D) g);}

        //End drawing\
        bs.show();
        g.dispose();
        }
}
