package org.example.Levels;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Constants.Window.*;

public class LevelManager {
    private BufferedImage[] textures;
    private Level currentLevel;
    private int currentLevelIndex;

    public LevelManager() {
        this.currentLevelIndex = 0;
        loadTextures();
        loadNextLevel(currentLevelIndex);
    }

    private void loadNextLevel(int currentLevelIndex) {
        currentLevel = new Level(currentLevelIndex);
    }

    public void draw(Graphics g, int xLvlOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++)
            for (int i = 0; i < TILES_IN_WIDTH; i++) {
                int index = currentLevel.getBlockIndex(i, j);
                g.drawImage(textures[index], TILES_SIZE * i - xLvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
    }

    public void update() {

    }


    private void loadTextures() {
        BufferedImage img = LoadContent.GetSpriteAtlas(LoadContent.LEVEL_ATLAS);
        textures = new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                textures[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    public int getPlayerX() {
        return currentLevel.getPlayerX();
    }

    public int getPlayerY() {
        return currentLevel.getPlayerY();
    }
}
