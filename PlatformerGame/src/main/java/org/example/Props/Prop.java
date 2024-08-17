package org.example.Props;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Window.SCALE;

public abstract class Prop {
    protected int x;
    protected int y;
    protected int objectType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation;
    protected boolean active = true;
    protected int animationFrame;
    protected int animationTick;
    protected int xDrawOffset, yDrawOffset;
    protected int[][] levelBlockIndexes;

    public Prop(int x, int y, int objectType) {
        this.y = y;
        this.x = x;
        this.objectType = objectType;
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width * SCALE, height* SCALE);
    }

    public void debug_drawHitbox(Graphics g, int xLvlOffset) {
        // For debugging the hitbox
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);

    }


    public void update(){

    }

    public void draw(Graphics g, int xLvlOffset){

    }



    public Rectangle2D getHitbox() {
        return hitbox;
    }

    public void setActive(boolean b) {
        active = b;
    }

    public boolean isActive() {
        return active;
    }

    public abstract void reset();

    public void addLevelData(int[][] levelBlockIndexes) {
        this.levelBlockIndexes = levelBlockIndexes;
    }
}