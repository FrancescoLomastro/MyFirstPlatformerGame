package org.example.UI;

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
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage temp = LoadContent.GetSpriteAtlas(LoadContent.URM_BUTTONS);
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

    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }
}
