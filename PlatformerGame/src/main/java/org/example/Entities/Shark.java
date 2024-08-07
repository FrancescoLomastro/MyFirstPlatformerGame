package org.example.Entities;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


import static org.example.Constants.Sprites.Enemy.*;
import static org.example.Constants.Sprites.Enemy.Shark.*;
import static org.example.Constants.Sprites.ENTITY_ANIMATION_SPEED;
import static org.example.Constants.Window.SCALE;

public class Shark extends Enemy {
    private static BufferedImage[][] sprites = LoadAnimations();

    public Shark(float initialX, float initialY) {
        super(initialX, initialY, SHARK_WIDTH, SHARK_HEIGHT);
        initHitbox( 18, 22);
        xImageOffset = SHARK_DRAWOFFSET_X;
        yImageOffset = SHARK_DRAWOFFSET_Y;
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(hitbox.x,hitbox.y,SHARK_ATTACKBOX_WIDTH, SHARK_ATTACKBOX_HEIGHT);
        attackBoxOffsetX = SHARK_ATTACKBOX_OFFSET_X;
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
        super.update();
        updateAttack();
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttack() {
        if(!inAir){
            if(animationFrame == 0)
                attackChecked = false;
            if(animationFrame == 3 && !attackChecked)
                checkEnemyHit(attackBox);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        int imageX = (int) (hitbox.x - xImageOffset) - xLvlOffset + flipX;
        int imageY = (int) (hitbox.y - yImageOffset);
        int width = (int) (initialWidth * flipW);
        g.drawImage(sprites[animation][animationFrame], imageX, imageY, width, initialHeight,  null);
        debug_drawHitbox(g, xLvlOffset, hitbox);
        debug_drawHitbox(g, xLvlOffset, attackBox);
    }



    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= ENTITY_ANIMATION_SPEED){
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
