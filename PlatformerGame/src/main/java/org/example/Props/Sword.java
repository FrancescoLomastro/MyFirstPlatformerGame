package org.example.Props;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Ship.SHIP_ANIMATION_SPEED;
import static org.example.Constants.Prop.Ship.SHIP_SPRITE_AMOUNT;
import static org.example.Constants.Prop.Sword.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Utility.LoadContent.SWORD_ATLAS;

public class Sword extends Prop {
    private static BufferedImage[] images = LoadAnimation();

    private static BufferedImage[] LoadAnimation() {
        BufferedImage[] imgs = new BufferedImage[7];
        BufferedImage img = LoadContent.GetSpriteAtlas(SWORD_ATLAS);
        for(int i = 0; i < SWORD_SPRITE_AMOUNT; i++) {
            imgs[i] = img.getSubimage(i*SWORD_WIDTH_DEFAULT, 0, SWORD_WIDTH_DEFAULT, SWORD_HEIGHT_DEFAULT);
        }
        return imgs;
    }


    public Sword(int x, int y, int objectType) {
        super(x, y, objectType);
        initHitbox(SWORD_WIDTH_DEFAULT, SWORD_HEIGHT_DEFAULT);
        xDrawOffset = (int) (5 * SCALE);
        yDrawOffset = (int) (15 * SCALE);
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
        debug_drawHitbox(g, xLvlOffset);
    }

    @Override
    public void reset() {
        active = true;
    }
}
