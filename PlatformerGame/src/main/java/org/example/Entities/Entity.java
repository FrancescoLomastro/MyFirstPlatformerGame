package org.example.Entities;

import org.example.GameScenes.PlayScene;
import org.example.Levels.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Sprites.Player.*;
import static org.example.Constants.Window.SCALE;

public abstract class Entity {
    protected PlayScene playScene;

    protected float initialX, initialY;
    protected int initialWidth, initialHeight;
    protected Rectangle2D.Float hitbox;

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

    public Entity(float initialX, float initialY, int initialWidth, int initialHeight) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.initialWidth = initialWidth;
        this.initialHeight = initialHeight;
        this.animationFrame = 0;
        this.animationTick = 0;
        this.animation = IDLE;
        this.inAir = true;
        this.flipX = 0;
        this.flipW = 1;
    }

    protected void debug_drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
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

    public void linkPlayScene(PlayScene playScene) {
        this.playScene = playScene;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}
