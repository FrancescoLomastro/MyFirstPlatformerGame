package org.example.Entities;

import org.example.Levels.Level;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.example.Constants.Motion.COLLISION_FALL_SPEED;
import static org.example.Constants.Motion.GRAVITY;
import static org.example.Constants.Sprites.PLAYER_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Player.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Utility.HelpMethods.XPositionNextToWall;
import static org.example.Utility.HelpMethods.YPositionUnderRoofOrAboveFloor;

public class Player extends Entity{
    private BufferedImage[][] playerNoSwordImages;
    private BufferedImage[][] playerWithSwordImages;
    private float xImageOffset = 21 * SCALE;
    private float yImageOffset = 4 * SCALE;

    private boolean left;
    private boolean right;
    private boolean jump;
    private boolean moving;
    private boolean hasSword;
    private boolean landed;


    public Player(float y, float x, int width, int height) {
        super(y, x, width, height);
        initHitbox(20,27);
        loadAnimations();
        this.walkSpeed = 1.0f * SCALE;
        this.jumpSpeed = -2.25f * SCALE;
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(hitbox.x,hitbox.y, (int)(20*SCALE), (int)(20*SCALE));
        //resetAttackBox();
    }



    private void loadAnimations() {
        BufferedImage imgNoSword = LoadContent.GetSpriteAtlas(LoadContent.PLAYER_NO_SWORD_ATLAS);
        playerNoSwordImages = new BufferedImage[7][8];
        for (int j = 0; j < playerNoSwordImages.length; j++)
            for (int i = 0; i < playerNoSwordImages[j].length; i++)
                playerNoSwordImages[j][i] = imgNoSword.getSubimage(i * 64, j * 40, 64, 40);


        BufferedImage imgWithSword = LoadContent.GetSpriteAtlas(LoadContent.PLAYER_SWORD_ATLAS);
        playerWithSwordImages = new BufferedImage[12][6];
        for (int j = 0; j < playerWithSwordImages.length; j++)
            for (int i = 0; i < playerWithSwordImages[j].length; i++)
                playerWithSwordImages[j][i] = imgWithSword.getSubimage(i * 64, j * 40, 64, 40);
    }



    public void update(){
        updatePosition();

        checkSwordPicked();
        updateAttackBox();
        updateAnimationTick();
        setAnimation();
    }



    private void updateAttackBox() {
        if(flipW == 1)
            attackBox.x = hitbox.x + hitbox.width + (SCALE*5);
        else
            attackBox.x = hitbox.x - attackBox.width - (SCALE*5);;

        attackBox.y = hitbox.y + (int)(SCALE*7);
    }



    private void checkSwordPicked() {
        if(!hasSword)
            hasSword = playScene.isSwordPicked();
    }



    public void draw(Graphics g, int xLvlOffset){
        int imageX = (int) (hitbox.x - xImageOffset) - xLvlOffset + flipX;
        int imageY = (int) (hitbox.y - yImageOffset);
        int width = (int) (initialWidth * flipW);
        BufferedImage[][] images = hasSword ? playerWithSwordImages : playerNoSwordImages;
        g.drawImage(images[animation][animationFrame], imageX , imageY, width , initialHeight, null);
        debug_drawHitbox(g, xLvlOffset, hitbox);
        debug_drawHitbox(g, xLvlOffset, attackBox);
    }



    private void setAnimation() {
        int oldAnimation = animation;


        if (moving) {
            animation = RUN;
        } else if (!landed){
            animation = IDLE;
        }
        if (inAir) {
            if (speedInAir < 0)
                animation = JUMP;
            else
                animation = FALL;
        }


        if (oldAnimation != animation) {
            if(oldAnimation == FALL && animation == IDLE){
                landed = true;
                animation = LAND;
            }
            resetAnimationTick();
        }
    }



    private void resetAnimationTick() {
        animationTick = 0;
        animationFrame = 0;
    }



    private void updatePosition() {
        moving = false;
        
        if(!inAir && !left && !right && !jump)
            return;
        
        float xSpeed = 0;
        if (jump){
            if (!inAir){
                inAir = true;
                speedInAir = jumpSpeed;
            }
        }

        if (left && !right) {
            xSpeed -= walkSpeed;
            flipX = initialWidth;
            flipW = -1;
            moving = true;
        }
        if (right && !left) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
            moving = true;
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
            hitbox.y = YPositionUnderRoofOrAboveFloor(hitbox, speedInAir);
            if (speedInAir > 0) {
                inAir = false;
                speedInAir = 0;
            }
            else {
                speedInAir = COLLISION_FALL_SPEED;
            }
        }
    }



    private void updateXPosition(float xSpeed) {
        if (Level.CanMoveInPosition(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelBlockIndexes)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = XPositionNextToWall(hitbox, xSpeed);
        }
    }



    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= PLAYER_ANIMATION_SPEED){
            animationTick = 0;
            animationFrame++;
            if(animationFrame >= getPlayerSpriteAmount(animation)) {
                if(landed){
                    landed = false;
                }
                animationFrame = 0;
            }
        }
    }



    private int getPlayerSpriteAmount(int state) {
        switch (state) {
            case IDLE:
                return 5;
            case RUN:
                return 6;
            case JUMP:
                return 3;
            case LAND:
                return 2;
            case FALL:
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
