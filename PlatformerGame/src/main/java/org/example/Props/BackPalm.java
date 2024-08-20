package org.example.Props;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.BackPalm.*;
import static org.example.Constants.Prop.WaterLight.*;
import static org.example.Constants.Window.SCALE;

public class BackPalm extends Prop {

    private static BufferedImage[][] images = LoadImages();

    private static BufferedImage[][] LoadImages() {
        BufferedImage temp = LoadContent.GetSpriteAtlas(LoadContent.BACK_PALM_1_ATLAS);
        BufferedImage[][] imgs = new BufferedImage[3][4];
        for(int i = 0; i < BACK_PALM_SPRITE_AMOUNT; i++) {
            imgs[0][i] = temp.getSubimage(i*BACK_PALM_1_WIDTH_DEFAULT, 0, BACK_PALM_1_WIDTH_DEFAULT, BACK_PALM_1_HEIGHT_DEFAULT);
        }
        temp = LoadContent.GetSpriteAtlas(LoadContent.BACK_PALM_2_ATLAS);
        for(int i = 0; i < BACK_PALM_SPRITE_AMOUNT; i++) {
            imgs[1][i] = temp.getSubimage(i*BACK_PALM_2_WIDTH_DEFAULT, 0, BACK_PALM_2_WIDTH_DEFAULT, BACK_PALM_2_HEIGHT_DEFAULT);
        }
        temp = LoadContent.GetSpriteAtlas(LoadContent.BACK_PALM_3_ATLAS);
        for(int i = 0; i < BACK_PALM_SPRITE_AMOUNT; i++) {
            imgs[2][i] = temp.getSubimage(i*BACK_PALM_3_WIDTH_DEFAULT, 0, BACK_PALM_3_WIDTH_DEFAULT, BACK_PALM_3_HEIGHT_DEFAULT);
        }
        return imgs;
    }


    public BackPalm(int x, int y, int objectType) {
        super(x, y, objectType);
    }


    @Override
    public void reset() {

    }

    public void update(){
        updateAnimationTick();
    }

    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(images[objectType][animationFrame], x - xLvlOffset, y, BACK_PALM_WIDTH, BACK_PALM_HEIGHT, null);

    }


    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= BACK_PALM_ANIMATION_SPEED) {
            animationTick = 0;
            animationFrame++;
            if (animationFrame >= BACK_PALM_SPRITE_AMOUNT)
                animationFrame = 0;
        }
    }
}
