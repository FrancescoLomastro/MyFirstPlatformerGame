package org.example.Levels;

import org.example.Entities.*;
import org.example.Props.Cannon;
import org.example.Props.Potion;
import org.example.Props.Prop;
import org.example.Props.Sword;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.example.Constants.Prop.Cannon.CANNON_LEFT;
import static org.example.Constants.Prop.Cannon.CANNON_RIGHT;
import static org.example.Constants.Prop.Potion.POTION;
import static org.example.Constants.Prop.Sword.SWORD;
import static org.example.Constants.Sprites.Level.*;
import static org.example.Constants.Window.*;

public class Level {
    private BufferedImage levelImage;
    private int index;
    private Point playerSpawnPoint;

    private int[][] levelBlockIndexes;

    private int maxLevelOffsetX; // Amount of right space in the level that the camera is not seeing at the beginning

    private ArrayList<Prop> props;
    private ArrayList<Enemy> enemies ;





    public Level(int currentLevelIndex) {
        this.index = currentLevelIndex;
        this.levelImage = LoadContent.GetSpriteAtlas("levels/"+index+".png");
        this.enemies= new ArrayList<>();
        this.props= new ArrayList<>();
        extractLevelData();
        calculateMaxLevelOffsetX();
    }

    public static boolean IsSightClear(int[][] levelBlockIndexes, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int tileY) {
        int firstXTile = (int) (firstHitbox.x / TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / TILES_SIZE);

        if(firstXTile > secondXTile) {
            return IsAllTilesWalkable(secondXTile,firstXTile,tileY,levelBlockIndexes);
        }else{
            return IsAllTilesWalkable(firstXTile,secondXTile,tileY,levelBlockIndexes);
        }
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        if(IsAllTilesClear(xStart, xEnd, y, lvlData)){
            for (int i = 0; i < xEnd - xStart; i++) {
                if (!IsTileSolid(xStart + i, y + 1, lvlData)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
        for(int i = 0; i<xEnd - xStart; i++) {
            if(IsTileSolid(xStart + i, y, lvlData)) {
                return false;
            }
        }
        return true;
    }


    private void extractLevelData() {
        levelBlockIndexes = new int[levelImage.getHeight()][levelImage.getWidth()];
        for (int j = 0; j < levelImage.getHeight(); j++)
            for (int i = 0; i < levelImage.getWidth(); i++) {
                Color color = new Color(levelImage.getRGB(i, j));
                extractEnvironment(color, j ,i);
                extractEnemies(color, j , i);
                extractObjects(color, j, i);
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

    private void extractEnvironment(Color color, int j, int i) {
        int value = color.getRed();
        if (value >= 51) {
            value = 0;
        }

        levelBlockIndexes[j][i] = value;
    }

    private void extractObjects(Color color, int j, int i) {
        int value = color.getBlue();
        switch (value){
            case 0 -> this.props.add( new Sword(i * TILES_SIZE,j *TILES_SIZE, SWORD));
            case 1 -> this.props.add( new Cannon(i * TILES_SIZE,j *TILES_SIZE, CANNON_RIGHT));
            case 2 -> this.props.add( new Cannon(i * TILES_SIZE,j *TILES_SIZE, CANNON_LEFT));
            case 4 -> this.props.add( new Potion(i * TILES_SIZE,j *TILES_SIZE, POTION));
        }
    }

    private void extractEnemies(Color color, int j, int i) {
        int value = color.getGreen();
        switch (value){
            case 0 -> this.enemies.add(new Crabby(i * TILES_SIZE, j * TILES_SIZE));
            case 1 -> this.enemies.add(new Shark(i * TILES_SIZE, j * TILES_SIZE));
            case 2 -> this.enemies.add(new Star(i * TILES_SIZE, j * TILES_SIZE));
        }
    }

    private void calculateMaxLevelOffsetX() {
        maxLevelOffsetX = TILES_SIZE * (levelImage.getWidth() - TILES_IN_WIDTH);
    }




    public int getBlockIndex(int x, int y) {
        return levelBlockIndexes[y][x];
    }

    public int[][] getBlockIndexes() {
        return levelBlockIndexes;
    }

    public int getPlayerX() {
        return playerSpawnPoint.x;
    }

    public int getPlayerY() {
        return playerSpawnPoint.y;
    }

    public int getLevelTileWidth(){
        return levelBlockIndexes[0].length;
    }

    public int getMaxLevelOffsetX() {
        return maxLevelOffsetX;
    }

    public static boolean IsPositionSolid(float x, float y, int[][] lvlData){
        int levelMaxWidth = lvlData[0].length * TILES_SIZE;
        int levelMaxHeight = GAME_HEIGHT;

        // Gli estremi della mappa sono sempre considerati solidi
        if(x<0 || x >= levelMaxWidth) return true;
        if(y<0 || y >= levelMaxHeight) return true;

        int tileX = (int) (x / TILES_SIZE);
        int tileY = (int) (y / TILES_SIZE);

        return IsTileSolid(tileX, tileY, lvlData);
    }

    public static boolean IsTileSolid(int tileX, int tileY, int[][] lvlData) {
        int value = lvlData[tileY][tileX];
        return value != SPRITE_HOLE && value != WATER;
    }

    public static boolean CanMoveInPosition(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsPositionSolid(x, y, lvlData))
            if (!IsPositionSolid(x + width, y + height, lvlData))
                if (!IsPositionSolid(x + width, y, lvlData))
                    if (!IsPositionSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    public static boolean IsOnFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if(xSpeed > 0)
            return IsPositionSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y+hitbox.height+1, lvlData);
        return IsPositionSolid(hitbox.x + xSpeed, hitbox.y+hitbox.height+1, lvlData);
    }




    public ArrayList<Enemy> getEnemies() {
        for(Enemy e: enemies){
            e.addLevelData(levelBlockIndexes);
        }
        return enemies;
    }

    public ArrayList<Prop> getProps() {
        for(Prop p: props){
            p.addLevelData(levelBlockIndexes);
        }
        return props;
    }

}
