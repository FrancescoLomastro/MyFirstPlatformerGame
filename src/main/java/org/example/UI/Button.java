package org.example.UI;

import java.awt.*;
import static org.example.Constants.UI.Buttons.*;

/**
 * Button class that creates a button with a rectangle bounds
 */
public class Button {
    protected int x, y, width, height;
    protected Rectangle bounds;

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createBounds();
    }

    public Button(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = BUTTON_WIDTH;
        this.height = BUTTON_HEIGHT;
        createBounds();
    }

    /**
     * Creates a rectangle bounds for the button
     */
    private void createBounds() {
        bounds = new Rectangle(x, y, width, height);
    }


    /**
     * Getter for the button bounds
     * @return the rectangle bounds of the button
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Getter for the height of the button
     * @return the height of the button
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter for the width of the button
     * @return the width of the button
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter for the Y coordinate of the button
     * @return the width of the button
     */
    public int getY() {
        return y;
    }


    /**
     * Getter for the X coordinate of the button
     * @return the width of the button
     */
    public int getX() {
        return x;
    }

}
