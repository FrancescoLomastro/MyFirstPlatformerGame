package org.example.Props.Animated;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Sword.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Utility.LoadContent.SWORD_SPRITE;

/**
 * Sword class for animated sword prop
 */
public class Sword extends AnimatedProp {
    private static BufferedImage[] images = LoadImages();

    public Sword(int x, int y) {
        super(x, y);
        initHitbox(SWORD_WIDTH_DEFAULT, SWORD_HEIGHT_DEFAULT);
        xDrawOffset = SWORD_X_DRAW_OFFSET;
        yDrawOffset = SWORD_Y_DRAW_OFFSET;
        hitbox.y += yDrawOffset;
        hitbox.x += xDrawOffset;
        active = true;
    }


    @Override
    public void update(){
        updateAnimationTick();
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= SWORD_ANIMATION_SPEED) {
            animationTick = 0;
            animationFrame++;
            if (animationFrame >= SWORD_SPRITE_AMOUNT)
                animationFrame = 0;
        }
    }


    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(images[animationFrame], (int) (hitbox.x - xLvlOffset), (int) hitbox.y, SWORD_WIDTH, SWORD_HEIGHT, null);
        //debug_drawHitbox(g, xLvlOffset);
    }


    private static BufferedImage[] LoadImages() {
        BufferedImage[] imgs = new BufferedImage[7];
        BufferedImage img = LoadContent.GetResourceAsBufferedImage(SWORD_SPRITE);
        for(int i = 0; i < SWORD_SPRITE_AMOUNT; i++) {
            imgs[i] = img.getSubimage(i*SWORD_WIDTH_DEFAULT, 0, SWORD_WIDTH_DEFAULT, SWORD_HEIGHT_DEFAULT);
        }
        return imgs;
    }
}
