package org.example.Props.Animated;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Candle.*;
import static org.example.Utility.LoadContent.CANDLE_SPRITE;

/**
 * Candle class for animated candle prop
 */
public class Candle extends AnimatedProp {
    private static BufferedImage[] images = LoadImages();

    public Candle(int x, int y) {
        super(x, y);
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



    private static BufferedImage[] LoadImages() {
        BufferedImage[] imgs = new BufferedImage[7];
        BufferedImage img = LoadContent.GetResourceAsBufferedImage(CANDLE_SPRITE);
        for(int i = 0; i < CANDLE_SPRITE_AMOUNT; i++) {
            imgs[i] = img.getSubimage(i*CANDLE_WIDTH_DEFAULT, 0, CANDLE_WIDTH_DEFAULT, CANDLE_HEIGHT_DEFAULT);
        }
        return imgs;
    }
}
