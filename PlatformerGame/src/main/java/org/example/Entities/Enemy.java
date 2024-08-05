package org.example.Entities;

import java.awt.*;

import static org.example.Constants.Sprites.PLAYER_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Player.*;
import static org.example.Constants.Sprites.Player.FALL;

public abstract class Enemy extends Entity{
    public Enemy(float initialX, float initialY, int initialWidth, int initialHeight) {
        super(initialX, initialY, initialWidth, initialHeight);
    }

    public void update() {
    }

    public void draw(Graphics g, int xLvlOffset) {
    }



}
