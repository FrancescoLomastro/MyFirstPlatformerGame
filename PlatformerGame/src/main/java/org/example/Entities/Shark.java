package org.example.Entities;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Sprites.Enemy.Crabby.CRABBY_HEIGHT_DEFAULT;
import static org.example.Constants.Sprites.Enemy.Crabby.CRABBY_WIDTH_DEFAULT;
import static org.example.Constants.Sprites.Enemy.Shark.*;
import static org.example.Constants.Sprites.PLAYER_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Player.*;

public class Shark extends Enemy {
    private static BufferedImage[][] sprites = LoadAnimations();

    public Shark(float initialX, float initialY) {
        super(initialX, initialY, SHARK_WIDTH, SHARK_HEIGHT);
        initHitbox( 22, 19);
    }

    private static BufferedImage[][] LoadAnimations() {
        BufferedImage temp = LoadContent.GetSpriteAtlas(LoadContent.SHARK_ATLAS);
        BufferedImage[][] images = new BufferedImage[5][8];
        for(int j = 0; j < images.length; j++){
            for(int i = 0; i < images[j].length; i++){
                images[j][i] = temp.getSubimage(i* SHARK_WIDTH_DEFAULT, j *SHARK_HEIGHT_DEFAULT, SHARK_WIDTH_DEFAULT, SHARK_HEIGHT_DEFAULT);
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
            case IDLE, ATTACK:
                return 8;
            case RUN:
                return 6;
            case HIT:
                return 4;
            case DEAD:
                return 5;
            default:
                return 1;
        }
    }

}
