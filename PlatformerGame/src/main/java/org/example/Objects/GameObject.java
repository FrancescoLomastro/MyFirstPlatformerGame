package org.example.Objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Window.SCALE;

public class GameObject {
    protected int x;
    protected int y;
    protected int objectType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    public GameObject(int x, int y, int objectType) {
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
}