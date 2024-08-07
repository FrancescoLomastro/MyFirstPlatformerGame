package org.example.Entities;

import org.example.Levels.Level;

import java.awt.*;

import static org.example.Constants.Motion.COLLISION_FALL_SPEED;
import static org.example.Constants.Motion.GRAVITY;
import static org.example.Constants.Sprites.PLAYER_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Player.*;
import static org.example.Constants.Sprites.Player.FALL;
import static org.example.Utility.HelpMethods.YPositionUnderRoofOrAboveFloor;

public abstract class Enemy extends Entity{
    public Enemy(float initialX, float initialY, int initialWidth, int initialHeight) {
        super(initialX, initialY, initialWidth, initialHeight);
    }

    public void update() {
        if(!inAir){
            if(!onTheFloor(levelBlockIndexes)){
                inAir = true;
            }
        }
        if(inAir){
            handleGravity();
        }
    }



    public void draw(Graphics g, int xLvlOffset) {
    }



}
