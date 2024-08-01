package org.example.Entities;

import org.example.Levels.Level;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static org.example.Constants.Motion.GRAVITY;
import static org.example.Constants.Sprites.PLAYER_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Player.*;
import static org.example.Constants.Window.SCALE;

public class Player extends Entity{
    private BufferedImage[][][] playerImages;
    private float xImageOffset = 21 * SCALE;
    private float yImageOffset = 4 * SCALE;

    private boolean left;
    private boolean right;
    private boolean jump;


    public Player(float y, float x, int width, int height) {
        super(y, x, width, height);
        initHitbox(20,27);
        loadAnimations();
        this.walkSpeed = 1.0f * SCALE;
        this.jumpSpeed = -2.25f * SCALE;
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
        int imageX = (int) (hitbox.x - xImageOffset) - xLvlOffset;
        int imageY = (int) (hitbox.y - yImageOffset);
        g.drawImage(playerImages[0][state][animationFrame],imageX , imageY, hitbox_width , hitbox_height, null);
        debug_drawHitbox(g, xLvlOffset);
    }






    private void updatePosition() {


        float xSpeed = 0;

        if (jump){
            if (!inAir){
                inAir = true;
                speedInAir = jumpSpeed;
            }
        }

        if (left && !right) {
            xSpeed -= walkSpeed;
        }
        if (right && !left) {
            xSpeed += walkSpeed;
        }

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
            inAir = false;
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


    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_SPACE:
                jump = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_SPACE:
                jump = false;
                break;
        }
    }
}
