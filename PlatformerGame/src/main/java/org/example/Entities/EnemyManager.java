package org.example.Entities;

import java.awt.*;
import java.util.ArrayList;

public class EnemyManager {

    private ArrayList<Enemy> enemies;
    private int[][] blockIndexes;

    public EnemyManager(ArrayList<Enemy> enemies, int[][] blockIndexes) {
        this.enemies = enemies;
        this.blockIndexes = blockIndexes;

        for(Enemy e : enemies){
            e.addLevelData(blockIndexes);
        }

    }


    public void update(){
        for(Enemy e : enemies){
            e.update();
        }
    }

    public void draw(Graphics g, int xLvlOffset){
        for(Enemy e : enemies){
            e.draw(g,xLvlOffset);
        }
    }


}
