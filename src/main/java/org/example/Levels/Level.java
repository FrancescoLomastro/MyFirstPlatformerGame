package org.example.Levels;

import org.example.Entities.*;
import org.example.Exceptions.NoMoreLevelsException;
import org.example.Exceptions.PlayerSpawnPointNotFoundException;
import org.example.Props.Animated.*;
import org.example.Props.NotAnimated.Barrel;
import org.example.Props.NotAnimated.Bottle;
import org.example.Props.NotAnimated.Door;
import org.example.Props.Prop;
import org.example.Utility.LoadContent;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.example.Constants.Prop.BackPalm.*;
import static org.example.Constants.Prop.Barrels.*;
import static org.example.Constants.Prop.Bottles.*;
import static org.example.Constants.Prop.Cannon.*;
import static org.example.Constants.Prop.Water.*;
import static org.example.Constants.Prop.WaterLight.*;
import static org.example.Constants.Levels.*;
import static org.example.Constants.Window.*;

/**
 * This class is responsible for loading the level image and extracting the level data from it.
 * Level data corresponds to tiles, enemies, props and player spawn point.
 */
public class Level {
    private final BufferedImage levelImage;
    private final ArrayList<Prop> props;
    private final ArrayList<AnimatedProp> animatedAnimatedProps;
    private final ArrayList<Enemy> enemies ;
    private Point playerSpawnPoint;

    /**
     * A matrix that contains the texture indexes of the tiles in the level
     */
    private int[][] levelBlockIndexes;

    /**
     * The amount of right space in the level that the camera is not seeing at the beginning
     */
    private final int levelXOffset_max;

    
    public Level(int currentLevelIndex) throws NoMoreLevelsException{
        try {
            this.levelImage = LoadContent.GetResourceAsBufferedImage("levels/"+currentLevelIndex+".png");
        }catch (NullPointerException e)
        {
            throw new NoMoreLevelsException("Cannot find file level file "+currentLevelIndex+".png, the game has no more levels to load");
        }
        this.enemies= new ArrayList<>();
        this.animatedAnimatedProps = new ArrayList<>();
        this.props = new ArrayList<>();
        this.levelXOffset_max = TILES_SIZE * (levelImage.getWidth() - TILES_IN_WIDTH);
        extractLevelData();
    }

    /**
     * Extracts the level data from the level image
     * Textures (RED), Enemies and player spawn (GREEN), Props (BLUE) 
     */
    private void extractLevelData() {
        levelBlockIndexes = new int[levelImage.getHeight()][levelImage.getWidth()];
        for (int j = 0; j < levelImage.getHeight(); j++)
            for (int i = 0; i < levelImage.getWidth(); i++) {
                Color color = new Color(levelImage.getRGB(i, j));
                
                extractTextures(color.getRed(), j, i);
                extractEnemies(color.getGreen(), j , i);
                extractProps(color.getBlue(), j, i);
                extractPlayerSpawn(color.getGreen(), i, j);
            }
    }

    /**
     * Extracts the textures from the level image
     */
    private void extractTextures(int redIndex, int j, int i) {
        levelBlockIndexes[j][i] = redIndex;
    }

    /**
     * Extracts the enemies from the level image
     */
    private void extractEnemies(int greenIndex, int j, int i) {
        switch (greenIndex){
            case 30 -> this.enemies.add(new Crabby(i * TILES_SIZE, j * TILES_SIZE));
            case 60 -> this.enemies.add(new Shark(i * TILES_SIZE, j * TILES_SIZE));
            case 90 -> this.enemies.add(new Star(i * TILES_SIZE, j * TILES_SIZE));
        }
    }

