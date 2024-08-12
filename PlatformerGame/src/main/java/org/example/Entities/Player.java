package org.example.Entities;

import org.example.GameScenes.PlayScene;
import org.example.Levels.Level;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import static org.example.Constants.HUD.*;
import static org.example.Constants.Sprites.ENTITY_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Player.*;
import static org.example.Constants.Window.SCALE;
import static org.example.Utility.HelpMethods.XPositionNextToWall;

public class Player extends Entity{
    private BufferedImage[][] playerNoSwordImages;
    private BufferedImage[][] playerWithSwordImages;


    private boolean left;
    private boolean right;
    private boolean jump;
    private boolean moving;
    private boolean hasSword;
    private boolean landed;
    private boolean attack;

    private BufferedImage healthHUD;


    public Player(float x, float y) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
        initHitbox(20,27);
        loadAnimations();
        this.jumpSpeed = -2.25f * SCALE;
        this.xImageOffset = 21 * SCALE;
        this.yImageOffset = 4 * SCALE;
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(hitbox.x,hitbox.y, (int)(20*SCALE), (int)(20*SCALE));
        
        //resetAttackBox();
    }



    private void loadAnimations() {
        BufferedImage imgNoSword = LoadContent.GetSpriteAtlas(LoadContent.PLAYER_NO_SWORD_ATLAS);
        playerNoSwordImages = new BufferedImage[8][8];
        for (int j = 0; j < playerNoSwordImages.length; j++)
            for (int i = 0; i < playerNoSwordImages[j].length; i++)
                playerNoSwordImages[j][i] = imgNoSword.getSubimage(i * 64, j * 40, 64, 40);


        BufferedImage imgWithSword = LoadContent.GetSpriteAtlas(LoadContent.PLAYER_SWORD_ATLAS);
        playerWithSwordImages = new BufferedImage[13][6];
        for (int j = 0; j < playerWithSwordImages.length; j++)
            for (int i = 0; i < playerWithSwordImages[j].length; i++)
                playerWithSwordImages[j][i] = imgWithSword.getSubimage(i * 64, j * 40, 64, 40);

        healthHUD = LoadContent.GetSpriteAtlas(LoadContent.HEALTH_HUD);
    }



    public void update(){
        updateAnimationTick();
        if(animation != DEAD) {
            updatePosition();
            checkSwordPicked();
            updateAttackBox();
            setAnimation();
            if (attack)
                checkEnemyAttacked();
        }else if(animationFrame == getPlayerSpriteAmount(DEAD)-1){
            PlayScene.getInstance().setGameOver(true);
        }

    }

    private void checkEnemyAttacked() {
        if(attackChecked || animationFrame != 1){
            return;
        }
        attackChecked = true;
        PlayScene.getInstance().checkEnemyAttacked(attackBox);
    }


    private void updateAttackBox() {
        if(flipW == 1)
            attackBox.x = hitbox.x + hitbox.width + (SCALE*5);
        else
            attackBox.x = hitbox.x - attackBox.width - (SCALE*5);;

        attackBox.y = hitbox.y + (int)(SCALE*7);
    }



    private void checkSwordPicked() {
        if(!hasSword) {
            PlayScene playScene = PlayScene.getInstance();
            hasSword = playScene.isSwordPicked();
        }
    }



    public void draw(Graphics g, int xLvlOffset){
        int imageX = (int) (hitbox.x - xImageOffset) - xLvlOffset + flipX;
        int imageY = (int) (hitbox.y - yImageOffset);
        int width = (int) (initialWidth * flipW);
        BufferedImage[][] images = hasSword ? playerWithSwordImages : playerNoSwordImages;
        if(hitten)
            drawHittenPlayer(g, imageX, imageY, width, initialHeight, images[animation][animationFrame]);
        else
            g.drawImage(images[animation][animationFrame], imageX , imageY, width , initialHeight, null);
        debug_drawHitbox(g, xLvlOffset, hitbox);
        debug_drawHitbox(g, xLvlOffset, attackBox);
        drawHUD(g);
    }

    private void drawHittenPlayer(Graphics g, int imageX, int imageY, int width, int height, BufferedImage image) {
        // Disegna l'immagine image ma con un filtro che la rende interamente bianca

        RescaleOp op = new RescaleOp(new float[]{1.0f, 1.0f, 1.0f, 1.0f}, new float[]{255, 255, 255, 0}, null);
        BufferedImage imageCopy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        op.filter(image, imageCopy);
        g.drawImage(imageCopy, imageX, imageY, width, height, null);
        hittenFrameCounter++;
        if(hittenFrameCounter >= PLAYER_HITTEN_COUNTER_MAX) {
            hittenFrameCounter = 0;
            hitten = false;
        }
    }

    private void drawHUD(Graphics g) {
        g.drawImage(healthHUD, HEALTH_BAR_X, HEALTH_BAR_Y, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, null);

        int currentHealthWidth = (int) ((currentHealth / (float) maxHealth) * HEALTH_MAX_WIDTH);
        g.setColor(Color.RED);
        g.fillRect(HEALTH_X_START, HEALTH_Y_START, currentHealthWidth, HEALTH_HEIGHT);
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
        if(attack){
            animation = ATTACK;
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




    private void updateXPosition(float xSpeed) {
        if (Level.CanMoveInPosition(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelBlockIndexes)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = XPositionNextToWall(hitbox, xSpeed);
        }
    }



    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= ENTITY_ANIMATION_SPEED){
            animationTick = 0;
            animationFrame++;
            if(animationFrame >= getPlayerSpriteAmount(animation)) {
                if(landed){
                    landed = false;
                }
                attack = false;
                attackChecked = false;
                animationFrame = 0;
            }
        }
    }


    public void alterHealth(int damage) {
        int newHealth = currentHealth + damage;
        if(newHealth <= 0) {
            currentHealth = 0;
            newAnimation(DEAD);
        }
        else if (newHealth > maxHealth)
            currentHealth = maxHealth;
        else {
            currentHealth = newHealth;
            hitten = true;
        }
    }


    private int getPlayerSpriteAmount(int state) {
        switch (state) {
            case IDLE:
                return 5;
            case RUN:
                return 6;
            case DEAD:
                return 4;
            case JUMP:
            case ATTACK:
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



    public void mouseClicked(MouseEvent e) {
        if(hasSword && !attack)
            attack = true;
    }




}
