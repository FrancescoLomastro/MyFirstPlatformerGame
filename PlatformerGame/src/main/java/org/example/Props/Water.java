package org.example.Props;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Water.*;

public class Water extends Prop {

    private static BufferedImage[] images = LoadImages();

    private static BufferedImage[] LoadImages() {
        BufferedImage temp = LoadContent.GetSpriteAtlas(LoadContent.LEVEL_ANIMATED_WATER);
        BufferedImage[] imgs = new BufferedImage[ANIMATED_WATER_SPRITE_AMOUNT+1];
        for(int i = 0; i < ANIMATED_WATER_SPRITE_AMOUNT; i++) {
            imgs[i] = temp.getSubimage(i*32, 0, 32, 32);
        }
        imgs[ANIMATED_WATER_SPRITE_AMOUNT] = temp.getSubimage(ANIMATED_WATER_SPRITE_AMOUNT * 32, 0, 32,32);
        return imgs;
    }


    public Water(int x, int y, int objectType) {
        super(x, y, objectType);
    }


    @Override
    public void reset() {

    }

    public void update(){
        updateAnimationTick();
    }

    public void draw(Graphics g, int xLvlOffset) {
        if(objectType == DEEP_WATER)
            g.drawImage(images[ANIMATED_WATER_SPRITE_AMOUNT], x - xLvlOffset, y, WATER_WIDTH, WATER_HEIGHT, null);
        else
            g.drawImage(images[animationFrame], x - xLvlOffset, y, WATER_WIDTH, WATER_HEIGHT, null);
    }


    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= WATER_ANIMATION_SPEED) {
            animationTick = 0;
            animationFrame++;
            if (animationFrame >= ANIMATED_WATER_SPRITE_AMOUNT)
                animationFrame = 0;
        }
    }
}