    /**
     * Extracts the animated and not animated props from the level image
     */
    private void extractProps(int blueIndex, int j, int i) {
        switch (blueIndex){
            
            /* Not animated props goes from 0 to 19 */
            case 0 -> this.props.add(new Barrel(i * TILES_SIZE, j * TILES_SIZE, STAND_BARREL));
            case 1 -> this.props.add(new Barrel(i * TILES_SIZE, j * TILES_SIZE, SIDE_BARREL));
            case 2 -> this.props.add(new Barrel(i * TILES_SIZE, j * TILES_SIZE, DOUBLE_SIDE_BARREL));
            case 3 -> this.props.add(new Barrel(i * TILES_SIZE, j * TILES_SIZE, BARREL_POTION));
            case 4 -> this.props.add(new Bottle(i * TILES_SIZE, j * TILES_SIZE, STAND_POTION1));
            case 5 -> this.props.add(new Bottle(i * TILES_SIZE, j * TILES_SIZE, SIDE_POTION2));
            case 10 -> this.props.add(new Door(i * TILES_SIZE, j * TILES_SIZE));
            
            /* Animated props goes from 20 to 255 */
            
            /* Water indexes 20 to 39*/
            case 20 -> this.animatedAnimatedProps.add(new Water(i*TILES_SIZE, j*TILES_SIZE, SURFACE_WATER));
            case 21 -> this.animatedAnimatedProps.add(new Water(i*TILES_SIZE, j*TILES_SIZE, DEEP_WATER));
            case 25 -> {
                this.animatedAnimatedProps.add(new Water(i*TILES_SIZE, j*TILES_SIZE, SURFACE_WATER));
                this.animatedAnimatedProps.add(new WaterLight(i*TILES_SIZE, j*TILES_SIZE, WATER_LIGHT_1));
            }
            case 26 -> {
                this.animatedAnimatedProps.add(new Water(i*TILES_SIZE, j*TILES_SIZE, SURFACE_WATER));
                this.animatedAnimatedProps.add(new WaterLight(i*TILES_SIZE, j*TILES_SIZE, WATER_LIGHT_2));
            }
            
            case 40 -> this.animatedAnimatedProps.add( new BackPalm(i * TILES_SIZE,j *TILES_SIZE, BACK_PALM_1));
            case 41 -> this.animatedAnimatedProps.add( new BackPalm(i * TILES_SIZE,j *TILES_SIZE, BACK_PALM_2));
            case 42 -> this.animatedAnimatedProps.add( new BackPalm(i * TILES_SIZE,j *TILES_SIZE, BACK_PALM_3));
            case 45 -> this.animatedAnimatedProps.add( new Candle(i * TILES_SIZE,j *TILES_SIZE));
            case 50 -> this.animatedAnimatedProps.add( new Sword(i * TILES_SIZE,j *TILES_SIZE));
            case 55 -> this.animatedAnimatedProps.add( new Cannon(i * TILES_SIZE,j *TILES_SIZE, CANNON_RIGHT));
            case 60 -> this.animatedAnimatedProps.add( new Cannon(i * TILES_SIZE,j *TILES_SIZE, CANNON_LEFT));
            case 65 -> this.animatedAnimatedProps.add( new Potion(i * TILES_SIZE,j *TILES_SIZE));
        }
    }

    /**
     * Extracts the player spawn point from the level image
     */
    private void extractPlayerSpawn(int greenIndex, int i, int j) {
        if(playerSpawnPoint == null)
        {
            switch (greenIndex) {
                case 0 -> playerSpawnPoint = new Point(i * TILES_SIZE, j * TILES_SIZE);
            }
        }
    }

    /**
     * Getter that returns the arrayList of Enemies
     */
    public ArrayList<Enemy> getEnemies() {
        for(Enemy e: enemies){
            e.addLevelData(levelBlockIndexes);
        }
        return enemies;
    }

    /**
     * Getter that returns the arrayList of animated Props
     */
    public ArrayList<AnimatedProp> getAnimatedProps() {
        for(AnimatedProp p: animatedAnimatedProps){
            p.addLevelData(levelBlockIndexes);
        }
        return animatedAnimatedProps;
    }

    /**
     * Getter that returns the arrayList of not animated Props
     */
    public ArrayList<Prop> getUnAnimatedProps() {
        return props;
    }

    /**
     * Getter that returns the texture index at position (column, row) in the game window 
     * @param column column index
     * @param raw raw index
     */
    public int getTextureIndex(int column, int raw) {
        return levelBlockIndexes[raw][column];
    }
    
    /*
     *   Getter that returns the whole texture matrix for the level
     */
    public int[][] getTextureMatrix() {
        return levelBlockIndexes;
    }

    /**
     * Getter that returns the X coordinate of player spawn 
     */
    public int getPlayerX() {
        if(playerSpawnPoint == null)
            throw new PlayerSpawnPointNotFoundException("Player spawn point not found in the level, check that the level has a player spawn point");
        return playerSpawnPoint.x;
    }

    /**
     * Getter that returns the Y coordinate of player spawn 
     */
    public int getPlayerY() {
        if(playerSpawnPoint == null)
            throw new PlayerSpawnPointNotFoundException("Player spawn point not found in the level, check that the level has a player spawn point");
        return playerSpawnPoint.y;
    }

    /**
     * Getter that returns the width of the level in tiles
     */
    public int getLevelTileWidth(){
        return levelBlockIndexes[0].length;
    }

    /**
     * Getter that returns the level max offset in the X axis
     */
    public int getLevelXOffset_max() {
        return levelXOffset_max;
    }


    /*
    -------------------------------
      Static utility methods 
    -------------------------------  
     */


    /**
     * Checks if the float position (x,y) is solid (Not traversable)
     * @param x X coordinate for the position
     * @param y Y coordinate for the position
     * @param levelTexture the current level texture matrix
     * @return true if the position is solid or a map boundary, false otherwise
     */
    public static boolean IsPositionSolid(float x, float y, int[][] levelTexture){
        int levelMaxWidth = levelTexture[0].length * TILES_SIZE;
        int levelMaxHeight = GAME_HEIGHT;

        // Map boundaries are always considered solid
        if(x<0 || x >= levelMaxWidth) return true;
        if(y<0 || y >= levelMaxHeight) return true;

        int tileX = (int) (x / TILES_SIZE);
        int tileY = (int) (y / TILES_SIZE);

        return IsTileSolid(tileX, tileY, levelTexture);
    }

