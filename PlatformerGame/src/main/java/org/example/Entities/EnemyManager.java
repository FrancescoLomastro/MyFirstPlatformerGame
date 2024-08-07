package org.example.Entities;

import java.awt.*;
import java.util.ArrayList;

public class EnemyManager {

    private ArrayList<Enemy> enemies;

    public EnemyManager(ArrayList<Enemy> enemies) {
        this.enemies = enemies;



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
