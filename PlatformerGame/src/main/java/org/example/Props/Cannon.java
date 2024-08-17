package org.example.Props;


import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Cannon.*;
import static org.example.Constants.Window.*;

public class Cannon extends Prop{
    private int tileY;
    private BufferedImage[] images;


    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        loadAnimation();
        tileY = y / TILES_SIZE;
        initHitbox(40,26);
        hitbox.x -= (int)(4 * SCALE);
        hitbox.y += (int)(6 * SCALE);
    }

    private void loadAnimation() {
        images = new BufferedImage[7];
        BufferedImage temp = LoadContent.GetSpriteAtlas(LoadContent.CANNON_ATLAS);
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(40*i, 0, 40, 26);
        }

    }

    public void update(){
        /*if(doAnimation)*/
            updateAnimationTick();

    }

    public void draw(Graphics g, int xLvlOffset) {
        int x = (int) (hitbox.x - xLvlOffset);
        int width = CANNON_WIDTH;
        if (objectType == CANNON_RIGHT) {
            x += width;
            width *= -1;
        }
        g.drawImage(images[animationFrame], x, (int) (hitbox.y), width, CANNON_HEIGHT, null);
    }

    @Override
    public void reset() {

    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= CANNON_ANIMATION_SPEED) {
            animationTick = 0;
            animationFrame++;
            if (animationFrame >= CANNON_SPRITE_AMOUNT) {
                animationFrame = 0;
                doAnimation = false;
            }
        }
    }

    public int getTileY() {
        return tileY;
    }
}

