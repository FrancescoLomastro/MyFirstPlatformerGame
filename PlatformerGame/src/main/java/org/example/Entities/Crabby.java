package org.example.Entities;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.example.Constants.Entities.Enemy.Crabby.*;
import static org.example.Constants.Entities.Enemy.*;

/**
 * Class for the enemy Crabby
 */
public class Crabby extends Enemy{

    private static BufferedImage[][] images = LoadImages();

    public Crabby(float initialX, float initialY) {
        super(initialX, initialY, CRABBY_WIDTH, CRABBY_HEIGHT);
        initHitbox( CRABBY_HIT_BOX_WIDTH, CRABBY_HIT_BOX_HEIGHT);
        this.damage = CRABBY_DAMAGE;
        xImageOffset = CRABBY_DRAWOFFSET_X;
        yImageOffset = CRABBY_DRAWOFFSET_Y;
        initAttackBox();
    }

    /**
     * This method initializes the attack box of the crabby
     */
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(hitbox.x,hitbox.y, CRABBY_ATTACK_BOX_WIDTH, CRABBY_ATTACK_BOX_HEIGHT);
        attackBoxOffsetX = CRABBY_ATTACK_BOX_OFFSET_X;
    }

    /**
     * This method updates crabby
     */
    public void update() {
        super.update();
        updateAttack();
        updateAnimationTick(getEnemySpriteAmount(animation));
        updateAttackBox();
    }

    /**
     * This method updates the attack phase of the crabby
     */
    private void updateAttack() {
        if(!inAir){
            if(animationFrame == 0)
                attackChecked = false;
            if(animationFrame == 3 && animation == ATTACK && !attackChecked)
                checkEnemyHitPlayer(attackBox, damage);
        }
    }


    /**
     * This method draws the crabby
     */
    public void draw(Graphics g, int xLvlOffset) {
        int imageX = (int) (hitbox.x - xImageOffset) - xLvlOffset + flipX;
        int imageY = (int) (hitbox.y - yImageOffset);
        int width = (int) (initialWidth * flipW);
        g.drawImage(images[animation][animationFrame], imageX, imageY, width, initialHeight,  null);
        //debug_drawHitbox(g, xLvlOffset, hitbox);
        //debug_drawHitbox(g, xLvlOffset, attackBox);
    }



    /**
     * This method returns the amount of sprites for each animation of the crabby
     * @param state the state of the crabby
     * @return the amount of sprites
     */
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


    /**
     * This method loads the images of the crabby
     * @return the images of the crabby
     */
    private static BufferedImage[][] LoadImages() {
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(LoadContent.CRABBY_ATLAS);
        BufferedImage[][] images = new BufferedImage[5][9];
        for(int j = 0; j < images.length; j++){
            for(int i = 0; i < images[j].length; i++){
                images[j][i] = temp.getSubimage(i* CRABBY_WIDTH_DEFAULT, j *CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
            }
        }
        return images;
    }
}
