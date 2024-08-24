package org.example.Entities;

import org.example.GameScenes.PlayScene;
import org.example.Levels.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Motion.Dirctions.*;
import static org.example.Constants.Sprites.ENTITY_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Enemy.*;
import static org.example.Constants.Window.*;
import static org.example.Levels.Level.IsOnFloor;

/**
 * Abstract class for all enemies in the game
 * Contains all the basic methods and fields that all enemies share
 */
public abstract class Enemy extends Entity{
    protected int walkingDir;
    protected int attackDistance;
    protected int sightRange;

    protected int damage;

    public Enemy(float initialX, float initialY, int initialWidth, int initialHeight) {
        super(initialX, initialY, initialWidth, initialHeight);
        this.animation = IDLE;
        this.walkingDir = LEFT;
        this.attackDistance = TILES_SIZE;
        this.damage = 10;
        this.currentHealth = 100;
        this.walkSpeed = 0.5f * SCALE;
        this.sightRange = 5;
    }

    /**
     * Updates the enemy
     * Updates the enemy's behaviour and animation
     */
    public void update() {
        updateInAir();
        updateBehaviour();
        flipX();
        flipW();
    }

    /**
     * Updates the enemy's animation
     * @param spriteAmount the amount of sprites in the current animation
     */
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

    /**
     * Updates the enemy's behaviour
     * Changes the enemy's animation based on the player's position
     */
    protected void updateBehaviour() {
        if(!inAir){
            switch (animation){
                case IDLE:
                    animation = RUN;
                    break;
                case RUN:
                    move();
                    if(canApproachPlayer()) {
                        turnTowardsPlayer();
                        if (isPlayerCloseForAttack())
                            changeAnimation(ATTACK);
                    }
                    break;

            }
        }
    }

    /**
     * Checks if the player is close enough to attack
     * @return true if the player is close enough to attack (based on the attackDistance), false otherwise
     */
    private boolean isPlayerCloseForAttack() {
        PlayScene playScene = PlayScene.getInstance();
        int absValue = (int) Math.abs(playScene.getPlayerHitbox().x - hitbox.x);
        return absValue <= attackDistance;
    }

    /**
     * Turns the enemy towards the player
     */
    private void turnTowardsPlayer(){
        PlayScene playScene = PlayScene.getInstance();
        if(playScene.getPlayerHitbox().x > hitbox.x){
            walkingDir = RIGHT;
        }else {
            walkingDir = LEFT;
        }
    }

    /**
     * Checks if the player is in range of the enemy
     * @return true if the player is in range, false otherwise
     */
    private boolean isPlayerInRange() {
        PlayScene playScene = PlayScene.getInstance();
        int absValue = (int) Math.abs(playScene.getPlayerHitbox().x - hitbox.x);
        return absValue <= TILES_SIZE * sightRange;
    }

    /**
     * Checks if the enemy can see and approach the player
     * @return true if the enemy can see and approach the player, false otherwise
     */
    private boolean canApproachPlayer() {
        PlayScene playScene = PlayScene.getInstance();
        int playerTileY = (int) (playScene.getPlayerHitbox().y/TILES_SIZE);
        int tileY = (int) (hitbox.y/TILES_SIZE);
        if(playerTileY == tileY){
            if(isPlayerInRange()){
                if(Level.IsPathWalkable(levelTextures,hitbox, playScene.getPlayerHitbox(), tileY)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Moves the enemy in the direction it is facing.
     * If the enemy approach an obstacle or a hole, the enemy inverts the direction.
     */
    protected void move() {
        float xSpeed = 0;
        if(walkingDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = +walkSpeed;
        if(Level.CanMoveInPosition(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelTextures)){
            if(IsOnFloor(hitbox, xSpeed, levelTextures)){
                hitbox.x += xSpeed;
                return;
            }
        }
        walkingDir = (walkingDir == LEFT) ? RIGHT : LEFT;
    }

    /**
     * Updates the enemy's position in the air
     */
    private void updateInAir() {
        if(!inAir){
            if(!isOnFloor(levelTextures)){
                inAir = true;
            }
        }
        if(inAir){
            handleGravity();
        }
    }

    /**
     * This method checks if the enemy's attack box intersects with the player's hit box and hurts the player.
     */
    protected void checkEnemyHitPlayer(Rectangle2D.Float attackBox, int damage) {
        PlayScene playScene = PlayScene.getInstance();
        if(attackBox.intersects(playScene.getPlayerHitbox())){
            playScene.hitPlayer(damage);
            attackChecked = true;
        }
    }

    /**
     * Draws the enemy
     * @param g the graphics object
     * @param xLvlOffset the x offset of the level
     */
    public abstract void draw(Graphics g, int xLvlOffset);


    /**
     * Flips the enemy's sprite in the x axis
     */
    public void flipX(){
        if(walkingDir == RIGHT){
            flipX = 0;
        }else
            flipX = initialWidth;
    }

    /**
     * Flips the enemy's sprite (by inverting the width of the sprite)
     */
    public void flipW(){
        if(walkingDir == RIGHT){
            flipW = 1;
        }else
            flipW = -1;
    }


    /**
     * Change the enemy current health by subtracting the damage
     * @param damage the (positive) damage to be subtracted
     */
    public void alterHealth(int damage) {
        int newHealth = currentHealth - damage;
        if(newHealth <= 0) {
            currentHealth = 0;
            changeAnimation(DEAD);
        }
        else if (newHealth > maxHealth)
            currentHealth = maxHealth;
        else {
            currentHealth = newHealth;
            changeAnimation(HIT);
        }
    }

    /**
     * Resets the enemy
     */
    @Override
    public void reset() {
        super.reset();
        changeAnimation(IDLE);
    }
}
