package com.pacman_game;

import java.io.*;


public class HighScore {
    public int highScore;
    private FileWriter writer;

    public HighScore() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("highscore/high_score.txt"));
            highScore = Integer.parseInt(br.readLine());

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void update(int score){
        if (score > highScore){
            highScore = score;
        }

    }

    public void saveHighScore(){
        try {writer = new FileWriter(new File("res/high_score.txt"));
            writer.write(String.valueOf(highScore));
            writer.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
