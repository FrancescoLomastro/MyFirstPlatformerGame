package org.example.Entities;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


import static org.example.Constants.Entities.Enemy.*;
import static org.example.Constants.Entities.Enemy.Shark.*;
import static org.example.Constants.Window.TILES_SIZE;

public class Shark extends Enemy {
    private static BufferedImage[][] images = LoadImages();

    public Shark(float initialX, float initialY) {
        super(initialX, initialY, SHARK_WIDTH, SHARK_HEIGHT);
        initHitbox( 18, 22);
        this.attackDistance = TILES_SIZE/2;
        this.damage = SHARK_DAMAGE;
        xImageOffset = SHARK_DRAWOFFSET_X;
        yImageOffset = SHARK_DRAWOFFSET_Y;
        initAttackBox();
    }

    /**
     * This method initializes the attack box of the crabby
     */
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(hitbox.x,hitbox.y,SHARK_ATTACKBOX_WIDTH, SHARK_ATTACKBOX_HEIGHT);
        attackBoxOffsetX = SHARK_ATTACKBOX_OFFSET_X;
    }

    /**
     * This method updates shark
     */
    public void update() {
        super.update();
        updateAttack();
        updateAnimationTick(getEnemySpriteAmount(animation));
        updateAttackBox();
    }

    /**
     * This method updates the attack phase of the shark
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
     * This method draws the shark
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
     * This method returns the amount of sprites in the current animation
     * @param state the current animation state
     * @return the amount of sprites in the current animation
     */
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

    /**
     * This method loads the images of the shark
     * @return the images of the shark
     */
    private static BufferedImage[][] LoadImages() {
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(LoadContent.SHARK_ATLAS);
        BufferedImage[][] images = new BufferedImage[5][8];
        for(int j = 0; j < images.length; j++){
            for(int i = 0; i < images[j].length; i++){
                images[j][i] = temp.getSubimage(i* SHARK_WIDTH_DEFAULT, j *SHARK_HEIGHT_DEFAULT, SHARK_WIDTH_DEFAULT, SHARK_HEIGHT_DEFAULT);
            }
        }
        return images;
    }
}
