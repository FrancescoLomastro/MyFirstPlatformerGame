package org.example.Levels;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import static org.example.Constants.Sprites.Level.WATER_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Level.ANIMATED_WATER_SPRITE_AMOUNT;
import static org.example.Constants.Window.*;

public class LevelManager {
    private BufferedImage[] textures;
    private BufferedImage[] animatedWater;
    private Level currentLevel;
    private int currentLevelIndex;

    //Animation Variables
    protected int animationTick;
    protected int animationFrame;

    public LevelManager() {
        this.currentLevelIndex = 0;
        this.animationFrame = 0;
        this.animationTick = 0;
        loadTextures();
        loadNextLevel(currentLevelIndex);
    }

    private void loadNextLevel(int currentLevelIndex) {
        currentLevel = new Level(currentLevelIndex);
    }

    public void draw(Graphics g, int xLvlOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++)
            for (int i = 0; i < currentLevel.getLevelTileWidth(); i++) {
                int index = currentLevel.getBlockIndex(i, j);
                if(index == 50) //Animated Water
                    g.drawImage(animatedWater[animationFrame], TILES_SIZE * i - xLvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
                else
                    g.drawImage(textures[index], TILES_SIZE * i - xLvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
    }

    public void update() {
        updateAnimationTick();
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



    private void loadTextures() {
        BufferedImage img = LoadContent.GetSpriteAtlas(LoadContent.LEVEL_GROUND_TEXTURE);
        textures = new BufferedImage[60];

            for (int row = 0; row < 5; row++) {
                for (int columns = 0; columns < 12; columns++) {
                    int index = row * 12 + columns;
                    try {
                        textures[index] = img.getSubimage(columns * 32, row * 32, 32, 32);
                    } catch (RasterFormatException e) {
                        System.out.println("columns = " + columns + " row = " + row+ " index = " + index);
                    }
                }
            }


        img = LoadContent.GetSpriteAtlas(LoadContent.LEVEL_ANIMATED_WATER);
        animatedWater = new BufferedImage[ANIMATED_WATER_SPRITE_AMOUNT];
        for(int i = 0; i < ANIMATED_WATER_SPRITE_AMOUNT; i++) {
            animatedWater[i] = img.getSubimage(i*32, 0, 32, 32);
        }
    }

    public int getPlayerX() {
        return currentLevel.getPlayerX();
    }

    public int getPlayerY() {
        return currentLevel.getPlayerY();
    }

    public int[][] getBlockIndexes() {
        return currentLevel.getBlockIndexes();
    }

    public int getMaxLevelCameraOffset() {
        return currentLevel.getMaxLevelOffsetX();
    }

}
