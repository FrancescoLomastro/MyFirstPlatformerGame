package org.example.Entities;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Sprites.Enemy.Shark.*;
import static org.example.Constants.Sprites.Enemy.Star.*;
import static org.example.Constants.Sprites.PLAYER_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Player.*;

public class Star extends Enemy {
    private static BufferedImage[][] sprites = LoadAnimations();

    public Star(float initialX, float initialY) {
        super(initialX, initialY, SHARK_WIDTH, SHARK_HEIGHT);
        initHitbox( 17, 21);
        xImageOffset = STAR_DRAWOFFSET_X;
        yImageOffset = STAR_DRAWOFFSET_Y;

    }

    private static BufferedImage[][] LoadAnimations() {
        BufferedImage temp = LoadContent.GetSpriteAtlas(LoadContent.STAR_ATLAS);
        BufferedImage[][] images = new BufferedImage[5][8];
        for(int j = 0; j < images.length; j++){
            for(int i = 0; i < images[j].length; i++){
                images[j][i] = temp.getSubimage(i* STAR_WIDTH_DEFAULT, j *STAR_HEIGHT_DEFAULT, STAR_WIDTH_DEFAULT, STAR_HEIGHT_DEFAULT);
            }
        }
        return images;
    }

    public void update() {
        updateAnimationTick();
    }

    public void draw(Graphics g, int xLvlOffset) {
        int imageX = (int) (hitbox.x - xImageOffset) - xLvlOffset + flipX;
        int imageY = (int) (hitbox.y - yImageOffset);
        int width = (int) (initialWidth * flipW);
        g.drawImage(sprites[animation][animationFrame], imageX, imageY, width, initialHeight,  null);
        debug_drawHitbox(g, xLvlOffset, hitbox);
    }

    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= PLAYER_ANIMATION_SPEED){
            animationTick = 0;
            animationFrame++;
            if(animationFrame >= getEnemySpriteAmount(animation)) {
                animationFrame = 0;
            }
        }
    }



    private int getEnemySpriteAmount(int state) {
        switch (state) {
            case IDLE:
                return 8;
            case RUN:
                return 6;
            case ATTACK:
                return 7;
            case HIT:
                return 4;
            case DEAD:
                return 5;
            default:
                return 1;
        }
    }
}
