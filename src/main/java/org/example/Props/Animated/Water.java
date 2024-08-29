package org.example.Props.Animated;

import org.example.GameScenes.PlayScene;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.Water.*;
import static org.example.Constants.Window.TILES_SIZE;

/**
 * Water class, used to create water objects in the game.
 */
public class Water extends AnimatedProp {

    private static BufferedImage[] images = LoadImages();


    public Water(int x, int y, int objectType) {
        super(x, y, objectType);
        initHitbox(TILES_SIZE, TILES_SIZE);
    }


    public void update(){
        updateAnimationTick();
        checkPlayerCollision();
    }

    /**
     * This method is used to check if the player touches the water, and kill the player if it does.
     */
    private void checkPlayerCollision() {
        PlayScene playScene = PlayScene.getInstance();
        if(hitbox.intersects(playScene.getPlayerHitbox())) {
            playScene.killPlayer();
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        if(objectType == DEEP_WATER)
            g.drawImage(images[ANIMATED_WATER_SPRITE_AMOUNT], x - xLvlOffset, y, WATER_WIDTH, WATER_HEIGHT, null);
        else
            g.drawImage(images[animationFrame], x - xLvlOffset, y, WATER_WIDTH, WATER_HEIGHT, null);
    }


    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= WATER_ANIMATION_SPEED) {
            animationTick = 0;
            animationFrame++;
            if (animationFrame >= ANIMATED_WATER_SPRITE_AMOUNT)
                animationFrame = 0;
        }
    }

    private static BufferedImage[] LoadImages() {
        BufferedImage temp = LoadContent.GetResourceAsBufferedImage(LoadContent.WATER_SPRITE);
        BufferedImage[] imgs = new BufferedImage[ANIMATED_WATER_SPRITE_AMOUNT+1];
        for(int i = 0; i < ANIMATED_WATER_SPRITE_AMOUNT; i++) {
            imgs[i] = temp.getSubimage(i*WATER_WIDTH_DEFAULT, 0, WATER_WIDTH_DEFAULT, WATER_WIDTH_DEFAULT);
        }
        imgs[ANIMATED_WATER_SPRITE_AMOUNT] = temp.getSubimage(ANIMATED_WATER_SPRITE_AMOUNT * WATER_WIDTH_DEFAULT, 0, WATER_WIDTH_DEFAULT,WATER_WIDTH_DEFAULT);
        return imgs;
    }
}
