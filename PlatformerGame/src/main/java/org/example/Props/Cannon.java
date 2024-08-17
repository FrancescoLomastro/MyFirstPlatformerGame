package org.example.Props;


import org.example.GameScenes.PlayScene;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Cannon.*;
import static org.example.Constants.Window.*;
import static org.example.Levels.Level.IsAllTilesClear;

public class Cannon extends Prop{
    private int tileY;
    private int attackDistance;
    private BufferedImage[] images;


    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        loadAnimation();
        attackDistance = TILES_SIZE;
        tileY = y / TILES_SIZE;
        initHitbox(40,26);
        hitbox.x -= (int)(4 * SCALE);
        hitbox.y += (int)(6 * SCALE);
    }

    private void loadAnimation() {
        images = new BufferedImage[7];
        BufferedImage temp = LoadContent.GetSpriteAtlas(LoadContent.CANNON_ATLAS);
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(40*i, 0, 40, 26);
        }

    }

    public void update(){
        if(doAnimation) {
            updateAnimationTick();
            if(animationFrame == 4 && animationTick == 0){}
                //shootCannon(c);
        }

        if(!doAnimation){
            int playerTileY = (int) (PlayScene.getInstance().getPlayerHitbox().y/TILES_SIZE);
            if(tileY == playerTileY){
                if(isPlayerInCannonRange()){
                    if(isPlayerInFrontOfCannon()){
                        if(canCannonSeePlayer(levelBlockIndexes)){
                            doAnimation = true;
                        }
                    }
                }
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        int x = (int) (hitbox.x - xLvlOffset);
        int width = CANNON_WIDTH;
        if (objectType == CANNON_RIGHT) {
            x += width;
            width *= -1;
        }
        g.drawImage(images[animationFrame], x, (int) (hitbox.y), width, CANNON_HEIGHT, null);
    }

    @Override
    public void reset() {

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

    private boolean isPlayerInCannonRange() {
        PlayScene playScene = PlayScene.getInstance();
        int absValue = (int) Math.abs(playScene.getPlayerHitbox().x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

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

    public boolean canCannonSeePlayer(int[][] lvlData) {

        int cannonXTile = (int) (hitbox.x / TILES_SIZE);
        int playerXTile = (int) (PlayScene.getInstance().getPlayerHitbox().x / TILES_SIZE);

        if(cannonXTile > playerXTile) {
            return IsAllTilesClear(playerXTile,cannonXTile,tileY,lvlData);
        }else{
            return IsAllTilesClear(cannonXTile,playerXTile,tileY,lvlData);
        }
    }


}

