package org.example.Props.UnAnimated;

import java.awt.*;

public abstract class UnAnimatedProp {
    protected int x;
    protected int y;
    protected int objectType;

    public UnAnimatedProp(int x, int y, int objectType) {
        this.y = y;
        this.x = x;
        this.objectType = objectType;
    }


    public abstract void draw(Graphics g, int xLvlOffset);
}
