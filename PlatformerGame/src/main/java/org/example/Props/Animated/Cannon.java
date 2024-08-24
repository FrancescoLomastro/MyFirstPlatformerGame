package org.example.Props.Animated;

import org.example.GameScenes.PlayScene;
import org.example.Levels.Level;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.example.Constants.Prop.Cannon.*;
import static org.example.Constants.Window.*;
import static org.example.Levels.Level.areAllTilesClear;

/**
 * Cannon class for animated cannon prop
 */
public class Cannon extends AnimatedProp {
    private int tileY;
    private int attackDistance;
    protected boolean doAnimation;
    private static BufferedImage[] images = LoadImages();

    private ArrayList<CannonBall> cannonBalls;


    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        this.cannonBalls = new ArrayList<>();
        attackDistance = CANNON_ATTACK_DISTANCE;
        tileY = y / TILES_SIZE;
        initHitbox(CANNON_WIDTH_DEFAULT,CANNON_HEIGHT_DEFAULT);
        hitbox.x -= (int)(4 * SCALE);
        hitbox.y += (int)(6 * SCALE);
    }


    /**
     * This method is used to update the cannon prop.
     * It checks if the player is in range of the cannon and shoot
     * It also moves the shot projectiles
     */
    public void update(){
        if(doAnimation) {
            updateAnimationTick();
            if(animationFrame == 4 && animationTick == 0)
                shoot();
        }

        if(!doAnimation){
            int playerTileY = (int) (PlayScene.getInstance().getPlayerHitbox().y/TILES_SIZE);
            if(tileY == playerTileY){
                if(isPlayerInCannonRange()){
                    if(isShootLineClear(levelTexture)) {
                        doAnimation = true;
                    }
                }
            }
        }

        updateProjectiles();
    }

    /**
     * This method is used to update the position of the cannon balls
     */
    private void updateProjectiles() {
        PlayScene playScene = PlayScene.getInstance();
        for(CannonBall cb : cannonBalls){
            if(cb.isActive()){
                cb.update();
                if(cb.getHitbox().intersects(playScene.getPlayerHitbox())){
                    playScene.hitPlayer(CANNON_DAMAGE);
                    cb.crush();
                }else if(isProjectileCrushed(cb)){
                    cb.crush();
                }
            }
        }
    }

    /**
     * This method is used to check if the projectile is crushed by a solid block
     */
    private boolean isProjectileCrushed(CannonBall cb) {
        return Level.IsPositionSolid(cb.getHitbox().x + cb.getHitbox().width/2, cb.getHitbox().y +  cb.getHitbox().height, levelTexture);
    }

    /**
     * This method is used to shoot a cannon ball
     */
    private void shoot() {
        int dir = 1;
        if (objectType == CANNON_LEFT){
            dir = -1;
        }
        cannonBalls.add(new CannonBall((int) hitbox.x, (int) hitbox.y, dir));
    }

    /**
     * This method is used to draw the cannon prop.
     */
    public void draw(Graphics g, int xLvlOffset) {
        int x = (int) (hitbox.x - xLvlOffset);
        int width = CANNON_WIDTH;
        if (objectType == CANNON_RIGHT) {
            x += width;
            width *= -1;
        }
        g.drawImage(images[animationFrame], x, (int) (hitbox.y), width, CANNON_HEIGHT, null);
        //debug_drawHitbox(g,xLvlOffset);
        drawCannonBalls(g, xLvlOffset);
    }

    /**
     * This method is used to draw the cannon balls
     */
    private void drawCannonBalls(Graphics g, int xLvlOffset) {
        for(CannonBall cb : cannonBalls){
            if(cb.isActive()){
                cb.draw(g, xLvlOffset);
            }
        }
    }


    @Override
    public void reset() {
        super.reset();
        cannonBalls.clear();
    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= CANNON_ANIMATION_SPEED) {
            animationTick = 0;
            animationFrame++;
            if (animationFrame >= CANNON_SPRITE_AMOUNT) {
                animationFrame = 0;
                doAnimation = false;
            }
        }
    }

    /**
     * This method is used to check if the player is in range of the cannon
     * @return true if the player is in range of the cannon
     */
    private boolean isPlayerInCannonRange() {
        PlayScene playScene = PlayScene.getInstance();
        int absValue = (int) Math.abs(playScene.getPlayerHitbox().x - hitbox.x);
        return (absValue <= attackDistance) && isPlayerInFrontOfCannon();
    }

    /**
     * This method is used to check if the player is in front of the cannon
     * @return true if the player is in front of the cannon
     */
    private boolean isPlayerInFrontOfCannon() {
        PlayScene playScene = PlayScene.getInstance();
        if(objectType == CANNON_LEFT){
            if (hitbox.x > playScene.getPlayerHitbox().x) {
                return true;
            }
        }else if(objectType == CANNON_RIGHT){
            if (hitbox.x < playScene.getPlayerHitbox().x) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to check if the shoot line is clear and there are no solid blocks in between
     * @return true if the shoot line is clear
     */
    public boolean isShootLineClear(int[][] lvlData) {
        int cannonXTile = (int) (hitbox.x / TILES_SIZE);
        int playerXTile = (int) (PlayScene.getInstance().getPlayerHitbox().x / TILES_SIZE);

        if(cannonXTile > playerXTile) {
            return areAllTilesClear(playerXTile,cannonXTile,tileY,lvlData);
        }else{
            return areAllTilesClear(cannonXTile,playerXTile,tileY,lvlData);
        }
    }

    private static BufferedImage[] LoadImages() {
        BufferedImage[] imgs = new BufferedImage[7];
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(LoadContent.CANNON_SPRITE);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(CANNON_WIDTH_DEFAULT*i, 0, CANNON_WIDTH_DEFAULT, CANNON_HEIGHT_DEFAULT);
        }
        return imgs;
    }


}

