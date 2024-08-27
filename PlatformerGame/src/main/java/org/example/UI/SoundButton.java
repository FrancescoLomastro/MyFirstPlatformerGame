package org.example.UI;

import org.example.Audio.AudioManager;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.UI.SoundButtons.*;

public class SoundButton extends Button {
    private BufferedImage[][] images;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int rowIndex, columnIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(LoadContent.SOUND_BUTTONS);
        images = new BufferedImage[2][3];
        for(int j = 0; j < images.length; j++) {
            for(int i = 0; i < images[j].length; i++) {
                images[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT,SOUND_SIZE_DEFAULT);
            }
        }
    }

    public void update(){
        if(muted){
            rowIndex = 1;
        }else {
            rowIndex = 0;
        }

        columnIndex = 0;
        if(mouseOver){
            columnIndex = 1;
        }
        if(mousePressed){
            columnIndex = 2;
        }

    }

    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }

    public void draw(Graphics g){
        g.drawImage(images[rowIndex][columnIndex], x, y, width, height, null);
    }


    public void setMouseOver(boolean mouseOver) {
        if(mouseOver && !this.mouseOver){
            AudioManager.getInstance().playSelectionSound();
        }
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

}
