package org.example.Levels;

import org.example.Audio.AudioManager;
import org.example.Entities.EnemyManager;
import org.example.Exceptions.NoMoreLevelsException;
import org.example.GameScenes.Scene;
import org.example.Props.PropManager;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static org.example.Constants.Prop.BigCloud.*;
import static org.example.Constants.Window.*;

/**
 * This class is responsible for managing the levels in the game.
 * It loads the levels, draws them, updates them and checks for collisions.
 * It also manages the enemies and props in the levels, loads the background images and the textures for the levels.
 */
public class LevelManager {
    private BufferedImage[] textures;

    private Level currentLevel;
    private int currentLevelIndex;

    /* Background animation variables */
    private BufferedImage bigCloud, smallCloud;
    private BufferedImage deepBackground;
    protected int animationTick;
    protected int animationFrame;

    private EnemyManager enemyManager;
    private PropManager propManager;

    public LevelManager() {
        this.currentLevelIndex = -1;
        this.animationFrame = 0;
        this.animationTick = 0;
        loadImages();
        loadNextLevel();
    }


    /**
     * Loads the images for the textures and the background.
     */
    private void loadImages() {
        BufferedImage img = LoadContent.GetResourceAsBufferedImage(LoadContent.TILES_TEXTURES);
        textures = new BufferedImage[256];
        for (int row = 0; row < 21; row++) {
            for (int columns = 0; columns < 12; columns++) {
                int index = row * 12 + columns;
                textures[index] = img.getSubimage(columns * 32, row * 32, 32, 32);
            }
        }

        deepBackground = LoadContent.GetResourceAsBufferedImage(LoadContent.PLAYSCENE_DEEP_BACKGROUND);
        bigCloud = LoadContent.GetResourceAsBufferedImage(LoadContent.BIG_CLOUD_1);
        smallCloud = LoadContent.GetResourceAsBufferedImage(LoadContent.SMALL_CLOUD);
    }

    /**
     * Loads the next level in the game and also the Enemy and Prop managers for that level.
     * If there are no more levels, the game is completed and the current scene will be the MENU.
     */
    public void loadNextLevel() {
        currentLevelIndex++;
        try {
            currentLevel = new Level(currentLevelIndex);
        } catch (NoMoreLevelsException e) {
            currentLevelIndex = 0;
            System.out.println(e.getMessage());
            Scene.changeScene(Scene.MENU);
            try {
                currentLevel = new Level(currentLevelIndex);
            } catch (NoMoreLevelsException ex) {
                throw new RuntimeException("No levels found");
            }
        }

        enemyManager = new EnemyManager(currentLevel.getEnemies());
        propManager = new PropManager(currentLevel.getAnimatedProps(), currentLevel.getUnAnimatedProps());
    }

    /**
     * Draws the textures, the background, the props and the enemies.
     * */
    public void draw(Graphics g, int xLvlOffset) {
        drawBackground(g,xLvlOffset);
        drawTextures(g, xLvlOffset);
        propManager.draw(g, xLvlOffset);
        enemyManager.draw(g,xLvlOffset);
    }

    /**
     * Draws each tile texture
     */
    private void drawTextures(Graphics g, int xLvlOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++)
            for (int i = 0; i < currentLevel.getLevelTileWidth(); i++) {
                int index = currentLevel.getTextureIndex(i, j);
                g.drawImage(textures[index], TILES_SIZE * i - xLvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
    }

    /**
     * Draws the background images, like clouds and deep background
     *
     */
    private void drawBackground(Graphics g, int xLvlOffset) {
        g.drawImage(deepBackground, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        drawBigClouds(g,xLvlOffset);
        drawSmallClouds(g,xLvlOffset);
    }

    /*
    * Draws the big clouds in the background to fill the level horizontally
     */
    private void drawBigClouds(Graphics g, int xLvlOffset) {
        int sizeDrawn = 0;
        int sizeDrawnMax = currentLevel.getLevelTileWidth() * TILES_SIZE;
        do{
            g.drawImage(bigCloud, sizeDrawn - (int)(xLvlOffset * 0.3), (int)(204* SCALE), BIG_CLOUD_WIDTH[0], BIG_CLOUD_HEIGHT[1], null);
            sizeDrawn += BIG_CLOUD_WIDTH[0];
        }while(sizeDrawn < sizeDrawnMax);
    }

    /*
     * Draws the small clouds in the background to fill the level horizontally
     */
    private void drawSmallClouds(Graphics g, int xLvlOffset) {
        int sizeDrawn = 0;
        int sizeDrawnMax = currentLevel.getLevelTileWidth() * TILES_SIZE;
        do{
            g.drawImage(smallCloud, sizeDrawn - (int)(xLvlOffset * 0.7), (int)(110* SCALE), SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
            sizeDrawn += SMALL_CLOUD_WIDTH * 5;
        }while(sizeDrawn < sizeDrawnMax);
    }

    /**
     * Updates the level forwarding the props and the enemies.
     */
    public void update() {
        propManager.update();
        enemyManager.update();
    }

    /**
     * Forwards the reset command to the Enemy and Prop managers
     */
    public void reset() {
        enemyManager.reset();
        propManager.reset();
    }

    /**
     * Check if the attack box of the player collides with an enemy hit box
     *
     * @param attackBox player attack box
     * @param damage
     */
    public void checkEnemyAttacked(Rectangle2D.Float attackBox, int damage) {
        enemyManager.checkEnemyAttacked(attackBox, damage);
    }

    /**
     * Getter for the player X coordinate
     */
    public int getPlayerX() {
        return currentLevel.getPlayerX();
    }

    /**
     * Getter for the player Y coordinate
     */
    public int getPlayerY() {
        return currentLevel.getPlayerY();
    }

    /*
     *   Getter that returns the whole texture matrix for the level
     */
    public int[][] getTextureIndex() {
        return currentLevel.getTextureMatrix();
    }

    /**
     * Getter that returns the level max offset in the X axis
     */
    public int getMaxLevelCameraOffset() {
        return currentLevel.getLevelXOffset_max();
    }

    /**
     * Getter that returns true if the sword has been picked by the player
     */
    public boolean isSwordPicked(Rectangle2D.Float hitbox) {
        return propManager.isSwordPicked(hitbox);
    }

    /**
     * This method is used to restart game levels from the beginning
     */
    public void restart() {
        currentLevelIndex = -1;
    }
}
