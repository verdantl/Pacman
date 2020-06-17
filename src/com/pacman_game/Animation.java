package com.pacman_game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Animation {
    private int speed, index;
    private long lastTime, timer;
    private BufferedImage[][] frames;
    private int direction;
    private boolean loop = true;

    public Animation(int speed, String[][] frames){
        this.speed = speed;
        loadFrames(frames);
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
        direction = 3;
    }
    private void loadFrames(String[][] paths){
        frames = new BufferedImage[paths.length][paths[0].length];
        for (int i = 0; i < paths.length; i++){
            for (int j = 0; j < paths[i].length ; j++)
                try{
                    frames[i][j] = ImageIO.read(new File(paths[i][j]));
                } catch (IOException e){
                    e.printStackTrace();
                }
        }
    }

    public void setLoop(boolean loop){
        this.loop = loop;
    }

    public void resetTimer(){
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public int getIndex(){
        return index;
    }
    public void setFrames(BufferedImage[][] frames){
        this.frames = frames;
    }

    public void setDirection(int i){
        direction = i;
    }

    public void update(){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if (timer > speed){
            index++;
            timer = 0;
            if (index >= frames[direction].length){
                if (loop) {
                    index = 0;
                }
                else{
                    index -= 1;
                }
            }
        }
    }

    public void resetIndex(){
        index = 0;
    }
    public BufferedImage getFrame() {
        return frames[direction][index];

    }
}
