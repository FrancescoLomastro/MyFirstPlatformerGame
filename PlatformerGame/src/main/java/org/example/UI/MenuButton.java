package org.example.UI;

import org.example.GameScenes.Scene;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.UI.Buttons.*;

public class MenuButton extends Button {
    private int imageIndex;
    private int rowIndex;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    private Scene scene;


    public MenuButton(int x, int y, int rowIndex, Scene scene) {
        super(x, y);
        this.scene = scene;
        this.rowIndex = rowIndex;
        loadImgs();
    }


    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadContent.GetSpriteAtlas(LoadContent.MENU_BUTTONS);
        for(int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i* B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
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

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void changeScene(){
        Scene.CurrentScene = scene;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void resetBools(){
        mouseOver=false;
        mousePressed = false;
    }
}
