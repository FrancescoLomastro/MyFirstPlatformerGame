package org.example.Entities;

import org.example.Levels.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Sprites.Player.*;
import static org.example.Constants.Window.SCALE;

public abstract class Entity {
    protected float hitbox_x, hitbox_y;
    protected int hitbox_width, hitbox_height;
    protected Rectangle2D.Float hitbox;

    //Animation Variables
    protected int animationTick;
    protected int animationFrame;
    protected int state;

    //Position Variables
    protected boolean inAir;
    protected float speedInAir;
    protected int[][] levelBlockIndexes;
    protected float walkSpeed;
    protected float jumpSpeed;

    public Entity(float hitbox_x, float hitbox_y, int hitbox_width, int hitbox_height) {
        this.hitbox_x = hitbox_x;
        this.hitbox_y = hitbox_y;
        this.hitbox_width = hitbox_width;
        this.hitbox_height = hitbox_height;
        animationFrame = 0;
        animationTick = 0;
        state = IDLE;
        inAir = false;
    }

    protected void debug_drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }


    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(hitbox_x, hitbox_y, width * SCALE, height* SCALE);
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
}
