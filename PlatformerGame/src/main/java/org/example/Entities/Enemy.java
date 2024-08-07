package org.example.Entities;

import org.example.Levels.Level;

import java.awt.*;

import static org.example.Constants.Motion.Dirctions.LEFT;
import static org.example.Constants.Motion.Dirctions.RIGHT;
import static org.example.Constants.Sprites.Enemy.*;
import static org.example.Constants.Window.TILES_SIZE;
import static org.example.Levels.Level.IsOnFloor;

public abstract class Enemy extends Entity{
    protected int walkingDir = LEFT;
    private int attackDistance;

    public Enemy(float initialX, float initialY, int initialWidth, int initialHeight) {
        super(initialX, initialY, initialWidth, initialHeight);
        this.attackDistance = TILES_SIZE;
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
                    if(canSeePlayer()) {
                        turnTowardsPlayer();
                        if (isPlayerCloseForAttack())
                            animation = ATTACK;
                    }

                    move();
                    break;
                case ATTACK:
                    break;
            }
        }
    }

    private boolean isPlayerCloseForAttack() {
        int absValue = (int) Math.abs(Player.lastPosition.x - hitbox.x);
        return absValue <= attackDistance;
    }

    private void turnTowardsPlayer(){
        if(Player.lastPosition.x > hitbox.x){
            walkingDir = RIGHT;
        }else {
            walkingDir = LEFT;
        }
    }

    private boolean canSeePlayer() {
        int playerTileY = (int) (Player.lastPosition.y/TILES_SIZE);
        int tileY = (int) (hitbox.y/TILES_SIZE);
        if(playerTileY == tileY){
            if(isPlayerInRange()){
                if(Level.IsSightClear(levelBlockIndexes,hitbox, Player.lastPosition, tileY)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPlayerInRange() {
        int absValue = (int) Math.abs(Player.lastPosition.x - hitbox.x);
        return absValue <= attackDistance * 5;
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
