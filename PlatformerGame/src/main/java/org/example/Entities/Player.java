package org.example.Entities;

import org.example.Levels.Level;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Motion.GRAVITY;
import static org.example.Constants.Sprites.PLAYER_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Player.*;

public class Player extends Entity{
    private BufferedImage[][][] playerImages;


    public Player(float y, float x, int width, int height) {
        super(y, x, width, height);
        initHitbox(20,27);
        loadAnimations();
    }

    private void loadAnimations() {
        BufferedImage img = LoadContent.GetSpriteAtlas(LoadContent.PLAYER_NO_SWORD_ATLAS);

        playerImages = new BufferedImage[2][7][8];
        for (int j = 0; j < playerImages[0].length; j++)
            for (int i = 0; i < playerImages[0][j].length; i++)
                playerImages[0][j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
    }

    public void update(){
        updatePosition();
        updateAnimationTick();
    }

    public void draw(Graphics g, int xLvlOffset){
        g.drawImage(playerImages[0][state][animationFrame],(int) (hitbox.x) - xLvlOffset, (int) (hitbox.y), hitbox_width , hitbox_height, null);
    }






    private void updatePosition() {

        float xSpeed = 0;

        if(!inAir){
            if(!onTheFloor(levelBlockIndexes)){
                inAir = true;
            }
        }
        if(inAir){
            handleGravity();
        }
        updateXPosition(xSpeed);
    }

    private void handleGravity() {
        if (Level.CanMoveInPosition(hitbox.x, hitbox.y + speedInAir, hitbox.width, hitbox.height, levelBlockIndexes)) {
            hitbox.y += speedInAir;
            speedInAir += GRAVITY;
        } else {
            /*hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
            if (airSpeed > 0)
                resetInAir();
            else
                airSpeed = fallSpeedAfterCollision;*/
        }
    }

    private void updateXPosition(float xSpeed) {
        if (Level.CanMoveInPosition(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelBlockIndexes)) {
            hitbox.x += xSpeed;
        } else {
            /*hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
            if(powerAttackActive){
                powerAttackActive = false;
                powerAttackTick = 0;
            }*/
        }
    }

    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= PLAYER_ANIMATION_SPEED){
            animationTick = 0;
            animationFrame++;
            if(animationFrame >= getPlayerSpriteAmount(state))
                animationFrame = 0;
        }
    }

    private int getPlayerSpriteAmount(int state) {
        switch (state) {
            case IDLE:
                return 5;
            case RUN:
                return 6;
            default:
                return 1;
        }
    }



}
