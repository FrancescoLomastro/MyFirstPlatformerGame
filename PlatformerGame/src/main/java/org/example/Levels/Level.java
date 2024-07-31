package org.example.Levels;

import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.image.BufferedImage;
import static org.example.Constants.Window.*;

public class Level {
    private BufferedImage levelImage;
    private int index;
    private Point playerSpawnPoint;

    private int[][] levelBlockIndexes;

    public Level(int currentLevelIndex) {
        this.index = currentLevelIndex;
        this.levelImage = LoadContent.GetSpriteAtlas("levels/"+index+".png");
        extractLevelData();
    }



    private void extractLevelData() {
        levelBlockIndexes = new int[levelImage.getHeight()][levelImage.getWidth()];
        for (int j = 0; j < levelImage.getHeight(); j++)
            for (int i = 0; i < levelImage.getWidth(); i++) {
                Color color = new Color(levelImage.getRGB(i, j));
                levelBlockIndexes[j][i] = extractEnvironment(color);
                extractEnemies(color);
                extractObjects(color);
                extractPlayerSpawn(color, i, j);
            }
    }

    private void extractPlayerSpawn(Color color, int i, int j) {
        if(playerSpawnPoint == null)
        {
            int value = color.getGreen();
            if (value == 100)
                playerSpawnPoint= new Point(i*TILES_SIZE, j*TILES_SIZE);
        }
    }

    private int extractEnvironment(Color color) {
        int value = color.getRed();
        if (value >= 48)
            value = 0;
        return value;
    }

    private Object extractObjects(Color color) {
        return null;
    }

    private Object extractEnemies(Color color) {
        return null;
    }






    public int getBlockIndex(int x, int y) {
        return levelBlockIndexes[y][x];
    }

    public int getPlayerX() {
        return playerSpawnPoint.x;
    }

    public int getPlayerY() {
        return playerSpawnPoint.y;
    }
}
