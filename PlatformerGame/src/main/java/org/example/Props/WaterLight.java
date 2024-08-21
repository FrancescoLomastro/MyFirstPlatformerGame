package org.example.Props;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.WaterLight.*;
import static org.example.Constants.Window.SCALE;

public class WaterLight extends Prop {

    private static BufferedImage[][] images = LoadImages();

    private static BufferedImage[][] LoadImages() {
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(LoadContent.WATER_LIGHT_1_SPRITE);
        BufferedImage[][] imgs = new BufferedImage[2][4];
        for(int i = 0; i < WATER_LIGHT_1_SPRITE_AMOUNT; i++) {
            imgs[0][i] = temp.getSubimage(i*WATER_LIGHT_WIDTH_DEFAULT, 0, WATER_LIGHT_WIDTH_DEFAULT, WATER_LIGHT_HEIGHT_DEFAULT);
        }
        temp = LoadContent.GetResourceAsBufferedImage(LoadContent.WATER_LIGHT_2_SPRITE);
        for(int i = 0; i < WATER_LIGHT_2_SPRITE_AMOUNT; i++) {
            imgs[1][i] = temp.getSubimage(i*WATER_LIGHT_WIDTH_DEFAULT, 0, WATER_LIGHT_WIDTH_DEFAULT, WATER_LIGHT_HEIGHT_DEFAULT);
        }
        return imgs;
    }


    public WaterLight(int x, int y, int objectType) {
        super(x, y, objectType);
        yDrawOffset = (int) (2 * SCALE);
    }


    @Override
    public void reset() {

    }

    public void update(){
        updateAnimationTick();
    }

    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(images[objectType][animationFrame], x - xLvlOffset, y + yDrawOffset, WATER_LIGHT_WIDTH, WATER_LIGHT_HEIGHT, null);
    }


    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= WATER_LIGHT_ANIMATION_SPEED) {
            animationTick = 0;
            animationFrame++;
            if (objectType == WATER_LIGHT_1 && animationFrame >= WATER_LIGHT_1_SPRITE_AMOUNT)
                animationFrame = 0;
            else if (objectType == WATER_LIGHT_2 && animationFrame >= WATER_LIGHT_2_SPRITE_AMOUNT)
                animationFrame = 0;
        }
    }
}
