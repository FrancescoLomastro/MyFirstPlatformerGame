package org.example.UI;

import org.example.Audio.AudioManager;
import org.example.Utility.LoadContent;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.UI.VolumeButtons.*;


public class VolumeButton extends Button{
    private BufferedImage[] imgs;
    private BufferedImage slider;
    private int index =0;
    private boolean mouseOver, mousePressed;
    private int buttonX;
    private int minX, maxX;
    private float floatValue = 0f;

    public VolumeButton(int x, int y, int width, int height) {
        super(x + width/2, y, VOLUME_WIDTH, height);
        this.x = x;
        this.width = width;
        bounds.x -= VOLUME_WIDTH/2;
        buttonX = x+width/2;
        minX = x + VOLUME_WIDTH/2;
        maxX = x+width - VOLUME_WIDTH/2;
        loadImgs();
    }


    private void loadImgs() {
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(LoadContent.VOLUME_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * VOLUME_WIDTH_DEFAULT, 0, VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
        }
        slider = temp.getSubimage(3*VOLUME_WIDTH_DEFAULT, 0, SLIDER_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
    }

    public void update(){
        index =0;
        if(mouseOver){
            index = 1;
        }
        if(mousePressed){
            index =2;
        }
    }

    public void draw(Graphics g){
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(imgs[index], buttonX-VOLUME_WIDTH/2, y, VOLUME_WIDTH, height, null);
    }

    public void changeX(int x){
        if(x<minX)
            buttonX = minX;
        else if (x > maxX)
            buttonX = maxX;
        else
            buttonX = x;
        updateFloatValue();
        bounds.x = buttonX-VOLUME_WIDTH/2;  //sposto la hitbox insieme al pirulino, il pirulino e shiftato di "-VOLUME_WIDTH/2" nella funzione draw()

    }

    private void updateFloatValue() {
        float range = maxX - minX;
        float value = buttonX - minX;
        System.out.println("Range " + range+ "; Max " + maxX + "; Min " + minX + "; Value " + value + "; ButtonX " + buttonX);
        floatValue = value / range;
        System.out.println("Float value " + floatValue);
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


    public void setMouseOver(boolean mouseOver) {
        if(mouseOver && !this.mouseOver){
            AudioManager.getInstance().playSelectionSound();
        }
        this.mouseOver = mouseOver;
    }

    public float getFloatValue() {
        return floatValue;
    }
}
