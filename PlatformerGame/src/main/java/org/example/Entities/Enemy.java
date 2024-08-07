package org.example.Entities;

import org.example.Levels.Level;

import java.awt.*;

import static org.example.Constants.Motion.Dirctions.LEFT;
import static org.example.Constants.Motion.Dirctions.RIGHT;
import static org.example.Constants.Sprites.Player.*;
import static org.example.Levels.Level.IsOnFloor;

public abstract class Enemy extends Entity{
    protected int walkingDir = LEFT;

    public Enemy(float initialX, float initialY, int initialWidth, int initialHeight) {
        super(initialX, initialY, initialWidth, initialHeight);
    }

    public void update() {
        updateInAir();
        updateBehaviour();
    }

    private void updateBehaviour() {
        if(!inAir){
            switch (animation){
                case IDLE:
                    animation = RUN;
                    break;
                case RUN:
                    move();
                    break;
                case ATTACK:
                    break;
            }
        }
    }

    private void move() {
        float xSpeed = 0;
        if(walkingDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = +walkSpeed;
        if(Level.CanMoveInPosition(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelBlockIndexes)){
            if(IsOnFloor(hitbox, xSpeed, levelBlockIndexes)){
                hitbox.x += xSpeed;
                return;
            }
        }
        walkingDir = (walkingDir == LEFT) ? RIGHT : LEFT;
    }

    private void updateInAir() {
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
