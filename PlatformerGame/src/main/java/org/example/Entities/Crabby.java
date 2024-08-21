package org.example.Entities;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.example.Constants.Sprites.Enemy.Crabby.*;
import static org.example.Constants.Sprites.Enemy.*;

public class Crabby extends Enemy{

    private static BufferedImage[][] sprites = LoadAnimations();

    private static BufferedImage[][] LoadAnimations() {
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(LoadContent.CRABBY_ATLAS);
        BufferedImage[][] images = new BufferedImage[5][9];
        for(int j = 0; j < images.length; j++){
            for(int i = 0; i < images[j].length; i++){
                images[j][i] = temp.getSubimage(i* CRABBY_WIDTH_DEFAULT, j *CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
            }
        }
        return images;
    }


    public Crabby(float initialX, float initialY) {
        super(initialX, initialY, CRABBY_WIDTH, CRABBY_HEIGHT);
        initHitbox( 22, 19);
        this.damage = -20;
        xImageOffset = CRABBY_DRAWOFFSET_X;
        yImageOffset = CRABBY_DRAWOFFSET_Y;
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(hitbox.x,hitbox.y,CRABBY_ATTACKBOX_WIDTH, CRABBY_ATTACKBOX_HEIGHT);
        attackBoxOffsetX = CRABBY_ATTACKBOX_OFFSET_X;
    }


    public void update() {
        super.update();
        updateAttack();
        updateAnimationTick(getEnemySpriteAmount(animation));
        updateAttackBox();
    }

    private void updateAttack() {
        if(!inAir){
            if(animationFrame == 0)
                attackChecked = false;
            if(animationFrame == 3 && animation == ATTACK && !attackChecked)
                checkEnemyHitPlayer(attackBox, damage);
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





    private int getEnemySpriteAmount(int state) {
        switch (state) {
            case IDLE:
                return 9;
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
