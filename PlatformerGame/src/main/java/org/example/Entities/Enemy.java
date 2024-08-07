package org.example.Entities;

import org.example.GameScenes.PlayScene;
import org.example.Levels.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Motion.Dirctions.LEFT;
import static org.example.Constants.Motion.Dirctions.RIGHT;
import static org.example.Constants.Sprites.Enemy.*;
import static org.example.Constants.Window.TILES_SIZE;
import static org.example.Levels.Level.IsOnFloor;

public abstract class Enemy extends Entity{
    protected int walkingDir = LEFT;
    private int attackDistance;
    protected boolean attackChecked;
    protected int attackBoxOffsetX;
    protected int damage;

    public Enemy(float initialX, float initialY, int initialWidth, int initialHeight) {
        super(initialX, initialY, initialWidth, initialHeight);
        this.attackDistance = TILES_SIZE;
        this.damage = -1;
    }

    public void update() {
        updateInAir();
        updateBehaviour();
        flipX();
        flipW();
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
            }
        }
    }

    private boolean isPlayerCloseForAttack() {
        PlayScene playScene = PlayScene.getInstance();
        int absValue = (int) Math.abs(playScene.getPlayerHitbox().x - hitbox.x);
        return absValue <= attackDistance;
    }

    private void turnTowardsPlayer(){
        PlayScene playScene = PlayScene.getInstance();
        if(playScene.getPlayerHitbox().x > hitbox.x){
            walkingDir = RIGHT;
        }else {
            walkingDir = LEFT;
        }
    }

    private boolean canSeePlayer() {
        PlayScene playScene = PlayScene.getInstance();
        int playerTileY = (int) (playScene.getPlayerHitbox().y/TILES_SIZE);
        int tileY = (int) (hitbox.y/TILES_SIZE);
        if(playerTileY == tileY){
            if(isPlayerInRange()){
                if(Level.IsSightClear(levelBlockIndexes,hitbox, playScene.getPlayerHitbox(), tileY)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPlayerInRange() {
        PlayScene playScene = PlayScene.getInstance();
        int absValue = (int) Math.abs(playScene.getPlayerHitbox().x - hitbox.x);
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

    protected void checkEnemyHitPlayer(Rectangle2D.Float attackBox, int damage) {
        PlayScene playScene = PlayScene.getInstance();
        if(attackBox.intersects(playScene.getPlayerHitbox())){
            playScene.hitPlayer(damage);
        }
        attackChecked = true;
    }


    public void draw(Graphics g, int xLvlOffset) {
    }

    public void flipX(){
        if(walkingDir == RIGHT){
            flipX = initialWidth;
        }else
            flipX = 0;
    }

    public void flipW(){
        if(walkingDir == RIGHT){
            flipW = -1;
        }else
            flipW = 1;
    }

    protected void updateAttackBox() {
        if(flipW == 1)
            attackBox.x = hitbox.x + hitbox.width - attackBox.width - attackBoxOffsetX;
        else
            attackBox.x = hitbox.x + attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }


}
