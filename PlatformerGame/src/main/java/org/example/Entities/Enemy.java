package org.example.Entities;

import org.example.GameScenes.PlayScene;
import org.example.Levels.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Motion.Dirctions.LEFT;
import static org.example.Constants.Motion.Dirctions.RIGHT;
import static org.example.Constants.Sprites.ENTITY_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Enemy.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Constants.Window.TILES_SIZE;
import static org.example.Levels.Level.IsOnFloor;

public abstract class Enemy extends Entity{
    protected int walkingDir;
    protected int attackDistance;
    protected int attackBoxOffsetX;
    protected int damage;

    public Enemy(float initialX, float initialY, int initialWidth, int initialHeight) {
        super(initialX, initialY, initialWidth, initialHeight);
        this.walkingDir = LEFT;
        this.attackDistance = TILES_SIZE;
        this.damage = -1;
        this.currentHealth = 40;
        this.walkSpeed = 0.5f * SCALE;
    }

    public void update() {
        updateInAir();
        updateBehaviour();
        flipX();
        flipW();
    }

    protected void updateAnimationTick(int spriteAmount) {
        animationTick++;
        if(animationTick >= ENTITY_ANIMATION_SPEED){
            animationTick = 0;
            animationFrame++;
            if(animationFrame >= spriteAmount ) {
                animationFrame = 0;
                switch (animation){
                    case ATTACK,HIT -> animation = IDLE;
                    case DEAD -> active = false;
                }
            }
        }
    }

    protected void updateBehaviour() {
        if(!inAir){
            switch (animation){
                case IDLE:
                    animation = RUN;
                    break;
                case RUN:
                    move();
                    if(canSeePlayer()) {
                        turnTowardsPlayer();
                        if (isPlayerCloseForAttack())
                            newAnimation(ATTACK);
                    }
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

    protected void move() {
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
            attackChecked = true;
        }
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


    public void alterHealth(int damage) {
        int newHealth = currentHealth + damage;
        if(newHealth <= 0) {
            currentHealth = 0;
            newAnimation(DEAD);
        }
        else if (newHealth > maxHealth)
            currentHealth = maxHealth;
        else {
            currentHealth = newHealth;
            newAnimation(HIT);
        }
    }



}
