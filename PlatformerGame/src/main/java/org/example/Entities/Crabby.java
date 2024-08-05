package org.example.Entities;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Sprites.Enemy.Crabby.*;
import static org.example.Constants.Sprites.PLAYER_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Player.*;
import static org.example.Constants.Sprites.Player.FALL;

public class Crabby extends Enemy{
    private BufferedImage[][] sprites;


    public Crabby(float initialX, float initialY) {
        super(initialX, initialY, CRABBY_WIDTH, CRABBY_HEIGHT);
        loadAnimations();
        initHitbox( 22, 19);
    }

    private void loadAnimations() {
        BufferedImage temp = LoadContent.GetSpriteAtlas(LoadContent.CRABBY_ATLAS);
        sprites = new BufferedImage[5][9];
        for(int j = 0; j < sprites.length; j++){
            for(int i = 0; i < sprites[j].length; i++){
                sprites[j][i] = temp.getSubimage(i* CRABBY_WIDTH_DEFAULT, j *CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
            }
        }
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
            case IDLE:
                return 9;
            case RUN:
                return 6;
            case ATTACK:
                return 3;
            case HIT:
                return 4;
            case DEAD:
                return 5;
            default:
                return 1;
        }
    }

}
