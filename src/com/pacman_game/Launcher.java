package com.pacman_game;

public class Launcher {
    public static final int WIDTH = 700;
    public static final int HEIGHT = 680;
    public static final int SCALE = 1;

    public static void main(String[] args) {
        Game game = new Game("Pacman", WIDTH, HEIGHT);
        game.start();

    }
}