    /**
     * Checks if the tile at integer position (tileX, tileY) is solid (Not traversable)
     * @param tileX X coordinate for the tile
     * @param tileY Y coordinate for the tile
     * @param levelTexture the current level texture matrix
     * @return true if the tile is solid, false otherwise
     */
    public static boolean IsTileSolid(int tileX, int tileY, int[][] levelTexture) {
        int value = levelTexture[tileY][tileX];
        
        /* All texture indexes that goes from INTERNAL_TEXTURE_INIT to INTERNAL_TEXTURE_END are not solid as they 
        are used as background*/
        if (value >= INTERNAL_TEXTURE_INIT && value <= INTERNAL_TEXTURE_END){
            return false;
        }
        
        /* SPRITE_HOLE corresponds to an invisible texture*/  
        return value != SPRITE_HOLE;
    }

    /**
     * Checks if an entity with a certain width and height can be placed (moved) into a certain float position (x,y).
     * The check is done by looking at the 4 corners of the entity hit box.
     * @param x X coordinate for the position
     * @param y Y coordinate for the position
     * @param levelTexture the current level texture matrix
     * @return true the entity can move in the position, otherwise false
     */
    public static boolean CanMoveInPosition(float x, float y, float width, float height, int[][] levelTexture) {
        if (!IsPositionSolid(x, y, levelTexture))
            if (!IsPositionSolid(x + width, y + height, levelTexture))
                if (!IsPositionSolid(x + width, y, levelTexture))
                    if (!IsPositionSolid(x, y + height, levelTexture))
                        return true;
        return false;
    }

    /**
     * Checks if the hit box of an entity that moves horizontally with a xSpeed is on the floor (a solid block)
     * @param hitbox the entity hit box
     * @param xSpeed the horizontal speed
     * @param levelTexture the level texture matrix
     * @return true if the entity is moving on the floor, otherwise false
     */
    public static boolean IsOnFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelTexture) {
        if(xSpeed > 0)
            return IsPositionSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y+hitbox.height+1, levelTexture);
        return IsPositionSolid(hitbox.x + xSpeed, hitbox.y+hitbox.height+1, levelTexture);
    }


    /**
     * Checks if the first hit box can walk through the second hit box horizontally in a certain Y coordinate
     * @param levelTexture the level texture matrix
     * @param firstHitbox the entity hit box
     * @param secondHitbox the vertical speed
     * @param tileY the Y coordinate to consider
     * @return true if the first hit box can walk through the second one in a walkable path
     */
    public static boolean IsPathWalkable(int[][] levelTexture, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int tileY) {
        int firstXTile = (int) (firstHitbox.x / TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / TILES_SIZE);

        if(firstXTile > secondXTile) {
            return areAllTilesWalkable(secondXTile,firstXTile,tileY,levelTexture);
        }else{
            return areAllTilesWalkable(firstXTile,secondXTile,tileY,levelTexture);
        }
    }


    /**
     * Checks if from a starting tile X coordinate to an ending tile X coordinate, in a certain vertical tile Y coordinate
     * all the tiles are walkable or not.
     * To meet this requirement the tiles must be clear and each tile must have a solid tile below it
     * @param tileX_start X coordinate of the starting tile
     * @param tileX_end X coordinate of the ending tile
     * @param tileY Y coordinate of the tiles
     * @param levelTexture the level texture matrix
     * @return true if all tiles from tileX_start to tileX_end are walkable, otherwise false.
     */
    public static boolean areAllTilesWalkable(int tileX_start, int tileX_end, int tileY, int[][] levelTexture) {
        if(areAllTilesClear(tileX_start, tileX_end, tileY, levelTexture)){
            for (int i = 0; i < tileX_end - tileX_start; i++) {
                if (!IsTileSolid(tileX_start + i, tileY + 1, levelTexture)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if from a starting tile X coordinate to an ending tile X coordinate, in a certain vertical tile Y coordinate
     * all the tiles are solid or not.
     * @param tileX_start X coordinate of the starting tile
     * @param tileX_end X coordinate of the ending tile
     * @param tileY Y coordinate of the tiles
     * @param levelTexture the level texture matrix
     * @return true if all tiles from tileX_start to tileX_end are not solid, otherwise false.
     */
    public static boolean areAllTilesClear(int tileX_start, int tileX_end, int tileY, int[][] levelTexture) {
        for(int i = 0; i<tileX_end - tileX_start; i++) {
            if(IsTileSolid(tileX_start + i, tileY, levelTexture)) {
                return false;
            }
        }
        return true;
    }




    

}
