package org.example.Levels;

import org.example.Entities.EnemyManager;
import org.example.Entities.Player;
import org.example.GameScenes.Scene;
import org.example.Props.PropManager;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import static org.example.Constants.Prop.BigCloud.*;
import static org.example.Constants.Sprites.Level.WATER_ANIMATION_SPEED;
import static org.example.Constants.Sprites.Level.ANIMATED_WATER_SPRITE_AMOUNT;
import static org.example.Constants.Window.*;

public class LevelManager {

    private BufferedImage bigCloud, smallCloud;
    private BufferedImage deepBackground;
    private BufferedImage[] textures;
    private BufferedImage[] animatedWater;
    private Level currentLevel;
    private int currentLevelIndex;
    private int maxLevelIndex;

    //Animation Variables
    protected int animationTick;
    protected int animationFrame;

    private EnemyManager enemyManager;
    private PropManager propManager;

    public LevelManager() {
        this.currentLevelIndex = -1;
        this.maxLevelIndex = LoadContent.GetNumberOfFilesInFolder("levels");
        this.animationFrame = 0;
        this.animationTick = 0;
        loadTextures();
        loadNextLevel();
        this.enemyManager = new EnemyManager(currentLevel.getEnemies());
        this.propManager = new PropManager(currentLevel.getProps(), currentLevel.getUnAnimatedProps());
    }



    public void draw(Graphics g, int xLvlOffset) {
        drawBackground(g,xLvlOffset);
        drawBlocks(g, xLvlOffset);
        propManager.draw(g, xLvlOffset);
        enemyManager.draw(g,xLvlOffset);
    }




    private void drawBlocks(Graphics g, int xLvlOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++)
            for (int i = 0; i < currentLevel.getLevelTileWidth(); i++) {
                int index = currentLevel.getBlockIndex(i, j);
                if(index == 50) //Animated Water
                    g.drawImage(animatedWater[animationFrame], TILES_SIZE * i - xLvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
                else
                    g.drawImage(textures[index], TILES_SIZE * i - xLvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
                //g.setColor(Color.BLUE);
                //g.drawRect(TILES_SIZE * i - xLvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE);
            }
    }

    private void drawBackground(Graphics g, int xLvlOffset) {
        g.drawImage(deepBackground, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        drawBigClouds(g,xLvlOffset);
        drawSmallClouds(g,xLvlOffset);
    }

    private void drawBigClouds(Graphics g, int xLvlOffset) {
        int sizeDrawn = 0;
        int sizeDrawnMax = currentLevel.getLevelTileWidth() * TILES_SIZE;
        do{
            g.drawImage(bigCloud, sizeDrawn - (int)(xLvlOffset * 0.3), (int)(204* SCALE), BIG_CLOUD_WIDTH[0], BIG_CLOUD_HEIGHT[1], null);
            sizeDrawn += BIG_CLOUD_WIDTH[0];
        }while(sizeDrawn < sizeDrawnMax);
    }

    private void drawSmallClouds(Graphics g, int xLvlOffset) {
        int sizeDrawn = 0;
        int sizeDrawnMax = currentLevel.getLevelTileWidth() * TILES_SIZE;
        do{
            g.drawImage(smallCloud, sizeDrawn - (int)(xLvlOffset * 0.7), (int)(110* SCALE), SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
            sizeDrawn += SMALL_CLOUD_WIDTH * 5;
        }while(sizeDrawn < sizeDrawnMax);
    }

    public void update() {
        updateAnimationTick();
        propManager.update();
        enemyManager.update();
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
        textures = new BufferedImage[256];

            for (int row = 0; row < 21; row++) {
                for (int columns = 0; columns < 12; columns++) {
                    int index = row * 12 + columns;
                    if(index > 255)
                        break;
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

        deepBackground = LoadContent.GetSpriteAtlas(LoadContent.PLAYSCENE_DEEP_BACKGROUND);
        bigCloud = LoadContent.GetSpriteAtlas(LoadContent.BIG_CLOUD_1);
        smallCloud = LoadContent.GetSpriteAtlas(LoadContent.SMALL_CLOUD);
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

    public boolean isSwordPicked(Player player) {
        return propManager.isSwordPicked(player);
    }

    public void checkEnemyAttacked(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyAttacked(attackBox);
    }

    public void reset() {
        enemyManager.reset();
        propManager.reset();
    }

    public void loadNextLevel() {
        currentLevelIndex++;
        if(currentLevelIndex >= maxLevelIndex) {
            currentLevelIndex = 0;
            System.out.println("No More Levels, Game Completed");
            Scene.CurrentScene = Scene.MENU;
        }

        currentLevel = new Level(currentLevelIndex);
        enemyManager = new EnemyManager(currentLevel.getEnemies());
        propManager = new PropManager(currentLevel.getProps(), currentLevel.getUnAnimatedProps());
    }
}
