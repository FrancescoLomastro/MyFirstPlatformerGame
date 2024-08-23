package org.example.Props.Animated;

import org.example.Props.Prop;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.example.Constants.Window.SCALE;

/**
 * This class is the parent class for all props that are animated.
 */
public abstract class AnimatedProp extends Prop {
    protected Rectangle2D.Float hitbox;
    protected boolean active;
    protected int animationFrame;
    protected int animationTick;
    protected int xDrawOffset, yDrawOffset;
    protected int[][] levelTexture;

    public AnimatedProp(int x, int y, int objectType) {
        super(x, y, objectType);
        active = true;
    }

    public AnimatedProp(int x, int y) {
        super(x, y);
        active = true;
    }

    /**
     * Initializes the hitbox for the prop.
     */
    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width * SCALE, height* SCALE);
    }


    public abstract void update();

    public abstract void draw(Graphics g, int xLvlOffset);

    /**
     * Resets the prop to its default state.
     */
    public void reset() {
        active = true;
        animationTick = 0;
        animationFrame = 0;
    }

    /**
     * Getter for the hitbox of the prop.
     * @return The hitbox of the prop.
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    /**
     * Setter to activate/deactivate the prop.
     */
    public void setActive(boolean b) {
        active = b;
    }

    /**
     * Getter for the active state of the prop.
     * @return The active state of the prop.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Setter for the level data of the prop.
     * This can be useful for props such as Cannons that need to know the level data to not shoot through walls.
     */
    public void addLevelData(int[][] levelBlockIndexes) {
        this.levelTexture = levelBlockIndexes;
    }

    /**
     * This method is used as debug to draw the hitbox of the prop.
     */
    public void debug_drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }
}