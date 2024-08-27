package org.example.Entities;

import org.example.Audio.AudioManager;
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

/**
 * This class handles the player movement, updates and behaviours
 */
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
        initHitbox(PLAYER_HIT_BOX_WIDTH,PLAYER_HIT_BOX_HEIGHT);
        loadImages();
        this.animation = IDLE;
        this.jumpSpeed = PLAYER_JUMP_SPEED * SCALE;
        this.xImageOffset = 21 * SCALE;
        this.yImageOffset = 4 * SCALE;
        this.damage = PLAYER_DAMAGE;
        initAttackBox();
    }

    /**
     * Initializes the attack box
     */
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(hitbox.x,hitbox.y, (int)(PLAYER_ATTACK_BOX_WIDTH*SCALE), (int)(PLAYER_ATTACK_BOX_HEIGHT*SCALE));
        attackBoxOffsetX = (int) (22 * SCALE);
        attackBoxOffsetY = (int) (7 * SCALE);
    }


    /**
     * Loads the images for the player
     */
    private void loadImages() {
        BufferedImage imgNoSword = LoadContent.GetResourceAsBufferedImage(LoadContent.PLAYER_ATLAS_NO_SWORD);
        playerNoSwordImages = new BufferedImage[8][8];
        for (int j = 0; j < playerNoSwordImages.length; j++)
            for (int i = 0; i < playerNoSwordImages[j].length; i++)
                playerNoSwordImages[j][i] = imgNoSword.getSubimage(i * PLAYER_WIDTH_DEFAULT, j * PLAYER_HEIGHT_DEFAULT, PLAYER_WIDTH_DEFAULT, PLAYER_HEIGHT_DEFAULT);


        BufferedImage imgWithSword = LoadContent.GetResourceAsBufferedImage(LoadContent.PLAYER_ATLAS_SWORD);
        playerWithSwordImages = new BufferedImage[13][6];
        for (int j = 0; j < playerWithSwordImages.length; j++)
            for (int i = 0; i < playerWithSwordImages[j].length; i++)
                playerWithSwordImages[j][i] = imgWithSword.getSubimage(i * PLAYER_WIDTH_DEFAULT, j * PLAYER_HEIGHT_DEFAULT, PLAYER_WIDTH_DEFAULT, PLAYER_HEIGHT_DEFAULT);

        healthHUD = LoadContent.GetResourceAsBufferedImage(LoadContent.HEALTH_HUD);
    }


    /**
     * Updates the player
     */
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
            PlayScene.getInstance().setGameOver();
        }

    }

    /**
     * Checks if the player attacked an enemy
     */
    private void checkEnemyAttacked() {
        if(attackChecked || animationFrame != 1){
            return;
        }
        attackChecked = true;
        PlayScene.getInstance().checkEnemyAttacked(attackBox, damage);
        AudioManager.getInstance().playPlayerAttack();
    }


    /**
     * Checks if the player picked up the sword
     */
    private void checkSwordPicked() {
        if(!hasSword) {
            PlayScene playScene = PlayScene.getInstance();
            hasSword = playScene.isSwordPicked(hitbox);
            if(hasSword)
                AudioManager.getInstance().playSwordPickSound();
        }
    }


    /**
     * Draws the player
     * @param g
     * @param xLvlOffset
     */
    public void draw(Graphics g, int xLvlOffset){
        int imageX = (int) (hitbox.x - xImageOffset) - xLvlOffset + flipX;
        int imageY = (int) (hitbox.y - yImageOffset);
        int width = (int) (initialWidth * flipW);
        BufferedImage[][] images = hasSword ? playerWithSwordImages : playerNoSwordImages;
        if(hit)
            drawHitPlayer(g, imageX, imageY, width, initialHeight, images[animation][animationFrame]);
        else
            g.drawImage(images[animation][animationFrame], imageX , imageY, width , initialHeight, null);
        //debug_drawHitbox(g, xLvlOffset, hitbox);
        //debug_drawHitbox(g, xLvlOffset, attackBox);
        drawHUD(g);
    }

    /**
     * Draw the player image with a white filter, so it looks like hit.
     */
    private void drawHitPlayer(Graphics g, int imageX, int imageY, int width, int height, BufferedImage image) {
        RescaleOp op = new RescaleOp(new float[]{1.0f, 1.0f, 1.0f, 1.0f}, new float[]{255, 255, 255, 0}, null);
        BufferedImage imageCopy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        op.filter(image, imageCopy);
        g.drawImage(imageCopy, imageX, imageY, width, height, null);
        entityHitFrameCounter++;
        if(entityHitFrameCounter >= PLAYER_HIT_COUNTER_MAX) {
            entityHitFrameCounter = 0;
            hit = false;
        }
    }

    /**
     * Draw the HUD section with the current value of health
     */
    private void drawHUD(Graphics g) {
        g.drawImage(healthHUD, HEALTH_BAR_X, HEALTH_BAR_Y, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, null);
        int currentHealthWidth = (int) ((currentHealth / (float) maxHealth) * HEALTH_MAX_WIDTH);
        g.setColor(Color.RED);
        g.fillRect(HEALTH_X_START, HEALTH_Y_START, currentHealthWidth, HEALTH_HEIGHT);
    }


    /**
     * This method select the correct animation to display
     */
    private void setAnimation() {
        int oldAnimation = animation;
        int newAnimation = animation;
        if (moving) {
            newAnimation = RUN;
        } else if(oldAnimation != LAND){
            newAnimation = IDLE;
        }
        if (inAir) {
            if (speedInAir < 0)
                newAnimation = JUMP;
            else
                newAnimation = FALL;
        }
        if(attack){
            newAnimation = ATTACK;
        }


        if (oldAnimation != newAnimation) {
            if(oldAnimation == FALL && newAnimation == IDLE){
                newAnimation = LAND;
            }
            changeAnimation(newAnimation);
        }
    }





    /**
     * This method handle the position of the player and triggers the moving behaviours
     */
    private void updatePosition() {
        moving = false;
        
        if(!inAir && !left && !right && !jump)
            return;
        
        float xSpeed = 0;
        if (jump){
            if (!inAir){
                AudioManager.getInstance().playPlayerJump();
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
            if(!isOnFloor(levelTextures)){
                inAir = true;
            }
        }
        if(inAir){
            handleGravity();
        }


        updateXPosition(xSpeed);
    }


    /**
     * This method change the horizontal position during movement
     * @param horizontalSpeed the horizontal speed
     */
    private void updateXPosition(float horizontalSpeed) {
        if (Level.CanMoveInPosition(hitbox.x + horizontalSpeed, hitbox.y, hitbox.width, hitbox.height, levelTextures)) {
            hitbox.x += horizontalSpeed;
        } else {
            hitbox.x = XPositionNextToWall(hitbox, horizontalSpeed);
        }
    }


    /**
     * Update the animation tick of the player
     */
    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= ENTITY_ANIMATION_SPEED){
            animationTick = 0;
            animationFrame++;
            if(animationFrame >= getPlayerSpriteAmount(animation)) {
                if(animation == LAND)
                    animation = IDLE;
                attack = false;
                attackChecked = false;
                animationFrame = 0;
            }
        }
    }

    /**
     * Change the player current health by subtracting the damage
     * @param damage the (positive) damage to be subtracted
     */
    public void alterHealth(int damage) {
        System.out.println("Entrato");
        int newHealth = currentHealth - damage;
        if(currentHealth > 0) {
            if(newHealth <= 0) {
                currentHealth = 0;
                changeAnimation(DEAD);
                AudioManager.getInstance().playPlayerDead();
            }
            else if (newHealth > maxHealth)
                currentHealth = maxHealth;
            else {
                currentHealth = newHealth;
                hit = true;
                if(damage > 0)
                    AudioManager.getInstance().playPlayerPain();
            }
        }
    }

    /**
     * This method selects the correct amount of sprites based on the current animation
     * @param currentAnimation the current animation
     * @return the correct amount of sprites based on the current animation
     */
    private int getPlayerSpriteAmount(int currentAnimation) {
        switch (currentAnimation) {
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

    /**
     * Resets the player state
     */
    public void reset() {
        super.reset();
        changeAnimation(IDLE);
        inAir = false;
        attack = false;
        moving = false;
        hasSword = false;
        resetBooleanDirections();
        if (!isOnFloor(levelTextures))
            inAir = true;
    }


    /**
     * This method kills the player
     */
    public void kill() {
        alterHealth(maxHealth);
    }

    /**
     * This method resets the direction booleans
     */
    public void resetBooleanDirections() {
        left = false;
        right = false;
    }

    /**
     * This method reacts to a key pressed event
     * @param e the key event
     */
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


    /**
     * This method reacts to a key released event
     * @param e the key event
     */
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

    /**
     * This method reacts to a mouse clicked event
     * @param e the mouse event
     */
    public void mouseClicked(MouseEvent e) {
        if(hasSword && !attack)
            attack = true;
    }
}

