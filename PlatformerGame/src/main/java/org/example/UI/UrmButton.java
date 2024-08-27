package org.example.UI;

import org.example.Audio.AudioManager;
import org.example.GameScenes.Scene;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.UI.URMButtons.*;

public class UrmButton extends Button {

    private BufferedImage[] imgs;
    private int rowIndex;
    private boolean mouseOver, mousePressed;
    private int imageIndex;

    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(LoadContent.URM_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i*URM_SIZE_DEFAULT, rowIndex*URM_SIZE_DEFAULT, URM_SIZE_DEFAULT, URM_SIZE_DEFAULT);
        }
    }

    public void update(){
        imageIndex = 0;
        if(mouseOver){
            imageIndex =1;
        }
        if(mousePressed){
            imageIndex =2;
        }
    }

    public void draw(Graphics g){
        g.drawImage(imgs[imageIndex], x, y, URM_SIZE,URM_SIZE, null);
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
     * Used to reset the mouse over and mouse pressed booleans
     */
    public void resetBooleans(){
        mouseOver = false;
        mousePressed = false;
    }
}
