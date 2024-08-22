package org.example.Entities;

import org.example.Levels.Level;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.example.Constants.Motion.Dirctions.LEFT;
import static org.example.Constants.Motion.Dirctions.RIGHT;
import static org.example.Constants.Sprites.Enemy.*;
import static org.example.Constants.Sprites.Enemy.Star.*;
import static org.example.Constants.Sprites.ENTITY_ANIMATION_SPEED;
import static org.example.Constants.Window.SCALE;
import static org.example.Constants.Window.TILES_SIZE;
import static org.example.Levels.Level.IsOnFloor;

public class Star extends Enemy {
    private static BufferedImage[][] sprites = LoadAnimations();
    private int attackTick;
    private int attackMaxTick;

    public Star(float initialX, float initialY ) {
        super(initialX, initialY, STAR_WIDTH, STAR_HEIGHT);
        initHitbox( 17, 21);
        this.attackDistance = TILES_SIZE*2;
        this.damage = -10;
        this.attackTick = 0;
        this.attackMaxTick = 300;
        xImageOffset = STAR_DRAWOFFSET_X;
        yImageOffset = STAR_DRAWOFFSET_Y;
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(hitbox.x,hitbox.y,STAR_ATTACKBOX_WIDTH, STAR_ATTACKBOX_HEIGHT);
        attackBoxOffsetX = STAR_ATTACKBOX_OFFSET_X;
    }


    private static BufferedImage[][] LoadAnimations() {
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(LoadContent.STAR_ATLAS);
        BufferedImage[][] images = new BufferedImage[5][8];
        for(int j = 0; j < images.length; j++){
            for(int i = 0; i < images[j].length; i++){
                images[j][i] = temp.getSubimage(i* STAR_WIDTH_DEFAULT, j *STAR_HEIGHT_DEFAULT, STAR_WIDTH_DEFAULT, STAR_HEIGHT_DEFAULT);
            }
        }
        return images;
    }

    public void update() {
        super.update();
        updateStarBehaviour();
        updateAttack();
        updateStarAnimationTick(getEnemySpriteAmount(animation));
        updateAttackBox();
    }

    private void updateStarBehaviour() {
        if(!inAir){
            switch (animation){
                case ATTACK:
                    attackMove();
                    break;
                default:
                    walkSpeed = 0.5f * SCALE;
                    break;
            }
        }
    }

    private void attackMove() {
        if(animationFrame > 2) {
            walkSpeed = 0.7f * SCALE;
            float xSpeed = 0;
            if (walkingDir == LEFT)
                xSpeed = -walkSpeed;
            else
                xSpeed = +walkSpeed;
            if (Level.CanMoveInPosition(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelTextures)) {
                if (IsOnFloor(hitbox, xSpeed, levelTextures)) {
                    hitbox.x += xSpeed;
                } else {
                    walkingDir = (walkingDir == LEFT) ? RIGHT : LEFT;
                    newAnimation(RUN);
                    walkSpeed = 0.5f * SCALE;
                    attackTick = 0;
                }
            } else {
                walkingDir = (walkingDir == LEFT) ? RIGHT : LEFT;
                newAnimation(RUN);
                walkSpeed = 0.5f * SCALE;
                attackTick = 0;
            }
        }
    }

    private void updateStarAnimationTick(int spriteAmount) {
        animationTick++;
        if(animation == ATTACK)
            attackTick++;
        if(animationTick >= ENTITY_ANIMATION_SPEED){
            animationTick = 0;
            animationFrame++;
            if(animationFrame >= spriteAmount ) {
                animationFrame = 0;
                switch (animation){
                    case ATTACK -> {
                        if(attackTick >= attackMaxTick){
                            animation = IDLE;
                            attackTick = 0;
                        }else {
                            animationFrame = 3;
                        }
                    }
                    case HIT -> animation = IDLE;
                    case DEAD -> active = false;
                }
            }
        }
    }


    private void handleAttack() {
        if(attackTick < attackMaxTick){
            attackTick++;
        }
    }

    private void updateAttack() {
        if(!inAir){
            if(animationFrame == 0)
                attackChecked = false;
            if((animationFrame >= 3) && (animation == ATTACK) && !attackChecked)
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
