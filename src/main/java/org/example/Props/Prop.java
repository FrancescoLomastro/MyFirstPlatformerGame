package org.example.Props;

import java.awt.*;

/**
 * This class is the parent class for all props that are not animated.
 */
public abstract class Prop {
    protected int x;
    protected int y;
    protected int objectType;

    public Prop(int x, int y, int objectType) {
        this.y = y;
        this.x = x;
        this.objectType = objectType;
    }

    public Prop(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public abstract void draw(Graphics g, int xLvlOffset);
}
