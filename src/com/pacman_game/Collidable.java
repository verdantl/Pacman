package com.pacman_game;

import java.awt.*;

public interface Collidable {

    void setCollisionDetector(CollisionDetector cd);
    boolean getMoving();

    int getDirection();

    int getNextDirection();

    Rectangle getBounds();
    void teleport(int y);

}
