package org.example.UI;

import org.example.Audio.AudioManager;
import org.example.GameScenes.Scene;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.UI.Buttons.*;

/**
 * This class is used to create buttons for the menu.
 */
public class MenuButton extends Button {
    private int imageIndex;
    private int imageRowIndex;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    private Scene scene;


    public MenuButton(int x, int y, int imageRowIndex, Scene scene) {
        super(x, y);
        this.scene = scene;
        this.imageRowIndex = imageRowIndex;
        loadImages();
    }


    private void loadImages() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(LoadContent.MENU_BUTTONS);
        for(int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i* B_WIDTH_DEFAULT, imageRowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[imageIndex], x, y, width, height, null );
    }

    public void update(){
        imageIndex = 0;
        if(mouseOver){
            imageIndex = 1;
        }
        if(mousePressed){
            imageIndex = 2;
        }
    }


    /**
     * Used to set the mouse over event
     */
    public void setMouseOver(boolean mouseOver) {
        if(mouseOver && !this.mouseOver){
            AudioManager.getInstance().playSelectionSound();
        }
        this.mouseOver = mouseOver;
    }

    /**
     * Used to set the mouse pressed event
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
     * Used to get the mouse pressed event
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Used change the current scene to the button scene
     */
    public void changeScene(){
        Scene.changeScene(scene);
    }

    /**
     * Used to reset the mouse over and mouse pressed booleans
     */
    public void resetBooleans(){
        mouseOver = false;
        mousePressed = false;
    }
}
