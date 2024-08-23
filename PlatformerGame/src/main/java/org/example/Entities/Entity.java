package org.example.Entities;

import org.example.Levels.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Motion.*;
import static org.example.Utility.HelpMethods.*;
import static org.example.Constants.Window.SCALE;

/**
 * Abstract class for all entities in the game
 * Contains all the basic methods and variables that all entities need
 */
public abstract class Entity {

    protected float initialX;
    protected float initialY;
    protected int initialWidth;
    protected int initialHeight;
    protected float xImageOffset;
    protected float yImageOffset;

    protected int attackBoxOffsetX;
    protected int attackBoxOffsetY;

    protected float speedInAir;
    protected float walkSpeed;
    protected float jumpSpeed;

    protected Rectangle2D.Float hitbox;
    protected Rectangle2D.Float attackBox;

    protected boolean active;
    protected boolean attackChecked;
    protected boolean hit;
    protected boolean inAir;

    //Mirror image for left/right movement
    protected int flipX;   // 0 = no flip, hitbox_width = flip
    protected int flipW;   // 1 = no flip, -1 = flip

    //Health Variables
    protected int maxHealth;
    protected int currentHealth;
    protected int damage;

    //Animation Variables
    protected int animationTick;
    protected int animationFrame;
    protected int animation;
    protected int entityHitFrameCounter;

    protected int[][] levelTextures;

    public Entity(float initialX, float initialY, int initialWidth, int initialHeight) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.initialWidth = initialWidth;
        this.initialHeight = initialHeight;
        this.animationFrame = 0;
        this.animationTick = 0;
        this.inAir = true;
        this.walkSpeed = 1.0f * SCALE;
        this.flipX = 0;
        this.flipW = 1;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.active = true;
        this.entityHitFrameCounter = 0;
    }

    /**
     * This method is used to initialize the entity hit box
     * @param width hit box width
     * @param height hit box height
     */
    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(initialX, initialY, width * SCALE, height* SCALE);
    }


    /**
     * This method checks if the entity is on the floor
     * @param levelTexture the level texture matrix
     * @return true if the entity is on the floor, otherwise false
     */
    protected boolean isOnFloor(int[][] levelTexture) {
        if (!Level.IsPositionSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelTexture))
            if (!Level.IsPositionSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelTexture))
                return false;
        return true;
    }

    /**
     * This method is used to change the air position of an entity according to a gravitational behaviour.
     * If the entity is not on the floor, the entity will fall.
     * If the entity is on the floor, the entity will not fall.
     */
    protected void handleGravity() {
        if (Level.CanMoveInPosition(hitbox.x, hitbox.y + speedInAir, hitbox.width, hitbox.height, levelTextures)) {
            hitbox.y += speedInAir;
            speedInAir += GRAVITY;
        } else {
            hitbox.y = YPositionUnderRoofOrAboveFloor(hitbox, speedInAir);
            if (speedInAir > 0) {
                inAir = false;
                speedInAir = 0;
            }
            else {
                speedInAir = COLLISION_FALL_SPEED;
            }
        }
    }

    /**
     * This method equips the entity with the passed level texture matrix
     * @param levelTexture the level texture matrix
     */
    public void addLevelData(int[][] levelTexture){
        this.levelTextures = levelTexture;
    }

    /**
     * This method is used to change the current animation for the entity.
     * @param animation the new animation to be used
     */
    protected void newAnimation(int animation) {
        this.animation = animation;
        animationTick = 0;
        animationFrame = 0;
    }

    /**
     * Updates the entity's attack box by moving it
     */
    protected void updateAttackBox() {
        if(flipW == 1)
            attackBox.x = hitbox.x + attackBoxOffsetX;
        else
            attackBox.x = hitbox.x + hitbox.width - attackBox.width - attackBoxOffsetX;
        attackBox.y = hitbox.y + attackBoxOffsetY;
    }

    /**
     * Resets the entity to its initial state.
     * Resect the position, the health, the speed in air
     */
    public void reset() {
        hitbox.x = initialX;
        hitbox.y = initialY;
        currentHealth = maxHealth;
        active = true;
        speedInAir = 0;
    }

    /**
     * This method is a getter for the Y coordinate of the entity
     * @return the Y coordinate of the entity
     */
    public float getY() {
        return hitbox.y;
    }

    /**
     * This method is a getter for the X coordinate of the entity
     * @return the X coordinate of the entity
     */
    public float getX() {
        return hitbox.x;
    }

    /**
     * This method is a getter for the width of the entity
     * @return the width of the entity
     */
    public float getWidth() {
        return hitbox.width;
    }

    /**
     * This method is a getter for the height of the entity
     * @return the height of the entity
     */
    public float getHeight() {
        return hitbox.height;
    }

    /**
     * This method is a getter for the entity hit box
     * @return the hit box of the entity
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    /**
     * This method is a getter for the active variable of the entity
     * @return true if the entity is active, otherwise false
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This method is a getter for the entity current health
     * @return the entity current health
     */
    public int getCurrentHealth() {
        return currentHealth;
    }


    /**
     * This method is used as debug to draw a specific box
     */
    protected void debug_drawHitbox(Graphics g, int xLvlOffset, Rectangle2D.Float box) {
        g.setColor(Color.RED);
        g.drawRect((int) box.x - xLvlOffset, (int) box.y, (int) box.width, (int) box.height);
    }

}
