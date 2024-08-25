package org.example.Entities;

import org.example.GameScenes.PlayScene;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * This class manages all the enemies in the game
 */
public class EnemyManager {

    private ArrayList<Enemy> enemies;

    public EnemyManager(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    /**
     * Updates all the enemies in the game and checks if the level is completed
     */
    public void update(){
        boolean isAnyActive = false;
        for(Enemy e : enemies){
            if(e.isActive()) {
                isAnyActive = true;
                e.update();
            }
        }

        if(!isAnyActive){
            PlayScene.getInstance().setLevelCompleted();
        }
    }

    /**
     * Draws all the enemies in the game if they are still active
     */
    public void draw(Graphics g, int xLvlOffset){
        for(Enemy e : enemies){
            if (e.isActive()) {
                e.draw(g, xLvlOffset);
            }
        }
    }


    /**
     * This method checks if the player has attacked an enemy
     * @param attackBox the attack box of the player
     * @param damage the damage the player deals
     */
    public void checkEnemyAttacked(Rectangle2D.Float attackBox, int damage) {
        for(Enemy e : enemies){
            if(e.getCurrentHealth() > 0) {
                if (e.isActive()) {
                    if (attackBox.intersects(e.getHitbox())) {
                        e.alterHealth(damage);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Forwards the reset command to all the enemies
     */
    public void reset() {
        for(Enemy e : enemies){
            e.reset();
        }
    }
}
