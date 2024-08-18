package org.example.Props;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Candle.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Utility.LoadContent.CANDLE_ATLAS;

public class Candle extends Prop {
    private static BufferedImage[] images = LoadAnimation();

    private static BufferedImage[] LoadAnimation() {
        BufferedImage[] imgs = new BufferedImage[7];
        BufferedImage img = LoadContent.GetSpriteAtlas(CANDLE_ATLAS);
        for(int i = 0; i < CANDLE_SPRITE_AMOUNT; i++) {
            imgs[i] = img.getSubimage(i*CANDLE_WIDTH_DEFAULT, 0, CANDLE_WIDTH_DEFAULT, CANDLE_HEIGHT_DEFAULT);
        }
        return imgs;
    }


    public Candle(int x, int y, int objectType) {
        super(x, y, objectType);
    }



    @Override
    public void update(){
        updateAnimationTick();
    }

    private void updateAnimationTick() {
        animationTick++;

        if (animationTick >= CANDLE_ANIMATION_SPEED) {
            animationTick = 0;
            animationFrame++;
            if (animationFrame >= CANDLE_SPRITE_AMOUNT)
                animationFrame = 0;
        }
    }


    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(images[animationFrame], x - xLvlOffset, y, CANDLE_WIDTH, CANDLE_HEIGHT, null);
    }

    @Override
    public void reset() {
        active = true;
    }
}
