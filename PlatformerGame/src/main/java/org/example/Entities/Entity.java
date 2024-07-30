package org.example.Entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Window.SCALE;

public abstract class Entity {
    protected float hitbox_x, hitbox_y;
    protected int hitbox_width, hitbox_height;
    protected Rectangle2D.Float hitbox;

    public Entity(float hitbox_x, float hitbox_y, int hitbox_width, int hitbox_height) {
        this.hitbox_x = hitbox_x;
        this.hitbox_y = hitbox_y;
        this.hitbox_width = hitbox_width;
        this.hitbox_height = hitbox_height;
    }

    protected void debug_drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }


    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(hitbox_x, hitbox_y, width * SCALE, height* SCALE);
    }



}
