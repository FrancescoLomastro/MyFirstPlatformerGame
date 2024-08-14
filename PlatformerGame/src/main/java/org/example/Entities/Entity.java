package org.example.Entities;

import org.example.GameScenes.PlayScene;
import org.example.Levels.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Motion.COLLISION_FALL_SPEED;
import static org.example.Constants.Motion.GRAVITY;
import static org.example.Constants.Sprites.Enemy.IDLE;
import static org.example.Constants.Sprites.Player.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Utility.HelpMethods.YPositionUnderRoofOrAboveFloor;

public abstract class Entity {

    protected float initialX, initialY;
    protected int initialWidth, initialHeight;
    protected Rectangle2D.Float hitbox;
    protected Rectangle2D.Float attackBox;
    protected float xImageOffset;
    protected float yImageOffset;

    //Animation Variables
    protected int animationTick;
    protected int animationFrame;
    protected int animation;

    //Position Variables
    protected boolean inAir;
    protected float speedInAir;
    protected int[][] levelBlockIndexes;
    protected float walkSpeed;
    protected float jumpSpeed;

    //Mirror image for left/right movement
    protected int flipX;   // 0 = no flip, hitbox_width = flip
    protected int flipW;   // 1 = no flip, -1 = flip

    //Health Variables
    protected int maxHealth;
    protected int currentHealth;

    protected boolean active;
    protected boolean attackChecked;
    protected boolean hitten;
    protected int hittenFrameCounter;

    public Entity(float initialX, float initialY, int initialWidth, int initialHeight) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.initialWidth = initialWidth;
        this.initialHeight = initialHeight;
        this.animationFrame = 0;
        this.animationTick = 0;
        this.animation = IDLE;
        this.inAir = true;
        this.walkSpeed = 1.0f * SCALE;
        this.flipX = 0;
        this.flipW = 1;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.active = true;
        this.hittenFrameCounter = 0;
    }

    protected void debug_drawHitbox(Graphics g, int xLvlOffset, Rectangle2D.Float hitbox_) {
        g.setColor(Color.RED);
        g.drawRect((int) hitbox_.x - xLvlOffset, (int) hitbox_.y, (int) hitbox_.width, (int) hitbox_.height);
    }



    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(initialX, initialY, width * SCALE, height* SCALE);
    }


    protected boolean onTheFloor(int[][] levelBlockIndexes) {
        if (!Level.IsPositionSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelBlockIndexes))
            if (!Level.IsPositionSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelBlockIndexes))
                return false;
        return true;
    }

    protected void handleGravity() {
        if (Level.CanMoveInPosition(hitbox.x, hitbox.y + speedInAir, hitbox.width, hitbox.height, levelBlockIndexes)) {
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

    public void addLevelData(int[][] blockIndexes){
        this.levelBlockIndexes = blockIndexes;
    }

    public float getY() {
        return hitbox.y;
    }

    public float getX() {
        return hitbox.x;
    }

    public float getWidth() {
        return hitbox.width;
    }

    public float getHeight() {
        return hitbox.height;
    }




    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }





    public boolean isActive() {
        return active;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    protected void newAnimation(int animation) {
        this.animation = animation;
        animationTick = 0;
        animationFrame = 0;
    }

    public void reset() {
        hitbox.x = initialX;
        hitbox.y = initialY;
        currentHealth = maxHealth;
        newAnimation(IDLE);
        active = true;
        speedInAir = 0;
    }
}
